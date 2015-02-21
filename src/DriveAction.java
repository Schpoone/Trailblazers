
public class DriveAction extends Action {
	private final float
			slowSpeed = 0.5f,
			fullSpeed = 1f
		;
	private int
			lastIncident = 0,
			previousLeft = 0,
			previousRight = 0,
			path = 0, // Current path
			turn = 0 // Turn direction
		;
	private boolean isSlow;
	
	public DriveAction(Driver d) {//sets the action to the write parameters
		super(d,Action.DRIVE_ACTION);
		path = driver.getCurrentPath();
		turn = driver.getTurn(path);
		isSlow = driver.getSlow(path);
	}
	
	public void act(int leftColor,int rightColor) {
		if (leftColor==ColorIdentifier.BLACK && rightColor==ColorIdentifier.BLACK) {//if there is black on both sides
			throttle = (isSlow) ? slowSpeed : fullSpeed;// turnary operators are some trash
			/*if(isSlow){
				throttle=slowSpeed;
			}else{
				throttle=fullSpeed;
			}*/
			trim = 0;
		}
		else if (leftColor==ColorIdentifier.RED || rightColor==ColorIdentifier.RED) {//if there is red on both sides
			nextAction = new TurnAction(driver,turn);//make the next turn
		}
		else if (leftColor==ColorIdentifier.WHITE
				|| leftColor==ColorIdentifier.YELLOW
				|| leftColor==ColorIdentifier.GRAY) {
			nextAction = new CorrectionAction(driver,this,lastIncident,-1);//tries to fix mistakes if it goes off the path
			lastIncident = 0;
		}
		else if (rightColor==ColorIdentifier.WHITE
				|| rightColor==ColorIdentifier.YELLOW
				|| rightColor==ColorIdentifier.GRAY) {
			nextAction = new CorrectionAction(driver,this,lastIncident,1);//tries to fix mistakes if it goes off the path
			lastIncident = 0;
		}
		
		lastIncident++;
		step++;
	}
}
