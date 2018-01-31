package Logic_5;

import java.util.Arrays;

import utilize.DeretAngka;

public class Logic_2 {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = DeretAngka.getTriAngularResult(n);
		this.kolom = DeretAngka.getTriAngularResult(n);
		
		data = new String[this.baris][this.kolom];
		int[] dataArr = DeretAngka.getTriAngular(n+1);
		System.out.println(Arrays.toString(dataArr));
		
		for(int bangun  = 0; bangun  < n; bangun++){
			for(int i  = 0; i < bangun+1; i++){
				for(int j = 0; j < bangun+1; j++){
				// do logic
					data[j + this.kolom - dataArr[bangun+1]][i + dataArr[bangun]] = "#";
				}
			}
		}
		 
		/*for(int i  = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
			// do logic
				data[i + 1][j + this.kolom - 3] = "#";
			}
		}
		for(int i  = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
			// do logic
				data[i + 3][j + this.kolom - 6] = "#";
			}
		}*/
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
		Logic_2 template = new Logic_2();
		template.setData(3);
		template.showData();
	}

}
