package com.fiap.postech.techchallenge.fastfoodpayment.infra.config.amqp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Binding {
    private String exchange;
    private String routingKey;
    private String queue;
}