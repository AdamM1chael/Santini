import mind.Santini;
import model.data.MindData;
import model.data.PredictionData;

public class Main {
	public static void main(String[] args) {
		Santini dolores = new Santini();
		//Santini.playSweetWater();
		MindData theCradle = dolores.gatherData();
		PredictionData predictionData = new PredictionData(theCradle);
		predictionData.calculatePredictionData();
	}
}
