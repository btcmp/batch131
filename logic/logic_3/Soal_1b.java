package logic_3;

import java.util.Arrays;

import utilize.DeretAngka;

public class Soal_1b {	
	public void setDataArray(int n){
		int[] deret = DeretAngka.getFibonachi(n, false);
		System.out.println(Arrays.toString(deret));
	}
	
	public static void main(String[] args){
		Soal_1b jawab = new Soal_1b();
		jawab.setDataArray(9);
	}
}
