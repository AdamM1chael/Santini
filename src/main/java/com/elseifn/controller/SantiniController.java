package com.elseifn.controller;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.elseifn.santini.mind.Santini;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
public class SantiniController {
  private static final Logger logger = Logger.getLogger(SantiniController.class);
  private static final String PATH_BALANCE = "/balance/btc";
  private static final String PATH_PROFIT = "/balance/profit";
  private static final String PATH_SHUTDOWN = "/seppuku";
  private static final String PATH_STATUS = "/status";
  private static final String PATH_OPEN_ORDERS = "/orders";
  private static final String RESPONSE_SUFFIX = " endpoint hit";
  private final Santini santini;

  @Autowired
  public SantiniController(Santini santini) {
    this.santini = santini;
  }

  @GetMapping(path = PATH_BALANCE)
  public ResponseEntity getTotalBTC() {
    logger.trace(PATH_BALANCE + RESPONSE_SUFFIX);
    return new ResponseEntity<>(santini.getCurrentBalance(), HttpStatus.OK);
  }

  @GetMapping(path = PATH_PROFIT)
  public ResponseEntity getTotalProfit() {
    logger.trace(PATH_PROFIT + RESPONSE_SUFFIX);
    return new ResponseEntity<>(santini.getCurrentProfit(), HttpStatus.OK);
  }

  @GetMapping(
      path = PATH_SHUTDOWN,
      params = {"pass"})
  public void seppuku(@RequestParam("pass") String pass, HttpServletRequest request) {
    logger.trace(PATH_SHUTDOWN + RESPONSE_SUFFIX);
    // Verify the password provided...
    String sha256hex = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
    if (sha256hex.equals("bc159b2d00a17af10d15f85c0fc3050626a9de62ddada278c086b5a53c883464")) {
      logger.info("Shutdown received from IP-address: " + request.getRemoteUser());
      System.exit(-1);
    } else {
      logger.info("Incorrect shutdown code from IP-address: " + request.getRemoteAddr());
    }
  }

