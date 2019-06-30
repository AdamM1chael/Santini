import mind.Santini;
import org.apache.log4j.Logger;
import utils.CalcUtils;

public class Main {
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Starting SANTINI (v4.1.1) ...");
		if (args.length < 6) {
			logger.error("Not enough arguments have been given");
			System.exit(-1);
		}
		for (; ; ) {
			Santini dolores = new Santini(args[0], args[1]);
			dolores.setTwitterCreds(args[2], args[3], args[4], args[5]);
			dolores.gatherMindData();
			dolores.predictAndTrade();
			dolores.printBalances();
			new CalcUtils().sleeper(25000);
		}
	}
}
