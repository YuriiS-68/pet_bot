package sky.pro.pet_bot;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
@EnableEncryptableProperties
public class PetBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetBotApplication.class, args);
    }

}
