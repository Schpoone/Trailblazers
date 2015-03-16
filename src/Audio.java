import java.io.File;

import lejos.hardware.Sound;

public class Audio {
	
	public void call() {
		Sound.playSample(new File("wall-e.wav"), Sound.VOL_MAX);
	}
	
}
