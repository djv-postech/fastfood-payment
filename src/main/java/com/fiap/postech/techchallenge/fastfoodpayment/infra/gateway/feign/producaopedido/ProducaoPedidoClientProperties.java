package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "apis.fastfood.producao-pedido.client")
public class ProducaoPedidoClientProperties {
  private int connectTimeout = 10000;

  private int readTimeout = 60000;

}
