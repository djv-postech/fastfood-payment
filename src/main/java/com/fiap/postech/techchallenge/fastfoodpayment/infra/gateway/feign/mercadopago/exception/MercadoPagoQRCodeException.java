package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.exception;

public class MercadoPagoQRCodeException extends RuntimeException {
  public MercadoPagoQRCodeException(String message) {
    super(message);
  }
}
