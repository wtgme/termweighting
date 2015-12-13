package util;

public class Calculater {

	static double N = 1000.0;
	static double C = 4.0;

	public static void main(String[] args) {
//		double c0[] = {89.0, 9.0, 29.0, 8.0};
//		double c1[] = {13.0, 10.0, 53.0, 3.0};
		double c0[] = {23.0, 0.0, 0.0, 0.0};
		double c1[] = {12.0, 8.0, 0.0, 0.0};
		double c2[] = {12.0, 4.0, 3.0, 0.0};
		double c3[] = {169.0, 10.0, 3.0, 6.0};
		double c4[] = {0.8 / 0.9, 0.1 / 0.9};
		double c5[] = {250, 0, 0, 0};
		double c6[] = {25, 675, 0, 0};
		double c7[] = {25, 0, 0, 0};
		double c8[] = {1, 747, 0, 0};
		double c9[] = {11, 95, 122, 31};
		System.out.println(chi(c5));
		System.out.println(chi(c6));
		System.out.println(chi(c7));
		System.out.println(chi(c8));
		System.out.println(chi(c9));
		
		System.out.println("--------------------------");
		
		System.out.println(ig(c5));
		System.out.println(ig(c6));
		System.out.println(ig(c7));
		System.out.println(ig(c8));
		System.out.println(ig(c9));
		// double c0[] = { 40.0 };
		// double c1[] = { 10.0, 30.0 };
		// double c2[] = { 10.0, 10.0, 10.0,10.0 };
		// double c3[] = { 10.0, 28.0, 1.0, 1.0 };

		System.out.println("ipo"+ "\t" + idf(c0) + "\t" + chi(c0) + "\t" + ig(c0) + "\t"
				+ rf(c0) + "\t" + iqfqficf(c0) + "\t" + dc(c0) + "\t" + cr(c0));
		System.out.println("amd"+ "\t" +idf(c1) + "\t" + chi(c1) + "\t" + ig(c1) + "\t"
				+ rf(c1) + "\t" + iqfqficf(c1) + "\t" + dc(c1) + "\t" + cr(c1));
		System.out.println("science"+ "\t" +idf(c9) + "\t" + chi(c9) + "\t" + ig(c9) + "\t"
				+ rf(c9) + "\t" + iqfqficf(c9) + "\t" + dc(c9));
		System.out.println("private"+ "\t" +idf(c2) + "\t" + chi(c2) + "\t" + ig(c2) + "\t"
				+ rf(c2) + "\t" + iqfqficf(c2) + "\t" + dc(c2));
		System.out.println("market"+ "\t" +idf(c3) + "\t" + chi(c3) + "\t" + ig(c3) + "\t"
				+ rf(c3) + "\t" + iqfqficf(c3) + "\t" + dc(c3));
		System.out.println(dc(c4));

		// double list[] = { 0, 0, 0 };
		// for (int a = 1; a < 10; a++) {
		// for (int b = 0; b < 10; b++) {
		// for (int c = 0; c < 10; c++) {
		// int t = a;
		// if (b > a) {
		// t = a;
		// a = b;
		// b = t;
		// }
		// if (c > a) {
		// t = a;
		// a = c;
		// c = t;
		// }
		// list[0] = a;
		// list[1] = b;
		// list[2] = c;
		// System.out.println(a + "\t" + b + "\t" + c + "\t"
		// + en(list) + "\t" + dc(list) + "\t" + rf(list)
		// + "\t" + iqfqficf(list));
		// }
		// }
		// }

	}
	public static double idf(double[] args) {
		double nf = 0;
		for (int i = 0; i < args.length; i++) {
			nf += args[i];
		}

		return log2(N / nf);
	}

	public static double rf(double[] args) {
		double a = args[0];
		double c = 0.0;
		for (int i = 1; i < args.length; i++) {
			c += args[i];
		}
		return log2(2 + a / Math.max(c, 1.0));
	}

	public static double iqfqficf(double[] args) {
		double a = args[0];
		double c = 0.0;
		double cf = 0.0;
		if (args[0] != 0.0) {
			cf = 1;
		}
		for (int i = 1; i < args.length; i++) {
			c += args[i];
			if (args[i] != 0.0) {
				cf++;
			}
		}

		return log2(N / (a + c)) * log2(a + 1) * log2((C / cf) + 1);

	}

	// log2 calculation
	public static double log2(double x) {
		return (Math.log(x) / Math.log(2.0));
	}

	public static double cr(double[] s) {
		int k = s.length;
		double sum = 0.0;
		for (int i = 0; i < k; i++) {
			sum += s[i];
		}
		return log2(2.0 + (s[0] / sum));
	}

	// entropy calculation
	public static double dc(double[] cfs1) {
		double sum = 0.0;
		int k = cfs1.length;
		for (int i = 0; i < k; i++) {
			sum += cfs1[i];
		}
		double entropy1 = 0.0;
		for (int i = 0; i < k; i++) {
			double pro = cfs1[i] / sum;
			if (pro != 0.0) {
				entropy1 += pro * log2(pro);
			}
		}
		return (1 + entropy1 / log2(C));
	}

	// entropy
	public static double en(double[] cfs1) {
		double sum = 0.0;
		int k = cfs1.length;
		for (int i = 0; i < k; i++) {
			sum += cfs1[i];
		}
		double entropy1 = 0.0;
		for (int i = 0; i < k; i++) {
			double pro = cfs1[i] / sum;
			if (pro != 0.0) {
				entropy1 += pro * log2(pro);
			}
		}
		return (-entropy1);
	}
	public static double alog(double a, double b, double c, double N) {
		if (a == 0)
			return 0;
		return (a / N) * Util.log2((a * N) / ((a + c) * (a + b)));
	}
	public static double ig(double[] list) {
		double a = list[0];
		double b = 250 - a;
		double c = list[1] + list[2] + list[3];
		double d = 750 - c;
		double ig = alog(a, b, c, N) + alog(b, a, d, N) + alog(c, a, d, N)
				+ alog(d, c, b, N);
		return ig;
	}

	public static double chi(double[] list) {
		double a = list[0];
		double b = 250 - a;
		double c = list[1] + list[2] + list[3];
		double d = 750 - c;
//		System.out.println(a + "\t" + b + "\t" + c + "\t" + d);
		double up = (double) a * d - b * c;
		double chi = (double) N
				* ((Math.pow(up, 2)) / ((double) (a + c) * (b + d) * (a + b) * (c + d)));
		return chi;
	}
}
