package vn.topica.itlab4.ex8;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 *
 * @author ManhHD
 *
 */
public class Ex1_4
{
	private static String path = System.getProperty("user.dir");
	private static String input1 = path + "/resource/input1.txt";
	private static String output1 = path + "/resource/output1.txt";
	
	public static void main(String[] args)
	{
		try
		{
			String s = FileUtil.readFile(input1);
			
			List<String> result = count(Utils.convertObject(s, true));
			
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
	private static List<String> count(List<Device> devices)
	{
		Map<String, Integer> keyword = new HashMap<String, Integer>();
		for (Device device : devices)
		{
			String name = device.getOwner();
			String[] arr = Utils.standardizedName(name).split("\\s");
			for (int i = 0; i < arr.length; i++)
			{
				if (keyword.containsKey(arr[i]))
				{
					keyword.computeIfPresent(arr[i], (k, v) -> v + 1);
				}
				else
				{
					keyword.put(arr[i], 1);
				}
			}
		}
		TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(new ValueComparator(keyword));
		sorted_map.putAll(keyword);
		
		List<String> result = new ArrayList<>();
		int max = 0;
		sorted_map.entrySet().toArray();
		for (Map.Entry<String, Integer> entry : sorted_map.entrySet())
		{
			if (max == 0)
			{
				max = entry.getValue();
			}
			if (max <= entry.getValue())
			{
				result.add(entry.getKey());
			}
			else
			{
				break;
			}
		}
		
		return result;
	}
}
