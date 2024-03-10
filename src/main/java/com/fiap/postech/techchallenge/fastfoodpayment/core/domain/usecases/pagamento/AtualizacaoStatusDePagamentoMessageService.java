package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosStatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.fiap.postech.techchallenge.fastfoodpayment.infra.config.amqp.PagamentoAMQPConfiguration.STATUS_PAGAMENTO_EX;

public class AtualizacaoStatusDePagamentoMessageService {


    private final RabbitTemplate rabbitTemplate;

    public AtualizacaoStatusDePagamentoMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void atualizarPagamento(String numeroPedido, StatusPagamento statusPagamento) {
        rabbitTemplate.convertAndSend(STATUS_PAGAMENTO_EX, new DadosStatusPagamento(numeroPedido, statusPagamento));
    }

}
