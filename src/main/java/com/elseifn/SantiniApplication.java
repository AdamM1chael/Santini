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

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(SantiniApplication.class, args);
    Santini dolores = context.getBean(Santini.class);

    if (args.length < 2) {
      logger.error("Too few arguments given!");
      System.exit(-1);
    }
    if (args.length == 6) {
      logger.info("6 arguments provided. Proceeding to set Binance and Twitter credentials");
      dolores.setBinanceCreds(args[0], args[1]);
      dolores.setTwitterCreds(args[2], args[3], args[4], args[5]);
    } else if (args.length == 2) {
      logger.info("2 arguments provided. Proceeding to set Binance credentials");
      dolores.setBinanceCreds(args[0], args[1]);
    } else {
      logger.error("Incorrect number of arguments given!");
      System.exit(-1);
    }
    logger.info("Starting Santini_v" + dolores.getVersion() + "...");
    runSantini(dolores);
  }

  private static void runSantini(Santini dolores) {
    for (; ; ) {
      try {
        dolores.gatherMindData();
        dolores.predictAndTrade();
      } catch (Exception e) {
        logger.error("There was an error during the main trading loop! {}", e);
      } finally {
        dolores.reset();
        new CalcUtils().sleeper(25000);
      }
    }
  }
}
