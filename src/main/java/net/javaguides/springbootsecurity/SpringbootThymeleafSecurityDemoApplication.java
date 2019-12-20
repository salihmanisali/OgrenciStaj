package net.javaguides.springbootsecurity;

import net.javaguides.springbootsecurity.helpers.storage.StorageProperties;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringbootThymeleafSecurityDemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringbootThymeleafSecurityDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
