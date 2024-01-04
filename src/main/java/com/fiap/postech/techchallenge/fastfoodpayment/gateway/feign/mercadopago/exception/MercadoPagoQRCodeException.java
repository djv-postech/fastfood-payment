package com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.mercadopago.exception;

public class MercadoPagoQRCodeException extends RuntimeException {
  public MercadoPagoQRCodeException(String message) {
    super(message);
  }
}
