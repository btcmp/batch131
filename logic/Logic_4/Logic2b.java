package Logic_4;

public class Logic2b {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n;
		this.kolom = n * 2 -1 ;
		data = new String[this.baris][this.kolom];
		for(int i  = 0; i < this.baris; i++){
			// do logic 
			int a = 0;
			for(int j = 0; j < this.kolom; j++){
				if(j - i >= 0 && i + j <= (n - 1)*2){
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
		Logic2b template = new Logic2b();
		template.setData(5);
		template.showData();
	}

}
