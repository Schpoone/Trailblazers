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
	
<<<<<<< HEAD:src/robot/IO.java
	public IO() {
=======
	protected float[] buffer = new float[8];
	
	public IO(EV3UltrasonicSensor sanic, EV3ColorSensor left, EV3ColorSensor right, EV3GyroSensor gyro, RegulatedMotor sonicMotor) {
		this.sanic = sanic;
		this.leftColor = left;
		this.rightColor = right;
		this.gyro = gyro;
		this.sanicMotor = sonicMotor;
>>>>>>> 751c89b05abf7c311741dc72144723c6cf506ea5:src/IO.java
		initSensors();
	}
	
	/**
	 * This method turns on all of the sensors
	 */
	private void initSensors() {
<<<<<<< HEAD:src/robot/IO.java
		Robot.ultra.enable();
		Robot.gyro.reset();
=======
		sanic.enable();
		gyro.reset();
>>>>>>> 751c89b05abf7c311741dc72144723c6cf506ea5:src/IO.java
	}
	
	/**
	 * Finds the distance in front of the ultrasonic sensor
	 * @return the distance in centimeters
	 */
<<<<<<< HEAD:src/robot/IO.java
	public int getSanicDistance() {
		float[] arr = new float[1];
		Robot.ultra.getDistanceMode().fetchSample(arr, 0);
		return (int) arr[0]*100;
=======
	public float getSanicDistance() {
		sanic.getDistanceMode().fetchSample(buffer, 0);
		return buffer[0]*100;
>>>>>>> 751c89b05abf7c311741dc72144723c6cf506ea5:src/IO.java
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
<<<<<<< HEAD:src/robot/IO.java
	public int getGyroAngle() {
		float[] arr = new float[1];
		Robot.gyro.getAngleMode().fetchSample(arr, 0);
		return (int) arr[0];
=======
	public float getGyroAngle() {
		gyro.getAngleMode().fetchSample(buffer, 0);
		return buffer[0];
>>>>>>> 751c89b05abf7c311741dc72144723c6cf506ea5:src/IO.java
	}
	
	/**
	 * Gets the angular velocity of the gyro sensor
	 * @return angular velocity in degrees per second
	 */
<<<<<<< HEAD:src/robot/IO.java
	public int getGyroAngVel() {
		float[] arr = new float[1];
		Robot.gyro.getRateMode().fetchSample(arr, 0);
		return (int) arr[0];
=======
	public float getGyroAngVel() {
		gyro.getRateMode().fetchSample(buffer, 0);
		return buffer[0];
>>>>>>> 751c89b05abf7c311741dc72144723c6cf506ea5:src/IO.java
	}
	
	/**
	 * I DON'T KNOW HOW TO IMPLEMENT THE SANIC MOTOR
	 */
	
}
