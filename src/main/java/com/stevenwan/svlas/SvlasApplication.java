package com.stevenwan.svlas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@MapperScan("com.stevenwan.svlas.mapper")
@ConfigurationPropertiesScan("com.stevenwan.svlas.config")
@SpringBootApplication
public class SvlasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SvlasApplication.class, args);
	}

}
