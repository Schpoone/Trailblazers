
public class CorrectionAction extends Action {
	private Action action;
	private int
			lastIncident,
			correctionSteps,
			direction
		;
	final float
			turnAmount = 0.2f, // HARDCODE
			forwardAmount = 0.8f
		;
	
	public CorrectionAction(Driver d,Action a,int li,int dir) {
		super(d,(dir<0) ? Action.LEFT_CORRECTION_ACTION : Action.RIGHT_CORRECTION_ACTION);
		action = a;
		lastIncident = li;
		correctionSteps = (int) (50/Math.sqrt(li/500.0)); // HARDCODE
		direction = dir;
	}
	
	public void act(int leftColor,int rightColor) {
		if (step<correctionSteps) {
			throttle = forwardAmount;
			trim = -turnAmount*direction;
		}
		else if (step<correctionSteps*3/2) {
			throttle = forwardAmount;
			trim = turnAmount*direction;
		}
		else {
			throttle = 0;
			trim = 0;
			nextAction = action;
		}
	}
}
