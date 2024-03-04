package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Slf4j
public class CriacaoQrCodeMessageService {

    private final CriacaoDePagamento criacaoDePagamento;
    public static final String QR_CODE_QUEUE = "queue.qr_code";

    private final RabbitTemplate rabbitTemplate;

    public CriacaoQrCodeMessageService(CriacaoDePagamento criacaoDePagamento, RabbitTemplate rabbitTemplate) {
        this.criacaoDePagamento = criacaoDePagamento;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void criacaoDePagamento(Pedido pedido) {
        pedido.setQrCode(criacaoDePagamento.gerarQrCodeParaPagamento(pedido));

        DadosPedido dadosPedido = new DadosPedido(pedido);

        rabbitTemplate.convertAndSend(QR_CODE_QUEUE, dadosPedido);

        log.info("Solicitação de pagamento gerada e enviada. Payload: {}", dadosPedido);
    }

}
