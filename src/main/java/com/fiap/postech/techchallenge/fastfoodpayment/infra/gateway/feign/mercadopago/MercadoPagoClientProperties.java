package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mercado.pago.client")
public class MercadoPagoClientProperties {
  private int connectTimeout = 10000;

  private int readTimeout = 60000;

  private String authToken;

  private String userId;

  private String externalPosId;
}
