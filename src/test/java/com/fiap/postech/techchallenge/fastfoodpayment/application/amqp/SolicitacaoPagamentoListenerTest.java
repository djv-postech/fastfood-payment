package com.fiap.postech.techchallenge.fastfoodpayment.application.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConfirmacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoQrCodeMessageService;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.ProducaoPedidoGateway;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.exception.MercadoPagoQRCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoPagamentoListenerTest {

    @Mock
    private CriacaoQrCodeMessageService criacaoQrCodeMessageService;

    @Mock
    private CriacaoDePagamento criacaoDePagamento;

    private SolicitacaoPagamentoListener solicitacaoPagamentoListener;

    @BeforeEach
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        solicitacaoPagamentoListener = new SolicitacaoPagamentoListener(criacaoDePagamento, objectMapper, criacaoQrCodeMessageService);
        MockMvcBuilders.standaloneSetup(criacaoQrCodeMessageService).build();
    }

    @DisplayName("Test - Deve criar pagamento")
    @Test
    public void deveGerarPagamento() throws Exception {
        // Dado
        DadosPedido dadosPedido = PedidoHelper.gerarDadosPedido();

        // Quando
        solicitacaoPagamentoListener.gerarPagamento(dadosPedido);

        // Entao
        verify(criacaoQrCodeMessageService, times(1)).criacaoDePagamento(any(Pedido.class));
    }

    @DisplayName("Test - Deve logar erro quando exceção é lançada na integração com mercado pago")
    @Test
    public void deveGerarMensagemDeErroQuandoIntegracaoMercadoPagaFalha() throws Exception {
        // Dado
        DadosPedido dadosPedido = PedidoHelper.gerarDadosPedido();
        doThrow(new MercadoPagoQRCodeException("Erro"))
                .when(criacaoQrCodeMessageService)
                .criacaoDePagamento(any());

        // Quando
        solicitacaoPagamentoListener.gerarPagamento(dadosPedido);

        // Entao
        verify(criacaoQrCodeMessageService, times(1)).criacaoDePagamento(any(Pedido.class));
        Assertions.assertThrows(
                MercadoPagoQRCodeException.class, () -> criacaoQrCodeMessageService.criacaoDePagamento(dadosPedido.convertToPedido()));

    }

}
