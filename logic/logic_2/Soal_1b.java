package logic_2;

import java.util.Arrays;

import utilize.DeretAngka;

public class Soal_1b {

	//data array
	String[][] data;
	//kolom
	int kolom = 0;
	//baris
	int baris = 0;
	
	public void setDataArray(int n){
		this.kolom = n;
		this.baris = n;
		data = new String[this.baris][this.kolom];
		int[] deret = DeretAngka.getDeret_1(n, false);
		System.out.println(java.util.Arrays.toString(deret));
		
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				if(i == j){
					data[i][j] = deret[i]+"";
				}
			}
		}
	}
	
	public void showData(int n){
		setDataArray(n);
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				System.out.print(data[i][j] + "\t");
			} 
			System.out.println();
		}
	}
	
	public static void main(String args[]){
		Soal_1b jawab = new Soal_1b();
		jawab.showData(9);
	}
	
}
