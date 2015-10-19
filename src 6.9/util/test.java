package util;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import filesIOUtil.FileWriterInterface;

public class test {
	public static void main(String[] args) {
		// List<List<String>> lists = new ArrayList<List<String>>();
		// List<String> list1 = new ArrayList();
		// list1.add("dfa");
		// list1.add("fadf");
		// List<String> list2 = new ArrayList();
		// list2.add("poi");
		// list2.add("lkj");
		// lists.add(list1);
		// lists.add(list2);
		//
		// ArrayList<String> list3 = (ArrayList<String>) lists.get(0);
		// list3.remove(0);
		// System.out.println(lists.get(0));
		//
		// double result = (double) 100 / 1002;
		// System.out.println(result);
		//
		// int a = 3058;
		// int b = 68185;
		// int c = 3324;
		// int d = 955324;
		// double up = a * d - b * c;
		// System.out.println(up);
		// int N = a + b + c + d;
		// double chi = ((double) N * (up * up))
		// / ((a + c) * (b + d) * (a + b) * (c + d));
		// System.out.println(chi);

		// int maxClass = (int) (Math.random() * 33 + 1);
		// System.out.println(maxClass);

		// System.out.println(Util.log2(3));

		// int a=1,b=3,c=4,d=4,N=12;
		// System.out.println(alog(a,b,c,N ));
		//
		// System.out.println(Util.log2(33));

		System.out.println("++++"+Util.log2(2.0 + (double)1 / 2) );
		Map<Integer, Double> map = new TreeMap<Integer, Double>();
		map.put(1, 0.2);
		map.put(1, 121.0);
		map.put(8798, 0.5);
		map.put(123, 215.0);

		for (Iterator<Map.Entry<Integer, Double>> outIte = map.entrySet()
				.iterator(); outIte.hasNext();) {
			Map.Entry<Integer, Double> ele = outIte.next();
			Integer outKey = ele.getKey();
			Double outValue = ele.getValue();
			System.out.println(outKey + ":" + outValue + " ");
		}
		int a = 5;
		int c = 10;
		int N = 100;
		System.out.println((double) N / (a + c));

		String s = "fasf fadfa fasdf";
		System.out.println(s.substring(0, s.indexOf(" ")));
		System.out.println(s.substring(s.indexOf(" ") + 1, s.length()));

		Random rand = new Random();
		System.out.println(rand.nextInt(10) + 1);
	}

	private static double alog(double a, double b, double c, double N) {
		if (a == 0)
			return 0;
		return a / N * Util.log2((a * N) / (a + c) * (a + b));
	}

}
