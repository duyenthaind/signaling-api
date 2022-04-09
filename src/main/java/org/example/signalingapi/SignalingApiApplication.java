package org.example.signalingapi;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@SpringBootApplication
@Configuration
@RestController
@Log4j2
public class SignalingApiApplication {

	public static void main(String[] args) {
		org.apache.logging.log4j.core.config.Configurator.initialize(null,System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j2.xml");
		SpringApplication.run(SignalingApiApplication.class, args);
		log.info("Application start ok!");
	}

}
