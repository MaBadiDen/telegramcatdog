package pro.sky.telegramcatdog;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition
public class TelegramcatdogApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramcatdogApplication.class, args);
	}
}
