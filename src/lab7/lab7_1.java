package lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class lab7_1 {

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		InputReader in = new InputReader(inputStream);
		int n = in.nextInt();
		int m = in.nextInt();
		long[][] arr = new long[n][m];
		int[] root = new int[n * m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				arr[i][j] = in.nextLong();
			}
		}
		long t1 = System.currentTimeMillis();

		for (int i = 0; i < root.length; i++)
			root[i] = i;
		Edge edges[] = new Edge[2 * m * n - m - n];
		Queue<Edge> queue = new PriorityQueue<>((s1, s2) -> {
			if (s1.time > s2.time)
				return 1;
			else if (s1.time < s2.time)
				return -1;
			else
				return 0;
		});
		int count = 0;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m - 1; j++) {
				edges[count] = new Edge(i, j, i, j + 1, arr[i][j] ^ arr[i][j + 1]);
				count++;
			}
		}
		for (int j = 0; j < m; j++) {
			for (int i = 0; i < n - 1; i++) {
				edges[count] = new Edge(i, j, i + 1, j, arr[i][j] ^ arr[i + 1][j]);
				count++;
			}
		}
		for (int i = 0; i < edges.length; i++)
			queue.add(edges[i]);

		long result = 0;
		Edge e;
		int point1;
		int point2;
		int index1;
		int index2;

		while (!queue.isEmpty()) {
			e = queue.poll();
			index1 = e.n1 * m + e.m1;
			index2 = e.n2 * m + e.m2;
			point1 = findRoot(root, index1);
			point2 = findRoot(root, index2);
			if (point1 != point2) {
				result += e.time;
				root[point1] = point2;
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println(edges.length);
		System.out.println(result);
		System.out.println("Time = " + (t2-t1));
	}

	public static int findRoot(int[] root, int index) {
		if (root[index] == index)
			return index;
		root[index] = findRoot(root, root[index]);
		return root[index];
	}

	static class Edge {
		long time;
		int n1;
		int m1;
		int n2;
		int m2;

		Edge(int n1, int m1, int n2, int m2, long t) {
			this.n1 = n1;
			this.m1 = m1;
			this.n2 = n2;
			this.m2 = m2;
			time = t;
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
