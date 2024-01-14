package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

  @JsonProperty(value = "title")
  private String titulo;

  @JsonProperty(value = "unit_price")
  private Integer precoUnitario;

  @JsonProperty(value = "quantity")
  private Integer quantidade;

  @JsonProperty(value = "unit_measure")
  private String unidadeDeMedida;

  @JsonProperty(value = "total_amount")
  private Integer valorTotal;
}
