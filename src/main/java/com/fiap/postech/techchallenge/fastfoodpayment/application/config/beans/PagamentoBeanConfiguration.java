package com.fiap.postech.techchallenge.fastfoodpayment.application.config.beans;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConfirmacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConsultaDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.MercadoPagoGateway;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoBeanConfiguration {
    private final MercadoPagoGateway mercadoPagoGateway;
    private final ProducaoPedidoGateway producaoPedidoGateway;

    public PagamentoBeanConfiguration(MercadoPagoGateway mercadoPagoGateway, ProducaoPedidoGateway producaoPedidoGateway) {
        this.mercadoPagoGateway = mercadoPagoGateway;
        this.producaoPedidoGateway = producaoPedidoGateway;
    }

    @Bean
    public CriacaoDePagamento criacaoDePagamento() {
        return new CriacaoDePagamento(mercadoPagoGateway);
    }

    @Bean
    public ConsultaDePagamento consultaDePagamento() {
        return new ConsultaDePagamento(producaoPedidoGateway);
    }

    @Bean
    public ConfirmacaoDePagamento confirmacaoDePagamento() {
        return new ConfirmacaoDePagamento(producaoPedidoGateway);
    }
}
