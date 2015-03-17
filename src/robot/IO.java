package robot;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;

/**
 * This class will retrieve data from sensors and control motors
 * (There is only one motor controls the ultrasonic sensor
 * 
 * @author Jason
 */

public class IO {
	
	protected EV3UltrasonicSensor sanic;
	protected EV3ColorSensor leftColor;
	protected EV3ColorSensor rightColor;
	protected EV3GyroSensor gyro;
	
	protected RegulatedMotor sanicMotor;
	
	protected float[] buffer = new float[8];
	
	public IO() {
		initSensors();
	}
	
	/**
	 * This method turns on all of the sensors
	 */
	private void initSensors() {
		Robot.ultra.enable();
		Robot.gyro.reset();
	}
	
	/**
	 * Finds the distance in front of the ultrasonic sensor
	 * @return the distance in centimeters
	 */
	public float getSanicDistance() {
		Robot.ultra.getDistanceMode().fetchSample(buffer, 0);
		return buffer[0]*100;
	}
	
	/**
	 * Gets the color detected under the left color sensor
	 * @return an int that matches the IDs of a color
	 */
	public int getLeftColor() {
		return Robot.leftColor.getColorID();
	}
	
	/**
	 * Gets the color detected under the right color sensor
	 * @return an int that matches the IDs of a color
	 */
	public int getRightColor() {
		return Robot.rightColor.getColorID();
	}
	
	/**
	 * Sets the floodlights on each color sensor based on the params
	 * @param left sets the state of the left color sensor, true means on
	 * @param right sets the state of the right color sensor, true mean on
	 */
	public void setFloodlights(boolean left, boolean right) {
		Robot.leftColor.setFloodlight(left);
		Robot.rightColor.setFloodlight(right);
	}
	
	/**
	 * Gets the angle between the current position and the position of when it was last reset
	 * @return angle in degrees
	 */
	public float getGyroAngle() {
		Robot.gyro.getAngleMode().fetchSample(buffer, 0);
		return buffer[0];
	}
	
	/**
	 * Gets the angular velocity of the gyro sensor
	 * @return angular velocity in degrees per second
	 */
	public float getGyroAngVel() {
		Robot.gyro.getRateMode().fetchSample(buffer, 0);
		return buffer[0];
	}
	
	/**
	 * I DON'T KNOW HOW TO IMPLEMENT THE SANIC MOTOR
	 */
	
}
