package vn.topica.itlab4.ex10songty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student
{
	private int id;
	private String name;
	private Date birthday;
	private int gender;
	private Date admissionDate;
	
	private static SimpleDateFormat sdf;
	
	public Student()
	{
		id = -1;
		name = "";
		birthday = new Date();
		gender = 1;
		admissionDate = new Date();
		sdf = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public Student(int id, String name, Date birthday, int gender, Date admissionDate)
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.admissionDate = admissionDate;
	}
	
	public Student(int id, String name, String birthday, int gender,
			String admissionDate) throws ParseException
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.id = id;
		this.name = name;
		this.birthday = sdf.parse(birthday);
		this.gender = gender;
		this.admissionDate = sdf.parse(admissionDate);
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Date getBirthday()
	{
		return birthday;
	}
	
	public String getBirthdayToString()
	{
		return sdf.format(birthday);
	}
	
	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}
	
	public void setBirthday(String birthday)
	{
		try
		{
			this.birthday = sdf.parse(birthday);
		}
		catch (ParseException e)
		{
			this.birthday = new Date();
		}
	}
	
	public int getGender()
	{
		return gender;
	}
	
	public String getGenderToString()
	{
		switch (gender)
		{
			case 0:
				return "Female";
			case 1:
				return "Male";
			default:
				return "Other";
		}
	}
	
	public void setGender(int gender)
	{
		this.gender = gender;
	}
	
	public Date getAdmissionDate()
	{
		return admissionDate;
	}
	
	public String getAdmissionDateToString()
	{
		return sdf.format(admissionDate);
	}
	
	public void setAdmissionDate(Date admissionDate)
	{
		this.admissionDate = admissionDate;
	}
	
	public void setAdmissionDate(String admissionDate)
	{
		try
		{
			this.admissionDate = sdf.parse(admissionDate);
		}
		catch (ParseException e)
		{
			this.admissionDate = new Date();
		}
	}
}
