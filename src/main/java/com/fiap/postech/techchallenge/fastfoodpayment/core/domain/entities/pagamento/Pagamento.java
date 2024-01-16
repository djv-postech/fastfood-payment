package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pagamento {

  private final String id;
  private final BigDecimal totalPagamento;
  private final TipoPagamento tipoPagamento;
  private final LocalDateTime dataEHorarioPagamento;
  private final StatusPagamento statusPagamento;

  public Pagamento(
      String id,
      BigDecimal totalPagamento,
      TipoPagamento tipoPagamento,
      LocalDateTime dataEHorarioPagamento,
      StatusPagamento statusPagamento) {

    this.id = id;
    this.totalPagamento = totalPagamento;
    this.tipoPagamento = tipoPagamento;
    this.dataEHorarioPagamento = dataEHorarioPagamento;
    this.statusPagamento = statusPagamento;
  }

  public StatusPagamento getStatusPagamento() {
    return statusPagamento;
  }

}
