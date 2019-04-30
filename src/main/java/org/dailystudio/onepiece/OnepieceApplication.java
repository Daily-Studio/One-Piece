package org.dailystudio.onepiece;

import org.dailystudio.onepiece.domain.Account;
import org.dailystudio.onepiece.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnepieceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnepieceApplication.class, args);
	}


}
