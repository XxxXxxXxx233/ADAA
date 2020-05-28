package lab3;

import java.io.*;
import java.util.*;

public class MinimumVolatility {
	private static int[] index;
	private static int[] after;
	private static int[] arr;

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		arr = new int[n+1];
		after = new int[n+1];
		index = new int[n+1];
		for(int i=1; i<n+1; i++) {
			arr[i] = in.nextInt();
			index[i] = i;
		}
		mergeSort(1, n);
		
		int[] next = new int[n+1];
		int[] prev = new int[n+1];
		next[0] = index[1];
		prev[0] = index[n];
		for(int i=1; i<n; i++) {
			next[index[i]] = index[i+1];
			prev[index[i+1]] = index[i];
		}
		
/*		out.print("next = ");
		for(int i=1; i<next.length; i++) {
			out.print(next[i] + " ");
		}
		out.println();
		out.print("prev = ");
		for(int i=1; i<prev.length; i++) {
			out.print(prev[i] + " ");
		}
		out.println();*/
		
		int head;
		int value;
		long sum = arr[1];
		for(int i=n; i>=2; i--) {
			head = i;
			value = 10000001;
			if(next[head] != 0) {
				value = Math.abs(arr[next[head]] - arr[i]);
			}
			if(prev[head] != 0) {
				value = Math.min(value, Math.abs(arr[prev[head]] - arr[i]));
			}
			sum += value;
			next[prev[i]] = next[i];
			prev[next[i]] = prev[i];
		}
		
		out.println(sum);
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
			if(rightIndex > r || (leftIndex <= center && arr[index[leftIndex]] <= arr[index[rightIndex]])) {
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
