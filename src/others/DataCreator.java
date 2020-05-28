package others;

import java.io.*;
import java.util.*;

public class DataCreator {
	public static void main(String[] args) throws FileNotFoundException {
		int n = 200000;
		long range = 200;
		PrintWriter p = new PrintWriter("data.txt");
		p.write(n + " \n");
		for(int i=0; i<n; i++) {
			long x = (long)(Math.random() * range);
			double check = Math.random();
			if(check > 0.5) {
				p.write(-1 + " ");
			} else {
				if(x == 0)
					x++;
				p.write(x + " ");
			}
		}
		p.write("\n");
		p.close();
	}
	
}
