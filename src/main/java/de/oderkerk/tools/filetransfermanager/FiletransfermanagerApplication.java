package de.oderkerk.tools.filetransfermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FiletransfermanagerApplication {
	private static final Logger logger = LogManager.getLogger(FiletransfermanagerApplication.class);

	public static void main(String[] args) {
		logger.info("Starting FiletransfermanagerApplication with args ={} ", args.toString());
		SpringApplication.run(FiletransfermanagerApplication.class, args);
	}

}
