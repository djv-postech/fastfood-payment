package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Saque {
  @JsonProperty(value = "amount")
  private Integer valor;
}
