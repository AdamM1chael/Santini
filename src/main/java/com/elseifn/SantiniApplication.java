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
    logger.info("Starting SANTINI (v6.2.3) ...");
    if (args.length < 6) {
      logger.error("Not enough arguments have been given");
      System.exit(-1);
    }
    Santini dolores = context.getBean(Santini.class);
    dolores.setBinanceCreds(args[0], args[1]);
    dolores.setTwitterCreds(args[2], args[3], args[4], args[5]);
    for (; ; ) {
      dolores.gatherMindData();
      dolores.predictAndTrade();
      dolores.printBalances();
      dolores.reset();
      new CalcUtils().sleeper(25000);
    }
  }
}
