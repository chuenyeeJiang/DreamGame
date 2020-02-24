package com.chuenyee;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

@EnableEurekaClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class FileApp {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(FileApp.class, args);

	}
}
