package com.dk.etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * 容器启动类
 * @author HarlanW
 */
@SpringBootApplication
@EnableAsync
public class EtlWorkerApplication {
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(EtlWorkerApplication.class, args);
	}

}
