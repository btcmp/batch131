package logic_3;

import java.util.Arrays;

import utilize.DeretAngka;

public class Soal_3b {	
	public void setDataArray(int n){
		int[] deret = DeretAngka.getFibonachi(n, false);
		System.out.println(Arrays.toString(deret));
		/*int a = 0;
		for(int i = 0; i < n; i++){
			System.out.print(deret[a] + " \t");
			if(i < n / 2)
				a = a + 1;
			else 
				a = a - 1;
		}*/
	}
	
	public static void main(String[] args){
		Soal_3b jawab = new Soal_3b();
		jawab.setDataArray(9);
	}
}
