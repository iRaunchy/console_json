package com.techtask.console_json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

@SpringBootApplication
@EnableAutoConfiguration
public class ConsoleJsonApplication implements CommandLineRunner {

    @Autowired
    CommandLineJSONProcessor commandLineJSONProcessor;

    private static Logger logger = LoggerFactory.getLogger(ConsoleJsonApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsoleJsonApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        logger.info("STARTING THE APPLICATION");
        app.run(args);
        logger.info("Application started");

    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            System.out.println(commandLineJSONProcessor.processJSONWithParameters(args[0], args[1], args[2]));
            exit(0);
        }

    }
}
