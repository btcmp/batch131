package logic_6;

import java.util.Arrays;

import utilize.DeretAngka;

public class Logic_4 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n * n;
		this.kolom = n * n;
		data = new String[this.baris][this.kolom];
		int[] dataBalik = DeretAngka.getDeret_2(1,(int)Math.pow(n, 3), false);
		System.out.println(Arrays.toString(dataBalik));
		
		int addBangun = 0;
		int angka = 0;
		for(int bangun = 0; bangun < n; bangun++){
			for(int i  = 0; i < this.baris; i++){
				 int angka2 = angka + n - 1;
				
				for(int j = 0; j < this.kolom; j++){
					// do logic 
					if(j < n && i < n){
						if(i % 2 == 0){
							data[i + addBangun][j+ addBangun] = dataBalik[angka] + "";
						} else {
							data[i + addBangun][j+ addBangun] = dataBalik[angka2 - j] + "";
						}
						angka= angka + 1;
					}
				}
				
			}
			addBangun = addBangun + n;
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
		Logic_4 template = new Logic_4();
		template.setData(3);
		template.showData();
	}

}
