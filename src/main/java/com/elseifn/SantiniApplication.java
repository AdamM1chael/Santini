package com.elseifn;

import com.elseifn.santini.mind.Santini;
import com.elseifn.santini.utils.CalcUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SantiniApplication {
  private static final Logger logger = Logger.getLogger(SantiniApplication.class);
  private static final String VERSION = "6.8.2";

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(SantiniApplication.class, args);
    Santini dolores = context.getBean(Santini.class);
    if (args.length < 2) {
      logger.error("Too few arguments given!");
      System.exit(-1);
    }
    if (args.length == 6) {
      logger.error("6 arguments provided. Proceeding to set Binance and Twitter credentials");
      dolores.setBinanceCreds(args[0], args[1]);
      dolores.setTwitterCreds(args[2], args[3], args[4], args[5]);
    } else if (args.length == 2) {
      logger.error("2 arguments provided. Proceeding to set Binance credentials");
      dolores.setBinanceCreds(args[0], args[1]);
    } else {
      logger.error("Incorrect number of arguments given!");
      System.exit(-1);
    }
    logger.info("Starting SANTINI (v" + VERSION + ") ...");
    runSantini(dolores);
  }

  public static String getVersion() {
    return VERSION;
  }

  private static void runSantini(Santini dolores) {
    for (; ; ) {
      dolores.gatherMindData();
      dolores.predictAndTrade();
      dolores.printBalances();
      dolores.reset();
      new CalcUtils().sleeper(25000);
    }
  }
}
