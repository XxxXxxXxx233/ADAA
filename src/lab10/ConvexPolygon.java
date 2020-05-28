package lab10;

import java.io.*;
import java.util.*;

public class ConvexPolygon {
	
	private static int n;
	private static int[] after;
	private static int[] index;
	private static int[] x;
	private static int[] y;
	private static PointPoly[] p;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		n = in.nextInt();
		after = new int[n];
		index = new int[n];
		x = new int[n];
		y = new int[n];
		p = new PointPoly[n];
		for(int i=0; i<n; i++) {
			x[i] = in.nextInt();
			y[i] = in.nextInt();
			index[i] = i;
		}
		
		mergeSort(0, n-1);

		for(int i=0; i<n; i++) {
			p[i] = new PointPoly(x[after[i]], y[after[i]]);
		}
		
		out.println(Graham());
		
		out.close();
	}
	
	public static int Graham() {
		int top = 0;
		int[] stack = new int[n];
		boolean[] visited = new boolean[n];
		for(int i=0; i<n; i++) {
			while(top > 1 && cross(p[stack[top-1]], p[i], p[stack[top-2]]) < 0) {
				top--;
				visited[stack[top]] = false;
			}
			stack[top] = i;
			top++;
			visited[i] = true;
		}
		
		int temp = top;
		for(int i=n-2; i>=0; i--) {
			while(top > temp && cross(p[stack[top-1]], p[i], p[stack[top-2]]) < 0) {
				top--;
				visited[stack[top]] = false;
			}
			if(!visited[i]) {
				stack[top] = i;
				top++;
			}
		}
		
		return top;
	}
	
	public static double cross(PointPoly p1, PointPoly p2, PointPoly p3) {
		return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
	}

	public static double distance(PointPoly p1, PointPoly p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
	}
	
	public static double area(PointPoly p1, PointPoly p2, PointPoly p3) {
		double s = p1.x * p2.y + p2.x * p3.y + p3.x * p1.y - p1.x * p3.y - p2.x * p1.y - p3.x * p2.y;
		return s/2;
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
			if(rightIndex > r || (leftIndex <= center && x[index[leftIndex]] < x[index[rightIndex]])) {
				after[i] = index[leftIndex];
				i++;
				leftIndex++;
			} else if(rightIndex > r || (leftIndex <= center && x[index[leftIndex]] == x[index[rightIndex]])) {
				if(y[index[leftIndex]] <= y[index[rightIndex]]) {
					after[i] = index[leftIndex];
					i++;
					leftIndex++;
				} else {
					after[i] = index[rightIndex];
					i++;
					rightIndex++;
				}
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

class PointPoly{
	long x;
	long y;
	
	public PointPoly(long x, long y) {
		this.x = x;
		this.y = y;
	}
}
