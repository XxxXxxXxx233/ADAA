package lab8;

import java.io.*;
import java.util.*;

public class TheMaximumIncome {
	
	private static int n;
	private static int[] S;
	private static int[] T;
	private static int[] V;
	private static int[] afterS;
	private static int[] indexS;
	private static int[] afterV;
	private static int[] indexV;
	private static int[] task;
	private static int[] activePoint;

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		n = in.nextInt();
		S = new int[n+1];
		T = new int[n+1];
		V = new int[n+1];
		afterS = new int[n+1];
		indexS = new int[n+1];
		afterV = new int[n+1];
		indexV = new int[n+1];
		for(int i=1; i<=n; i++) {
			S[i] = in.nextInt();
			T[i] = in.nextInt();
			V[i] = in.nextInt();
			indexS[i] = i;
			indexV[i] = i;
		}
		mergeSortS(1, n);
		mergeSortV(1, n);
		
		activePoint = new int[n+1];
		int position = 0;
		for(int i=1; i<=n; i++) {
			position = Math.max(position+1, S[afterS[i]]);
			activePoint[i] = position;
		}
		
		task = new int[n+1];
		int x = 0;
		int curIndex = 0;
		for(int i=1; i<=n; i++) {
			x = S[afterV[i]];
			curIndex = findIndex(x);
			find(afterV[i], curIndex);
		}
		
		long sum = 0;
		for(int i=0; i<task.length; i++) {
			if(task[i] != 0) {
				sum += V[task[i]];
			}
		}
		out.println(sum);
		out.close();
	}
	
	public static boolean find(int i, int cur) {
		
		if(activePoint[cur] > T[i]) {
			return false;
		}
		if(task[cur] == 0) {
			task[cur] = i;
			return true;
		} else {
			int j = task[cur];
			if(T[i] >= T[j]) {
				return find(i, cur + 1);
			} else {
				if(find(j, cur + 1)) {
					task[cur] = i;
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public static int findIndex(int x) {
		int l = 1;
		int r = n;
		if(x == activePoint[l]) {
			return l;
		} else if(x == activePoint[r]) {
			return r;
		} else {
			int center;
			while(l <= r) {
				center = l + (r - l) / 2;
				if(x <= activePoint[center]) {
					r = center - 1;
				} else {
					l = center + 1;
				}
			}
			return l;
		}
	}
	
	public static void mergeSortS(int l, int r) {
		if(l >= r)
			return;
		int center = l + (r - l) / 2;
		mergeSortS(l, center);
		mergeSortS(center + 1, r);
		mergeS(l, center, r);
	}
	
	public static void mergeS(int l, int center, int r) {
		int leftIndex = l;
		int rightIndex = center + 1;
		int i = leftIndex;
		while(leftIndex <= center || rightIndex <= r) {
			if(rightIndex > r || (leftIndex <= center && S[indexS[leftIndex]] <= S[indexS[rightIndex]])) {
				afterS[i] = indexS[leftIndex];
				i++;
				leftIndex++;
			} else {
				afterS[i] = indexS[rightIndex];
				i++;
				rightIndex++;
			}
		}
		for(int j=l; j<=r; j++) {
			indexS[j] = afterS[j];
		}
	}
	
	public static void mergeSortV(int l, int r) {
		if(l >= r)
			return;
		int center = l + (r - l) / 2;
		mergeSortV(l, center);
		mergeSortV(center + 1, r);
		mergeV(l, center, r);
	}
	
	public static void mergeV(int l, int center, int r) {
		int leftIndex = l;
		int rightIndex = center + 1;
		int i = leftIndex;
		while(leftIndex <= center || rightIndex <= r) {
			if(rightIndex > r || (leftIndex <= center && V[indexV[leftIndex]] >= V[indexV[rightIndex]])) {
				afterV[i] = indexV[leftIndex];
				i++;
				leftIndex++;
			} else {
				afterV[i] = indexV[rightIndex];
				i++;
				rightIndex++;
			}
		}
		for(int j=l; j<=r; j++) {
			indexV[j] = afterV[j];
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
