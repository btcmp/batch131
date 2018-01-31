package Logic_4;

import java.util.Arrays;

import utilize.DeretAngka;

public class Logic7b {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n;
		this.kolom = n * 2 -1;
		
		data = new String[this.baris][this.kolom];
		int loop = this.kolom;
		for(int i  = 0; i < this.baris; i++){
			int a = 0;
			int[] dataArray = DeretAngka.getDeret_2(1, loop , true);
			System.out.println(Arrays.toString(dataArray));
			for(int j = 0; j < this.kolom; j++){
				//do logic
				if(j - i >= 0 && i + j <= (n - 1)*2){
					data[i][j] = dataArray[a++] + "";//"*";
				}
			}	
			loop = loop - 2 ;
		}
	}
	
	public void showData(){
		for(int i  = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				System.out.print(this.data[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Logic7b template = new Logic7b();
		template.setData(3);
		template.showData();
	}

}
