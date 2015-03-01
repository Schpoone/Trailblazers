import lejos.nxt.ColorSensor;
import lejos.nxt.NXTRegulatedMotor;

public class Driver {
	// Path is an n x 4 array
	// Node (0-71), Next Node, Turn, Length
	int[][] path;
	int currentPath;
	NXTRegulatedMotor leftMotor,rightMotor;
	ColorSensor leftSensor,rightSensor;
	Action currentAction;
	
	public Driver(int[][] p,NXTRegulatedMotor lm,NXTRegulatedMotor rm,ColorSensor ls,ColorSensor rs) {
		path = p;
		currentPath = 0;
		leftMotor = lm;
		rightMotor = rm;
		leftSensor = ls;
		rightSensor = rs;
		currentAction = new RestAction(this);//idk
	}
	
	public void run() {
		if (currentPath>=path.length) return; // End path for now
		
		// Get colors
		ColorSensor.Color c;
		c = leftSensor.getRawColor();
		int leftColor = ColorIdentifier.identify(c.getRed(),c.getGreen(),c.getBlue());
		c = rightSensor.getRawColor();
		int rightColor = ColorIdentifier.identify(c.getRed(),c.getGreen(),c.getBlue());
		
		// Perform the action
		currentAction.act(leftColor,rightColor);
		float leftMotorSpeed = currentAction.throttle;
		float rightMotorSpeed = currentAction.throttle;
		if (currentAction.trim<0) {
			leftMotorSpeed += 2*currentAction.throttle*currentAction.trim;//if trim is negative it's abs is subtracted from the left motors speed
		}
		else {
			rightMotorSpeed -= 2*currentAction.throttle*currentAction.trim;//if trim is positive it is subtracted from right motors speed
		}
		
		// Set the motors
		leftMotor.setSpeed(leftMotorSpeed);
		rightMotor.setSpeed(rightMotorSpeed);
		
		// Set the next behavior
		currentAction = currentAction.nextAction;
	}
	
	public void notifyPathCompletion() {
		currentPath++;
	}
	
	public int getCurrentPath() {
		return currentPath;
	}
	
	public int getTurn(int p) {
		return path[p][2]; // Turning
	}
	
	public boolean getSlow(int p) {
		return false; // TODO
	}
}
