package utilize;

import java.util.Arrays;
import java.util.Collections;

public class DeretAngka {

	//untuk mendapatkan nilai 1 - n, example : 1,2,3,4..dst.
	public static int[] getDeret_1(int n, boolean reverse){
		int[] data = new int[n];
		
		int a = 1;
		for(int i = 0; i < n; i++){
			data[i] = a;
			
			if(reverse == true && i >= n / 2)
				a = a - 1;
			else
				a = a + 1;
		}
		
		return data;
	}
	
	//1, 3, 5 dst..
	public static int[] getAngkaKebalik(int start, int penambahan, int n){
		int[] data = new int[n];
		
		int hasil = start;
		for(int i = 0; i < n; i++){
			data[i] = hasil;
			hasil = hasil + penambahan;
		}
		
		return data;
	}
	
	//example : 0, 2, 4, 6, dst..
	public static int[] getDeret_2(int start, int n, boolean reverse){
		int[] data = new int[n];
		int[] dataTrue = new int[n];
		
		int a = start;
		for(int i = 0; i < n; i++){
			data[i] = a;
			
			if(reverse == true && i >= n / 2)
			a = a - 2;
			else
			a = a + 2;
		}
		return data;
	}
	
	public static int[] getFibonachi(int n, boolean reverse){
		int[] data = new int[n]; //1,1,2,3,5,8
		int[] dataTrue = new int[n]; //1,1,2,1,1
		data[0] = 1;
		data[1] = 1;
		//if false
		for(int i = 2; i < n; i++){
			data[i] = data[i - 1] + data[i - 2];	
		}
		//reverse true
		int a = 0;
		for(int i = 0; i < n; i++){
			dataTrue[i] = data[a];
			if(i < n/2){
				a = a + 1;
			} else {
				a = a - 1;
			}
		}
		if(reverse == true){
			return dataTrue;
		} 
		
		return data;
	}
	
	
	
	public static int[] triFibonachi(int n, boolean reverse){
		int[] data = new int[n];
		int[] dataTrue = new int[n];
		
		data[0] = 1;
		data[1] = 1;
		data[2] = 1;
		
		for(int i = 3; i < n; i++){
			data[i] = data[i - 1] + data[i - 2] + data[i - 3];
		}
		
		//if true
		int a = 0;
		for(int i = 0; i < n; i++){
			dataTrue[i] = data[a];
			if(i < n/2){
				a = a + 1;
			} else {
				a = a - 1;
			}
		}
		
		if(reverse == true){
			return dataTrue;
		}
		
		return data;
	}
	
	//[1, 0, 3, 0, 5, 0, 3, 0, 1]
	public static String[] genapGanjil(int n){
		String[] data = new String[n];
		int[] deret = getDeret_1(n, true);
		
		for(int i = 0; i < n; i++){
			if(i % 2 == 0){
				data[i] = deret[i] + "";
			} else {
				data[i] = " ";
			}
		}
		return data;
	}
	
	public static String[] getChar(int n){
		return new String[]{
			"A", "B", "C", "D", "E", "F", "G"	
		};
	}
	
	//1, 3, 6, 10, 15... dst => hasil akhir
	public static int getTriAngularResult(int n){
		int result = 0;
		for(int i = 1; i <= n; i++){
			result = result + i;
		}
		
		return result;
	}
	
	public static int[] getTriAngular(int n){
		int[] data = new int[n];
		
		int result = 0;
		int index = 0;
		for(int i = 1; i <= n; i++){
			data[index] = result;
			result = result + i;
			index++;
		}
		
		return data;
	}
	
	public static String[] genapGanjilFibonachi(int n){
		String[] data = new String[n];
		String[] dataChar = getChar(n);
		
		int[] dataFib = getFibonachi(n, true);
		System.out.println(Arrays.toString(dataFib));
		
		int[] deret = getDeret_1(n, true);
		
		int a = 0;
		int ab = 0;
		for(int i = 0; i < n; i++){
			if(i % 2 == 0){
				if(i <= n / 2){
					data[i] = dataFib[a++] + "";
				} else {
					data[i] = dataFib[--a] + "";
				}
			} else {
				data[i] = dataChar[a - 1]+"";
			}
		}
		return data;
	}
	
	//n =3, =>output : 3,2,1,2,3
	public static int[] getIncrementABC(int n){
		//menghitung nilai panjang array
		int maxArray = -1;
		for(int i = 0; i < n; i++){
			maxArray = maxArray + 2;
		}
		
		int[] data = new int[maxArray];
		//set data
		int hasil = n;
		for(int i = 0; i < data.length; i++){
			data[i] = hasil;
			if(i < data.length / 2)
				hasil = hasil - 1;
			else
				hasil = hasil + 1;	
		}
		
		return data;
	}
	
	public static void main(String args[]){
		System.out.println(getTriAngularResult(5));
	}
	
}
