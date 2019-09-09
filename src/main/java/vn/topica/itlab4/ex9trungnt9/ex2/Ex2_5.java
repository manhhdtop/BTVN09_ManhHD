package vn.topica.itlab4.ex9trungnt9.ex2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Ex2_5
{
	public static void main(String[] args) throws Exception
	{
		DataInputStream in = null;
		DataOutputStream out = null;
		Socket socket = null;
		try
		{
			socket = new Socket(Constant.HOST, Constant.PORT);
			System.out.println("Connected to server.");
			
			String phoneNumber = "0987654321";
			String key = "topica";
			String name = "ManhHD";
			ArrayList<TLV> tlvs;
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("--------------------");
			System.out.println("AUTHEN PhoneNumber " + phoneNumber + " Key " + key);
			tlvs = new ArrayList<>();
			tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
			tlvs.add(new TLV(Constant.KEY, key));
			TcpPacket packetOut = new TcpPacket(Constant.AUTHEN, tlvs);
			out.write(packetOut.getByte());
			System.out.println("--------------------");
			System.out.println("Server response...");
			TcpPacket packetIn = PacketUtils.readByte(in);
			packetIn.printPacket();
			
			System.out.println("--------------------");
			System.out.println("INSERT PhoneNumber 0987654321 Name " + name);
			tlvs = new ArrayList<>();
			tlvs.add(new TLV(Constant.PHONE_NUMBER, phoneNumber));
			tlvs.add(new TLV(Constant.NAME, name));
			packetOut = new TcpPacket(Constant.INSERT, tlvs);
			out.write(packetOut.getByte());
			System.out.println("--------------------");
			System.out.println("Server response...");
			packetIn = PacketUtils.readByte(in);
			packetIn.printPacket();
			
			System.out.println("--------------------");
			System.out.println("COMMIT PhoneNumber " + phoneNumber);
			packetOut = new TcpPacket(Constant.COMMIT, new TLV(Constant.PHONE_NUMBER, phoneNumber));
			out.write(packetOut.getByte());
			System.out.println("--------------------");
			System.out.println("Server response...");
			packetIn = PacketUtils.readByte(in);
			packetIn.printPacket();
			
			System.out.println("--------------------");
			System.out.println("SELECT PhoneNumber " + phoneNumber);
			packetOut = new TcpPacket(Constant.SELECT, new TLV(Constant.PHONE_NUMBER, phoneNumber));
			out.write(packetOut.getByte());
			System.out.println("--------------------");
			System.out.println("Server response...");
			packetIn = PacketUtils.readByte(in);
			packetIn.printPacket();
		}
		catch (ConnectException e)
		{
			System.out.println("Cannot connect to server.");
		}
		catch (SocketException e)
		{
			System.out.println("Server closed.");
		}
		finally
		{
			if (in != null)
			{
				in.close();
			}
			if (out != null)
			{
				out.close();
			}
			if (socket != null)
			{
				socket.close();
			}
		}
	}
	
}