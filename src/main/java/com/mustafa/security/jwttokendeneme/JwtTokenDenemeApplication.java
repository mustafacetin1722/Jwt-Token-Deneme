package com.mustafa.security.jwttokendeneme;

import com.mustafa.security.jwttokendeneme.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class JwtTokenDenemeApplication {
	public static void main(String[] args) {
		SpringApplication.run(JwtTokenDenemeApplication.class, args);
	}
}
