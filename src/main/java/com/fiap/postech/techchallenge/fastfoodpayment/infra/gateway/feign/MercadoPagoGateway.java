package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;

public interface MercadoPagoGateway {
    String gerarQRCode(Pedido pedido);
}
