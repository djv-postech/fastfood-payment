package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;

public interface ProducaoPedidoGateway {

    DadosPedido atualizarStatusPagamento(String numeroPedido, StatusPagamento statusPagamento);

    DadosPedido consultaStatusPedido(String numeroPedido);
}
