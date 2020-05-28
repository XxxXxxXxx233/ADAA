package lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import java.io.File;
import java.lang.reflect.Method;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class RunningTimeSurvey {
	//             task name            function name             run times upper
	static String[][] taskList = { 
			{ "LinearTimeTest",         "linearTime",            "100000000" },
			{ "LinearTimeTest",         "linearTimeCollections", "10000000" },
			{ "NlognTimeTest",       	"NlognTime",             "1000000"},
			{ "QuadraticTimeTest",   	"QuadraticTime",         "100000"},
			{ "CubicTimeTest",       	"CubicTime",             "1000"},
			{ "ExponentialTimeTest", 	"ExponentialTime",       "10"},
			{ "FactorialTimeTest",   	"FactorialTime",         "10"}
			 
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String osName = System.getProperty("os.name");
		System.out.println(osName);
		try {
			File xlsFile = new File("RunningTimeSurvey.xls");
			// Create a workbook
			WritableWorkbook workbook;
			workbook = Workbook.createWorkbook(xlsFile);

			// Create a worksheet
			WritableSheet sheet = workbook.createSheet("RunningTime", 0);
			// the first row
			for (int j = 1, n = 1; j <= 8; j++) {
				n = 10 * n;
				sheet.addCell(new Label(j + 1, 0, "n = " + n));
			}
			for (int i = 0; i < taskList.length; i++) {
				String[] taskInfo = taskList[i];

				Class<?> me = Class.forName(Thread.currentThread().getStackTrace()[1].getClassName());
				Method method = me.getMethod(taskInfo[1], int.class);
				int upper = Integer.parseInt(taskInfo[2]);
				sheet.addCell(new Label(0, i + 1, taskInfo[0]));
				sheet.addCell(new Label(1, i + 1, taskInfo[1]));

				for (int j = 1, n = 1; Math.pow(10, j) <= upper; j++) {
					n = 10 * n;
					Long time = (Long) method.invoke(null, n);
					// 向工作表中添加数据
					sheet.addCell(new Label(j + 1, i + 1, time.toString()));
				}

			}
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static long linearTimeCollections(int n) {
		ArrayList<Long> arrayList = new ArrayList<Long>(n);
		generateArrayList(n, arrayList);
		long timeStart = System.currentTimeMillis();
		getMax(n, arrayList);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static long linearTime(int n) {
		long[] list = new long[n];
		generateList(n, list);
		long timeStart = System.currentTimeMillis();
		getMax(n, list);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}

	public static long getMax(long n, long[] list) {
		long max = list[0];
		for (int i = 1; i < n; i++) {
			if (list[i] > max) {
				max = list[i];
			}
		}
		return max;
	}

	public static void generateList(int n, long[] list) {
		for (int i = 0; i < n; i++) {
			list[i] = i;
		}
		// shuffle
		Random rnd = new Random();
		for (int i = list.length; i > 1; i--) {
			int j = rnd.nextInt(i);
			long temp = list[j];
			list[j] = list[i - 1];
			list[i - 1] = temp;
		}
	}

	public static void generateArrayList(int n, ArrayList<Long> arrayList) {
		for (long i = 0; i < n; i++) {
			arrayList.add(i);
		}
		// shuffle
		Collections.shuffle(arrayList);
	}

	public static long getMax(long n, ArrayList<Long> arrayList) {
		long max = arrayList.get(0);
		for (int i = 1; i < n; i++) {
			if (arrayList.get(i) > max) {
				max = arrayList.get(i);
			}
		}
		return max;
	}

	public static long NlognTime(int n) {
		long[] list = new long[n];
		generateList(n, list);
		long timeStart = System.currentTimeMillis();
		mergeSort(list, 0, n-1);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}
	
	public static long QuadraticTime(int n) {
		long[] xlist = new long[n];
		generateList(n, xlist);
		long[] ylist = new long[n];
		generateList(n, ylist);
		long timeStart = System.currentTimeMillis();
		long min = (xlist[0] - xlist[1]) * (xlist[0] - xlist[1]) + (ylist[0] - ylist[1]) * (ylist[0] - ylist[1]);
		for(int j=1; j<n; j++) {
			for(int k=j+1; k<n; k++) {
				long distance = (xlist[j] - xlist[k]) * (xlist[j] - xlist[k]) + (ylist[j] - ylist[k]) * (ylist[j] - ylist[k]);
				if(distance < min) {
					min = distance;
				}
			}
		}
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}
	
	public static long CubicTime(int n) {
		int[] fullArr = new int[n];
		for(int i=0; i<n; i++) {
			fullArr[i] = i;
		}
		boolean[][] partBool = new boolean[n][n];
		int[][] partArr = new int[n][];
		for(int i=0; i<n; i++) {
			int l = (int)(Math.random() * n);
			int r = (int)(Math.random() * (n-l) + l);
			int length = r - l;
			partArr[i] = new int[length];
			for(int j=0; j<length; j++) {
				partArr[i][j] = fullArr[j + l];
				partBool[i][j+l] = true;
			}
			
		}
		long timeStart = System.currentTimeMillis();
		int pair = 0;
		for(int j=0; j<n; j++) {
			for(int k=0; k<n; k++) {
				if(k == j)
					continue;
				boolean check = true;
				for(int i=0; i<partArr[j].length; i++) {
					int number = partArr[j][i];
					if(partBool[j][number]) {
						check = false;
					}
				}
				if(check) {
					pair++;
				}
			}
		}
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}
	
	public static long ExponentialTime(int n) {
		long[] list = new long[n];
		long timeStart = System.currentTimeMillis();
		allNumber(0, n, list);
		long ans = count;
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}
	
	public static long FactorialTime(int n) {
		long timeStart = System.currentTimeMillis();
		long ans = Factorial(n);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		return timeCost;
	}
	
	public static long Factorial(int n) {
		if (n == 1)
			return 1;
		else {
			long sum = 0;
			for (int i = 0; i < n; i++) {
				sum += Factorial(n - 1);
			}
			return sum;
		}
	}
	
	static long count = 0;
	public static void allNumber(int m, int n, long[] list) {
		if(m == n) {
			count++;
		} else {
			list[m] = 0;
			allNumber(m+1, n, list);
			list[m] = 1;
			allNumber(m+1, n, list);
		}
	}
	
	public static void mergeSort(long[] arr, int l, int r) {
		if(l >= r)
			return;
		int center = l + (r - l) / 2;
		mergeSort(arr, l, center);
		mergeSort(arr, center + 1, r);
		merge(arr, l, center, r);
	}
	
	public static void merge(long[] arr, int l, int center, int r) {
		int leftLength = center - l + 1;
		int rightLength = r - center;
		long[] leftArr = new long[leftLength];
		long[] rightArr = new long[rightLength];
		for(int i=0; i<leftLength; i++)
			leftArr[i] = arr[l + i];
		for(int i=0; i<rightLength; i++)
			rightArr[i] = arr[center + 1 + i];
	
		int i = 0;
		int j = 0;
		int k = l;
		
		while(i<leftLength && j<rightLength) {
			if(leftArr[i] <= rightArr[j]) {
				arr[k] = leftArr[i];
				i++;
				k++;
			} else {
				arr[k] = rightArr[j];
				j++;
				k++;
			}
		}
		while(i<leftLength) {
			arr[k] = leftArr[i];
			i++;
			k++;
		}
		while(j<rightLength) {
			arr[k] = rightArr[j];
			j++;
			k++;
		}
	}
	
}

