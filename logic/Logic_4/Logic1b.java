package Logic_4;

public class Logic1b {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n;
		this.kolom = n * 2 -1;
		data = new String[this.baris][this.kolom];
		for(int i  = 0; i < this.baris; i++){
			int a = 0;
			for(int j = 0; j < this.kolom; j++){
				//do logic
				if((i +j >= n - 1) && (j <= n - 1 + i)){
					data[i][j] = ++a +"";//"*";
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
		Logic1b template = new Logic1b();
		template.setData(9);
		template.showData();
	}

}
