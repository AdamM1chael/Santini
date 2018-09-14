import mind.Santini;
import org.apache.log4j.Logger;
import utils.CalcUtils;

public class Main {

	final static Logger logger = Logger.getLogger(Santini.class);

	public static void main(String[] args) {
		if (args[0] == null || args[1] == null) System.exit(-1);
		if (args[0].equals("") || args[1].equals("")) System.exit(-2);
		//Santini.playSweetWater();
		logger.info("Starting SANTINI version 1.0.2 ...");
		for (; ; ) {
			Santini dolores = new Santini(args[0], args[1]);
			dolores.gatherMindData();
			dolores.gatherPredictionData();
			new CalcUtils().sleeper(16000);
		}
	}
}
