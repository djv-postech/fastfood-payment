package com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.mercadopago.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeResponse {
  @JsonProperty("in_store_order_id")
  private String numeroInternoPagamento;

  @JsonProperty("qr_data")
  private String qrCode;
}
