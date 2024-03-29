package com.backend.ecommerce.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc 
public class SwaggerConfig {
	
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.backend.ecommerce.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"Ecommerce Api", 
				"This Ecommerce API with SpringBoot by Muhamad Ichsan Al - Qudhori", 
				"1.0", 
				"Terms of Service", 
				new Contact("Muhamad Ichsan Al - Qudhori", null, "m.ichsan.alqudhori@gmail.com"), 
				"Apache 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0",
				Collections.emptyList()
				);	
		return apiInfo;
	}
}
