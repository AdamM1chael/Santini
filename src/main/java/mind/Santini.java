package mind;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import model.DataIdentifier;
import model.data.MindData;
import model.data.PredictionData;
import utils.CalcUtils;

import java.util.List;

public class Santini {

    private static int MAX_TRADES_PER_24HOURS = 10;
    private static CandlestickInterval[] intervalList = {
            CandlestickInterval.ONE_MINUTE,
            CandlestickInterval.THREE_MINUTES,
            CandlestickInterval.FIVE_MINUTES};
    private static String[] tickers = {"BTCUSDT", "ETHUSDT"};
    public MindData mindData;
    public PredictionData predictionData;
    private BinanceApiClientFactory factory = null;
    private BinanceApiRestClient client = null;


    public Santini(String apiKey, String secret) {
        mindData = new MindData();
        predictionData = new PredictionData(mindData);
        factory = BinanceApiClientFactory.newInstance(apiKey, secret);
        client = factory.newRestClient();
    }

    public static void playSweetWater() {
        System.out.println("\nLOADING SYSTEM ... \n");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println(" .----------------. .----------------. .----------------. .----------------. .----------------. ");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| .--------------. | .--------------. | .--------------. | .--------------. | .--------------. |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| | _____  _____ | | |  ____  ____  | | |      __      | | |  _________   | | |  _________   | |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| ||_   _||_   _|| | | |_  _||_  _| | | |     /  \\     | | | |  _   _  |  | | | |  _   _  |  | |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| |  | | /\\ | |  | | |   \\ \\  / /   | | |    / /\\ \\    | | | |_/ | | \\_|  | | | |_/ | | \\_|  | |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| |  | |/  \\| |  | | |    \\ \\/ /    | | |   / ____ \\   | | |     | |      | | |     | |      | |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| |  |   /\\   |  | | |    _|  |_    | | | _/ /    \\ \\_ | | |    _| |_     | | |    _| |_     | |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| |  |__/  \\__|  | | |   |______|   | | ||____|  |____|| | |   |_____|    | | |   |_____|    | |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| |              | | |              | | |              | | |              | | |              | |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println("| '--------------' | '--------------' | '--------------' | '--------------' | '--------------' |");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
        System.out.println(" '----------------' '----------------' '----------------' '----------------' '----------------' ");
        new CalcUtils().sleeper(CalcUtils.SLEEP_NUM);
    }

    public void gatherMindData() {
        for (String ticker : tickers) {
            for (CandlestickInterval interval : intervalList) {
                gatherIntervalData(mindData, interval, ticker);
                new CalcUtils().sleeper(500);
                System.out.println(ticker + " data fetched for interval: " + interval.getIntervalId() + " ...");
            }
        }
    }

    public void gatherPredictionData() {
		/*for (List<Can>) {

		}*/
    }

    private void gatherIntervalData(MindData mindData, CandlestickInterval interval, String ticker) {


        List<Candlestick> candlesticks = client.getCandlestickBars(ticker, interval);
        mindData.candlestickData.put(new DataIdentifier(interval, ticker), candlesticks);
        mindData.lastPriceData.put(new DataIdentifier(interval, ticker), client.get24HrPriceStatistics(ticker));
        mindData.candlestickIntAvgData.put(new DataIdentifier(interval, ticker),
                new CalcUtils().findAveragePrice(candlesticks));
    }

    public void performSellAndBuyBack() {

    }
}

