package sky.pro.pet_bot.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Pet_bot")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("yurii_s@bigmir.net")
                                                .url("https://yuriis-68.github.io/")
                                                .name("Skorodielov Yurii")
                                )
                );
    }
}
