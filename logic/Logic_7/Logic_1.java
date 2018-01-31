package Logic_7;

import utilize.DeretAngka;

public class Logic_1 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = (n*2) * 2 - 1;
		this.kolom = (n*2) * 2 - 1;
		
		data = new String[this.baris][this.kolom];
		int[] dataFib = DeretAngka.getFibonachi(n*n, false);
		int[] dataTri = DeretAngka.triFibonachi(n*n, false);
		
		int angka1 = 1;
		int loop = 0;
		int loop1 = 0;
		for(int i  = 0; i < this.baris; i++){
			
			for(int j = 0; j <  this.kolom; j++){
				if(i + j >= this.baris / 2 && i -j <= this.baris / 2 && j -i <= this.baris / 2 && i +j <= this.baris + (this.baris / 2 - 1) ){
					//sisi kiri
					if(j >= 0 && j < n){
						data[i][j] = dataFib[loop] + "";
						loop++;
					//sisi atas
					} else if(i >= 0 && i < n){
						data[i][j] = angka1 + "";
						angka1 = angka1 + 2;
					//sisi bawah
					} else if(i >= n + this.baris / 2){
						data[i][j] = angka1 + "";
						angka1 = angka1 + 2;
					//sisi kanan
					} else if(j >= n + this.baris / 2 ){
						data[i][j] = dataTri[loop1] + "";
						loop1++;
					}else {
						data[i][j] = " ";
					}
					
				}
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
		Logic_1 template = new Logic_1();
		template.setData(4);
		template.showData();
	}

}
