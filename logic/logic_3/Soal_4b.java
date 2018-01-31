package logic_3;

import java.util.Arrays;

import utilize.DeretAngka;

public class Soal_4b {

	String[][] data;
	int kolom;
	int baris;
	
	public void setDataArray(int n){
		this.baris = n;
		this.kolom = n;
		this.data = new String[this.baris][this.kolom];
		int[] deret = DeretAngka.getFibonachi(n, true);
		
		System.out.println(Arrays.toString(deret));
		
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				if(i <= j){
					data[i][j] = deret[j]+"";
				} else if(i + j >= n - 1){
					data[i][j] = deret[j]+"";
				}
					
			}
		}
	}
	
	public void showData(){
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				
				System.out.print(data[i][j] + "\t");
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args){
		Soal_4b jawab = new Soal_4b();
		jawab.setDataArray(9);
		jawab.showData();
	}
}
