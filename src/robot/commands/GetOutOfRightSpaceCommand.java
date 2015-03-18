package robot.commands;

import robot.IO;
import robot.Robot;

public class GetOutOfRightSpaceCommand implements Command {

	private final int spdToGetOutOfSpace = (int) Math.min(Robot.leftMotor.getMaxSpeed(), Robot.rightMotor.getMaxSpeed());

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		//idk how to write this in a way that you can keep looping through this
		Robot.io.setSanicAngle(IO.ANGLE_RIGHT);
		while (Robot.io.getSanicDistance() < Robot.collisionThreshold) {}
		Robot.io.setSanicAngle(IO.ANGLE_FORWARD);
		while (Robot.io.getSanicDistance() < Robot.collisionThreshold) {}
		Robot.io.setSanicAngle(IO.ANGLE_LEFT);
		while (Robot.io.getSanicDistance() < Robot.collisionThreshold) {}
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < 100) {
			Robot.drive.setSpeed(spdToGetOutOfSpace);
			Robot.drive.goForward();
		}
		Robot.drive.turnRight(0);//Turn right 90 degrees
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
