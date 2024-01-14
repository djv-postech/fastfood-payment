package com.fiap.postech.techchallenge.fastfoodpayment.application.config.beans;

import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.MercadoPagoClientProperties;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.MercadoPagoFeignGateway;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.converter.QRCodeRequestConverter;
import org.springframework.context.annotation.Bean;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.MercadoPagoFeignClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoBeanConfiguration {

  private final MercadoPagoFeignClient feignClient;
  private final MercadoPagoClientProperties mercadoPagoClientProperties;
  private final QRCodeRequestConverter converter;


  public MercadoPagoBeanConfiguration(
          MercadoPagoFeignClient mercadoPagoClient,
          MercadoPagoClientProperties mercadoPagoClientProperties,
          QRCodeRequestConverter converter) {
    this.feignClient = mercadoPagoClient;
    this.mercadoPagoClientProperties = mercadoPagoClientProperties;
    this.converter = converter;
  }

  @Bean
  public MercadoPagoFeignGateway mercadoPagoFeignGateway() {
    return new MercadoPagoFeignGateway(feignClient, mercadoPagoClientProperties, converter);
  }
}
