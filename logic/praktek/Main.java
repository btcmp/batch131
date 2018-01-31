package praktek;

public class Main {

	public static void main(String[] args){
		//polimorphism
		Employee employee = new Employee();
		Employee manager = new Manager();
		Manager director = new Director();
		
		Director dirManager = (Director) new Manager();
		//Employee emp2 = (Employee)new String("4567890");
		
		short abc = 5;
		int abcd = abc;
		
		int ab = 5;
		short cd = (short)ab;
	}
}
