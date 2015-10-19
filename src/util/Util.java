package util;

import java.util.Comparator;
import java.util.Map;

public class Util {
	/**
	 * @param value
	 * @return 计算log2()的值
	 */
	private static double log2Value = Math.log(2.0);
	private static double log33Value = Math.log(33.0);
	private static double log10Value = Math.log(10.0);
	private static double log50Value = Math.log(50.0);

	public static double log2(double value) {
		if (value == 0.0)
			return 0.0;
		else
			return (Math.log(value) / log2Value);
	}

	public static double log33(double value) {
		if (value == 0.0)
			return 0.0;
		else
			return (Math.log(value) / log33Value);
	}

	public static double log10(double value) {
		if (value == 0.0)
			return 0.0;
		else
			return (Math.log(value) / log10Value);
	}

	public static double log50(double value) {
		if (value == 0.0)
			return 0.0;
		else
			return (Math.log(value) / log50Value);
	}

	public static class ValueComparator
			implements
				Comparator<Map.Entry<Integer, Double>> {
		public int compare(Map.Entry<Integer, Double> m,
				Map.Entry<Integer, Double> n) {
			if (n.getValue() > m.getValue())
				return 1;
			else
				return 0;
		}
	}
}
