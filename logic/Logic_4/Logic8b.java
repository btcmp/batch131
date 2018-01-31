package Logic_4;

import java.util.Arrays;

import utilize.DeretAngka;

public class Logic8b {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n;
		this.kolom = n;
		data = new String[this.baris][this.kolom];
		
		int loop = 1;
		for(int i  = 0; i < this.baris; i++){
			int a = 0;
			int[] dataArray = DeretAngka.getDeret_2(1, loop , true);
			System.out.println(Arrays.toString(dataArray));
			for(int j = 0; j < this.kolom; j++){
				// do logic 
				if(i + j >= n / 2 && j - i <= n / 2 && i - j <= n /2 && i + j <= ((n / 2) + (n - 1))){
					data[i][j] = dataArray[a++] + "";
				}	
			}
			
			if(i < n / 2)
				loop = loop + 2;
			else 
				loop = loop - 2;
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
		Logic8b template = new Logic8b();
		template.setData(9);
		template.showData();
	}

}
