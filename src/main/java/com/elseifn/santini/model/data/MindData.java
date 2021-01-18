package com.elseifn.santini.model.data;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.TickerStatistics;
import com.elseifn.santini.model.DataIdentifier;

import java.util.HashMap;
import java.util.List;

public class MindData {
  public HashMap<DataIdentifier, List<Candlestick>> candlestickData;
  public HashMap<DataIdentifier, TickerStatistics> lastPriceData;
  public HashMap<DataIdentifier, Double> candlestickIntAvgData;

  public MindData() {
    candlestickData = new HashMap<>();
    lastPriceData = new HashMap<>();
    candlestickIntAvgData = new HashMap<>();
  }

  public HashMap<DataIdentifier, List<Candlestick>> getCandlestickData() {
    return candlestickData;
  }
}
