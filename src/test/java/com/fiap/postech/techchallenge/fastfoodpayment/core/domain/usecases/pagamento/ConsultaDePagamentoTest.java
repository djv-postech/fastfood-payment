package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.Pagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaDePagamentoTest {

    @Mock
    private ProducaoPedidoGateway producaoPedidoGateway;

    private ConsultaDePagamento consultaDePagamento;

    @BeforeEach
    public void init() {
        consultaDePagamento = new ConsultaDePagamento(producaoPedidoGateway);
        MockMvcBuilders.standaloneSetup(consultaDePagamento).build();
    }

    @DisplayName("Test - Deve permitir consulta de pagamento")
    @Test
    public void deveRetornarPagamento() throws Exception {
        // Dado
        String numeroPedido = "1";
        when(producaoPedidoGateway.consultaStatusPedido(numeroPedido)).thenReturn(PedidoHelper.gerarDadosPedido());

        // Quando
        Pagamento pagamento = consultaDePagamento.consulta("1");

        // Entao
        verify(producaoPedidoGateway, times(1)).consultaStatusPedido(numeroPedido);
        assertThat(pagamento).isNotNull();

    }
}