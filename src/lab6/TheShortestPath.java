package lab6;

import java.io.*;
import java.util.*;

public class TheShortestPath {
	
	private static topNodeF[] A;
	private static int MAX = Integer.MAX_VALUE;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		int m = in.nextInt();
		
		A = new topNodeF[n];
		for(int i=0; i<n; i++) {
			A[i] = new topNodeF(i, null);
		}
		
		for(int i=0; i<m; i++) {
			int from = in.nextInt() - 1;
			int to = in.nextInt() - 1;
			int value = in.nextInt();
			edgeNodeF edge = new edgeNodeF(to, null, value);
			edgeNodeF temp = A[from].adj;
			A[from].size++;
			if(temp == null) {
				A[from].adj = edge;
			} else {
				while(temp.next != null) {
					temp = temp.next;
				}
				temp.next = edge;
			}
		}
		
/*		for(int i=0; i<A.length; i++) {
			out.print("List of " + (A[i].num) + " with size " + A[i].size + " is: ");
			edgeNodeF cur = A[i].adj;
			while(cur != null) {
				out.print((cur.vertexNo) + " ");
				cur = cur.next;
			}
			out.println();
		}*/
		
		int begin = 0;
		int target = n-1;
		if(begin == target) {
			out.println(0);
		} else {
			out.println(dijkstra(begin, target));
		}
		
		out.close();
	}
	
	public static long dijkstra(int begin, int target) {
		int length = A.length;
		double[] ans = new double[length];
		boolean[] visited = new boolean[length];
		int[] from = new int[length];
		int[] weight = new int[length];
		from[begin] = -1;
		ans[begin] = 0;
		weight[begin] = 1;
		
		for(int i=0; i<length; i++) {
			if(i != begin)
				ans[i] = MAX;
		}
		
		PriorityQueue<priorityNode> q = new PriorityQueue<>();
		q.add(new priorityNode(begin, ans[begin]));
		
		int No, vertexNo;
		edgeNodeF cur = null;
		while(!q.isEmpty()) {
			priorityNode temp = q.poll();
			No = temp.No;
			if(!visited[No]) {
				visited[No] = true;
				cur = A[No].adj;
				while(cur != null) {
					vertexNo = cur.vertexNo;
					if(!visited[vertexNo] && (ans[No] + cur.logValue) < ans[vertexNo]) {
						ans[vertexNo] = ans[No] + cur.logValue;
						from[vertexNo] = No;
						weight[vertexNo] = cur.value;
						q.add(new priorityNode(vertexNo, ans[vertexNo]));
					}
					cur = cur.next;
				}
			}
		}
/*		for(int i=0; i<length; i++) {
			System.out.print(from[i] + " ");
		}
		System.out.println();
		for(int i=0; i<length; i++) {
			System.out.print(weight[i] + " ");
		}
		System.out.println();*/
		int i = from[target];
		long shortest = weight[target];
		long mod = 19260817;
		while(i != -1) {
			shortest = (shortest * weight[i]) % mod;
			i = from[i];
		}
		return shortest;
	}
	
	static class priorityNode implements Comparable<priorityNode> {
		int No;
		double distance;
		
		public priorityNode(int No, double distance) {
			this.No = No;
			this.distance = distance;
		}

		@Override
		public int compareTo(priorityNode o) {
			if(distance > o.distance)
				return 1;
			else if(distance < o.distance)
				return -1;
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

class topNodeF{
	int num;
	int size;
	edgeNodeF adj;
	
	public topNodeF(int num, edgeNodeF adj) {
		this.num = num;
		this.adj = adj;
	}
}

class edgeNodeF{
	int vertexNo;
	edgeNodeF next;
	int value;
	double logValue;
	
	public edgeNodeF(int vertexNo, edgeNodeF next, int value) {
		this.vertexNo = vertexNo;
		this.next = next;
		this.value = value;
		this.logValue = Math.log(value);
	}
}
