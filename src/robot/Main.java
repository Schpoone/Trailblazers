package robot;

public class Main {
	public static Robot eve;
	
	public static void main(String[] args) {
		eve = new Robot();
		//while (eve.isRunning()){
			eve.runPath();
			System.out.println("Main");
		//}
	}
	
}
