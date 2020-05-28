package lab5;

import java.io.*;
import java.util.*;

public class LanranFirstGF {
	
	private static int[] e;
	private static int[] index;
	private static int[] after;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int T = in.nextInt();
		for(int t=0; t<T; t++) {
			int n = in.nextInt();
			if(n == 0) {
				out.println(0);
				continue;
			}
			int[] b = new int[n];
			e = new int[n];
			index = new int[n];
			after = new int[n];
			
			for(int i=0; i<n; i++) {
				b[i] = in.nextInt();
				e[i] = in.nextInt();
				index[i] = i;
			}
			
			mergeSort(0, n-1);
			
/*			for(int i=0; i<n; i++) {
				System.out.print(after[i] + " ");
			}
			System.out.println();*/
			
			int count = 1;
			int cur = after[0];
			int end = e[cur];
			
			for(int i=1; i<n; i++) {
				cur = after[i];
				if(b[cur] > end) {
					end = e[cur];
					count++;
				}
			}
			out.println(count);
		}
		
		out.close();
	}

	public static void mergeSort(int l, int r) {
		if(l >= r)
			return;
		int center = l + (r - l) / 2;
		mergeSort(l, center);
		mergeSort(center + 1, r);
		merge(l, center, r);
	}
	
	public static void merge(int l, int center, int r) {
		int leftIndex = l;
		int rightIndex = center + 1;
		int i = leftIndex;
		while(leftIndex <= center || rightIndex <= r) {
			if(rightIndex > r || (leftIndex <= center && e[index[leftIndex]] <= e[index[rightIndex]])) {
				after[i] = index[leftIndex];
				i++;
				leftIndex++;
			} else {
				after[i] = index[rightIndex];
				i++;
				rightIndex++;
			}
		}
		for(int j=l; j<=r; j++) {
			index[j] = after[j];
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
