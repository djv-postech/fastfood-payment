package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido;

import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.MercadoPagoClientProperties;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.MercadoPagoFeignClient;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.MercadoPagoFeignGateway;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.converter.QRCodeRequestConverter;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json.QRCodeResponse;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProducaoPedidoFeignGatewayTest {
    @Mock
    private ProducaoPedidoFeignClient producaoPedidoFeignClient;


    private ProducaoPedidoFeignGateway producaoPedidoFeignGateway;

    @BeforeEach
    public void init() {
        producaoPedidoFeignGateway = new ProducaoPedidoFeignGateway(producaoPedidoFeignClient);
        MockMvcBuilders.standaloneSetup(producaoPedidoFeignGateway).build();
    }

    @DisplayName("Test - Deve atualizar status pagamento")
    @Test
    public void deveGerarQrCode() throws Exception {
        // Dado
        String numeroPedido = "1";
        StatusPagamento statusPagamento = StatusPagamento.APROVADO;
       when(producaoPedidoFeignClient.atualizarStatusPagamentoPedido(numeroPedido, statusPagamento)).thenReturn(PedidoHelper.gerarDadosPedido());

        // Quando
        DadosPedido dadosPedido = producaoPedidoFeignGateway.atualizarStatusPagamento(numeroPedido, statusPagamento);

        // Entao
        Assertions.assertEquals(dadosPedido.numeroPedido(), numeroPedido);
    }

    @DisplayName("Test - Deve retornar status pagamento")
    @Test
    public void deveRetornarStatusPagamento() throws Exception {
        // Dado
        String numeroPedido = "1";
        when(producaoPedidoFeignClient.listarPedido(numeroPedido)).thenReturn(PedidoHelper.gerarDadosPedido());

        // Quando
        DadosPedido dadosPedido = producaoPedidoFeignGateway.consultaStatusPedido(numeroPedido);

        // Entao
        Assertions.assertNotNull(dadosPedido);
        Assertions.assertEquals(dadosPedido.numeroPedido(), numeroPedido);
    }
}