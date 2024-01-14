package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QRCodeRequest {
  @JsonProperty(value = "external_reference")
  private String numeroPedido;

  @JsonProperty(value = "title")
  private String titulo;

  @JsonProperty(value = "total_amount")
  private Integer valorTotal;

  @JsonProperty(value = "description")
  private String descricao;

  @JsonProperty(value = "items")
  private List<Item> itens;

  @JsonProperty(value = "cash_out")
  private Saque saque;
}
