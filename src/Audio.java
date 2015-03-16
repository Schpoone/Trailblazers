import java.io.File;

import lejos.hardware.Sound;

public class Audio {
	
	public Audio(Robot robot) {
		Sound.playSample(new File("wall-e.wav"), Sound.VOL_MAX);
	}
	
}
