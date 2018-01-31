package logic_6;

import java.util.Arrays;

import utilize.DeretAngka;

public class Logic_3 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public int getMaxVertical(int n){
		return n * n;
	}
	
	public int getMaxHorizontal(int n){
		int result = -1;
		for(int i = 0; i < n; i++){
			result = result + 2;
		}
		return result * 2;
	}
	
	public void setData(int n){
		this.baris = getMaxVertical(n);
		this.kolom = getMaxHorizontal(n);
		data = new String[this.baris][this.kolom];
		int[] dataFib = DeretAngka.getFibonachi(n*n, false);
		System.out.println(Arrays.toString(dataFib));
		
		int addBangun = 0;
		for(int bangun = 0; bangun < n; bangun++){
			int angka = 0;
			int angka2 = 1;
			for(int i  = 0; i < this.baris; i++){
				for(int j = 0; j < this.kolom; j++){
					if(i + j >= n -1 && -i +j <= n - 1 && i <= n - 1 && j <= this.kolom / 2 - 1){
						if(bangun % 2 == 0){
							data[i+ addBangun][j] = dataFib[angka++] + "";
						} else {
							data[i+ addBangun][j + (this.kolom / 2)] = angka2 + "";
							angka2 = angka2 + 2;
						}
						
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
		Logic_3 template = new Logic_3();
		template.setData(3);
		template.showData();
	}

}
