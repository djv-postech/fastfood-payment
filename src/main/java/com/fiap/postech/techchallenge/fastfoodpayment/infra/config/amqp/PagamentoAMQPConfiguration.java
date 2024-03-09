package com.fiap.postech.techchallenge.fastfoodpayment.infra.config.amqp;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PagamentoAMQPConfiguration {


    public static final String QR_CODE_EX = "ex.qr_code";
    public static final String QR_CODE_QUEUE = "queue.qr_code";
    public static final String QR_CODE_QUEUE_DLX = "dlx.qr_code";
    public static final String QR_CODE_QUEUE_DLQ = "dlq.qr_code";

    public static final String SOLICITACAO_PAGAMENTO_EX = "ex.solicitacao_pagamento";
    public static final String SOLICITACAO_PAGAMENTO_QUEUE = "queue.solicitacao_pagamento";

    public static final String SOLICITACAO_PAGAMENTO_DLX = "dlx.solicitacao_pagamento";

    public static final String SOLICITACAO_PAGAMENTO_DLQ = "dlq.solicitacao_pagamento";

    @Bean
    public RabbitAdmin criarAdminConfig(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> startAdmin(RabbitAdmin rabbitAdmin){
        return  event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);

        return rabbitTemplate;
    }


    @Bean
    public Queue qrCodeQueue(){
        return QueueBuilder.nonDurable(QR_CODE_QUEUE)
                .deadLetterExchange(QR_CODE_QUEUE_DLX)
                .build();
    }

    @Bean
    public Queue solicitacaoPagamentoQueue(){
        return QueueBuilder.nonDurable(SOLICITACAO_PAGAMENTO_QUEUE)
                .deadLetterExchange(SOLICITACAO_PAGAMENTO_DLX)
                .build();
    }

    @Bean
    public FanoutExchange qrCodeExchange(){
        return new FanoutExchange(QR_CODE_EX);
    }

    @Bean
    public FanoutExchange solicitacaoPagamentoExchange(){
        return new FanoutExchange(SOLICITACAO_PAGAMENTO_EX);
    }

    @Bean
    public Binding qrCodeBinding(){
        return BindingBuilder.bind(qrCodeQueue()).to(qrCodeExchange());

    }

    @Bean
    public Binding solicitacaoPagamentoBinding(){
        return BindingBuilder.bind(solicitacaoPagamentoQueue()).to(solicitacaoPagamentoExchange());

    }

    @Bean
    public FanoutExchange qrCodeDLX(){
        return new FanoutExchange(QR_CODE_QUEUE_DLX);
    }

    @Bean
    public FanoutExchange solicitacaPagamentoDLX(){
        return new FanoutExchange(SOLICITACAO_PAGAMENTO_DLX);
    }

    @Bean
    public Queue qrCodeDLQ(){
        return QueueBuilder.nonDurable(QR_CODE_QUEUE_DLQ)
                .build();
    }

    @Bean
    public Queue solicitacaoPagamentoDLQ(){
        return QueueBuilder.nonDurable(SOLICITACAO_PAGAMENTO_DLQ)
                .build();
    }

    @Bean
    public Binding qrCodeDLXDLQBinding(){
        return BindingBuilder.bind(qrCodeDLQ()).to(qrCodeDLX());

    }

    @Bean
    public Binding solicitacaoPagamentoDLXDLQBinding(){
        return BindingBuilder.bind(solicitacaoPagamentoDLQ()).to(solicitacaPagamentoDLX());

    }

}
