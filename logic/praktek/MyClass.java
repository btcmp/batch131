package praktek;

public class MyClass {

	//variable global 
	int a = 5;
	int b = a;
	double ab = 5.0;
	
	//not primitive
	String datap = "";
	Double abc = 5.0;
	String[] data = new String[3];
	
	String[][] data2 = null;
	String[][][] data3 = null;
	String[] data4 = data;
	String[] data5 = data;
	
	//method / fungsi
	public Double getAvg(double a, double b){
		return a / b;
	}
	
	public String getUsername(){
		return this.datap;
	}
	
	//void
	public void setData(){
		this.datap = "aristo teles";
	}
    
	//main
	public static void main(String[] args){
		MyClass cc = new MyClass();
		cc.setData();
		String data = cc.getUsername();
		System.out.println("username : "+ data);
	}
}
