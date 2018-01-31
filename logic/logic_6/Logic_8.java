package logic_6;

import java.util.Arrays;

import utilize.DeretAngka;

public class Logic_8 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n*n;
		this.kolom = n*n;
		data = new String[this.baris][this.kolom];
		int addBlock = 0;
		int[] dataArray = DeretAngka.getIncrementABC(n);
		int nilaiAwal = (n/2 +1);
		
		for(int block = 0; block < n; block++){
			System.out.println(nilaiAwal);
			//System.out.println(Arrays.toString(dataArray));
			int addBangun = 0;
			for(int bangun = 0; bangun < n; bangun++){
				for(int i  = 0; i < this.baris; i++){
					int output = nilaiAwal;
					for(int j = 0; j < this.kolom; j++){
						if(j + i >= n/2 && j -i <= n/2 && i -j <= n/2 && i + j <= (n/2) + (n - 1)){
							if(bangun  == block){
								data[i+ addBlock][j + addBangun] = output +"";
								if(j < n/2)
								output--;
								else 
								output++;	
							} 
						}	
					}
				}
				addBangun = addBangun + n;
			}
			addBlock = addBlock + n;
			nilaiAwal = nilaiAwal + (n/2 +1);
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
		Logic_8 template = new Logic_8();
		template.setData(5);
		template.showData();
	}

}
