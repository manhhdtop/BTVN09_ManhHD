package vn.topica.itlab4.ex9.ex1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ManhHd
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
	
	/**
	 * Constructor init object to format date
	 */
	public Device()
	{
		sdf = new SimpleDateFormat(dateFormat);
	}
	
	/**
	 * Constructor init object to format date
	 * and assign value for variables
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
	
	/**
	 * @return code
	 */
	public String getCode()
	{
		return code;
	}
	
	/**
	 * @param code to set value for variable code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}
	
	/**
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name to set value for variable name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return owner
	 */
	public String getOwner()
	{
		return owner;
	}
	
	/**
	 * @param owner to set value for variable owner
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	/**
	 * @return date of input date
	 */
	public Date getInputDate()
	{
		return inputDate;
	}
	
	/**
	 * @return string format of input date
	 */
	public String getInputDateFormat()
	{
		return sdf.format(inputDate);
	}
	
	/**
	 * @param inputDate to set value for variable inputDate
	 */
	public void setInputDate(Date inputDate)
	{
		this.inputDate = inputDate;
	}
	
	/**
	 * @param inputDate is String of Date to set value for variable inputDate
	 *                  by String value
	 */
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
	
	/**
	 * @return warranty year
	 */
	public int getWarrantyYear()
	{
		return warrantyYear;
	}
	
	/**
	 * @param warrantyYear to set value for variable warrantyYear by integer
	 *                     value
	 */
	public void setWarrantyYear(int warrantyYear)
	{
		this.warrantyYear = warrantyYear;
	}
	
	/**
	 * @param warrantyYear to set value for variable warrantyYear by string
	 *                     value
	 */
	public void setWarrantyYear(String warrantyYear)
	{
		this.warrantyYear = Integer.parseInt(warrantyYear);
	}
}
