package Logic_4;

import java.util.Arrays;

import utilize.DeretAngka;

public class PreTest {

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
			int a = 1;
			for(int j = 0; j < this.kolom; j++){
				//arsiran atas
				if(i+j >= n / 2 && -i +j <= n / 2 && -j +i <= (n / 2) && +i +j <= (n/2 + (n-1))){
					data[i][j] = (a++) +"";
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
		PreTest jawab = new PreTest();
		jawab.setDataArray(9);
		jawab.showData();
	}
}
