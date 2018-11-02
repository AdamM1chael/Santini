import mind.Santini;
import org.apache.log4j.Logger;
import utils.CalcUtils;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting SANTINI version 1.0.5 ...");
        if (args[0] == null || args[1] == null) {
            System.exit(-1);
            logger.error("Not enough arguments have been given");
        }
        if (args[0].equals("") || args[1].equals("")) {
            System.exit(-2);
            logger.error("Arguments cannot be empty");
        }
        for (; ; ) {
            Santini dolores = new Santini(args[0], args[1]);
            dolores.gatherMindData();
            dolores.gatherPredictionData();
            dolores.printBalances();
            new CalcUtils().sleeper(25000);
        }
    }
}
