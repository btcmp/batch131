package logic_1;

public class Soal6 {

	public static void main(String[] args){
		int n = 9;
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(j <=i ){
					System.out.print("* \t");
				} else {
					System.out.print(" \t");
				}
			}
			System.out.println();
		}	
		
	}
}
