package com.fiap.postech.techchallenge.fastfoodpayment.infra.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(new Info().title("API de Pagamentos")
            .description("API reponsável por fazer o gerenciamento de pagamentos do fastfood")
            .version("1.0.0"));
  }
}
