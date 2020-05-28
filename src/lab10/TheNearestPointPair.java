package lab10;

import java.io.*;
import java.util.*;

public class TheNearestPointPair {
	
	private static int n;
	private static int k;
	private static Point[] p;
	private static Integer[] inside;
	  
	private static Comparator<Integer> x_com = new Comparator<Integer>() {

		@Override
		public int compare(Integer p1, Integer p2) {
			if(p[p1].x < p[p2].x)
				return -1;
			else
				return 1;
		}
		
	};
	
	private static Comparator<Integer> y_com = new Comparator<Integer>() {

		@Override
		public int compare(Integer p1, Integer p2) {
			if(p[p1].y < p[p2].y)
				return -1;
			else
				return 1;
		}
		
	};
	
	private static Comparator<Integer> y_then_z_com = new Comparator<Integer>() {

		@Override
		public int compare(Integer p1, Integer p2) {
			if((p[p1].y < p[p2].y) || (p[p1].y == p[p2].y && p[p1].z < p[p2].z))
				return -1;
			else
				return 1;
		}
		
	};
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		InputReader in = new InputReader(inputStream);

		n = in.nextInt();
		k = in.nextInt();
		p = new Point[n];
		inside = new Integer[n];
		if(k == 1) {
			for(int i=0; i<n; i++) {
				p[i] = new Point(in.nextInt());
			}
		} else if(k == 2) {
			for(int i=0; i<n; i++) {
				p[i] = new Point(in.nextInt(), in.nextInt());
			}
		} else {
			for(int i=0; i<n; i++) {
				p[i] = new Point(in.nextInt(), in.nextInt(), in.nextInt());
			}
		}
		
		Arrays.sort(p, new Comparator<Point>() {

			@Override
			public int compare(Point p1, Point p2) {
				if(p1.x < p2.x)
					return -1;
				else
					return 1;
			}
			
		});
		PointPair ans = ClosestPair(0, n-1);
		printPair(ans);
	}

	public static void printPair(PointPair pp) {
		OutputStream outputStream = System.out;
		PrintWriter out = new PrintWriter(outputStream);
		
		Point p1 = p[pp.point1];
		Point p2 = p[pp.point2];
		
		if((p1.x < p2.x) || (p1.x == p2.x && p1.y < p2.y) || (p1.x == p2.x && p1.y == p2.y && p1.z < p2.z)) {
			if(k == 1) {
				out.println(p1.x);
				out.println(p2.x);
			} else if(k == 2) {
				out.println(p1.x + " " + p1.y);
				out.println(p2.x + " " + p2.y);
			} else {
				out.println(p1.x + " " + p1.y + " " + p1.z);
				out.println(p2.x + " " + p2.y + " " + p2.z);
			}
		} else {
			if(k == 1) {
				out.println(p2.x);
				out.println(p1.x);
			} else if(k == 2) {
				out.println(p2.x + " " + p2.y);
				out.println(p1.x + " " + p1.y);
			} else {
				out.println(p2.x + " " + p2.y + " " + p2.z);
				out.println(p1.x + " " + p1.y + " " + p1.z);
			}
		}
		out.close();
	}
	
	public static PointPair ClosestPair(int l, int r) {
		PointPair ans;
		
		if(r - l == 1) {
			ans = new PointPair(l, r);
			ans.dis = findDistance(l, r);
		} else if(r - l == 2) {
			if(k == 1) {
				double d1 = findDistance(l, l+1);
				double d2 = findDistance(l+1, r);
				if(d1 <= d2) {
					ans = new PointPair(l, l+1);
					ans.dis = d1;
				} else {
					ans = new PointPair(l+1, r);
					ans.dis = d2;
				}
			} else {
				double d1 = findDistance(l, l+1);
				double d2 = findDistance(l, r);
				double d3 = findDistance(l+1, r);
				if(d1 < d2 && d1 < d3) {
					ans = new PointPair(l, l+1);
					ans.dis = d1;
				} else if(d2 < d1 && d2 < d3) {
					ans = new PointPair(l, r);
					ans.dis = d2;
				} else {
					ans = new PointPair(l+1, r);
					ans.dis = d3;
				}
			}
		} else {
			int center = l + (r - l) / 2;
			PointPair Qpair = ClosestPair(l, center);
			PointPair Rpair = ClosestPair(center, r);
			PointPair minPair;
			if(Qpair.dis <= Rpair.dis) {
				minPair = Qpair;
			} else {
				minPair = Rpair;
			}
			int count = 0;
			for(int i=l; i<=r; i++) {
				if(Math.abs(p[i].x - p[center].x) < minPair.dis) {
					inside[count] = i;
					count++;
				}
			}
			if(count > 0) {
				double temp;
				if(k == 1) {
					Arrays.sort(inside, 0, count-1, x_com);
					for(int i=0; i<count-1; i++) {
						temp = findDistance(inside[i], inside[i+1]);
						if(temp < minPair.dis) {
							minPair.point1 = inside[i];
							minPair.point2 = inside[i+1];
							minPair.dis = temp;
						}
					}
				} else if(k == 2) {
					Arrays.sort(inside, 0, count-1, y_com);
					for(int i=0; i<count-1; i++) {
						for(int j=i+1; j<count; j++) {
							if(Math.abs(p[inside[i]].y - p[inside[j]].y) >= minPair.dis) {
								break;
							}
							temp = findDistance(inside[i], inside[j]);
							if(temp < minPair.dis) {
								minPair.point1 = inside[i];
								minPair.point2 = inside[j];
								minPair.dis = temp;
							}
						}
					}
				} else {
					Arrays.sort(inside, 0, count-1, y_then_z_com);
					for(int i=0; i<count-1; i++) {
						for(int j=i+1; j<count; j++) {
							if(Math.abs(p[inside[i]].y - p[inside[j]].y) >= minPair.dis) {
								break;
							}
							if(Math.abs(p[inside[i]].z - p[inside[j]].z) >= minPair.dis) {
								break;
							}
							temp = findDistance(inside[i], inside[j]);
							if(temp < minPair.dis) {
								minPair.point1 = inside[i];
								minPair.point2 = inside[j];
								minPair.dis = temp;
							}
						}
					}
				}
			}
			ans = minPair;
		}
		
		return ans;
	}
	
	public static double findDistance(int p1, int p2) {
		if(k == 1) {
			return distance_dim1(p[p1], p[p2]);
		} else if (k == 2) {
			return distance_dim2(p[p1], p[p2]);
		} else {
			return distance_dim3(p[p1], p[p2]);
		}
	}
	
	public static double distance_dim1(Point p1, Point p2) {
		return Math.abs(p1.x - p2.x);
	}
	
	public static double distance_dim2(Point p1, Point p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
	}
	
	public static double distance_dim3(Point p1, Point p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y) + (p1.z - p2.z) * (p1.z - p2.z));
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

class Point{
	long x;
	long y;
	long z;
	
	public Point(long x) {
		this.x = x;
		this.y = 0;
		this.z = 0;
	}
	
	public Point(long x, long y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	public Point(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
}

class PointPair{
	int point1;
	int point2;
	double dis;
	
	public PointPair(int point1, int point2) {
		this.point1 = point1;
		this.point2 = point2;
	}
}

