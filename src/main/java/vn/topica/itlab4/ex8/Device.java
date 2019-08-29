package vn.topica.itlab4.ex8;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *
 * @author ManhHD
 *
 */
public class Device
{
	private String code;
	private String name;
	private String owner;
	private Date inputDate;
	private int warrantyYear;

	private static SimpleDateFormat sdf = null;
	private static String dateFormat = "dd/MM/yyyy";
	
	/*
	 * 
	 */
	public Device()
	{
		sdf = new SimpleDateFormat(dateFormat);
	}
	
	/*
	 * 
	 */
	public Device(String code, String name, String owner, Date inputDate, int warrantyYear)
	{
		sdf = new SimpleDateFormat(dateFormat);
		this.code = code;
		this.name = name;
		this.owner = owner;
		this.inputDate = inputDate;
		this.warrantyYear = warrantyYear;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public Date getInputDate()
	{
		return inputDate;
	}

	public String getInputDateFormat()
	{
		return sdf.format(inputDate);
	}

	public void setInputDate(Date inputDate)
	{
		this.inputDate = inputDate;
	}

	public void setInputDate(String inputDate)
	{
		try
		{
			this.inputDate = sdf.parse(inputDate);
		}
		catch (ParseException e)
		{
			this.inputDate = new Date();
		}
	}
	
	public int getWarrantyYear()
	{
		return warrantyYear;
	}

	public void setWarrantyYear(int warrantyYear)
	{
		this.warrantyYear = warrantyYear;
	}
	
	public void setWarrantyYear(String warrantyYear)
	{
		this.warrantyYear = Integer.parseInt(warrantyYear);
	}
}
