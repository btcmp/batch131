package Logic_7;

public class Logic_6 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.kolom = (n + 1) * n - 1;
		this.baris = n;
		data = new String[this.baris][this.kolom];
		int addBangun = 0;
		for(int bangun = 0; bangun < n; bangun++){
			for(int i  = 0; i < this.baris; i++){
				for(int j = 0; j < this.kolom; j++){
					if(i == 0 && j < n - 1 || j == 0 || j == n - 1|| i == n - 1 && j <= n - 1){
						data[i][j + (n + 1) * addBangun] = "*";
					}
				}
			}
			addBangun = addBangun + 1;
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
		Logic_6 template = new Logic_6();
		template.setData(4);
		template.showData();
	}

}
