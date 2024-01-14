package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;

public class ProducaoPedidoFeignGateway implements ProducaoPedidoGateway {

    private final ProducaoPedidoFeignClient producaoPedidoFeignClient;

    public ProducaoPedidoFeignGateway(ProducaoPedidoFeignClient producaoPedidoFeignClient) {
        this.producaoPedidoFeignClient = producaoPedidoFeignClient;
    }

    @Override
    public DadosPedido atualizarStatusPagamento(String numeroPedido, StatusPagamento statusPagamento) {
        return producaoPedidoFeignClient.atualizarStatusPagamentoPedido(numeroPedido, statusPagamento);
    }

    @Override
    public DadosPedido consultaStatusPedido(String numeroPedido) {
        return producaoPedidoFeignClient.listarPedido(numeroPedido);
    }
}
