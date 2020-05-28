package lab11;

import java.io.*;
import java.util.*;

public class FaFaTa3 {
	
	private static int n;
	private static int p;
	private static int[] A;
	private static int[] exp_to_r;
	private static int[] r_to_exp;
	private static double pi = Math.PI;
	private static int len = 1;
	private static double temp;
	
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
		
		long t1 = System.currentTimeMillis();
		long[] ans = FaFaFa();
		for(int i=0; i<ans.length; i++) {
			out.println(ans[i]);
		}
		long t2 = System.currentTimeMillis();
		out.println("Time = " + (t2-t1));
		
		out.close();
	}
	
	public static long[] FaFaFa() {
		long[] ans = new long[p];
		
		int root = findRoot(p);
		int re;
		for(int i=1; i<p; i++) {
			re = quickMod(root, i, p);
			exp_to_r[i] = re;
			r_to_exp[re] = i;
		}
		
		int[] coe = new int[p];
		long count = 0;
		for(int i=0; i<n; i++) {
			re = A[i] % p;
			if(re == 0) {
				count++;
				continue;
			}
			coe[r_to_exp[A[i] % p]]++;
		}
		
		while(len < p*2) {
			len = len * 2;
		}
		Complex[] a = new Complex[len];
		for(int i=0; i<p; i++) {
			a[i] = new Complex(coe[i], 0);
		}
		for(int i=p; i<len; i++) {
			a[i] = new Complex(0, 0);
		}
		
		fft(len, a);
		for(int i=0; i<len; i++) {
			a[i] = a[i].multi(a[i]);
		}
		ifft(len, a);
		
		int[] C = new int[len];
		for(int i=0; i<2*p-1; i++) {
			C[i] += (int)(a[i].real + 0.5);
		}
		
		int r;
		int exp;
		for(int i=1; i<2*p-1; i++) {
			if(C[i] == 0) {
				continue;
			}
			exp = i % (p-1);
			if(exp == 0)
				exp = p-1;
			r = exp_to_r[exp];
			ans[r] = ans[r] + C[i];
		}
		
		ans[0] = (2*n - count) * count;
		
		return ans;
	}
	
	public static void fft(int n, Complex[] a) {
		if(n == 1) {
			return;
		} else {
			int half = n/2;
			Complex[] even = new Complex[half];
			Complex[] odd = new Complex[half];
			for(int i=0; i<half; i++) {
				even[i] = a[i*2];
				odd[i] = a[i*2 + 1];
			}
			fft(half, even);
			fft(half, odd);
			
			for(int k=0; k<half; k++) {
				temp = (-1 * k * 2*pi)/n;
				Complex wk = new Complex(Math.cos(temp), Math.sin(temp));
				a[k] = even[k].add(wk.multi(odd[k]));
				a[k+half] = even[k].sub(wk.multi(odd[k]));
			}
		}
	}
	
	public static void ifft(int n, Complex[] a) {
		if(n == 1) {
			return;
		} else {
			int half = n/2;
			Complex[] even = new Complex[half];
			Complex[] odd = new Complex[half];
			for(int i=0; i<half; i++) {
				even[i] = a[i*2];
				odd[i] = a[i*2 + 1];
			}
			ifft(half, even);
			ifft(half, odd);
			
			for(int k=0; k<half; k++) {
				temp = (k * 2*pi)/n;
				Complex wk = new Complex(Math.cos(temp), Math.sin(temp));
				a[k] = even[k].add(wk.multi(odd[k]));
				a[k+half] = even[k].sub(wk.multi(odd[k]));
			}
			if(n == len) {
				for(int i=0; i<n; i++) {
					a[i] = a[i].div(n);
				}
			}
		}
	}
	
	public static int findRoot(int num) {
		ArrayList<Integer> primeFactor = new ArrayList<>();
		int temp1 = num-1;
		for(int i=2; i<=(int)Math.sqrt(num); i++) {
			if(temp1 % i == 0) {
				primeFactor.add(i);
				while(temp1 % i == 0)
					temp1 = temp1 / i;
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

class Complex{
	double real;
	double imag;
	
	public Complex(double re, double im) {
		this.real = re;
		this.imag = im;
	}
	
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imag + c.imag);
	}
	
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imag - c.imag);
	}
	
	public Complex multi(Complex c) {
		return new Complex(this.real*c.real - this.imag*c.imag, this.real*c.imag + this.imag*c.real);
	}
	
	public Complex div(int n) {
		return new Complex(this.real/n, this.imag/n);
	}
	
}
