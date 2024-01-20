package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago;

import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.converter.QRCodeRequestConverter;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.exception.MercadoPagoQRCodeException;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json.QRCodeResponse;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MercadoPagoFeignGatewayTest {

    @Mock
    private MercadoPagoFeignClient feignClient;

    @Mock
    private MercadoPagoClientProperties properties;

    @Mock
    private FeignException feignException;

    private MercadoPagoFeignGateway mercadoPagoFeignGateway;


    @BeforeEach
    public void init() {
        QRCodeRequestConverter converter = new QRCodeRequestConverter();
        mercadoPagoFeignGateway = new MercadoPagoFeignGateway(feignClient, properties, converter);
        MockMvcBuilders.standaloneSetup(mercadoPagoFeignGateway).build();
    }

    @DisplayName("Test - Deve gerar qrCode")
    @Test
    public void deveGerarQrCode() throws Exception {
        // Dado
        String qrCodeReturned = "qrCode324324234";
        QRCodeResponse qrCodeResponse = new QRCodeResponse();
        qrCodeResponse.setQrCode("qrCode324324234");
        Pedido pedido = PedidoHelper.gerarDadosPedido().convertToPedido();
        when(feignClient.gerarQRCode(any(), any(), any(), any())).thenReturn(qrCodeResponse);

        // Quando
        String qrCode = mercadoPagoFeignGateway.gerarQRCode(pedido);

        // Entao
        Assertions.assertEquals(qrCode, qrCodeReturned);
    }

    @DisplayName("Test - Deve retornar exception quando chamada feign retornar erro")
    @Test
    public void deveGerarExceptionAoReceberErro() throws Exception {
        // Dado
        Pedido pedido = PedidoHelper.gerarDadosPedido().convertToPedido();
        Map<String, Collection<String>> headers = new HashMap<>();
        when(feignClient.gerarQRCode(any(), any(), any(), any())).thenThrow(FeignException.errorStatus("any", Response.builder()
                .status(422)
                .request(Request.create(Request.HttpMethod.POST, "url", headers, "".getBytes(), StandardCharsets.UTF_8))
                .build()));

        // Quando
        // Entao
        Assertions.assertThrows(
                MercadoPagoQRCodeException.class, () -> mercadoPagoFeignGateway.gerarQRCode(pedido));


    }

}