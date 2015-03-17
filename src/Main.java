

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import robot.Robot;

public class Main {
	public static Robot eve;
	
	public static void main(String[] args) {
		Button.ENTER.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key k) {
				if(k.getId() == Button.ENTER.getId()) {
					System.exit(0);
				}
				
			}

			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub
				
			}
			
		});
		eve = new Robot();
		
		//while (eve.isRunning()){
			eve.runPath();
			System.out.println("Main");
		//}
	}
	
}
