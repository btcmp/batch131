package Logic_7;

public class Logic_7 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.kolom = (n + 1) * n - 1;
		this.baris = n;
		data = new String[this.baris][this.kolom];
		int addBangun = 0;
		int angka2 = getMaxLipatEmpat(n);
		
		for(int bangun = 0; bangun < n; bangun++){
			int angka = 1;
			
			for(int i  = 0; i < this.baris; i++){
				int wow = angka + n;
				for(int j = 0; j < this.kolom; j++){
				if(i == 0 && j < n - 1){
					data[i][j + (n + 1) * addBangun] = angka++ + "";
				}else if(i == n - 1 && j <= n - 1){
					data[i][j + (n + 1) * addBangun] = (wow - j - 1) + "";
				}else if( j == n - 1 ){
					data[i][j + (n + 1) * addBangun] = angka++ + "";
				}else if(j == 0){
						data[i][j + (n + 1) * addBangun] = angka2-- + "";
					}
				}
			}
			addBangun = addBangun + 1;
		}
	}
	
	public int getMaxLipatEmpat(int n){
		int reslut = -4;
		for(int i = 0; i < n; i++){
			reslut = reslut + 4;
		}
		return reslut;
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
		Logic_7 template = new Logic_7();
		template.setData(3);
		template.showData();
	}

}
