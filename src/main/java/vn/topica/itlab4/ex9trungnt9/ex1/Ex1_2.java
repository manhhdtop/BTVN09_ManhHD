package vn.topica.itlab4.ex9trungnt9.ex1;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author ManhHD
 *
 */
public class Ex1_2
{
	/**
	 * path is context path of project
	 */
	private static String path = System.getProperty("user.dir");
	private static String input1 = path + "/resource/input1.txt";
	private static String output1 = path + "/resource/output1.txt";
	
	public static void main(String[] args)
	{
		try
		{
			String s = FileUtil.readFile(input1);
			
			List<Device> devices = Utils.convertObject(s, true);
			
			/*
			 * Sort devices list by warranty year
			 */
			devices.sort((o1, o2) -> Integer.compare(o2.getWarrantyYear(), o1.getWarrantyYear()));
			
			FileUtil.writeFile(output1, devices);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not Found!");
		}
	}
	
}
