package com.evdealer.ev_dealer_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EvDealerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvDealerManagementApplication.class, args);
	}

}
