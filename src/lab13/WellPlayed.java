package lab13;

import java.io.*;
import java.util.*;

public class WellPlayed {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int n = in.nextInt();
		int k = in.nextInt();
		int cost = in.nextInt();
		int[][][] card = new int[n+1][k][2];
		for(int i=1; i<n+1; i++) {
			for(int j=0; j<k; j++) {
				card[i][j][0] = in.nextInt();
				card[i][j][1] = in.nextInt() + in.nextInt();
			}
		}
		
		int[][] opt = new int[n+1][cost+1];
		int bigger = 0;
		for(int i=1; i<n+1; i++) {
			for(int j=1; j<cost+1; j++) {
				for(int x=0; x<k; x++) {
					if(card[i][x][0] > j) {
						bigger = opt[i-1][j];
					} else {
						bigger = Math.max(opt[i-1][j], opt[i-1][j - card[i][x][0]] + card[i][x][1]);
					}
					opt[i][j] = Math.max(bigger, opt[i][j]);
				}
			}
		}
		out.println(opt[n][cost]);
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
