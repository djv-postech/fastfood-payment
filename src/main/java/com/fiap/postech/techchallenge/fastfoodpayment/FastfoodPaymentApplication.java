package com.fiap.postech.techchallenge.fastfoodpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
public class FastfoodPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastfoodPaymentApplication.class, args);
	}

}
