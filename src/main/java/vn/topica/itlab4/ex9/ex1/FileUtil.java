package vn.topica.itlab4.ex9.ex1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author ManhHD
 *
 */
public class FileUtil
{
	private static InputStream in = null;
	private static OutputStream out = null;
	
	public static String readFile(String path) throws FileNotFoundException
	{
		File file = new File(path);
		
		String s = readFile(file);
		
		return s;
		
	}
	
	public static String readFile(File file)
	{
		String s = "";
		if (in == null)
		{
			try
			{
				in = new FileInputStream(file);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		int k = 0;
		try
		{
			while ((k = in.read()) != -1)
			{
				s += (char) k;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * @param <E>
	 * 
	 */
	public static <E> void writeFile(String path, List<E> devices)
	{
		File file = new File(path);
		writeFile(file, devices);
	}
	
	/**
	 * @param <E>
	 * 
	 */
	public static <E> void writeFile(File file, List<E> devices)
	{
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (out == null)
		{
			try
			{
				out = new FileOutputStream(file, true);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		int index = 0;
		for (E e : devices)
		{
			String s = "";
			if (e.getClass() == Device.class)
			{
				Device device = (Device) e;
				if (index == 0)
				{
					s = device.getCode();
				}
				else
				{
					s = "\n" + device.getCode();
				}
				
				s += ", " + device.getName();
				s += ", " + device.getOwner();
				s += ", " + device.getInputDateFormat();
				s += ", " + device.getWarrantyYear();
				
				try
				{
					out.write(s.getBytes());
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
				index = 1;
			}
			else
			{
				try
				{
					s = (String) e;
					out.write(s.getBytes());
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
				index = 1;
			}
			
		}
		try
		{
			String breakPoint = "\n###\n";
			out.write(breakPoint.getBytes());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
