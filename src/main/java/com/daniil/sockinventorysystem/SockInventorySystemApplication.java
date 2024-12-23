package com.daniil.sockinventorysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SockInventorySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SockInventorySystemApplication.class, args);
	}

}
