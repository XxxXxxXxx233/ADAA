package lab7;

import java.io.*;
import java.util.*;

public class TheGreatMystery2 {
	
	private static edge[] e;
	private static int edgeSize = 0;
	private static int length;
	private static int[] parent;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		int m = in.nextInt();
		length = n*m;
		e = new edge[4 * length];
		int[] arr = new int[length];
		for(int i=0; i<length; i++) {
			arr[i] = in.nextInt();
		}
		
		int[] xMove = {1, 0, -1, 0};
		int[] yMove = {0, 1, 0, -1};
		int from, to, xNext, yNext;
		int x = 0;
		int y = 0;
		int cal = 0;
		int value = 0;
		for(int i=0; i<length; i++) {
			from = i;
			for(int j=0; j<4; j++) {
				xNext = x + xMove[j];
				yNext = y + yMove[j];
				if(xNext>=0 && xNext<m && yNext>=0 && yNext<n) {
					to = xNext + yNext * m;
					value = arr[from] ^ arr[to];
					e[edgeSize++] = new edge(from, to, value);
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
		
		long t1 = System.currentTimeMillis();
		out.println(kruskal());
		long t2 = System.currentTimeMillis();
		out.println("Time = " + (t2-t1));
		out.close();
	}
	
	public static long kruskal() {
		long sum = 0;
		parent = new int[length];
		PriorityQueue<edge> p = new PriorityQueue<>(new Comparator<edge>() {

			@Override
			public int compare(edge e1, edge e2) {
				return e1.value - e2.value;
			}
			
		});
		for(int i=0; i<length; i++) {
			parent[i] = i;
		}
		for(int i=0; i<edgeSize; i++) {
			p.add(e[i]);
		}
		System.out.println(edgeSize);
		boolean[] visited = new boolean[length];
		int count = 0;
		long t1 = System.currentTimeMillis();
		edge temp;
		int pFrom, pTo;
		while(!p.isEmpty()) {
			temp = p.poll();
			pFrom = findRoot(temp.from);
			pTo = findRoot(temp.to);
			if(pFrom != pTo) {
				sum += temp.value;
				parent[pTo] = pFrom;
				if(!visited[pFrom]) {
					visited[pFrom] = true;
					count++;
				}
				if(!visited[pTo]) {
					visited[pTo] = true;
					count++;
				}
				if(count == length) {
					System.out.println("BREAK");
					break;
				}
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("Time used to build MST: " + (t2-t1));
		
		return sum;
	}
	
	public static int findRoot(int number) {
		if(parent[number] == number)
			return number;
		parent[number] = findRoot(parent[number]);
		return parent[number];
	}
	
	public static void createMinHeap() {
		int parent, child;
		edge temp;
		for(int i=edgeSize/2; i>=1; i--) {
			parent = i;
			child = parent * 2;
			while(child <= edgeSize) {
				if(child+1 <= edgeSize && e[child].value > e[child+1].value) {
					child++;
				}
				if(e[child].value >= e[parent].value) {
					break;
				} else {
					temp = e[child];
					e[child] = e[parent];
					e[parent] = temp;
					parent = child;
					child = parent * 2;
				}
			}
		}
	}
	
	public static edge deleteMin() {
		edge cur = e[1];
		e[1] = e[edgeSize];
		edgeSize--;
		
		int parent = 1;
		int child = 2;
		edge temp;
		while(child <= edgeSize) {
			if(child+1 <= edgeSize && e[child].value > e[child+1].value) {
				child++;
			}
			if(e[child].value >= e[parent].value) {
				break;
			} else {
				temp = e[child];
				e[child] = e[parent];
				e[parent] = temp;
				parent = child;
				child = parent * 2;
			}
		}
		return cur;
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

class edge{
	int from;
	int to;
	int value;
	
	public edge(int from, int to, int value) {
		this.from = from;
		this.to = to;
		this.value = value;
	}
}
