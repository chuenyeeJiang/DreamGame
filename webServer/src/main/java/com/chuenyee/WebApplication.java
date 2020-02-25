package com.chuenyee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients 
public class WebApplication {

	
	
	public static void main(String[] args) {
	//	ApplicationContext applicationContext =
				SpringApplication.run(WebApplication.class, args);
	}
	
	
	
//	spring boot<=1.3
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	/*
	 *  spring boot>=1.4
	 */
	/*@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(RestTemplateBuilder builder){
		return builder.build();
	}*/
}
