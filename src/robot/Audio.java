package robot;
import java.io.File;

import lejos.hardware.Sound;

public class Audio {
	
	public void call() {
		Sound.playSample(new File("wall-e.wav"), Sound.VOL_MAX);
	}
	
	public void onSight() {
		Sound.playSample(new File("eve.wav"), Sound.VOL_MAX);
	}
	
}
