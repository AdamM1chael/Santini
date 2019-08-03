package model.data;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import model.DataIdentifier;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredictionEngine {
	private final static Logger logger = Logger.getLogger(PredictionEngine.class);
	public static Double buyBackAfterThisPercentage = 0.993;
	public Double targetPrice;
	private List<AverageData> averageData;
	private List<Double> targetPrices;
	private Map<CandlestickInterval, List<Candlestick>> candleMap;

	/**
	 *
	 */
	public PredictionEngine() {
		averageData = new ArrayList<AverageData>();
		targetPrices = new ArrayList<Double>();
		candleMap = new HashMap<CandlestickInterval, List<Candlestick>>();
	}

	/**
	 * @param mindData
	 */
	public void executeThoughtProcess(MindData mindData) {
		for (HashMap.Entry<DataIdentifier, List<Candlestick>> entry : mindData.getCandlestickData().entrySet()) {
			if (entry.getKey().getInterval() == CandlestickInterval.ONE_MINUTE
					&& entry.getKey().getTicker().equals("BTCUSDT")) {
				candleMap.put(CandlestickInterval.ONE_MINUTE, entry.getValue());
			}
			if (entry.getKey().getInterval() == CandlestickInterval.THREE_MINUTES
					&& entry.getKey().getTicker().equals("BTCUSDT")) {
				candleMap.put(CandlestickInterval.THREE_MINUTES, entry.getValue());
			}
			if (entry.getKey().getInterval() == CandlestickInterval.FIVE_MINUTES
					&& entry.getKey().getTicker().equals("BTCUSDT")) {
				candleMap.put(CandlestickInterval.FIVE_MINUTES, entry.getValue());
			}
			if (entry.getKey().getInterval() == CandlestickInterval.FIFTEEN_MINUTES
					&& entry.getKey().getTicker().equals("BTCUSDT")) {
				candleMap.put(CandlestickInterval.FIFTEEN_MINUTES, entry.getValue());
			}
		}
		for (HashMap.Entry<CandlestickInterval, List<Candlestick>> entry : candleMap.entrySet()) {
			averageData.add(calculateAverageData(entry));
		}
		for (AverageData avg : averageData) {
			Double target = Math.max(Math.max(Math.max(avg.getLowAvg(), avg.getOpenAvg()), avg.getHighAvg()), avg.getCloseAvg());
			targetPrices.add(target);
		}
		targetPrice = Math.floor(maxTarget(targetPrices) * 100.0) / 100.0;
	}

	/**
	 * @param entry
	 * @return
	 */
	private AverageData calculateAverageData(HashMap.Entry<CandlestickInterval, List<Candlestick>> entry) {
		AverageData averageData = new AverageData();
		Double low = 0.0;
		Double open = 0.0;
		Double close = 0.0;
		Double high = 0.0;
		for (Candlestick candle : entry.getValue()) {
			low += Double.valueOf(candle.getLow());
			open += Double.valueOf(candle.getOpen());
			close += Double.valueOf(candle.getClose());
			high += Double.valueOf(candle.getHigh());
		}
		averageData.setLowAvg(low / entry.getValue().size());
		averageData.setOpenAvg(open / entry.getValue().size());
		averageData.setCloseAvg(close / entry.getValue().size());
		averageData.setHighAvg(high / entry.getValue().size());
		return averageData;
	}

	/**
	 * Average the list of target prices
	 * @param list
	 * @return
	 */
	private Double maxTarget(List<Double> list) {
		Double highest = 0.0;
		for (Double num : list) {
			if (num > highest) {
				highest = num;
			}
		}
		return highest;
	}
}
