package lab11;

import java.io.*;
import java.util.*;

public class VCN_String {
	
	private static int n = 62;
	private static long[] power;
	private static pair[] p;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int T = in.nextInt();
		powerOf2();
		generateVCN();
/*		for(int i=0; i<n; i++) {
			out.println(p[i].V + " " + p[i].C + " " + p[i].N);
		}*/
		for(int t=0; t<T; t++) {
			long length = in.nextLong();
			
			pair ans = divide(length);
			out.println(ans.V + " " + ans.C + " " + ans.N);
		}
		
		out.close();
	}
	
	public static pair divide(long num) {
//		System.out.println("num = " + num);
		if(num == 0) {
			return new pair(0, 0, 0);
		}
		int index = findIndex(num);
		pair p1 = p[index];
		long noGreater = power[index];
		pair p2 = divide(num - noGreater);
		return new pair(p1.V + p2.N, p1.C + p2.V, p1.N + p2.C);
	}
	
	public static void generateVCN() {
		p = new pair[n];
		p[0] = new pair(1, 0, 0);
		p[1] = new pair(1, 1, 0);
		long V, C, N;
		for(int i=2; i<n; i++) {
			V = p[i-1].V + p[i-1].N;
			C = p[i-1].C + p[i-1].V;
			N = p[i-1].N + p[i-1].C;
			p[i] = new pair(V, C, N);
		}
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

class pair {
	long V;
	long C;
	long N;
	
	public pair(long V, long C, long N) {
		this.V = V;
		this.C = C;
		this.N = N;
	}
	
}
