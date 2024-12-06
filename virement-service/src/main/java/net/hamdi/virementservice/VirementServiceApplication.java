package net.hamdi.virementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VirementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirementServiceApplication.class, args);
	}

}
