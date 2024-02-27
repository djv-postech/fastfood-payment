package com.fiap.postech.techchallenge.fastfoodpayment.application.gateway.amqp;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@AllArgsConstructor
//FIXME: Construtor
public class SolicitacaoPagamentoListener {
    private final CriacaoDePagamento criacaoDePagamento;

    private final ObjectMapper objectMapper;
    public static final String SOLICITACAO_PAGAMENTO_QUEUE = "queue.solicitacao_pagamento";

    @RabbitListener(queues = SOLICITACAO_PAGAMENTO_QUEUE)
    public void cadastrarPedido(final Message message) {
        final String payload = new String(message.getBody(), StandardCharsets.UTF_8);

        log.info("Solicitação de pagamento recebida! Payload: {}",  payload);
        criacaoDePagamento.gerarQrCodeParaPagamento(deserialize(payload).convertToPedido());

        //TODO: atualizar pedido com novo qrCode gerado
    }

    private DadosPedido deserialize(final String payload) {
        DadosPedido userMessage = null;

        try {
            userMessage = objectMapper.readValue(payload, DadosPedido.class);

        } catch (IOException e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }
        return userMessage;
    }
}
