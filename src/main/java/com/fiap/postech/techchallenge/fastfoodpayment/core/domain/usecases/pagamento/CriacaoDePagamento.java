package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPagamentoGerado;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.MercadoPagoGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

public class CriacaoDePagamento {
    private final MercadoPagoGateway mercadoPagoGateway;
    public static final String QR_CODE_QUEUE = "queue.qr_code";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public CriacaoDePagamento(MercadoPagoGateway mercadoPagoGateway) {
        this.mercadoPagoGateway = mercadoPagoGateway;
    }

    public String gerarQrCodeParaPagamento(Pedido pedido) {
        String qrCode = mercadoPagoGateway.gerarQRCode(pedido);
        //FIXME: Criar dominio depois converter para record?

        DadosPagamentoGerado dadosPagamentoGerado =  new DadosPagamentoGerado(pedido.getNumeroPedido(), UUID.randomUUID().toString(), LocalDateTime.now(), qrCode);

        rabbitTemplate.convertAndSend(QR_CODE_QUEUE, dadosPagamentoGerado);

        return qrCode;
    }

}
