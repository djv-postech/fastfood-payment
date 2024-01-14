package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosProduto;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConfirmacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConsultaDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTest {

    @Mock
    private CriacaoDePagamento criacaoDePagamento;

    @Mock
    private ConfirmacaoDePagamento confirmacaoDePagamento;

    @Mock
    private ConsultaDePagamento consultaDePagamento;

    private MockMvc mockMvc;
    private PagamentoController pagamentoController;

    @BeforeEach
    public void init() {
        pagamentoController = new PagamentoController(criacaoDePagamento, confirmacaoDePagamento, consultaDePagamento);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
    }

    @DisplayName("Test - Deve permitir gerar QRCode para pagamento")
    @Test
    public void deveGerarQRCodeParaPagamento() throws Exception {
        //FIXME:  Criar helper


        // Dado
        DadosPedido dadosPedido = new DadosPedido("123", List.of(new DadosProduto(1, "Hamburguer", "Hamburger", new BigDecimal("10.5"), 1)), LocalDateTime.now(),
               null, new BigDecimal("10.5"));


        // Quando
        mockMvc.perform(post("/pagamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(dadosPedido)))
                .andExpect(status().isOk());

        // Entao
        verify(criacaoDePagamento, times(1))
                .gerarQrCodeParaPagamento(any(Pedido.class));

    }

    public static String convertToJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}