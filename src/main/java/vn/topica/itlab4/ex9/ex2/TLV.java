package vn.topica.itlab4.ex9.ex2;

import java.io.Serializable;

/**
 *
 * @author ManhHd
 *
 */
public class TLV implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5518072466609736448L;
	private short tag;
	private short length;
	private String value;
	
	public TLV(short tag, String value)
	{
		this.tag = tag;
		this.length = (short) value.getBytes().length;
		this.value = value;
	}
	
	public TLV(int tag, String value)
	{
		this.tag = (short) tag;
		this.length = (short) value.getBytes().length;
		this.value = value;
	}
	
	public short getTag()
	{
		return tag;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public short getLength()
	{
		return length;
	}
	
}