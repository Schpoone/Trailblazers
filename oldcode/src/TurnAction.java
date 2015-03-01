
public class TurnAction extends Action {//this does all the things
	final int turnSteps = 170; // HARDCODE
	final float
			turnAmount = 0.33f, // HARDCODE
			forwardAmount = 0.8f
		;
	private int direction;
	
	public TurnAction(Driver d,int dir) {
		super(d,(dir==0) ? Action.FORWARD_TURN_ACTION : (dir<0) ? Action.LEFT_TURN_ACTION : Action.RIGHT_TURN_ACTION);//why
		direction = dir;
	}

	public void act(int leftColor,int rightColor) {//either turns or goes forward the correct amount
		if (step<turnSteps) {
			if (type==Action.FORWARD_TURN_ACTION) {
				throttle = 1;
				trim = 0;
			}
			else if (type==Action.LEFT_TURN_ACTION) {
				throttle = forwardAmount;
				trim = -turnAmount;
			}
			else if (type==Action.RIGHT_TURN_ACTION) {
				throttle = forwardAmount;
				trim = turnAmount;
			}
		}
		else {
			throttle = 0;
			trim = 0;
			driver.notifyPathCompletion();
			nextAction = new DriveAction(driver);
		}
	}
}
