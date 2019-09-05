package vn.topica.itlab4.ex9.ex1;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author ManhHD
 *
 */
public class Ex1_2
{
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
			Collections.sort(devices, new Comparator<Device>()
			{
				@Override
				public int compare(Device o1, Device o2)
				{
					if (o1.getWarrantyYear() == o2.getWarrantyYear())
					{
						return 0;
					}
					if (o1.getWarrantyYear() < o2.getWarrantyYear())
					{
						return 1;
					}
					return -1;
				}
			});
			
			FileUtil.writeFile(output1, devices);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not Found!");
		}
	}
	
}
