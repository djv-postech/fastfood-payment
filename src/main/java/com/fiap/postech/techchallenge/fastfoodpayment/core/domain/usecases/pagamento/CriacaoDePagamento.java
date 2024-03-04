package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.MercadoPagoGateway;

public class CriacaoDePagamento {
    private final MercadoPagoGateway mercadoPagoGateway;

    public CriacaoDePagamento(MercadoPagoGateway mercadoPagoGateway) {
        this.mercadoPagoGateway = mercadoPagoGateway;
    }

    public String gerarQrCodeParaPagamento(Pedido pedido) {
        return mercadoPagoGateway.gerarQRCode(pedido);
    }

}
