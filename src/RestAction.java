
public class RestAction extends Action {
	public RestAction(Driver d) {
		super(d,Action.REST_ACTION);
	}
	
	public void act(int leftColor,int rightColor) {//does nothing
		trim = 0;
		throttle = 0;
		nextAction = this;
	}
}
