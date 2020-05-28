package lab9;

import java.io.*;
import java.util.*;

public class VC_String {
	
	private static int n = 62;
	private static long[] power;
	private static Map<Long, Long> map = new LinkedHashMap<>();
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int T = in.nextInt();
		powerOf2();
		for(int t=0; t<T; t++) {
			long a = in.nextLong();
			long b = in.nextLong();
			
			long sum1 = divide(b);
			long sum2 = divide(a-1);
			out.println(sum1 - sum2);
		}
		
		out.close();
	}
	
	public static long divide(long num) {
//		System.out.println("num = " + num);
		if(num <= 2) {
			return num;
		} else if(num == 3) {
			return 2;
		} else if(num == 7) {
			return 4;
		}
		if(map.containsKey(num)) {
			return map.get(num);
		}
		int index = findIndex(num);
		long noGreater = power[index];
		long count1 = divide(noGreater - 1);
		long count2 = 1;
		long rest = num - noGreater;
		long count3 = 0;
		if(rest > 0) {
			if(rest >= power[index-1]) {
				count3 = divide(rest) - 1;
			} else {
				count3 = divide(rest);
			}
		}
		if(!map.containsKey(num))
			map.put(num, count1+count2+count3);
		return count1 + count2 + count3;
	}
	
	
	public static int findIndex(long x) {
		int l = 0;
		int r = n-1;
		if(x == power[l]) {
			return l;
		} else if(x == power[r]) {
			return r;
		} else {
			int center;
			while(l <= r) {
				center = l + (r - l) / 2;
				if(x < power[center]) {
					r = center - 1;
				} else {
					l = center + 1;
				}
			}
			return r;
		}
	}
	
	public static void powerOf2() {
		power = new long[n];
		long num = 1;
		for(int i=0; i<n; i++) {
			power[i] = num;
			num *= 2;
		}
	}

	static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
			tokenizer = null;
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public char[] nextCharArray() {
			return next().toCharArray();
		}
	}
}
