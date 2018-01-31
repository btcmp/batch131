package logic_1;

public class Soal8 {

	public static void main(String args[]){
int n = 9;
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				//segita kebawah
				if((i <= j) && (i +j <= n -1)){
					System.out.print("# \t");
				} else if(i >= j && i + j >= n - 1){
					System.out.print("# \t");
				} else {
					System.out.print(" \t");
				}
			}
			System.out.println();
		}	
	}
}
