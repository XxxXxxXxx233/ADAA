package lab11;

import java.io.*;
import java.util.*;

public class FaFaTa {
	
	private static int n;
	private static int p;
	private static int[] A;
	private static int[] exp_to_r;
	private static int[] r_to_exp;
	private static double pi = Math.PI;
	private static int len = 1;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		n = in.nextInt();
		p = in.nextInt();
		A = new int[n];
		exp_to_r = new int[p];
		r_to_exp = new int[p];
		
		for(int i=0; i<n; i++) {
			A[i] = in.nextInt();
		}
		
//		long t1 = System.currentTimeMillis();
		long[] ans = FaFaFa();
		long[] ans2 = bf();
		for(int i=0; i<ans.length; i++) {
			out.println(i + " = " + ans[i] + " , " + ans2[i]);
			if(ans[i] != ans2[i])
				System.out.println("FALSE!");
		}
//		long t2 = System.currentTimeMillis();
//		out.println("Time = " + (t2-t1));
		
		out.close();
	}
	
	public static long[] bf() {
		long[] ans = new long[p];
		long multi;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				multi = (long)A[i] * A[j];
				ans[(int)(multi % p)]++;
			}
		}
		return ans;
	}
	
	public static long[] FaFaFa() {
		long[] ans = new long[p];
		
		int root = findRoot(p);
		int re;
//		ArrayList<Integer> arr = new ArrayList<>();
		for(int i=1; i<p; i++) {
			re = quickMod(root, i, p);
			exp_to_r[i] = re;
			r_to_exp[re] = i;
/*			if(arr.contains(re)) {
				System.out.println("Dup!!!!");
			}
			arr.add(re);*/
		}
		
		int[] coe = new int[p];
		int e;
		long count = 0;
		for(int i=0; i<n; i++) {
			re = A[i] % p;
			if(re == 0) {
//				System.out.println(A[i]);
				count++;
				continue;
			}
			e = r_to_exp[A[i] % p];
			coe[e]++;
		}
		
/*		for(int i=0; i<p; i++) {
			if(coe[i] != 0)
				System.out.print(coe[i] + "x^" + i + " ");
		}
		System.out.println();*/
		
		while(len < p*2) {
			len = len * 2;
		}
		Complex123[] a = new Complex123[len];
		for(int i=0; i<p; i++) {
			a[i] = new Complex123(coe[i], 0);
		}
		for(int i=p; i<len; i++) {
			a[i] = new Complex123(0, 0);
		}
		
		a = fft(len, a);
		for(int i=0; i<len; i++) {
			a[i] = a[i].multi(a[i]);
		}
		a = ifft(len, a);
		
		int[] C = new int[len];
		for(int i=0; i<2*p-1; i++) {
			C[i] += (int)(a[i].real + 0.5);
//			C[i+1] += C[i]/10;
//			C[i] = C[i] % 10;
		}
		
/*		for(int i=0; i<len; i++) {
			if(C[i] != 0)
				System.out.print(C[i] + "x^" + i + " ");
		}
		System.out.println();*/
		
		int r;
		int exp;
		for(int i=1; i<C.length; i++) {
			if(C[i] == 0) {
				continue;
			}
			exp = i % (p-1);
			if(exp == 0)
				exp = p-1;
			r = exp_to_r[exp];
//			System.out.println("i = " + i + " , exp = " + exp + " , r = " + r);
			ans[r] = ans[r] + C[i];
		}
		
		ans[0] = (int)((2*n - count) * count);
		
		return ans;
	}
	
	public static Complex123[] fft(int n, Complex123[] a) {
		if(n == 1) {
			return a;
		} else {
			int half = n/2;
			Complex123[] even = new Complex123[half];
			Complex123[] odd = new Complex123[half];
			for(int i=0; i<half; i++) {
				even[i] = a[i*2];
				odd[i] = a[i*2 + 1];
			}
			even = fft(half, even);
			odd = fft(half, odd);
			
			Complex123[] ans = new Complex123[n];
			double temp;
			for(int k=0; k<half; k++) {
				temp = (-1 * k * 2*pi)/n;
				Complex123 wk = new Complex123(Math.cos(temp), Math.sin(temp));
				ans[k] = even[k].add(wk.multi(odd[k]));
				ans[k+half] = even[k].sub(wk.multi(odd[k]));
			}
			return ans;
		}
	}
	
	public static Complex123[] ifft(int n, Complex123[] a) {
		if(n == 1) {
			return a;
		} else {
			int half = n/2;
			Complex123[] even = new Complex123[half];
			Complex123[] odd = new Complex123[half];
			for(int i=0; i<half; i++) {
				even[i] = a[i*2];
				odd[i] = a[i*2 + 1];
			}
			even = ifft(half, even);
			odd = ifft(half, odd);
			
			Complex123[] ans = new Complex123[n];
			double temp;
			for(int k=0; k<half; k++) {
				temp = (k * 2*pi)/n;
				Complex123 wk = new Complex123(Math.cos(temp), Math.sin(temp));
				ans[k] = even[k].add(wk.multi(odd[k]));
				ans[k+half] = even[k].sub(wk.multi(odd[k]));
			}
			if(n == len) {
				for(int i=0; i<n; i++) {
					ans[i] = ans[i].div(n);
				}
			}
			return ans;
		}
	}
	
	public static int findRoot(int num) {
		ArrayList<Integer> primeFactor = new ArrayList<>();
		int temp = num-1;
		for(int i=2; i<=(int)Math.sqrt(num); i++) {
			if(temp % i == 0) {
				primeFactor.add(i);
				while(temp % i == 0)
					temp = temp / i;
			}
		}
		for(int i=2; i<=num-1; i++) {
			boolean isRoot = true;
			for(int j=0; j<primeFactor.size(); j++) {
				if(quickMod(i, (num-1)/primeFactor.get(j), num) == 1) {
					isRoot = false;
					break;
				}
			}
			if(isRoot) {
				return i;
			}
		}
		return 0;
	}
	
	public static int quickMod(int number, int power, int mod) {
		long ans = 1;
		long t = number % mod;
		while(power != 0) {
			if(power % 2 == 1) {
				ans = (ans * t) % mod;
			}
			power = power / 2;
			t = (t * t) % mod;
		}
		return (int)ans;
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

class Complex123{
	double real;
	double imag;
	
	public Complex123(double re, double im) {
		this.real = re;
		this.imag = im;
	}
	
	public Complex123 add(Complex123 c) {
		return new Complex123(this.real + c.real, this.imag + c.imag);
	}
	
	public Complex123 sub(Complex123 c) {
		return new Complex123(this.real - c.real, this.imag - c.imag);
	}
	
	public Complex123 multi(Complex123 c) {
		return new Complex123(this.real*c.real - this.imag*c.imag, this.real*c.imag + this.imag*c.real);
	}
	
	public Complex123 div(int n) {
		return new Complex123(this.real/n, this.imag/n);
	}
	
}
