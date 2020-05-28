package lab5;

import java.io.*;
import java.util.*;

public class LanranSecondGF {
	
	private static int[] e;
	private static int[] index;
	private static int[] after;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int n = in.nextInt();
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
		
		int maxEnd = e[after[n-1]];
		int most = maxEnd / n;
//		System.out.println("maxEnd = " + maxEnd);
//		System.out.println("most = " + most);
		if(most == 0) {
			out.println(0);
		} else {	//most > 0
			int l = 0;
			int r = most;
			int mid = 0;
			int cur = 0;
			int begin = 0;
			int end = 0;
			int count = 0;
			boolean mostCheck = true;
			boolean[] used = new boolean[maxEnd + 1];
			for(int i=0; i<n; i++) {
				cur = after[i];
				begin = b[cur];
				end = e[cur];
				count = 0;
				for(int j=begin; j<=end; j++) {
					if(!used[j]) {
						used[j] = true;
						count++;
					}
					if(count == most) {
						break;
					}
				}
				if(count != most) {
					mostCheck = false;
					break;
				}
			}
			if(mostCheck) {
				out.println(most);
			} else {
				int ans = 0;
				while(l <= r) {
					mid = l + (r-l)/2;
					used = new boolean[maxEnd + 1];
					boolean check = true;
					for(int i=0; i<n; i++) {
						cur = after[i];
						begin = b[cur];
						end = e[cur];
						count = 0;
						for(int j=begin; j<=end; j++) {
							if(!used[j]) {
								used[j] = true;
								count++;
							}
							if(count == mid) {
								break;
							}
						}
						if(count != mid) {
							check = false;
							break;
						}
					}
//					System.out.println("mid = " + mid + ", check = " + check);
					if(check) {
						l = mid + 1;
						ans = mid;
					} else {
						r = mid - 1;
					}
				}
				out.println(ans);
			}
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
