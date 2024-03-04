package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosStatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class AtualizacaoStatusDePagamentoMessageService {

    public static final String STATUS_PAGAMENTO = "queue.status_pagamento";

    private final RabbitTemplate rabbitTemplate;

    public AtualizacaoStatusDePagamentoMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void atualizarPagamento(String numeroPedido, StatusPagamento statusPagamento) {
        rabbitTemplate.convertAndSend(STATUS_PAGAMENTO, new DadosStatusPagamento(numeroPedido, statusPagamento));
    }

}
