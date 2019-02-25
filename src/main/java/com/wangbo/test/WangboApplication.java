package com.wangbo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.wangbo.test" })
public class WangboApplication {

	public static void main(String[] args) {
		SpringApplication.run(WangboApplication.class, args);
	}

}
