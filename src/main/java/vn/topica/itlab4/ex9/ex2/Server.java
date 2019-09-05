package vn.topica.itlab4.ex9.ex2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A server program which accepts requests from clients to capitalize strings.
 * When
 * a client connects, a new thread is started to handle it. Receiving client
 * data,
 * capitalizing it, and sending the response back is all done on the thread,
 * allowing
 * much greater throughput because more clients can be handled concurrently.
 */
public class Server
{
	
	/**
	 * Runs the server. When a client connects, the server spawns a new thread
	 * to do
	 * the servicing and immediately returns to listening. The application
	 * limits the
	 * number of threads via a thread pool (otherwise millions of clients could
	 * cause
	 * the server to run out of resources by allocating too many threads).
	 */
	public static void main(String[] args) throws Exception
	{
		try (ServerSocket server = new ServerSocket(Constant.PORT))
		{
			System.out.println("Server is running...");
			ExecutorService pool = Executors.newFixedThreadPool(20);
			while (true)
			{
				pool.execute(new Capitalizer(server.accept()));
			}
		}
	}
	
	private static class Capitalizer implements Runnable
	{
		private Socket socket;
		private TcpPacket packetIn;
		private TcpPacket packetOut;
		String phoneRegex = "098[^01][0-9]{6}";
		
		DataInputStream in;
		DataOutputStream out;
		
		Capitalizer(Socket socket)
		{
			this.socket = socket;
		}
		
		@Override
		public void run()
		{
			try
			{
				System.out.println("Connected: " + socket);
				String phoneNumber = "";
				String key = "";
				String name = "";
				ArrayList<TLV> tlvs;
				
				State state = State.INIT;
				boolean run = true;
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				while (run)
				{
					packetIn = PacketUtils.readByte(in);
					switch (packetIn.getCmdCode())
					{
						case 0:
							for (TLV tlv : packetIn.getTlvs())
							{
								if (tlv.getTag() == Constant.PHONE_NUMBER)
								{
									phoneNumber = tlv.getValue();
									System.out.println("Phone pass");
								}
								if (tlv.getTag() == Constant.KEY)
								{
									key = tlv.getValue();
								}
							}
							if (phoneNumber.matches(phoneRegex))
							{
								TLV tlv;
								if (key.equalsIgnoreCase("topica"))
								{
									state = State.READY;
									tlv = new TLV(Constant.RESULT_CODE, Constant.OK_CODE);
								}
								else
								{
									tlv = new TLV(Constant.RESULT_CODE, Constant.NOT_OK_CODE);
								}
								packetOut = new TcpPacket(Constant.AUTHEN, tlv);
							}
							else
							{
								packetOut = new TcpPacket(Constant.ERROR, new TLV(Constant.RESULT_CODE, Constant.NA_CODE));
							}
							break;
						case 1:
							if (state == State.READY)
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.OK_CODE));
								packetOut = new TcpPacket(Constant.INSERT, tlvs);
							}
							else
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.NOT_OK_CODE));
								packetOut = new TcpPacket(Constant.INSERT, tlvs);
							}
							break;
						case 2:
							if (state == State.READY)
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.OK_CODE));
								packetOut = new TcpPacket(Constant.COMMIT, tlvs);
								state = State.SELECT;
							}
							else
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.NOT_OK_CODE));
								packetOut = new TcpPacket(Constant.COMMIT, tlvs);
							}
							break;
						case 3:
							if (state == State.SELECT)
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.OK_CODE));
								tlvs.add(new TLV(Constant.NAME, name));
								packetOut = new TcpPacket(Constant.SELECT, tlvs);
							}
							else
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.NOT_OK_CODE));
								packetOut = new TcpPacket(Constant.SELECT, tlvs);
							}
							break;
					}
					out.write(packetOut.getByte());
					out.write(-1);
				}
			}
			catch (
			
			IOException e)
			{
				System.out.println("Error: " + socket);
				e.printStackTrace();
			}
			finally
			{
				try
				{
					socket.close();
					
					in.close();
					out.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				System.out.println("Closed: " + socket);
			}
		}
		
	}
}