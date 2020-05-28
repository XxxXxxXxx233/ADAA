package lab9;

import java.io.*;
import java.util.*;

public class ExamsAreComing {
	
	private static int n;
	private static int[] index;
	private static int[] after;
	private static int[] score1;
	private static int[] score2;
	private static int[] num;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		n = in.nextInt();
		int[] tempScore = new int[n];
		score1 = new int[n];
		score2 = new int[n];
		after = new int[n];
		index = new int[n];
		num = new int[n];
		
		for(int i=0; i<n; i++) {
			score1[i] = in.nextInt();
			score2[i] = in.nextInt();
			index[i] = i;
		}
		if(n == 1) {
			out.println(0);
		} else {
			mergeSort(0, n-1);
			
			int[] score = new int[n];
			for(int i=0; i<n; i++) {
				score[i] = score2[after[i]];
			}
			long total = ((long)n) * (n-1) / 2;
			long sum = sortAndCount(score, 0, n-1);
//			System.out.println("Inversion = " + sum);
//			System.out.println("Total = " + total);
			out.println(total-sum);
		}
		out.close();
	}
	
	public static long sortAndCount(int[] arr, int l, int r) {
		if(l < r) {
			int center = l + (r - l) / 2;
			long count1 = sortAndCount(arr, l, center);
			long count2 = sortAndCount(arr, center + 1, r);
			long count3 = mergeAndCount(arr, l, center, r);
			return count1 + count2 + count3;
		} else {
			return 0;
		}
	}
	
	public static long mergeAndCount(int[] arr, int l, int center, int r) {
		long inversion = center - l + 1;
		long count = 0;
		int leftIndex = l;
		int rightIndex = center + 1;
		int i = leftIndex;
		while(leftIndex <= center || rightIndex <= r) {
			if(rightIndex > r || (leftIndex <= center && arr[leftIndex] <= arr[rightIndex])) {
				num[i] = arr[leftIndex];
				inversion--;
				i++;
				leftIndex++;
			} else {
				num[i] = arr[rightIndex];
				count += inversion;
				i++;
				rightIndex++;
			}
		}
		
		for(int t=l; t<=r; t++) {
			arr[t] = num[t];
		}
		return count;
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
			if(rightIndex > r || (leftIndex <= center && score1[index[leftIndex]] < score1[index[rightIndex]])) {
				after[i] = index[leftIndex];
				i++;
				leftIndex++;
			} else if(rightIndex > r || (leftIndex <= center && score1[index[leftIndex]] == score1[index[rightIndex]])) {
				if(score2[index[leftIndex]] <= score2[index[rightIndex]]) {
					after[i] = index[leftIndex];
					i++;
					leftIndex++;
				} else {
					after[i] = index[rightIndex];
					i++;
					rightIndex++;
				}
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
