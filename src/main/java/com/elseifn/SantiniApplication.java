package com.elseifn;

import com.elseifn.santini.mind.Santini;
import com.elseifn.santini.utils.CalcUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SantiniApplication {
  private final static Logger logger = Logger.getLogger(SantiniApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(SantiniApplication.class, args);
    logger.info("Starting SANTINI (v6.0.0) ...");
    if (args.length < 6) {
      logger.error("Not enough arguments have been given");
      System.exit(-1);
    }
    logger.info("Starting Santini trading...");
    for (;;) {
      Santini dolores = new Santini(args[0], args[1]);
      dolores.setTwitterCreds(args[2], args[3], args[4], args[5]);
      dolores.gatherMindData();
      dolores.predictAndTrade();
      dolores.printBalances();
      new CalcUtils().sleeper(25000);
    }
  }
}
