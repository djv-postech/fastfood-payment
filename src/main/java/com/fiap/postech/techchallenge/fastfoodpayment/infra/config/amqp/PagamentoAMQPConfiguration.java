package com.fiap.postech.techchallenge.fastfoodpayment.infra.config.amqp;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RabbitProperties.class)
@Slf4j
public class PagamentoAMQPConfiguration {
    private final RabbitProperties rabbitProperties;
    private final ObjectMapper mapper;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory(RabbitProperties.RabbitName.PAGAMENTO));
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory(RabbitProperties.RabbitName.PAGAMENTO));
    }

    private ConnectionFactory connectionFactory(final RabbitProperties.RabbitName rabbitName) {
        final RabbitProperties.RabbitConnection connection = rabbitProperties.getConnection(rabbitName);
        final CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(connection.getAddresses());
        factory.setUsername(connection.getCredentials().getUsername());
        factory.setPassword(connection.getCredentials().getPassword());
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        return getFactory();
    }

    private SimpleRabbitListenerContainerFactory getFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory(RabbitProperties.RabbitName.PAGAMENTO));
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public void manageQueues() {
        RabbitAdmin rabbitAdmin = rabbitAdmin();
        rabbitProperties
                .getBindings()
                .forEach(
                        binding -> {
                            final Queue queue = QueueBuilder.durable(binding.getQueue()).build();
                            final Exchange exchange =
                                    ExchangeBuilder.topicExchange(binding.getExchange()).durable(true).build();
                            final Binding rabbitBinding =
                                    BindingBuilder.bind(queue)
                                            .to(exchange)
                                            .with(binding.getRoutingKey())
                                            .and(new HashMap<>());

                            rabbitAdmin.declareQueue(queue);
                            rabbitAdmin.declareExchange(exchange);
                            rabbitAdmin.declareBinding(rabbitBinding);
                            log.info("Fila {} criada com sucesso!", queue);
                        });
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(mapper);
    }
}