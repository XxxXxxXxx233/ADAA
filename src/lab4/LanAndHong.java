package lab4;

import java.io.*;
import java.util.*;

public class LanAndHong {
	
	private static long count = 0;
	private static int[] xMove = {1, 0, -1, 0};
	private static int[] yMove = {0, 1, 0, -1};
	private static topNodeLan[] A;
	private static boolean[] visited;
	private static int curVisited = 1;
	private static int size, n;
	private static topNodeLan cur;
	private static edgeNodeLan e;
	private static boolean bPrun;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		n = in.nextInt();
		A = new topNodeLan[n*n];
		visited = new boolean[n*n];
		for(int i=0; i<n*n; i++) {
			A[i] = new topNodeLan(i, null);
		}
		String str = "";
		int[][] arr = new int[n][n];
		for(int j=0; j<n; j++) {
			str = in.next();
			for(int k=0; k<str.length(); k++) {
				if(str.charAt(k) != 'x') {
					arr[j][k] = 1;
					size++;
				}
			}
		}
		int from, to;
		int x = 0;
		int y = 0;
		int xNext, yNext;
		int cal = 0;
		edgeNodeLan edge, temp;
		for(int i=0; i<n*n; i++) {
			from = i;
			if(arr[y][x] != 0) {
				for(int j=0; j<4; j++) {
					xNext = x + xMove[j];
					yNext = y + yMove[j];
					if(xNext>=0 && xNext<n && yNext>=0 && yNext<n && arr[yNext][xNext] == 1) {
						to = xNext + yNext * n;
						edge = new edgeNodeLan(to, null);
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
			}
			x++;
			cal++;
			if(cal == n) {
				y++;
				cal = 0;
				x = 0;
			}
		}
/*		for(int i=0; i<A.length; i++) {
			out.print("List of " + (A[i].num) + " with size " + A[i].size + " is: ");
			edgeNodeLan cur = A[i].adj;
			while(cur != null) {
				out.print((cur.vertexNo) + " ");
				cur = cur.next;
			}
			out.println();
		}*/
		
		int target = n*(n-1);
		visited[0] = true;
		dfs(0, target);
		out.println(count);
		out.close();
	}
	
	public static void dfs(int start, int target) {
		if(start == target) {
			if(curVisited == size)
				count++;
			return;
		}
		cur = A[start];
		e = cur.adj;
		int[] arr = new int[3];
		int availAble = 0;
		while(e != null) {
			if(!visited[e.vertexNo]) {
				arr[availAble] = e.vertexNo;
				availAble++;
			}
			e = e.next;
		}
		if(availAble == 0) {
			return;
		}
		int next = onlyOne(arr, availAble, target);
		if(next == -2) {
			return;
		} else if(next != -1) {
			visited[next] = true;
			curVisited++;
			dfs(next, target);
			curVisited--;
			visited[next] = false;
			return;
		}
		if(availAble == 2) {
			if(reachAble(arr[0], arr[1])) {
				for(int i=0; i<availAble; i++) {
					visited[arr[i]] = true;
					curVisited++;
					dfs(arr[i], target);
					curVisited--;
					visited[arr[i]] = false;
				}
			}
		} else if(availAble == 3) {
			visited[arr[0]] = true;
			bPrun = reachAble(arr[1], arr[2]);
			visited[arr[0]] = false;
			if(bPrun) {
				visited[arr[0]] = true;
				curVisited++;
				dfs(arr[0], target);
				curVisited--;
				visited[arr[0]] = false;
			}
			visited[arr[1]] = true;
			bPrun = reachAble(arr[0], arr[2]);
			visited[arr[1]] = false;
			if(bPrun) {
				visited[arr[1]] = true;
				curVisited++;
				dfs(arr[1], target);
				curVisited--;
				visited[arr[1]] = false;
			}
			visited[arr[2]] = true;
			bPrun = reachAble(arr[0], arr[1]);
			visited[arr[2]] = false;
			if(bPrun) {
				visited[arr[2]] = true;
				curVisited++;
				dfs(arr[2], target);
				curVisited--;
				visited[arr[2]] = false;
			}
		} else if(availAble == 1) {
			visited[arr[0]] = true;
			curVisited++;
			dfs(arr[0], target);
			curVisited--;
			visited[arr[0]] = false;
		}
	}
	
	public static int onlyOne(int[] arr, int num, int target) {
		int[] available = new int[num];
		int only = 0;
		int index = -1;
		boolean containOut = false;
		for(int i=0; i<num; i++) {
			if(arr[i] == target) {
				containOut = true;
			}
			cur = A[arr[i]];
			e = cur.adj;
			while(e != null) {
				if(!visited[e.vertexNo]) {
					available[i]++;
				}
				e = e.next;
			}
			if(available[i] == 1) {
				only++;
				index = i;
			}
		}
		
		if(only > 1 && !containOut) {
			return -2;
		} else if(only == 1 && !containOut) {
			return arr[index];
		}
		return -1;
	}
	
	public static boolean reachAble(int start, int end) {
		int[] queue = new int[size - curVisited];
		int front = 0;
		int rear = 0;
		queue[rear] = start;
		rear++;
		visited[start] = true;
		while(front < size-curVisited) {
			cur = A[queue[front]];
			front++;
			if(cur.num == end) {
				for(int i=0; i<rear; i++) {
					visited[queue[i]] = false;
				}
				return true;
			}
			e = cur.adj;
			while(e != null) {
				if(!visited[e.vertexNo]) {
					queue[rear] = e.vertexNo;
					rear++;
					visited[e.vertexNo] = true;
				}
				e = e.next;
			}
		}
		for(int i=0; i<rear; i++) {
			visited[queue[i]] = false;
		}
		return false;
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

class topNodeLan{
	int num;
	int size;
	edgeNodeLan adj;
	
	public topNodeLan(int num, edgeNodeLan adj) {
		this.num = num;
		this.adj = adj;
	}
}

class edgeNodeLan{
	int vertexNo;
	edgeNodeLan next;
	
	public edgeNodeLan(int vertexNo, edgeNodeLan next) {
		this.vertexNo = vertexNo;
		this.next = next;
	}
}
