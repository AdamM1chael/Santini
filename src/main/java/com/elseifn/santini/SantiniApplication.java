package com.elseifn.santini;

import com.elseifn.santini.mind.Santini;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SantiniApplication {
  private final static Logger logger = Logger.getLogger(SantiniApplication.class);

	public static void main(String[] args) {
    logger.info("Starting SANTINI (v5.1.7) ...");
    if (args.length < 6) {
      logger.error("Not enough arguments have been given");
      System.exit(-1);
    }
		ConfigurableApplicationContext context = SpringApplication.run(SantiniApplication.class, args);
    Santini santini = context.getBean(Santini.class);
    santini.startTrading();
	}
}
