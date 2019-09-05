package vn.topica.itlab4.ex9.ex2;

import java.util.ArrayList;

/**
 *
 * @author ManhHd
 *
 */
public class ChildPacket
{
	byte[] tag;
	byte[] length;
	byte[] value;
	
	public static byte[] getChildPacket(ArrayList<TLV> tlvs)
	{
		ArrayList<ChildPacket> childs = new ArrayList<>();
		
		for (TLV obj : tlvs)
		{
			ChildPacket child = new ChildPacket();
			child.tag = PacketUtils.toByteArray2(obj.getTag());
			child.length = PacketUtils.toByteArray2((short) obj.getValue().length());
			child.value = obj.getValue().getBytes();
			childs.add(child);
		}
		
		byte[] packet_data = PacketUtils.concatBytes(childs);
		
		return packet_data;
	}
}
