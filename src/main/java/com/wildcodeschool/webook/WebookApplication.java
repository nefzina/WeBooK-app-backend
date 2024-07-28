package com.wildcodeschool.webook;

import com.wildcodeschool.webook.fileUpload.application.StorageProperties;
import com.wildcodeschool.webook.fileUpload.domain.service.IUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class WebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebookApplication.class, args);
	}
	@Bean
	CommandLineRunner init(IUploadService iUploadService) {
		return (args) -> {
			iUploadService.init();
		};
	}
}
