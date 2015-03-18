package robot.commands;

import lejos.robotics.Color;
import robot.Robot;

public class ParkLeftCommand implements Command {

	private final int spdToGetInSpace = (int) Math.min(Robot.leftMotor.getMaxSpeed(), Robot.rightMotor.getMaxSpeed());

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		//Assuming we have found an open spot
		//idk how to write this in a way that you can keep looping through this
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < 100) {
			Robot.drive.setSpeed(spdToGetInSpace);
			Robot.drive.goForward();
		}
		Robot.drive.turnRight(0);//Turn right 90 degrees
		while (Robot.io.getLeftColor() == Color.BLUE || Robot.io.getRightColor() == Color.BLUE) {
			Robot.drive.setSpeed(spdToGetInSpace);//arbitrary speed
			Robot.drive.goBackward();
		}
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	@Override
	public void interrupted() {
		// TODO Auto-generated method stub

	}

}
