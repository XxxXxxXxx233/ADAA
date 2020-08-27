package lab14;

import java.io.*;
import java.util.*;

public class VinceBlackAndVinceWhite {
	
	private static int MAX = Integer.MAX_VALUE;
	private static int[][] A;
	private static int n, m;
	private static boolean[] visited;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		n = in.nextInt();
		n += 2;
		m = in.nextInt();
		A = new int[n][n];
		visited = new boolean[n];
		int color;
		for(int i=1; i<n-1; i++) {
			color = in.nextInt();
			if(color == 1) {
				A[0][i] = 1;
			} else {
				A[i][n-1] = 1;
			}
		}
		
		int from, to;
		for(int i=0; i<m; i++) {
			from = in.nextInt();
			to = in.nextInt();
			A[from][to] = 1;
			A[to][from] = 1;
		}
		
/*		for(int i=0; i<n; i++) {
			System.out.print("Vertex " + i + " : ");
			for(int j=0; j<n; j++) {
				if(A[i][j] != 0) {
					System.out.print(j + " ");
				}
			}
			System.out.println();
		}*/
		
		out.println(maxFlow(0, n-1));
		out.close();
	}
	
	public static int maxFlow(int from, int to) {
		int ans = 0;
		int flow = 0;
		while(true) {
			for(int i=0; i<n; i++) {
				visited[i] = false;
			}
			flow = dfs(from, to, MAX);
			if(flow == 0) {
				break;
			}
			ans += flow;
		}
		return ans;
	}
	
	public static int dfs(int from, int to, int value) {
		if(from == to)
			return value;
		visited[from] = true;
		int temp = 0;
		for(int i=0; i<n; i++) {
			if(A[from][i] > 0 && !visited[i]) {
				temp = dfs(i, to, Math.min(A[from][i], value));
				if(temp > 0) {
					A[from][i] -= temp;
					A[i][from] += temp;
					break;
				}
			}
		}
		return temp;
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
