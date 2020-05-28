package lab1;

import java.io.*;
import java.util.*;

public class StableMatch {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int number = in.nextInt();
		Map<String, girl> findW = new LinkedHashMap<>();
		boy[] b = new boy[number];
		girl[] g = new girl[number];
		String name = null;
		for(int i=0; i<number; i++) {
			name = in.next();
			b[i] = new boy(name);
			for(int j=1; j<number+1; j++) {
				b[i].preference.add(in.next());
			}
		}
		for(int i=0; i<number; i++) {
			name = in.next();
			g[i] = new girl(name);
			findW.put(name, g[i]);
			for(int j=1; j<number+1; j++) {
				g[i].preference.put(in.next(), j);
			}
		}
		
		boolean stableMatch = true;
		Stack<boy> manStack = new Stack<>();
		for(int i=number-1; i>=0; i--) {
			manStack.add(b[i]);
		}
		boy man = null;
		girl woman = null;
		boy currentMatch = null;
		int currentPrefer = -1;
		int manPrefer = -1;
		boolean findAMatch = false;
		while(!manStack.isEmpty()) {
			man = manStack.pop();
			findAMatch = false;
			while(true) {
				woman = findW.get(man.preference.get(man.currentOrder));
				if(!woman.isMatch) {
					man.curMatch = woman;
					man.isMatch = true;
					woman.curMatch = man;
					woman.isMatch = true;
					findAMatch = true;
					break;
				} else {
					currentMatch = woman.curMatch;
					currentPrefer = woman.preference.get(currentMatch.name);
					manPrefer = woman.preference.get(man.name);
					if(manPrefer < currentPrefer) {
						man.curMatch = woman;
						man.isMatch = true;
						woman.curMatch = man;
						currentMatch.curMatch = null;
						currentMatch.isMatch = false;
						currentMatch.currentOrder++;
						manStack.add(currentMatch);
						findAMatch = true;
						break;
					} else {
						man.currentOrder++;
					}
				}
			}
			if(!findAMatch) {
				stableMatch = false;
				break;
			}
		}
		if(stableMatch) {
			for(int i=0; i<number; i++) {
				out.println(b[i].name + " " + b[i].curMatch.name);
			}
		} else {
			out.println("impossible");
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

class boy{
	String name;
	boolean isMatch;
	girl curMatch;
	ArrayList<String> preference = new ArrayList<>();
	int currentOrder;
	
	public boy(String name) {
		this.name = name;
	}
}

class girl{
	String name;
	boolean isMatch;
	boy curMatch;
	Map<String, Integer> preference = new LinkedHashMap<>();
	
	public girl(String name) {
		this.name = name;
	}
}
