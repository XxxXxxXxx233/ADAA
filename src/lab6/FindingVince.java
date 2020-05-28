package lab6;

import java.io.*;
import java.util.*;

public class FindingVince {
	
	private static topNode[] A;
	private static int MAX = Integer.MAX_VALUE;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		int m = in.nextInt();
		
		A = new topNode[n];
		for(int i=0; i<n; i++) {
			A[i] = new topNode(i, null);
		}
		
		for(int i=0; i<m; i++) {
			int from = in.nextInt() - 1;
			int to = in.nextInt() - 1;
			int value = in.nextInt();
			edgeNode edge = new edgeNode(to, null, value);
			edgeNode temp = A[from].adj;
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
		
		for(int i=0; i<n; i++) {
			A[i].bad = in.nextInt();
			A[i].good = in.nextInt();
			A[i].total = A[i].good + A[i].bad;
		}
		
/*		for(int i=0; i<A.length; i++) {
			out.print("List of " + (A[i].num) + " with size " + A[i].size + " is: ");
			edgeNode cur = A[i].adj;
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
		long[] ans = new long[length];
		boolean[] visited = new boolean[length];
		ans[begin] = 0;
		
		for(int i=0; i<length; i++) {
			if(i != begin)
				ans[i] = MAX;
		}
		
		PriorityQueue<priorityNode> q = new PriorityQueue<>();
		q.add(new priorityNode(begin, ans[begin]));
		
		int No, vertexNo;
		long time, wait;
		edgeNode cur = null;
		while(!q.isEmpty()) {
			priorityNode temp = q.poll();
			No = temp.No;
			if(!visited[No]) {
				visited[No] = true;
				cur = A[No].adj;
				while(cur != null) {
					vertexNo = cur.vertexNo;
					if(!visited[vertexNo]) {
						time = ans[No] + cur.value;
						wait = time % A[vertexNo].total;
//						System.out.println("From " + No + " to " + vertexNo + " time = " + time + " wait = " + wait);
						if(wait >= 0 && wait < A[vertexNo].bad) {
							wait = A[vertexNo].bad - wait;
//							System.out.println("wait = " + wait);
							if(time + wait < ans[vertexNo]) {
								ans[vertexNo] = time + wait;
								q.add(new priorityNode(vertexNo, ans[vertexNo]));
							}
						} else {
							if(time < ans[vertexNo]) {
								ans[vertexNo] = time;
								q.add(new priorityNode(vertexNo, ans[vertexNo]));
							}
						}
					}
					cur = cur.next;
				}
			}
		}
		
		return ans[target];
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

class topNode{
	int num;
	int size;
	int bad;
	int good;
	int total;
	edgeNode adj;
	
	public topNode(int num, edgeNode adj) {
		this.num = num;
		this.adj = adj;
	}
}

class edgeNode{
	int vertexNo;
	edgeNode next;
	int value;
	
	public edgeNode(int vertexNo, edgeNode next, int value) {
		this.vertexNo = vertexNo;
		this.next = next;
		this.value = value;
	}
}
