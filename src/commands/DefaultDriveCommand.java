package commands;

import lejos.robotics.Color;
import robot.Robot;

public class DefaultDriveCommand implements Command {
	
	private double speed;
	
	public DefaultDriveCommand(double speed) {
		this.speed = speed;
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute() {
		if (Robot.io.getLeftColor() == Color.RED) {
			Robot.drive.stop();
		}
		Robot.drive.setVel((int) speed);
		Robot.drive.goForward();
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
