package lab1;

import java.io.*;
import java.util.*;

public class StableMatchMLE {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		
		int number = in.nextInt();
		Map<String, people> findPeople = new LinkedHashMap<>();
		people[] p = new people[number*2];
		String name = null;
		for(int i=0; i<number*2; i++) {
			name = in.next();
			p[i] = new people(name);
			findPeople.put(name, p[i]);
			for(int j=1; j<number+1; j++) {
				p[i].preference.put(in.next(), j);
			}
		}
		
		boolean stableMatch = true;
		Stack<people> manStack = new Stack<>();
		for(int i=number-1; i>=0; i--) {
			manStack.add(p[i]);
		}
		people man = null;
		Iterator<String> prefer = null;
		people woman = null;
		people currentMatch = null;
		int currentPrefer = -1;
		int manPrefer = -1;
		boolean findAMatch = false;
		while(!manStack.isEmpty()) {
			man = manStack.pop();
			prefer = man.preference.keySet().iterator();
			woman = null;
			findAMatch = false;
			while(prefer.hasNext()) {
				woman = findPeople.get(prefer.next());
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
						manStack.add(currentMatch);
						findAMatch = true;
						break;
					} else {
						
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
				out.println(p[i].name + " " + p[i].curMatch.name);
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

class people{
	String name;
	boolean isMatch;
	people curMatch;
	Map<String, Integer> preference = new LinkedHashMap<>();
//	String[] preferName;
	
	public people(String name) {
		this.name = name;
	}
}
