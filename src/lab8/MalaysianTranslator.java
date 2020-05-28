package lab8;

import java.io.*;
import java.util.*;

public class MalaysianTranslator {
	
	private static long sum = 0;
	private static long parent = 0;
	
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int T = in.nextInt();
		for(int t=0; t<T; t++) {
			String str = in.next();
			char[] arr = str.toCharArray();
			
			int charIndex = 97;
			long[] frequency = new long[26];
			ArrayList<Long> list = new ArrayList<>();

			for(int i=0; i<arr.length; i++) {
				frequency[(int)arr[i] - charIndex]++;
			}
			
			list.add((long) -1);
			int count = 1;
			for(int i=0; i<26; i++) {
				if(frequency[i] != 0) {
					list.add(frequency[i]);
					insertion(list, count);
					count++;
				}
			}
			
			if(list.size() <= 2) {
				out.println(list.get(1));
			} else {
				huffmanTree(list);
				out.println(sum);
				sum = 0;
			}
		}
		
		out.close();
	}
	
	public static void huffmanTree(ArrayList<Long> list) {
		while(list.size() > 2) {
			delete_min(list);
			delete_min(list);
			list.add(parent);
			insertion(list, list.size()-1);
			parent = 0;
		}
	}
	
	public static void insertion(ArrayList<Long> list, int num) {
		long temp;
		while(num/2 > 0) {
			if(list.get(num/2) >= list.get(num)) {
				temp = list.get(num/2);
				list.set(num/2, list.get(num));
				list.set(num, temp);
				num = num/2;
			} else {
				break;
			}
		}
	}
	
	public static void delete_min(ArrayList<Long> list) {
		parent += list.get(1);
		sum += list.get(1);
		int rightMost = list.size() - 1;
		list.set(1, list.get(rightMost));
		list.remove(rightMost);
		rightMost--;
		
		int par = 1;
		int child = 2;
		long temp;
		while(child <= rightMost) {
			if(child+1 <= rightMost && list.get(child) > list.get(child+1)) {
				child++;
			}
			if(list.get(child) >= list.get(par)) {
				break;
			} else {
				temp = list.get(child);
				list.set(child, list.get(par));
				list.set(par, temp);
				par = child;
				child = par * 2;
			}
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