  @GetMapping(path = PATH_STATUS)
  public ResponseEntity getState() {
    logger.trace(PATH_STATUS + RESPONSE_SUFFIX);
    String response =
        "`Mb(&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;db&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)d'<br>"
            + "&nbsp;YM.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;,PM.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;,P&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/<br>"
            + "&nbsp;`Mb&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;d'Mb&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;d'&nbsp;____&nbsp;&nbsp;&nbsp;&nbsp;___&nbsp;&nbsp;&nbsp;___&nbsp;&nbsp;&nbsp;/M&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/M<br>"
            + "&nbsp;&nbsp;YM.&nbsp;&nbsp;&nbsp;,P&nbsp;YM.&nbsp;&nbsp;&nbsp;,P&nbsp;&nbsp;`MM(&nbsp;&nbsp;&nbsp;&nbsp;)M'&nbsp;6MMMMb&nbsp;/MMMMM&nbsp;/MMMMM<br>"
            + "&nbsp;&nbsp;`Mb&nbsp;&nbsp;&nbsp;d'&nbsp;`Mb&nbsp;&nbsp;&nbsp;d'&nbsp;&nbsp;&nbsp;`Mb&nbsp;&nbsp;&nbsp;&nbsp;d'&nbsp;8M'&nbsp;&nbsp;`Mb&nbsp;MM&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MM<br>"
            + "&nbsp;&nbsp;&nbsp;YM.&nbsp;,P&nbsp;&nbsp;&nbsp;YM.&nbsp;,P&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YM.&nbsp;&nbsp;,P&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;,oMM&nbsp;MM&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MM<br>"
            + "&nbsp;&nbsp;&nbsp;`Mb&nbsp;d'&nbsp;&nbsp;&nbsp;`Mb&nbsp;d'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MM&nbsp;&nbsp;M&nbsp;&nbsp;&nbsp;,6MM9'MM&nbsp;MM&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MM<br>"
            + "&nbsp;&nbsp;&nbsp;&nbsp;YM,P&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YM,P&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Mbd'&nbsp;&nbsp;&nbsp;MM'&nbsp;&nbsp;&nbsp;MM&nbsp;MM&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MM<br>"
            + "&nbsp;&nbsp;&nbsp;&nbsp;`MM'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`MM'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YMP&nbsp;&nbsp;&nbsp;&nbsp;MM.&nbsp;&nbsp;,MM&nbsp;YM.&nbsp;&nbsp;,&nbsp;YM.&nbsp;&nbsp;,<br>"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;M&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`YMMM9'Yb.YMMM9&nbsp;&nbsp;YMMM9<br>"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;d'<br>"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(8),P&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(v"+ santini.getVersion()+ ")<br>"
            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YMM<br>";
    if (Santini.DEVELOPMENT_MODE) response += "<br>### DEVELOPMENT MODE ###";
    response += "<br>--- Status report ---";
    response += "<br>Status: " + santini.getCurrentStateString();
    response += "<br>Investment: " + santini.getInitialInvestment() + " BTC";
    response += "<br>Portfolio  ≈ " + santini.getCurrentBalance() + " BTC";
    response += santini.getBalances();
    response += "<br>Profit: " + santini.getCurrentProfit() + "%";
    response += "<br><br>--- Market ---";
    response += "<br>BTC Price: $" + santini.getCurrentPrice();
    response += "<br>Target: $" + santini.getCurrentTargetPrice();
    response += "<br>Buy back: $" + santini.getCurrentBuyBackPrice();
    response += "<br>Sell confidence: " + santini.getCurrentSellConfidence() + "%";
    if (!santini.currentState) {
      Double diff = santini.getCurrentPrice() - santini.getOpenBuyBackPrice();
      diff = Math.round(diff * 1000.0) / 1000.0;
      response += "<br><br>--- Open buy back ---";
      response +=
          "<br>Amount: " + santini.getOpenBuyBackAmt() + " BTC @ $" + santini.getOpenBuyBackPrice();
      response += "<br>Difference: $" + diff + " (" + santini.getOpenBuyBackPercentage() + "%)";
    }
    response += "<br><br>--- Links ---";
    response +=
        "<br><a href=\"https://github.com/elseifn/santini\" style=\"color:#F7931A\">Source Code</a>";
    response +=
        "<br><a href=\"https://twitter.com/WestworldSantini\" style=\"color:#F7931A\">Twitter</a>";
    response +=
        "<br><a href=\"https://www.elseif.cn/full.php\" style=\"color:#F7931A\">Full log</a>";
    response += "<br><br>--- Donate ---";
    response +=
        "<br>Personal: <a href=\"https://www.blockchain.com/btc/address/"
            + "14Xqn75eLQVZEgjFgrQzF8C2PxNDf894yj\" style=\"color:#F7931A\">14X...4yj</a>";
    response +=
        "<br>Santini: <a href=\"https://www.blockchain.com/btc/address/"
            + "1BWu4LtW1swREcDWffFHZSuK3VTT1iWuba\" style=\"color:#F7931A\">1BW...uba</a>";
    return new ResponseEntity<>(
        "<html>\n"
            + "<head>\n"
            + "<link rel=\"apple-touch-icon\" sizes=\"180x180\" href=\"https://www.elseif.cn/apple-touch-icon.png\">\n"
            + "<link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"https://www.elseif.cn/favicon-32x32.png\">\n"
            + "<link rel=\"icon\" type=\"image/png\" sizes=\"16x16\" href=\"https://www.elseif.cn/favicon-16x16.png\">\n"
            + "<link rel=\"manifest\" href=\"https://www.elseif.cn/site.webmanifest\">\n"
            + "<link rel=\"mask-icon\" href=\"https://www.elseif.cn/safari-pinned-tab.svg\" color=\"#5bbad5\">\n"
            + "<meta name=\"msapplication-TileColor\" content=\"#da532c\">\n"
            + "<meta name=\"theme-color\" content=\"#ffffff\">\n"
            + "<meta http-equiv=\"refresh\" content=\"25\" />"
            + "</head>\n"
            + "<title>Santini</title>\n"
            + "<body bgcolor=\"#000000\">\n"
            + "<font face=\"Courier\" size=\"3\" color=\"#F7931A\">\n"
            + response
            + "</font> \n"
            + "</body>\n"
            + "</html> ",
        HttpStatus.OK);
  }

  @GetMapping(path = PATH_OPEN_ORDERS)
  public ResponseEntity getOpenOrders() {
    logger.trace(PATH_OPEN_ORDERS + RESPONSE_SUFFIX);
    return new ResponseEntity<>(new Gson().toJson(santini.getOpenOrders()), HttpStatus.OK);
  }
}
