package lab3;

import java.io.*;
import java.util.*;

public class Casino {
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
//		String[] arr = new String[n];
		long[] arr = new long[n];
		int[] length = new int[n];
		int[] len = new int[11];
		for(int i=0; i<n; i++) {
			arr[i] = in.nextLong();
			length[i] = (arr[i]+"").length();
			len[length[i]]++;
		}
		
//		long t1 = System.currentTimeMillis();
		long mod = 998244353;
		long[] power = new long[20];
		power[0] = 1;
		for(int i=0; i<power.length-1; i++) {
			power[i+1] = (power[i] * 10)%mod;
		}
		
		long sum = 0;
		int a = 0;
		long temp = 0;
		long temp2 = 0;
		int strLen = 0;
		int position = 0;
		long num1 = 0;
		long num2 = 0;
		for(int i=0; i<n; i++) {
			strLen = length[i];
			for(int j=strLen-1; j>=0; j--) {
				a = strLen - j - 1;
				temp2 = arr[i]/10;
				temp = arr[i] - (temp2 * 10);
				for(int k=1; k<len.length; k++) {
					if(len[k] == 0)
						continue;
					position = findPostion1(a, strLen, k);
					num1 = (power[position] * temp)%mod;
					position = findPostion2(a, strLen, k);
					num2 = (power[position] * temp)%mod;
					num1 = (num1 + num2)%mod;
					sum = (sum + (num1 * len[k])%mod)%mod;
				}
				arr[i] = temp2;
			}
		}
		
		out.println(sum);
//		long t2 = System.currentTimeMillis();
//		out.println("Time = " + (t2-t1));
		
		out.close();
	}
	public static int findPostion1(int n, int strLen, int p) {
		if(p >= strLen) {
			return 2*n + 1;
		} else {
			if(n == p) {
				return 2*n;
			} else if(n > p) {
				return n + p;
			} else {
				return 2*n + 1;
			}
		}
	}
	
	public static int findPostion2(int n, int strLen, int p) {
		if(p >= strLen) {
			return 2*n;
		} else {
			if(n == p) {
				return 2*n;
			} else if(n > p) {
				return n + p;
			} else {
				return 2*n;
			}
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

