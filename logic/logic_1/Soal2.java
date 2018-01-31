package logic_1;

public class Soal2 {

	public static void main(String args[]){
int n = 9;
		//(x, y)
/*
 * x = j = column
 * y = i = row
 * (x, y) => (y, x) / (i, j)
 * y, x
 * */

		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(+i - j == 0){
					System.out.print("* \t");
				} else if(+i +j == n - 1){
					System.out.print("* \t");
				} else {
					System.out.print(" \t");
				}
			}
			System.out.println();
		}	
	}
}
