package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosStatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public  class AtualizacaoStatusDePagamentoMessageServiceTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    private AtualizacaoStatusDePagamentoMessageService atualizacaoStatusDePagamentoMessageService;


    @BeforeEach
    public void init() {
        atualizacaoStatusDePagamentoMessageService = new AtualizacaoStatusDePagamentoMessageService(rabbitTemplate);
        MockMvcBuilders.standaloneSetup(atualizacaoStatusDePagamentoMessageService).build();
    }

    @DisplayName("Test - Atualizar status de pagamento")
    @Test
    public void deveAtualizarStatusDePagamento() throws Exception {
        // Dado
        String numeroPedido = UUID.randomUUID().toString();
        StatusPagamento statusPagamento = StatusPagamento.APROVADO;
        DadosStatusPagamento dadosStatusPagamento = new DadosStatusPagamento(numeroPedido, statusPagamento);
        final String STATUS_PAGAMENTO_QUEUE = "queue.status_pagamento";

        // Quando
        atualizacaoStatusDePagamentoMessageService.atualizarPagamento(numeroPedido, statusPagamento);

        // Entao
        verify(rabbitTemplate, times(1)).convertAndSend(STATUS_PAGAMENTO_QUEUE, dadosStatusPagamento);
    }
}