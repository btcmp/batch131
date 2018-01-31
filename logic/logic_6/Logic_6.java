package logic_6;

public class Logic_6 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n*n;
		this.kolom = n*n;
		data = new String[this.baris][this.kolom];
		int addBlock = 0;
		for(int block = 0; block < n; block++){
			int addBangun = 0;
			for(int bangun = 0; bangun < n; bangun++){
				for(int i  = 0; i < this.baris; i++){
					for(int j = 0; j < this.kolom; j++){
						boolean a = j + i >= n/2 && j -i <= n/2 && i -j <= n/2;
						boolean b = i + j <= (n/2) + (n - 1);
						if(a && b){
							if(bangun  == block){
								data[i+ addBlock][j + addBangun] = "#";
							} else if(bangun + block == n - 1){
								data[i+ addBlock][j + addBangun] = "#";
							}
							
						}	
					}
				}
				addBangun = addBangun + n;
			}
			addBlock = addBlock + n;
		}
		
		
	}
	
	public void showData(){
		for(int i  = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				if(data[i][j] != null){
					System.out.print(this.data[i][j]+"\t");
				}
				else {
					System.out.print("\t");
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Logic_6 template = new Logic_6();
		template.setData(3);
		template.showData();
	}

}
