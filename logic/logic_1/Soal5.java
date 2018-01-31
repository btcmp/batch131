package logic_1;

public class Soal5 {

	public static void main(String args[]){
int n = 9;
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(+i + j == n - 1){
					System.out.print("* \t");
				}else if(+i -j == 0){
					System.out.print("* \t");
				} else if(j == n / 2){
					System.out.print("* \t");
				}
				else if(i == n / 2){
					System.out.print("* \t");
				}else if(i == 0){
					System.out.print("* \t");
				}
				else if(j == 0){
					System.out.print("* \t");
				}
				else if(j == n - 1){
					System.out.print("* \t");
				}
				else if(i == n - 1){
					System.out.print("* \t");
				}
				else {
					System.out.print(" \t");
				}
			}
			System.out.println();
		}	
	}
}
