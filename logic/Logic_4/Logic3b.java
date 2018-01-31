package Logic_4;

public class Logic3b {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n;
		this.kolom = n;
		data = new String[this.baris][this.kolom];
		for(int i  = 0; i < this.baris; i++){
			int a = 0;
			for(int j = 0; j < this.kolom; j++){
				// do logic 
				if(i + j >= n / 2 && j - i <= n / 2 && i - j <= n /2 && i + j <= ((n / 2) + (n - 1))){
					data[i][j] = ++a + "";
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
		Logic3b template = new Logic3b();
		template.setData(9);
		template.showData();
	}

}
