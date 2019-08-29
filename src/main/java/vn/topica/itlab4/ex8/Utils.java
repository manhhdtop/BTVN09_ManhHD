package vn.topica.itlab4.ex8;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhoks
 *
 */
public class Utils
{
	/**
	 * 
	 * Convert Object Device from String
	 * 
	 * @param s
	 * 
	 */
	public static List<Device> convertObject(String s, boolean standardized)
	{
		List<Device> devices = new ArrayList<Device>();
		Device device;
		String[] arr = s.split("[\r\n]+");
		
		for (int i = 0; i < arr.length; i++)
		{
			String[] attr = arr[i].split(",");
			device = new Device();
			device.setCode(attr[0]);
			device.setName(attr[1]);
			if (standardized)
			{
				device.setOwner(standardizedName(attr[2]));
			}
			else
			{
				device.setOwner(attr[2]);
			}
			device.setInputDate(attr[3]);
			device.setWarrantyYear(attr[4]);
			devices.add(device);
		}
		
		return devices;
	}
	
	public static String standardizedName(String s)
	{
		if (s.length() > 0)
		{
			s = s.toLowerCase().trim();
			s = s.replaceAll("\\s+", " ");
			String[] arr = s.split("\\s");
			for (int i = 0; i < arr.length; i++)
			{
				if (arr[i].length() > 0)
				{
					String c = arr[i].charAt(0) + "";
					arr[i] = c.toUpperCase() + arr[i].substring(1);
				}
			}
			s = String.join(" ", arr);
		}
		return s;
	}
}
