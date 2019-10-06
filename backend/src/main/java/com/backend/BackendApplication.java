package com.backend;

import com.backend.singleton.ConfiguradorSingleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {

	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
			}
		};
	}

	public static void main(String[] args) {
		//SpringApplication.run(BackendApplication.class, args);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BackendApplication.class, args);
		ConfiguradorSingleton configuradorSingleton = applicationContext.getBean(ConfiguradorSingleton.class);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Accesos accesos = applicationContext.getBean(Accesos.class);

	}

}
