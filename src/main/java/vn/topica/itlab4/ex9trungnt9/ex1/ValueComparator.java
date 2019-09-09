package vn.topica.itlab4.ex9trungnt9.ex1;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author ManhHD
 *
 */
public class ValueComparator implements Comparator<String>
{
	Map<String, Integer> base;
	
	public ValueComparator(Map<String, Integer> base)
	{
		this.base = base;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(String s1, String s2)
	{
		if (base.get(s1) >= base.get(s2))
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
	
}
