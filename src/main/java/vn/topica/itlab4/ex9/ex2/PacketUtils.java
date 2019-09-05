package vn.topica.itlab4.ex9.ex2;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ManhHd
 *
 */
public class PacketUtils
{
	public static byte[] toByteArray4(int value)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		byte[] bytes = buffer.putInt(value).array();
		buffer.flip();
		return bytes;
	}
	
	public static byte[] toByteArray2(short value)
	{
		ByteBuffer buffer = ByteBuffer.allocate(2);
		byte[] bytes = buffer.putShort(value).array();
		buffer.flip();
		return bytes;
	}
	
	public static byte[] concatBytes(byte[]... arrays)
	{
		int totalLength = 0;
		for (int i = 0; i < arrays.length; i++)
		{
			totalLength += arrays[i].length;
		}
		
		byte[] result = new byte[totalLength];
		
		int currentIndex = 0;
		for (int i = 0; i < arrays.length; i++)
		{
			System.arraycopy(arrays[i], 0, result, currentIndex, arrays[i].length);
			currentIndex += arrays[i].length;
		}
		
		return result;
	}
	
	public static byte[] getByte(TcpPacket packet)
	{
		byte[] cmdCode = PacketUtils.toByteArray2(packet.getCmdCode());
		byte[] version = PacketUtils.toByteArray2((short) 0);
		
		byte[] bytes = new byte[0];
		for (TLV tlv : packet.getTlvs())
		{
			bytes = concatBytes(bytes, PacketUtils.toByteArray2(tlv.getTag()));
			bytes = concatBytes(bytes, PacketUtils.toByteArray2(tlv.getLength()));
			bytes = concatBytes(bytes, tlv.getValue().getBytes());
		}
		
		byte[] packet_data = PacketUtils.concatBytes(cmdCode, version, bytes);
		byte[] lengthOfMessage = PacketUtils.toByteArray4(packet_data.length + 4);
		packet_data = PacketUtils.concatBytes(lengthOfMessage, packet_data);
		return packet_data;
	}
	
	public static TcpPacket getPacket(ArrayList<Byte> bytes)
	{
		TcpPacket packet = new TcpPacket();
		int index = 0;
		int i = getInt(bytes, index, index += 4);
		packet.setLengthOfMessage(i);
		short c = getShort(bytes, index, index += 2);
		packet.setCmdCode(c);
		c = getShort(bytes, index, index += 2);
		packet.setVersion(c);
		
		ArrayList<TLV> tlvs = new ArrayList<>();
		while (index < bytes.size())
		{
			short tag = getShort(bytes, index, index += 2);
			short length = getShort(bytes, index, index += 2);
			String value = getString(bytes, index, index += length);
			tlvs.add(new TLV(tag, value));
		}
		packet.setTlvs(tlvs);
		return packet;
	}
	
	public static byte[] concatBytes(ArrayList<ChildPacket> packets) //  concatenate byte arrays
	{
		// Determine the length of the result array
		int totalLength = 0;
		
		for (ChildPacket child : packets)
		{
			totalLength += child.tag.length + child.length.length + child.value.length;
		}
		// create the result array
		byte[] result = new byte[totalLength];
		
		// copy the source arrays into the result array
		int currentIndex = 0;
		for (ChildPacket child : packets)
		{
			System.arraycopy(child.tag, 0, result, currentIndex, child.tag.length);
			currentIndex += child.tag.length;
			System.arraycopy(child.length, 0, result, currentIndex, child.length.length);
			currentIndex += child.length.length;
			System.arraycopy(child.value, 0, result, currentIndex, child.value.length);
			currentIndex += child.value.length;
		}
		
		return result;
	}
	
	/**
	 * 
	 */
	private static int getInt(ArrayList<Byte> packet, int begin, int end)
	{
		byte[] bytes = new byte[packet.size()];
		for (int i = 0; i < packet.size(); i++)
		{
			bytes[i] = packet.get(i).byteValue();
		}
		byte[] result = Arrays.copyOfRange(bytes, begin, end);
		return ByteBuffer.wrap(result).getInt();
	}
	
	/**
	 * 
	 */
	private static short getShort(ArrayList<Byte> packet, int begin, int end)
	{
		byte[] bytes = new byte[packet.size()];
		for (int i = 0; i < packet.size(); i++)
		{
			bytes[i] = packet.get(i).byteValue();
		}
		byte[] result = Arrays.copyOfRange(bytes, begin, end);
		return ByteBuffer.wrap(result).getShort();
	}
	
	/**
	 * 
	 */
	private static String getString(ArrayList<Byte> packet, int begin, int end)
	{
		byte[] bytes = new byte[packet.size()];
		for (int i = 0; i < packet.size(); i++)
		{
			bytes[i] = packet.get(i).byteValue();
		}
		byte[] result = Arrays.copyOfRange(bytes, begin, end);
		return new String(result);
	}
	
	public static String getTagName(int type)
	{
		String s = null;
		switch (type)
		{
			case Constant.KEY:
				s = "Key";
				break;
			case Constant.PHONE_NUMBER:
				s = "PhoneNumber";
				break;
			case Constant.NAME:
				s = "Name";
				break;
			case Constant.RESULT_CODE:
				s = "ResultCode";
				break;
			default:
				s = "";
				break;
		}
		return s;
	}
	
	public static String getCmdCodeName(int type)
	{
		String s = null;
		switch (type)
		{
			case Constant.AUTHEN:
				s = "AUTHEN";
				break;
			case Constant.INSERT:
				s = "INSERT";
				break;
			case Constant.COMMIT:
				s = "COMMIT";
				break;
			case Constant.SELECT:
				s = "SELECT";
				break;
			case Constant.ERROR:
				s = "ERROR";
				break;
			default:
				s = "";
				break;
		}
		return s;
	}
	
	/**
	 * @throws IOException
	 * 
	 */
	public static TcpPacket readByte(DataInputStream in) throws IOException
	{
		ArrayList<Byte> packet = new ArrayList<>();
		byte count;
		try
		{
			while ((count = in.readByte()) != -1)
			{
				packet.add(count);
			}
		}
		catch (EOFException e)
		{
		}
		
		return PacketUtils.getPacket(packet);
	}
	
}