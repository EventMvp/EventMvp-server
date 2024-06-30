package com.eventhive.eventHive;

import com.eventhive.eventHive.config.RsaConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaConfigProperties.class)
@SpringBootApplication
public class EventHiveApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventHiveApplication.class, args);
	}

}
