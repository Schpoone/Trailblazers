package commands;

import lejos.robotics.Color;
import robot.Robot;
import robot.commands.Command;

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
		if (Robot.io.getLeftColor() == Color.RED && Robot.io.getRightColor() == Color.RED) {
			Robot.drive.stop();
		} else {
			if (Robot.io.getLeftColor() == Color.WHITE) {
				Robot.drive.setSpeedRight((int) (Robot.drive.getSpeedRight()*.8));
			}
			if (Robot.io.getRightColor() == Color.WHITE) {
				Robot.drive.setSpeedLeft((int) (Robot.drive.getSpeedLeft()*.8));
			}
			if (Robot.drive.getSpeedLeft() != Robot.drive.getSpeedRight()) {
				Robot.drive.setSpeed(Math.max(Robot.drive.getSpeedLeft(), Robot.drive.getSpeedRight()));
			}
				Robot.drive.setVel((int) speed);
				Robot.drive.goForward();
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
