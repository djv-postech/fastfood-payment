package com.fiap.postech.techchallenge.fastfoodpayment.gateway;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;

public interface MercadoPagoGateway {
    String gerarQRCode(Pedido pedido);
}
