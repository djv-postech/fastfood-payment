package com.fiap.postech.techchallenge.fastfoodpayment.application.config.beans;

import com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.MercadoPagoFeignClient;
import com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.mercadopago.MercadoPagoClientProperties;
import com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.mercadopago.MercadoPagoFeignGateway;
import com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.mercadopago.converter.QRCodeRequestConverter;
import org.springframework.context.annotation.Bean;

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
