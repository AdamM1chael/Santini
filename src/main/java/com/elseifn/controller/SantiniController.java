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
    String response = "=====  >>>>>  SANTINI (v" + santini.getVersion() + ") <<<<<  =====<br>";
    if (Santini.DEVELOPMENT_MODE) response += "<br>### DEVELOPMENT MODE ###<br>";
    response += "<br>Status  :::  " + santini.getCurrentStateString();
    response += "<br><br>--- Engine data ---";
    response += "<br>BTC Price: $" + santini.getCurrentPrice();
    response += "<br>Target: $" + santini.getCurrentTargetPrice();
    response += "<br>Buy back: $" + santini.getCurrentBuyBackPrice();
    response += "<br><br>--- Status report ---";
    if (!santini.currentState)
      response +=
          "<br>There is an open buy back order at: $"
              + santini.getOpenBuyBackPrice()
              + " for "
              + santini.getOpenBuyBackAmt()
              + " BTC";
    response += "<br>Initial investment: " + santini.getInitialInvestment() + " BTC";
    response += "<br>Current portfolio value: " + santini.getCurrentBalance() + " BTC";
    response += "<br>Current profit: " + santini.getCurrentProfit() + "%";
    return new ResponseEntity<>(
        "<html>\n"
            + "<head>\n"
            + "<link rel=\"apple-touch-icon\" sizes=\"180x180\" href=\"/apple-touch-icon.png\">\n"
            + "<link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"/favicon-32x32.png\">\n"
            + "<link rel=\"icon\" type=\"image/png\" sizes=\"16x16\" href=\"/favicon-16x16.png\">\n"
            + "<link rel=\"manifest\" href=\"/site.webmanifest\">\n"
            + "<link rel=\"mask-icon\" href=\"/safari-pinned-tab.svg\" color=\"#5bbad5\">\n"
            + "<meta name=\"msapplication-TileColor\" content=\"#da532c\">\n"
            + "<meta name=\"theme-color\" content=\"#ffffff\">\n"
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
