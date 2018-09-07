import mind.Santini;
import utils.CalcUtils;

public class Main {
	public static void main(String[] args) {
		if (args[0] == null || args[1] == null) System.exit(-1);
		if (args[0].equals("") || args[1].equals("")) System.exit(-2);
		//Santini.playSweetWater();
		System.out.println("Starting SANTINI version 1.0.2 ...");
		for (; ; ) {
			Santini dolores = new Santini(args[0], args[1]);
			dolores.gatherMindData();
			dolores.gatherPredictionData();
			new CalcUtils().sleeper(16000);
		}
	}
}
