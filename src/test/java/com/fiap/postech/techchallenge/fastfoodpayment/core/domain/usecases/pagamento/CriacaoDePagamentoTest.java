package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.ProdutoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Produto;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.MercadoPagoGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
        Pedido pedido =
                PedidoHelper.gerarDadosPedido().convertToPedido();

        // Quando
        criacaoDePagamento.gerarQrCodeParaPagamento(pedido);

        // Entao
        verify(mercadoPagoGateway, times(1)).gerarQRCode(pedido);

    }
}
