package Logic_7;

public class Logic_3 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = (n*2)*2 - 1;
		this.kolom = (n*2)*2 - 1;
		
		data = new String[this.baris][this.kolom];
		for(int i  = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				//sisi kiri
				if(i >= j && i + j <= this.kolom - 1){
					if(j % 2 == 0){
						data[i][j] = "*";
					}
					
				//sisi atas
				} else if(i -j <= 0 && i+j <= this.kolom - 1){
					if(i % 2 == 0){
						data[i][j] = "*";
					}
				//sisi kanan
				} else if(i <= j){
					if(j % 2 == 0){
						data[i][j] = "*";
					}
				//sisi bawah
				} else {
					if(j % 2 == 0){
						data[i][j] = "*";
					}
					
				}
			}
		}
	}
	
	public void showData(int n){
		for(int i  = 0; i < this.baris / 2 + n - 1; i++){
			for(int j = 0; j < this.kolom; j++){
				if(this.data[i][j] != null){
					System.out.print(this.data[i][j]+"\t");
				} else{
					System.out.print("\t");
				}
				
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Logic_3 template = new Logic_3();
		int n = 3;
		template.setData(n);
		template.showData(n);
	}

}
