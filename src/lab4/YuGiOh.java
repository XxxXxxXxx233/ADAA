package lab4;

import java.io.*;
import java.util.*;

public class YuGiOh {
	
	private static long mod = 998244353;
	private static int n;
	private static int win, draw;
	private static int[] cur;
	private static int[] score;
	private static int[] diff;
	private static Map<Long, Long> hash = new LinkedHashMap<>();
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		n = in.nextInt();
		cur = new int[n + 1];
		score = new int[n + 1];
		diff = new int[n + 1];
		
		int sum = 0;
		int temp = 0;
		for(int i=1; i<=n; i++) {
			temp = in.nextInt();
			score[i] = temp;
			sum += temp;
		}
		win = sum - n*n + n;
		draw = (sum - 3*win) / 2;
		quickSort(score, 1, n);
/*		for(int i=1; i<=n; i++) {
			System.out.print(score[i] + " ");
		}
		System.out.println();
		System.out.println("Sum = " + sum);
		System.out.println("Win = " + win + ", Draw = " + draw);*/
		long count = dfs(1, 2);
		out.println(count);
		out.close();
	}
	
	public static long dfs(int u, int v) {
//		System.out.println("u = " + u + ", v = " + v);
		long ans = 0;
		
		if(u == n) {
//			System.out.println("Return when u = n!");
			return 1;
		}
		if(cur[u] > score[u]) {
			return 0;
		}
		if(cur[u] + 3 * (n-v+1) < score[u]) {
//			System.out.println("Impossible!");
			return 0;
		}
		if(v > n) {
			for(int i=u+1; i<=n; i++) {
				diff[i] = score[i] - cur[i];
			}
			quickSort(diff, u, n);
			long curHash = 0;
			for(int i=u+1; i<=n; i++) {
				curHash = curHash * 17 + diff[i];
			}
			
			if(!hash.containsKey(curHash)) {
//				System.out.println("New hash");
				hash.put(curHash, dfs(u+1, u+2));
			}
			return hash.get(curHash);
		}
		if(cur[u] + 3 <= score[u] && win > 0) {
			cur[u] += 3;
			win--;
			ans = (ans + dfs(u, v+1)) % mod;
			win++;
			cur[u] -= 3;
		}
		if(cur[u] + 2 <= score[u] && cur[v] + 1 <= score[v] && win > 0) {
			cur[u] += 2;
			cur[v] += 1;
			win--;
			ans = (ans + dfs(u, v+1)) % mod;
			win++;
			cur[v] -= 1;
			cur[u] -= 2;
		}
		if(cur[u] + 1 <= score[u] && cur[v] + 1 <= score[v] && draw > 0) {
			cur[u] += 1;
			cur[v] += 1;
			draw--;
			ans = (ans + dfs(u, v+1)) % mod;
			draw++;
			cur[v] -= 1;
			cur[u] -= 1;
		}
		if(cur[u] + 1 <= score[u] && cur[v] + 2 <= score[v] && win > 0) {
			cur[u] += 1;
			cur[v] += 2;
			win--;
			ans = (ans + dfs(u, v+1)) % mod;
			win++;
			cur[v] -= 2;
			cur[u] -= 1;
		}
		if(cur[v] + 3 <= score[v] && win > 0) {
			cur[v] += 3;
			win--;
			ans = (ans + dfs(u, v+1)) % mod;
			win++;
			cur[v] -= 3;
		}
		
		return ans % mod;
	}
	
	public static void quickSort(int[] arr, int begin, int end) {
		if(begin > end)
			return;
		int l = begin;
		int r = end;
		int pivot = arr[l];
		while(l<r) {
			while(l<r && arr[r] < pivot)
				r--;
			while(l<r && arr[l] >= pivot)
				l++;
			if(l<r) {
				int temp = arr[r];
				arr[r] = arr[l];
				arr[l] = temp;
			}
		}
		
		int temp = arr[begin];
		arr[begin] = arr[l];
		arr[l] = temp;
		
		quickSort(arr, begin, l-1);
		quickSort(arr, l+1, end);
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
