package Logic_7;

public class Logic_9 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n*n;
		this.kolom = n*n;
		data = new String[this.baris][this.kolom];
		int addBlock = 0;
		int angka = 1;
		boolean addTrue = false;
		for(int block = 0; block < n; block++){
			int addBangun = 0;
			for(int bangun = 0; bangun < n; bangun++){
				
				for(int i  = 0; i < this.baris; i++){
					for(int j = 0; j < this.kolom; j++){
					// do logic 	
						boolean diagonalPositiv = i < n && j < n && i + j == n - 1;
						boolean batasBawah = i < n && j < n && i == n - 1;
						boolean batasKanan = i < n && j < n && j == n - 1;
						boolean batasKiri = i < n && j < n && j == 0;
						boolean diagonalNegativ = i < n && j < n && i - j == 0;
						
						if(diagonalPositiv || batasBawah || batasKanan){
							if(block == 0){
								data[i+addBlock][j+addBangun] = angka + "";
								addTrue = true;
							}else if(bangun == 0){
								data[i+addBlock][j+addBangun] = angka + "";
								addTrue = true;
							} else if(block == n - 1){
								data[i+addBlock][j+addBangun] = angka + "";
								addTrue = true;
							} else if(bangun == n - 1){
								data[i+addBlock][j+addBangun] = angka + "";
								addTrue = true;
							}
							
						}
					}
				}
				addBangun = addBangun + n;
				if(addTrue == true){
					angka++;
					addTrue = false;
				}
				
			}
			addBlock = addBlock + n;
			
			
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
		Logic_9 template = new Logic_9();
		template.setData(4);
		template.showData();
	}

}
