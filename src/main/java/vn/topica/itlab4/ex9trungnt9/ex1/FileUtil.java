package vn.topica.itlab4.ex9trungnt9.ex1;

import java.io.*;
import java.util.List;

/**
 * @author ManhHD
 */
public class FileUtil
{
	private static InputStream in = null;
	private static OutputStream out = null;
	
	/**
	 * Read value from file by path
	 *
	 * @param path is path of file to read
	 */
	public static String readFile(String path) throws FileNotFoundException
	{
		File file = new File(path);
		
		String s = readFile(file);
		
		return s;
		
	}
	
	/**
	 * Read value from file
	 *
	 * @param file is file to read
	 */
	private static String readFile(File file)
	{
		StringBuilder sb = new StringBuilder();
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
				sb.append((char) k);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * Write a list to a file
	 *
	 * @param list is list value to write
	 * @param path is path of file to write
	 */
	public static <E> void writeFile(String path, List<E> list)
	{
		File file = new File(path);
		writeFile(file, list);
	}
	
	/**
	 * Write a list to a file
	 *
	 * @param list is list value to write
	 * @param file is file to write
	 */
	public static <E> void writeFile(File file, List<E> list)
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
		for (E e : list)
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
