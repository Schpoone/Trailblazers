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
	
	public IO(EV3UltrasonicSensor sanic, EV3ColorSensor left, EV3ColorSensor right, EV3GyroSensor gyro, RegulatedMotor sonicMotor) {
		this.sanic = sanic;
		this.sanic.enable();
		this.leftColor = left;
		this.rightColor = right;
		this.gyro = gyro;
		this.sanicMotor = sonicMotor;
	}
	
	
	public int getSanicDistance() {
		float[] arr = new float[1];
		sanic.getDistanceMode().fetchSample(arr, 0);
		return (int) arr[0];
	}
	
	
	
}
