package logic_3;

import java.util.Arrays;

import utilize.DeretAngka;

public class Soal_2b {	
	public void setDataArray(int n){
		int[] deret = DeretAngka.triFibonachi(n, false);
		System.out.println(Arrays.toString(deret));
	}
	
	public static void main(String[] args){
		Soal_2b jawab = new Soal_2b();
		jawab.setDataArray(9);
	}
}
