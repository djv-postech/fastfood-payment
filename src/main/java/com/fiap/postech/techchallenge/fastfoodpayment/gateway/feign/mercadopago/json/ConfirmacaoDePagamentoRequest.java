package com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.mercadopago.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ConfirmacaoDePagamentoRequest {
  private String id;

  @JsonProperty("action")
  private String acao;

  @JsonProperty("date_created")
  private LocalDateTime dataHoraDeConfirmacao;

  @JsonProperty("data")
  private DadosDoPagamento dadosDoPagamento;
}
