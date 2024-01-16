package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConfirmacaoDePagamentoTest {

    @Mock
    private ProducaoPedidoGateway producaoPedidoGateway;

    private ConfirmacaoDePagamento confirmacaoDePagamento;

    @BeforeEach
    public void init() {
        confirmacaoDePagamento = new ConfirmacaoDePagamento(producaoPedidoGateway);
        MockMvcBuilders.standaloneSetup(confirmacaoDePagamento).build();
    }

    @DisplayName("Test - Deve confirmar pagamento")
    @Test
    public void deveConfirmarPagamento() throws Exception {
        // Dado
        String numeroPedido = "1";
        when(producaoPedidoGateway.atualizarStatusPagamento(numeroPedido, StatusPagamento.APROVADO)).thenReturn(PedidoHelper.gerarDadosPedido());

        // Quando
        confirmacaoDePagamento.confirmarPagamento("1");

        // Entao
        verify(producaoPedidoGateway, times(1)).atualizarStatusPagamento(numeroPedido, StatusPagamento.APROVADO);

    }
}