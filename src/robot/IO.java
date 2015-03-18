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
	
	/**
	 * [ultraDistance (meters),leftColorID (int),rightColorID (int),gyroAngle (degrees),gyroRate (degrees/sec)]
	 */
	protected float[] buffer = new float[8];
	
	public IO() {
		System.out.println("IO init start");
		initSensors();
		System.out.println("IO init end");
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
		return buffer[0]*100;
	}
	
	/**
	 * Gets the color detected under the left color sensor
	 * @return an int that matches the IDs of a color
	 */
	public int getLeftColor() {
		return (int) buffer[1];
	}
	
	/**
	 * Gets the color detected under the right color sensor
	 * @return an int that matches the IDs of a color
	 */
	public int getRightColor() {
		return (int) buffer[2];
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
		return buffer[3];
	}
	
	/**
	 * Gets the angular velocity of the gyro sensor
	 * @return angular velocity in degrees per second
	 */
	public float getGyroAngVel() {
		return buffer[4];
	}
	
	/**
	 * reads the values from the sensors into an array [ultraDistance (meters),leftColorID (int),rightColorID (int),gyroAngle (degrees),gyroRate (degrees/sec)]
	 */
	public float[] read() {
		Robot.ultra.getDistanceMode().fetchSample(buffer, 0);
		Robot.leftColor.getColorIDMode().fetchSample(buffer, 1);
		Robot.rightColor.getColorIDMode().fetchSample(buffer, 2);
		Robot.gyro.getAngleMode().fetchSample(buffer, 3);
		Robot.gyro.getRateMode().fetchSample(buffer, 4);
		return buffer;
	}
	
	/**
	 * I DON'T KNOW HOW TO IMPLEMENT THE SANIC MOTOR
	 */
	
}
