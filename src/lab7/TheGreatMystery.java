package lab7;

import java.io.*;
import java.util.*;

public class TheGreatMystery {
	
	private static int MAX = Integer.MAX_VALUE;
	private static topNodeA[] A;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		int m = in.nextInt();
		int length = n*m;
		A = new topNodeA[length];
		for(int i=0; i<length; i++) {
			A[i] = new topNodeA(i, null);
		}
		int count = 0;
		int[][] arr = new int[n][m];
		for(int j=0; j<n; j++) {
			for(int k=0; k<m; k++) {
				arr[j][k] = in.nextInt();
				A[count].value = arr[j][k];
				count++;
			}
		}
		
		int[] xMove = {1, 0, -1, 0};
		int[] yMove = {0, 1, 0, -1};
		int from, to;
		int x = 0;
		int y = 0;
		int xNext, yNext;
		int cal = 0;
		edgeNodeA edge, temp;
		for(int i=0; i<length; i++) {
			from = i;
			for(int j=0; j<4; j++) {
				xNext = x + xMove[j];
				yNext = y + yMove[j];
				if(xNext>=0 && xNext<m && yNext>=0 && yNext<n) {
					to = xNext + yNext * m;
					edge = new edgeNodeA(to, null);
					edge.value = A[from].value ^ A[to].value;
					temp = A[from].adj;
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
			}
			x++;
			cal++;
			if(cal == m) {
				y++;
				cal = 0;
				x = 0;
			}
		}
		
/*		for(int i=0; i<A.length; i++) {
			out.print("List of " + (A[i].num+1) + " with size " + A[i].size + " is: ");
			edgeNodeA cur = A[i].adj;
			while(cur != null) {
				out.print((cur.vertexNo+1) + " cost = " + cur.value + ", ");
				cur = cur.next;
			}
			out.println();
		}*/
		long t1 = System.currentTimeMillis();
		out.println(prim(0));
		long t2 = System.currentTimeMillis();
		out.println("Time = " + (t2-t1));
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
		edgeNodeA cur = null;
		int No, vertexNo;
		while(!q.isEmpty()) {
			temp = q.poll();
			No = temp.No;
			if(!visited[No]) {
				visited[No] = true;
				cur = A[No].adj;
				sum += temp.distance;
				while(cur != null) {
					vertexNo = cur.vertexNo;
					if(!visited[vertexNo] && cur.value < ans[vertexNo]) {
						ans[vertexNo] = cur.value;
						q.add(new priorityNode(vertexNo, ans[vertexNo]));
					}
					cur = cur.next;
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

class topNodeA{
	int num;
	int size;
	int value;
	edgeNodeA adj;
	
	public topNodeA(int num, edgeNodeA adj) {
		this.num = num;
		this.adj = adj;
	}
}

class edgeNodeA{
	int vertexNo;
	int value;
	edgeNodeA next;
	
	public edgeNodeA(int vertexNo, edgeNodeA next) {
		this.vertexNo = vertexNo;
		this.next = next;
	}
}
