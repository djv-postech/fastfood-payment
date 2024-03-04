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
    public void gerarPagamento(final DadosPedido dadosPedido) {

        log.info("Solicitação de pagamento recebida! Payload: {}", dadosPedido);


        try {
            criacaoQrCodeMessageService.criacaoDePagamento(dadosPedido.convertToPedido());
        }catch (MercadoPagoQRCodeException mercadoPagoQRCodeException){
            log.error("Erro ao gerar qrCode: {}", mercadoPagoQRCodeException.getMessage());
        }

    }

}
