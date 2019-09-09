package vn.topica.itlab4.ex9trungnt9.ex2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
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
	
	/**
	 * Class Capitalizer is a thread to process a Client
	 */
	private static class Capitalizer implements Runnable
	{
		private Socket socket;
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
				
				// State of this Client
				State state = State.INIT;
				boolean run = true;
				
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				while (run)
				{
					TcpPacket packetIn = PacketUtils.readByte(in);
					packetIn.printPacket();
					
					TcpPacket packetOut;
					switch (packetIn.getCmdCode())
					{
						case Constant.AUTHEN:
							for (TLV tlv : packetIn.getTlvs())
							{
								if (tlv.getTag() == Constant.PHONE_NUMBER)
								{
									phoneNumber = tlv.getValue();
								}
								if (tlv.getTag() == Constant.KEY)
								{
									key = tlv.getValue();
								}
							}
							if (phoneNumber.matches(phoneRegex))
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER,
										phoneNumber));
								if (key.equalsIgnoreCase("topica"))
								{
									state = State.READY;
									tlvs.add(new TLV(Constant.RESULT_CODE,
											Constant.OK_CODE));
								}
								else
								{
									tlvs.add(new TLV(Constant.RESULT_CODE,
											Constant.NOT_OK_CODE));
								}
								packetOut = new TcpPacket(Constant.AUTHEN,
										tlvs);
							}
							else
							{
								packetOut = new TcpPacket(Constant.ERROR,
										new TLV(Constant.RESULT_CODE, Constant.NA_CODE));
							}
							break;
						case Constant.INSERT:
							if (state == State.READY)
							{
								for (TLV tlv : packetIn.getTlvs())
								{
									if (tlv.getTag() == Constant.NAME)
									{
										name = tlv.getValue();
									}
								}
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
						case Constant.COMMIT:
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
						case Constant.SELECT:
							if (state == State.SELECT)
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.OK_CODE));
								tlvs.add(new TLV(Constant.NAME, name));
								packetOut = new TcpPacket(Constant.SELECT, tlvs);
								run = false;
							}
							else
							{
								tlvs = new ArrayList<>();
								tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
								tlvs.add(new TLV(Constant.RESULT_CODE, Constant.NOT_OK_CODE));
								packetOut = new TcpPacket(Constant.SELECT, tlvs);
							}
							break;
						default:
							packetOut = new TcpPacket(Constant.ERROR, new TLV(Constant.RESULT_CODE, Constant.NA_CODE));
							break;
					}
					out.write(packetOut.getByte());
				}
			}
			catch (SocketException e)
			{
				System.out.println("Socket closed: " + socket);
			}
			catch (IOException e)
			{
				System.out.println("Error: " + socket);
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
			}
		}
		
	}
}