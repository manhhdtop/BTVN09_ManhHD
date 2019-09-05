package vn.topica.itlab4.ex9.ex2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client
{
	private static TcpPacket packetIn;
	private static TcpPacket packetOut;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception
	{
		try (Socket socket = new Socket(Constant.HOST, Constant.PORT))
		{
			System.out.println("Server is open.");
			Scanner scanner = new Scanner(System.in);
			DataInputStream in;
			DataOutputStream out;
			
			boolean run = true;
			String phoneNumber = "";
			String key = "";
			String name = "";
			ArrayList<TLV> tlvs;
			int choose = -1;
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			while (run)
			{
				boolean validateInput = false;
				while (!validateInput)
				{
					System.out.println("Choose action:");
					System.out.println("1: Authen");
					System.out.println("2: Insert");
					System.out.println("3: Commit");
					System.out.println("4: Select");
					System.out.println("0: Exit");
					System.out.print("Choose: ");
					try
					{
						choose = scanner.nextInt();
						if (choose >= 0 && choose <= 4)
						{
							validateInput = true;
						}
					}
					catch (Exception e)
					{
					}
					if (!validateInput)
					{
						System.out.println("Your choose not valid. Please choose again!");
					}
				}
				switch (choose)
				{
					case 1:
						System.out.println("--------------------");
						System.out.println("Init...");
						System.out.print("Enter phone number: ");
						phoneNumber = scanner.nextLine();
						System.out.print("Enter key: ");
						key = scanner.nextLine();
						
						tlvs = new ArrayList<>();
						tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
						tlvs.add(new TLV(Constant.KEY, key));
						packetOut = new TcpPacket(Constant.AUTHEN, tlvs);
						out.write(packetOut.getByte());
						out.write(-1);
						
						packetIn = PacketUtils.readByte(in);
						packetIn.printPacket();
						
						break;
					case 2:
						System.out.println("--------------------");
						System.out.println("Insert...");
						System.out.print("Enter your name: ");
						name = scanner.nextLine();
						
						tlvs = new ArrayList<>();
						tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
						tlvs.add(new TLV(Constant.NAME, name));
						packetOut = new TcpPacket(Constant.AUTHEN, tlvs);
						out.write(packetOut.getByte());
						out.write(-1);
						
						packetIn = PacketUtils.readByte(in);
						packetIn.printPacket();
						
						break;
					case 3:
						System.out.println("--------------------");
						System.out.println("Commit...");
						tlvs = new ArrayList<>();
						tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
						tlvs.add(new TLV(Constant.NAME, name));
						packetOut = new TcpPacket(Constant.AUTHEN, tlvs);
						out.write(packetOut.getByte());
						out.write(-1);
						
						packetIn = PacketUtils.readByte(in);
						packetIn.printPacket();
						
						break;
					case 4:
						System.out.println("--------------------");
						System.out.println("Select...");
						tlvs = new ArrayList<>();
						tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
						tlvs.add(new TLV(Constant.NAME, name));
						packetOut = new TcpPacket(Constant.AUTHEN, tlvs);
						out.write(packetOut.getByte());
						out.write(-1);
						
						packetIn = PacketUtils.readByte(in);
						packetIn.printPacket();
						
						for (TLV tlv : packetIn.getTlvs())
						{
							if (tlv.getTag() == Constant.RESULT_CODE
							        && tlv.getValue().equalsIgnoreCase(Constant.OK_CODE))
							{
								run = false;
							}
						}
						break;
					case 0:
						run = false;
						break;
				}
			}
			in.close();
			out.close();
			socket.close();
		}
		catch (ConnectException e)
		{
			System.out.println("Cannot connect to server.");
		}
		catch (SocketException e)
		{
			System.out.println("Server closed.");
		}
	}
	
}