package com.fiap.postech.techchallenge.fastfoodpayment.application.amqp;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoQrCodeMessageService;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.exception.MercadoPagoQRCodeException;
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
public class SolicitacaoPagamentoListener {
    private final CriacaoDePagamento criacaoDePagamento;

    private final ObjectMapper objectMapper;
    public static final String SOLICITACAO_PAGAMENTO_QUEUE = "queue.solicitacao_pagamento";

    private CriacaoQrCodeMessageService criacaoQrCodeMessageService;

    @RabbitListener(queues = SOLICITACAO_PAGAMENTO_QUEUE)
    public void gerarPagamento(final Message message) {
        final String payload = new String(message.getBody(), StandardCharsets.UTF_8);

        log.info("Solicitação de pagamento recebida! Payload: {}", payload);

        Pedido pedido = deserialize(payload).convertToPedido();

        try {
            criacaoQrCodeMessageService.criacaoDePagamento(pedido);
        }catch (MercadoPagoQRCodeException mercadoPagoQRCodeException){
            log.error("Erro ao gerar qrCode: {}", mercadoPagoQRCodeException.getMessage());
        }

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
