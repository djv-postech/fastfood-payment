package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
public class QRCodeResponse {
  @JsonProperty("qr_data")
  private String qrCode;
}
