package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Produto;
import com.fiap.postech.techchallenge.fastfoodpayment.gateway.MercadoPagoGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CriacaoDePagamentoTest {
    @InjectMocks
    private CriacaoDePagamento criacaoDePagamento;

    @Mock
    private MercadoPagoGateway mercadoPagoGateway;

    @DisplayName("Test - Deve criar pagamento")
    @Test
    public void dadoPedido_EntaoGerarQRCodeParaPagamento() {
        // Dado
        Produto produto =
                new Produto(
                        1, "big mac", "pao, hamburguer e queijo", new BigDecimal("1"), 3);

        Pedido pedido =
                new Pedido(
                        "IdPedido",
                        List.of(produto),
                        BigDecimal.valueOf(30.00),
                        LocalDateTime.now());

        // Quando
        criacaoDePagamento.gerarQrCodeParaPagamento(pedido);

        // Entao
        verify(mercadoPagoGateway, times(1)).gerarQRCode(pedido);

    }
}
