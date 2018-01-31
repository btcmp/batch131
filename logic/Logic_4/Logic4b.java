package Logic_4;

import java.util.Arrays;

import utilize.DeretHuruf;

public class Logic4b {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = n;
		this.kolom = n * 2 -1;
		
		String[] dataHuruf = DeretHuruf.getChar(n * 2, false);
		System.out.println(Arrays.toString(dataHuruf));
		
		data = new String[this.baris][this.kolom];
		for(int i  = 0; i < this.baris; i++){
			int a = 0;
			for(int j = 0; j < this.kolom; j++){
				//do logic
				if((i +j >= n - 1) && (j <= n - 1 + i)){
					data[i][j] = dataHuruf[a++] +"";//"*";
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
		Logic4b template = new Logic4b();
		template.setData(9);
		template.showData();
	}

}
