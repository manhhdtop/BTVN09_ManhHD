package vn.topica.itlab4.ex9.ex1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ManhHD
 */
public class Utils
{
	/**
	 * Convert Object Device from String
	 *
	 * @param s            is string value of Device object
	 * @param standardized is format of name
	 */
	public static List<Device> convertObject(String s, boolean standardized)
	{
		List<Device> devices = new ArrayList<>();
		Device device;
		String[] arr = s.split("[\r\n]+");
		
		for (String value : arr)
		{
			String[] attr = value.split(",");
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
	
	/**
	 * Standard string as VietNam name
	 */
	public static String standardizedName(String s)
	{
		if (s.length() > 0)
		{
			s = s.toLowerCase().trim().replaceAll("\\s+", " ");
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
