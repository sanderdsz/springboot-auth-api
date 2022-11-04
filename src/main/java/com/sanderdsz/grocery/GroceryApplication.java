package com.sanderdsz.grocery;

import com.sanderdsz.grocery.domain.model.RefreshToken;
import com.sanderdsz.grocery.domain.model.User;
import com.sanderdsz.grocery.domain.repository.RefreshTokenRepository;
import com.sanderdsz.grocery.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GroceryApplication {

	public static void main(String[] args) { SpringApplication.run(GroceryApplication.class, args); }

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository users,
			PasswordEncoder passwordEncoder,
			RefreshTokenRepository tokens
	) {
		return args -> {
			users.save(
					User.builder()
							.name("Sander")
							.password(passwordEncoder.encode("1234"))
							.email("sanderdsz@gmail.com")
							.role("ADMIN")
					.build()
			);
		};
	}

}

