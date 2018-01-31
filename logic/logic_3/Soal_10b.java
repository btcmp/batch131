package logic_3;

import java.util.Arrays;

import utilize.DeretAngka;

public class Soal_10b {

	String[][] data;
	int kolom;
	int baris;
	
	public void setDataArray(int n){
		this.baris = n;
		this.kolom = n;
		this.data = new String[this.baris][this.kolom];
		String[] deret = DeretAngka.genapGanjilFibonachi(n);
		System.out.println(Arrays.toString(deret));
		
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				//arsiran atas
				if(i <= j && i + j <= n -1){
					data[i][j] = deret[i]+"";
				} else if(i >= j && i + j >= n - 1){
					data[i][j] = deret[i]+"";
				} else {
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
		Soal_10b jawab = new Soal_10b();
		jawab.setDataArray(11);
		jawab.showData();
	}
}
