package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido;


import feign.Request.Options;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProducaoPedidoClientConfig {
  private final ProducaoPedidoClientProperties properties;

  @Bean("producaoPedidoClient")
  public Options options() {
    return new Options(properties.getConnectTimeout(), properties.getReadTimeout());
  }
}
