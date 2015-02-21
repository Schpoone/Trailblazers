import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorConstants;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.SensorSelector;


public class Main {
	public static void main(String args[]) throws FileNotFoundException {
		File pathFile = new File("Path.txt");
		Scanner fin = new Scanner(pathFile);
		ArrayList<String> lines = new ArrayList<String>();
		while (fin.hasNext()) {
			String line = fin.nextLine().trim();//puts each line of the path in an array list
			if (line.length()>0) {
				lines.add(line);
			}
		}
		
		int[][] path = new int[lines.size()][4];
		int nnum;
		String[] parts;
		for (int i=0; i<lines.size(); i++) {
			parts = lines.get(i).split(" ");
			nnum = Integer.parseInt(parts[0]);
			path[i][0] = (i==0) ? nnum : path[i-1][0];//the fuck is this someone gonna fail the ap, it should be
			/*if (i==0) {
				path[i][0]= nnum;
			}else {
				path[i][0]=path[i-1][0];
			}*/// not sure what this does
			path[i][1] = nnum;
			path[i][2] = Integer.parseInt(parts[1]);
			path[i][3] = Integer.parseInt(parts[2]);
		}
		
		Driver robot = new Driver(path,Motor.A,Motor.B,new ColorSensor(SensorPort.S1),new ColorSensor(SensorPort.S2));//makes the robot
		for (int i=0; i<100; i++) {//runs the robot
			robot.run();
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
