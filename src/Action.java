
public abstract class Action {
	public static final int
			NO_ACTION = -1,
			REST_ACTION = 0,
			DRIVE_ACTION = 1,
			
			LEFT_CORRECTION_ACTION = 10,
			RIGHT_CORRECTION_ACTION = 11,
			
			LEFT_TURN_ACTION = 20,
			RIGHT_TURN_ACTION = 21,
			FORWARD_TURN_ACTION = 22,
			
			LEFT_PARK_ACTION = 30,
			RIGHT_PARK_ACTION = 31,
			LEFT_UNPARK_ACTION = 40,
			RIGHT_UNPARK_ACTION = 41,
			
			VICTORY_LAP_ACTION = 9001
		;
	
	public Driver driver;
	public int
			step = 0,
			type = NO_ACTION
		;
	public float
			trim = 0,
			throttle = 0,
			lerp = 0
		;
	public Action
			nextAction = this
		;
	
	public Action(Driver d,int t) {
		this.driver = d;
		this.type = t;
	}
	
	public abstract void act(int leftColor, int rightColor);
}
