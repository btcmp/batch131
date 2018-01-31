package praktek;

public class Template {
	
	int baris = 0;
	int kolom = 0;
	String[][] area = null;
	
	//membuat area logika
	public void setData(int input){
		this.baris = input;
		this.kolom = input;
		this.area = new String[this.baris][this.kolom];
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				area[i][j] = "&";
			}
		}
	}
	
	//show area + data
	public void showData(){
		for(int i = 0; i < this.baris; i++){
			for(int j = 0; j < this.kolom; j++){
				System.out.print(this.area[i][j] + "\t");
			}
			System.out.println("");
		}
	}
	
	public static void main(String args[]){
		Template tmp = new Template();
		tmp.setData(9);
		tmp.showData();
	}
}
