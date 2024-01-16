package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.postech.techchallenge.fastfoodpayment.PagamentoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosProduto;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConfirmacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConsultaDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json.ConfirmacaoDePagamentoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTest {

    @Mock
    private CriacaoDePagamento criacaoDePagamento;

    @Mock
    private ConsultaDePagamento consultaDePagamento;

    @Mock
    private ConfirmacaoDePagamento confirmacaoDePagamento;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        PagamentoController pagamentoController = new PagamentoController(criacaoDePagamento, confirmacaoDePagamento, consultaDePagamento);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
    }

    @DisplayName("Test - Deve permitir gerar QRCode para pagamento")
    @Test
    public void deveGerarQRCodeParaPagamento() throws Exception {
        // Dado
        DadosPedido dadosPedido = PedidoHelper.gerarDadosPedido();


        // Quando
        mockMvc.perform(post("/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(dadosPedido)))
                .andExpect(status().isOk());

        // Entao
        verify(criacaoDePagamento, times(1))
                .gerarQrCodeParaPagamento(any(Pedido.class));

    }

    @DisplayName("Test - Deve retornar status do pagamento")
    @Test
    public void deveRetornarStatusDePagamento() throws Exception {
        // Dado
        DadosPedido dadosPedido = PedidoHelper.gerarDadosPedido();

        when(consultaDePagamento.consulta(dadosPedido.numeroPedido())).thenReturn(dadosPedido.pagamento().convertToPagamento());

        // Quando
        mockMvc.perform(get("/pagamento/1/statusPagamento")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Entao
        verify(consultaDePagamento, times(1))
                .consulta(anyString());

    }

    @DisplayName("Test - Deve confirmar o pagamento")
    @Test
    public void deveConfirmarPagamento() throws Exception {
        //Dado
        ConfirmacaoDePagamentoRequest confirmacaoDePagamentoRequest = PagamentoHelper.gerarConfirmacaoDePagamentoRequest();

        // Quando
        mockMvc.perform(post("/pagamento/confirmacaoPagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(confirmacaoDePagamentoRequest)))
                .andExpect(status().isOk());

        // Entao
        verify(confirmacaoDePagamento, times(1))
                .confirmarPagamento(anyString());

    }

    public static String convertToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}