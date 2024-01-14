package com.fiap.postech.techchallenge.fastfoodpayment.application.config.beans;

import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido.ProducaoPedidoFeignClient;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido.ProducaoPedidoFeignGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducaoPedidoBeanConfiguration {

    private final ProducaoPedidoFeignClient feignClient;

    public ProducaoPedidoBeanConfiguration(ProducaoPedidoFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Bean
    public ProducaoPedidoFeignGateway producaoPedidoFeignGateway(){
        return new ProducaoPedidoFeignGateway(feignClient);
    }
}
