package lab14;

import java.io.*;
import java.util.*;

public class BallBallVince {
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int n = in.nextInt();
		int ballCount = 0;
		int c = 101;
		int sum = 0;
		
		Box[] b = new Box[n+1];
		for(int i=1; i<n+1; i++) {
			b[i] = new Box(in.nextInt(), in.nextInt());
			ballCount += b[i].current;
			b[i].c = c - b[i].capacity;
			sum += b[i].c;
		}
		b[0] = new Box(0, 0);
		b[0].c = Integer.MAX_VALUE;
		
		Arrays.sort(b, new Comparator<Box>() {

			@Override
			public int compare(Box b1, Box b2) {
				return b2.capacity - b1.capacity;
			}
			
		});
		
		int temp = ballCount;
		int m = 0;
		for(int i=0; i<n; i++) {
			temp -= b[i].capacity;
			m++;
			if(temp <= 0)
				break;
		}
		
		int upper = m * c - ballCount;
		int lastSelect = -1;
		int[][][] opt = new int[n+1][upper+1][m+1];
		int num1, num2;
		
		for(int i=1; i<n+1; i++) {
			for(int j=1; j<upper+1; j++) {
				for(int k=1; k<m+1; k++) {
					if(b[i].c > j) {
						opt[i][j][k] = opt[i-1][j][k];
					} else {
						num1 = opt[i-1][j][k];
						num2 = b[i].c + opt[i-1][j - b[i].c][k - 1];
						if(num1 > num2) {
							opt[i][j][k] = num1;
						} else if(num1 < num2) {
							if(lastSelect != -1) {
								ballCount += b[i].current;
							}
							opt[i][j][k] = num2;
							lastSelect = i;
							ballCount -= b[i].current;
						}
						opt[i][j][k] = Math.max(opt[i-1][j][k], b[i].c + opt[i-1][j - b[i].c][k - 1]);
					
					}
				}
			}
		}
		
		
		out.close();
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

class Box {
	
	int current;
	int capacity;
	int c;
	
	public Box(int current, int capacity) {
		this.current = current;
		this.capacity = capacity;
	}
	
}
