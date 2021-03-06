package Logic_4;

import java.util.Arrays;

import utilize.DeretAngka;
import utilize.DeretHuruf;

public class Logic10b {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n, int jumlah){

		int kurang = jumlah - 1;
		this.baris = n;
		this.kolom = n * jumlah - kurang;
		
		data = new String[this.baris][this.kolom];
		for(int bangun = 0; bangun < jumlah; bangun++){
			int loop = 1;
			for(int i  = 0; i < this.baris; i++){
				int a = 0;
				String[] dataArray = DeretHuruf.getChar(loop, true);
				for(int j = 0; j < this.kolom; j++){
					// do logic 
					if(i + j >= n / 2 && j - i <= n / 2 && i - j <= n /2 && i + j <= ((n / 2) + (n - 1))){
						data[i][j + ((n - 1)*bangun) ] = dataArray[a] + "";
						data[i][j + (n - 1)] = dataArray[a] + "";
						a++;
					}	
				}
				
				if(i < n / 2)
					loop = loop + 2;
				else 
					loop = loop - 2;
			}
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
		Logic10b template = new Logic10b();
		template.setData(5, 2);
		template.showData();
	}

}
