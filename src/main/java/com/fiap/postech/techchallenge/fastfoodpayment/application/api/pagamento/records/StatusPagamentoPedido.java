package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;


import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;

public record StatusPagamentoPedido(String numeroPedido, StatusPagamento statusPagamento) {


}
