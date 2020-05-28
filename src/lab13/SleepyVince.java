package lab13;

import java.io.*;
import java.util.*;

public class SleepyVince {
	
	private static long mod = 998244353;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int n = in.nextInt();
		int[] arr = new int[n+1];
		for(int i=1; i<n+1; i++) {
			arr[i] = in.nextInt();
		}
		long[][][] opt = new long[2][201][2];
		if(arr[1] == -1) {
			for(int i=1; i<201; i++) {
				opt[0][i][0] = 1;
			}
		} else {
			opt[0][arr[1]][0] = 1;
		}
		
		long[][] sum = new long[2][201];
		for(int i=2; i<n+1; i++) {
			for(int j=1; j<201; j++) {
	            sum[0][j] = (sum[0][j-1] + opt[0][j][0]) % mod;
	            sum[1][j] = (sum[1][j-1] + opt[0][j][1]) % mod;
			}
			if(arr[i] == -1) {
				for(int j=1; j<201; j++) {
	                opt[1][j][0] = (sum[0][j-1] + sum[1][j-1]) % mod;
	                opt[1][j][1] = (opt[0][j][0] + (sum[1][200] - sum[1][j-1]) + mod) % mod;
				}
			} else {
	            opt[1][arr[i]][0] = (sum[0][arr[i]-1] + sum[1][arr[i]-1]) % mod;
	            opt[1][arr[i]][1] = (opt[0][arr[i]][0] + (sum[1][200] - sum[1][arr[i]-1]) + mod) % mod;
			}
	        for(int j=1; j<201; j++) {
	            opt[0][j][0] = opt[1][j][0];
	            opt[0][j][1] = opt[1][j][1];
	            opt[1][j][0] = 0;
	            opt[1][j][1] = 0;
	        }
		}
		long ans = 0;
		for(int i=1; i<201; i++) {
			ans = (ans + opt[0][i][1]) % mod;
		}
		out.println(ans);
		out.close();
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
