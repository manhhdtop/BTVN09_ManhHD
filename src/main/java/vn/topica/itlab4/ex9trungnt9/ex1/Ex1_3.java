package vn.topica.itlab4.ex9trungnt9.ex1;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ManhHD
 */
public class Ex1_3
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
			
			List<Device> result = search(Utils.convertObject(s, true));
			
			FileUtil.writeFile(output1, result);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not Found!");
		}
	}
	
	/**
	 *
	 */
	private static List<Device> search(List<Device> devices)
	{
		String keyword = "TOPICA";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate;
		Date endDate;
		try
		{
			startDate = sdf.parse("31/10/2018");
			endDate = sdf.parse("31/10/2019");
		}
		catch (ParseException e)
		{
			startDate = new Date();
			endDate = new Date();
		}
		
		List<Device> result = new ArrayList<>();
		for (Device device : devices)
		{
			if (device.getCode().toLowerCase().contains(keyword.toLowerCase()))
			{
				if (device.getInputDate().after(startDate) && device.getInputDate().before(endDate))
				{
					result.add(device);
				}
			}
		}
		
		return result;
	}
}
