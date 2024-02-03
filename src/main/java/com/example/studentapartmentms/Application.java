package com.example.studentapartmentms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@MapperScan("com.example.studentapartmentms.mapper")
@ConfigurationPropertiesScan("com.example.studentapartmentms.config")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
