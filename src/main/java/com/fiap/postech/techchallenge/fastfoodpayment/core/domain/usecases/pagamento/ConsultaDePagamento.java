package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.Pagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;

public class ConsultaDePagamento {

    private final ProducaoPedidoGateway producaoPedidoGateway;

    public ConsultaDePagamento(ProducaoPedidoGateway producaoPedidoGateway) {
        this.producaoPedidoGateway = producaoPedidoGateway;
    }

    public Pagamento consulta(String numeroPedido){
        return producaoPedidoGateway.consultaStatusPedido(numeroPedido).pagamento().convertToPagamento();

    }
}
