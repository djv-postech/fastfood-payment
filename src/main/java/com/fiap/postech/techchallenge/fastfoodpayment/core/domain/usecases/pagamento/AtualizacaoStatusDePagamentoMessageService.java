package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosStatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.fiap.postech.techchallenge.fastfoodpayment.infra.config.amqp.PagamentoAMQPConfiguration.STATUS_PAGAMENTO_EX;

@Slf4j
public class AtualizacaoStatusDePagamentoMessageService {


    private final RabbitTemplate rabbitTemplate;

    public AtualizacaoStatusDePagamentoMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void atualizarPagamento(String numeroPedido, StatusPagamento statusPagamento) {
        DadosStatusPagamento dadosStatusPagamento = new DadosStatusPagamento(numeroPedido, statusPagamento);

        rabbitTemplate.convertAndSend(STATUS_PAGAMENTO_EX, "", dadosStatusPagamento);

        log.info("Atualização de pagamento enviada! Payload: {}", dadosStatusPagamento);
    }

}
