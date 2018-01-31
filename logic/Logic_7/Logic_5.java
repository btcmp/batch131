package Logic_7;

public class Logic_5 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = (n*2)*2 - 1;
		this.kolom = (n*2)*2 - 1;
		
		int angka = this.baris / 2 + n - 2;
		data = new String[this.baris][this.kolom];
		for(int i  = 0; i < this.baris; i++){
			int angka2 = 1;
			for(int j = 0; j < this.kolom; j++){
				//sisi kiri
				if(i >= j && i + j <= this.kolom - 1){
					if(j % 2 == 0){
						data[i][j] = angka + "";
					}
				
				} else if(i + j == this.kolom - 1){
					if(j % 2 == 0){
						data[i][j] = angka + "";
					}
				//sisi atas
				}else if(i -j <= 0 && i+j <= this.kolom - 1){
					if(i % 2 == 0){
						data[i][j] = angka2 +"";
						if(j < this.kolom / 2)
							angka2++;
						else 
							angka2--;
					}
				//sisi kanan
				} else if(i -j <= 0){
					if(j % 2 == 0){
						data[i][j] = angka + "";
					}
				//sisi bawah
				} else {
					if(j % 2 == 0){
						data[i][j] = angka + "";
					}
					
				}
			}
			angka = angka - 1;
		}
	}
	
	public void showData(int n){
		for(int i  = 0; i < this.baris / 2 + n - 2; i++){
			for(int j = 0; j < this.kolom; j++){
				System.out.print(this.data[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Logic_5 template = new Logic_5();
		int n = 4;
		template.setData(n);
		template.showData(n);
	}

}
