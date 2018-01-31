package utilize;

public class MyArray {

	//Array
	String[][] data = null;
	int kolom =0;
	int baris =0;
	
	//set data
	public void setDataArray(int n){
		this.kolom = n;
		this.baris = n;
		this.data = new String[this.baris][this.kolom];
		
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				if(i == j || +i + j == n - 1 || j == n / 2 || i == n / 2 ){
					data[i][j] = i + "." + j;
				}
				
			}
		}
		
	}
	
	//menampilkan data array
	public void showData(int n){
		setDataArray(n);
		
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				System.out.print(data[i][j]+ "\t");
			}
			System.out.println("");
		}
	}
	
	
	
	//main
	public static void main(String args[]){
		MyArray ma = new MyArray();
		ma.showData(9);
	}
}
