package lab7;

import java.io.*;
import java.util.*;

public class VinceAndYee {
	
	private static int MAX = Integer.MAX_VALUE;
	private static int[][] A;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		n++;
		A = new int[n][n];
		int value;
		for(int j=0; j<n-1; j++) {
			for(int k=j+1; k<n; k++) {
				value = in.nextInt();
				A[j][k] = value;
				A[k][j] = value;
			}
		}
		
		out.println(prim(0));
		out.close();
	}
	
	public static long prim(int begin) {
		long sum = 0;
		int length = A.length;
		boolean[] visited = new boolean[length];
		long[] ans = new long[length];
		for(int i=0; i<length; i++) {
			if(i != begin)
				ans[i] = MAX;
		}
		
		PriorityQueue<priorityNode> q = new PriorityQueue<>();
		q.add(new priorityNode(begin, ans[begin]));
		
		priorityNode temp = null;
		int No;
		while(!q.isEmpty()) {
			temp = q.poll();
			No = temp.No;
			if(!visited[No]) {
				visited[No] = true;
				sum += temp.distance;
//				System.out.println("No = " + (temp.No+1) + ", Distance = " + temp.distance);
				for(int i=0; i<A.length; i++) {
					if(!visited[i] && A[No][i] < ans[i]) {
//						System.out.println("vertexNo = " + (vertexNo+1) + ", Distance = " + cur.value);
						ans[i] = A[No][i];
						q.add(new priorityNode(i, ans[i]));
					}
				}
			}
		}
		
		return sum;
	}
	
	static class priorityNode implements Comparable<priorityNode> {
		int No;
		long distance;
		
		public priorityNode(int No, long distance) {
			this.No = No;
			this.distance = distance;
		}

		@Override
		public int compareTo(priorityNode o) {
			if(distance < o.distance)
				return -1;
			else if(distance > o.distance)
				return 1;
			return 0;
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
