package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;

public record DadosStatusPagamento(String numeroPedido, StatusPagamento statusPagamento) {
    public DadosStatusPagamento(String numeroPedido, StatusPagamento statusPagamento) {
        this.numeroPedido = numeroPedido;
        this.statusPagamento = statusPagamento;
    }
}
