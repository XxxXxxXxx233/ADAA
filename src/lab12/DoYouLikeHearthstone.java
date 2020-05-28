package lab12;

import java.io.*;
import java.util.*;

public class DoYouLikeHearthstone {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int weight = in.nextInt();
		int card = 30;
		int n = 91;
		int[] w = new int[n];
		int[] attr = new int[n];
		for(int i=1; i<n; i++) {
			w[i] = in.nextInt();
			attr[i] = in.nextInt() + in.nextInt();
		}
		
		int[][][] opt = new int[n][weight+1][card+1];
		for(int i=1; i<n; i++) {
			for(int j=1; j<weight+1; j++) {
				for(int k=1; k<card+1; k++) {
					if(w[i] > j) {
						opt[i][j][k] = opt[i-1][j][k];
					} else {
						opt[i][j][k] = Math.max(opt[i-1][j][k], attr[i] + opt[i-1][j - w[i]][k - 1]);
					}
				}
			}
		}
		out.println(opt[90][weight][card]);
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
