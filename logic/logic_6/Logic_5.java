package logic_6;

import java.util.Arrays;

public class Logic_5 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n*n;
		this.kolom = n*n;
		
		data = new String[this.baris][this.kolom];
		int[] dataIncrement = getIncrement((int)Math.pow(n, 3));
		System.out.println(Arrays.toString(dataIncrement));
		
		int addBlock = 0;
		int angka = 0;
		for(int block = 0; block < n; block++){
			int addBangun = 0;
			for(int bangun = 0; bangun < n; bangun++){
				for(int i  = 0; i < this.baris; i++){
					int angka2 = angka + n - 1;
					for(int j = 0; j < this.baris; j++){
						// do logic 
							if(i < n && j < n){
								if(block + bangun == n - 1 ){
									if(i %2 == 0){
										data[i + addBlock][j + addBangun] = dataIncrement[angka] + "";
									}else {
										data[i + addBlock][j + addBangun] = dataIncrement[angka2 - j] +"";
									}
									angka++;
								} else if(block == bangun){
									data[i + addBlock][j + addBangun] = "#";
								}
								
							}
						
						
					}
				}
				addBangun = addBangun + n;
			}
			addBlock = addBlock + n;
		}
	}
	
	public int[] getIncrement(int n){
		int[] data = new int[n];
		int angka = 1;
		for(int i = 0; i < n; i++){
			data[i]= angka;
			angka = angka + 2;
		}
		return data;
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
		Logic_5 template = new Logic_5();
		template.setData(5);
		template.showData();
	}

}
