package com.fiap.postech.techchallenge.fastfoodpayment.application.config.beans;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.gateway.MercadoPagoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoBeanConfiguration {
    private final MercadoPagoGateway mercadoPagoGateway;

    public PagamentoBeanConfiguration(MercadoPagoGateway mercadoPagoGateway) {
        this.mercadoPagoGateway = mercadoPagoGateway;
    }

    @Bean
    public CriacaoDePagamento criacaoDePagamento() {
        return new CriacaoDePagamento(mercadoPagoGateway);
    }

//    @Bean
//    public AtualizacaoDePagamento atualizacaoDePagamento() {
//        return new AtualizacaoDePagamento();
//    }
//
//    @Bean
//    public ConfirmacaoDePagamento confirmacaoDePagamento() {
//        return new ConfirmacaoDePagamento();
//    }
}
