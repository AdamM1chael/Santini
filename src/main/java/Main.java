import mind.Santini;
import utils.CalcUtils;

public class Main {
    public static void main(String[] args) {

        if (args[0] == null || args[1] == null) System.exit(-1);
        if (args[0].equals("") || args[1].equals("")) System.exit(-2);

        Santini.playSweetWater();

        for (; ; ) {
            Santini dolores = new Santini(args[0], args[1]);
            dolores.gatherMindData();
            dolores.gatherPredictionData();
            //if (dolores.predictionData.sellConfidencePercentage > 85.0) {
            //    dolores.performSellAndBuyBack();
            //} else {
            //    new CalcUtils().sleeper(30000);
            //}
        }
    }
}
