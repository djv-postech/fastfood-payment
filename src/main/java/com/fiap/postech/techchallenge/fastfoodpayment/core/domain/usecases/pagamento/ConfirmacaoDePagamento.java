package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;

public class ConfirmacaoDePagamento {
    private final ProducaoPedidoGateway producaoPedidoGateway;


    public ConfirmacaoDePagamento(ProducaoPedidoGateway producaoPedidoGateway) {
        this.producaoPedidoGateway = producaoPedidoGateway;
    }

    public void confirmarPagamento(String numeroPedido){
        //FIXME: enviar atualização de status para produçao mesmo?
        producaoPedidoGateway.atualizarStatusPagamento(numeroPedido, StatusPagamento.APROVADO);
    }
}
