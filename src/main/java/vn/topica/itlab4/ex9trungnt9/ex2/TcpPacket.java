package vn.topica.itlab4.ex9trungnt9.ex2;

import java.util.ArrayList;

/**
 *
 * @author ManhHd
 *
 */
public class TcpPacket
{
	private int lengthOfMessage;
	private short cmdCode;
	private short version;
	
	private ArrayList<TLV> tlvs;
	
	/**
	 * 
	 */
	public TcpPacket()
	{
		cmdCode = -1;
		tlvs = new ArrayList<>();
	}
	
	/**
	 * 
	 */
	public TcpPacket(int code, ArrayList<TLV> tlv)
	{
		cmdCode = (short) code;
		version = 0;
		this.setTlvs(tlv);
	}
	
	/**
	 * 
	 */
	public TcpPacket(int code, TLV tlv)
	{
		cmdCode = (short) code;
		version = 0;
		ArrayList<TLV> tlvs = new ArrayList<>();
		tlvs.add(tlv);
		this.setTlvs(tlvs);
	}
	
	public byte[] getByte()
	{
		return PacketUtils.getByte(this);
	}
	
	/**
	 * Print packet to screen as template
	 */
	public void printPacket()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(PacketUtils.getCmdCodeName(cmdCode)).append(" ");
		for (TLV tlv : tlvs)
		{
			sb.append(PacketUtils.getTagName(tlv.getTag())).append(" ");
			sb.append(tlv.getValue()).append(" ");
		}
		System.out.println(sb.toString());
	}
	
	public short getCmdCode()
	{
		return cmdCode;
	}
	
	public void setCmdCode(short cmdCode)
	{
		this.cmdCode = cmdCode;
	}
	
	public short getVersion()
	{
		return version;
	}
	
	public void setVersion(short version)
	{
		this.version = version;
	}
	
	/**
	 * @return the tlvs
	 */
	public ArrayList<TLV> getTlvs()
	{
		return tlvs;
	}
	
	/**
	 * @param tlvs the tlvs to set
	 */
	public void setTlvs(ArrayList<TLV> tlvs)
	{
		this.tlvs = tlvs;
	}
	
	/**
	 * @return the lengthOfMessage
	 */
	public int getLengthOfMessage()
	{
		return lengthOfMessage;
	}
	
	/**
	 * @param lengthOfMessage the lengthOfMessage to set
	 */
	public void setLengthOfMessage(int lengthOfMessage)
	{
		this.lengthOfMessage = lengthOfMessage;
	}
}
