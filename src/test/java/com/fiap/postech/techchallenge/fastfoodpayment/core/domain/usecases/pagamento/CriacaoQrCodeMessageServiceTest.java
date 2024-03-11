package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CriacaoQrCodeMessageServiceTest {

    @Mock private CriacaoDePagamento criacaoDePagamento;

    @Mock private RabbitTemplate rabbitTemplate;

    private CriacaoQrCodeMessageService criacaoQrCodeMessageService;


    @BeforeEach
    public void init() {
        criacaoQrCodeMessageService = new CriacaoQrCodeMessageService(criacaoDePagamento, rabbitTemplate);
        MockMvcBuilders.standaloneSetup(criacaoQrCodeMessageService).build();
    }

    @DisplayName("Test - Gerar qr code para pagamento")
    @Test
    public void deveGerarQrCodeParaPagamento() throws Exception {
        // Dado
        DadosPedido dadosPedido = PedidoHelper.gerarDadosPedido();

        // Quando
        criacaoQrCodeMessageService.criacaoDePagamento(dadosPedido.convertToPedido());

        // Entao
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(DadosPedido.class));
    }

}