package cl.itam.fintoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class FintocApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintocApplication.class, args);
	}

}
