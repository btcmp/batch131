import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Template {
	
	int baris = 0;
	int kolom = 0;
	String[][] data = null;
	
	public void setData(int n){
		this.baris = (n*2);
		this.kolom = (n* 2 - 1) * 3;
		
		data = new String[this.baris][this.kolom];
		for(int i  = 0; i < this.baris; i++){
			// do logic 
		for(int j = 0; j < this.kolom; j++){
			//segitiga atas
			if(i + j >= n - 1 && j - i <= n - 1 && i <= n - 1){
				data[i][j] = "*";
				data[i + 3][j + 5] = "*";
				data[i + 5][j + 7] = "*";
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
	
	public static void main(String[] args) throws Exception{
		Template template = new Template();
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		System.out.print("masukkan angka >>> ");
		int angka = Integer.parseInt(br.readLine());
		template.setData(angka);
		template.showData();
	}
}
