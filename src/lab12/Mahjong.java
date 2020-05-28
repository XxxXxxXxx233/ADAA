package lab12;

import java.io.*;
import java.util.*;

public class Mahjong {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		int m = in.nextInt();
		int[] count = new int[m + 1];
		for(int i=0; i<n; i++) {
			count[in.nextInt()]++;
		}
		int[][][] opt = new int[2][3][3];
		int num;
		for(int i=1; i<m+1; i++) {
			for(int x=0; x<3; x++) {
				for(int y=0; y<3; y++) {
					opt[0][x][y] = opt[1][x][y];
					opt[1][x][y] = 0;
				}
			}
			
			for(int j=0; j<3; j++) {
				for(int k=0; k<3; k++) {
					for(int l=0; l<3; l++) {
						num = j + k + l;
						if(count[i] >= num) {
							opt[1][j][k] = Math.max(opt[1][j][k], opt[0][k][l] + j + (count[i] - num)/3);
						}
					}
				}
			}
		}
		out.println(opt[1][0][0]);
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
