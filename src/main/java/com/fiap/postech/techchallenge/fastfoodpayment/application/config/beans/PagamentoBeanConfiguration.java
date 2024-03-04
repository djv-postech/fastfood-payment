package com.fiap.postech.techchallenge.fastfoodpayment.application.config.beans;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.*;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.MercadoPagoGateway;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoBeanConfiguration {
    private final MercadoPagoGateway mercadoPagoGateway;
    private final ProducaoPedidoGateway producaoPedidoGateway;

    private final RabbitTemplate rabbitTemplate;

    public PagamentoBeanConfiguration(MercadoPagoGateway mercadoPagoGateway, ProducaoPedidoGateway producaoPedidoGateway, RabbitTemplate rabbitTemplate) {
        this.mercadoPagoGateway = mercadoPagoGateway;
        this.producaoPedidoGateway = producaoPedidoGateway;
        this.rabbitTemplate = rabbitTemplate;
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

    @Bean
    public CriacaoQrCodeMessageService criacaoQrCodeMessageService() {
        return new CriacaoQrCodeMessageService(criacaoDePagamento(), rabbitTemplate);
    }

    @Bean
    public AtualizacaoStatusDePagamentoMessageService atualizacaoStatusDePagamentoMessageService(){
        return new AtualizacaoStatusDePagamentoMessageService(rabbitTemplate);
    }

}
