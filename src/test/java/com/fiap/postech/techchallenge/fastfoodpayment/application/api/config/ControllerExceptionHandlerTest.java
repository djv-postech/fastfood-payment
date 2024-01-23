package com.fiap.postech.techchallenge.fastfoodpayment.application.api.config;

import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.exception.MercadoPagoQRCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ControllerExceptionHandlerTest {
    private static final String EXCEPTION_MESSAGE = "Exception Message";

    private ControllerExceptionHandler controllerExceptionHandler;

    @BeforeEach
    public void init() {
        controllerExceptionHandler = new ControllerExceptionHandler();
        MockMvcBuilders.standaloneSetup(controllerExceptionHandler).build();
    }

    @Test
    @DisplayName("Test - Deve tratar MercadoPagoQRCodeException")
    public void trataSubtracaoDeMercadoPagoQRCodeExceptionn() {
        // Dado
        final var exception = new MercadoPagoQRCodeException(EXCEPTION_MESSAGE);

        // Quando
        final var response = controllerExceptionHandler.handlerMercadoPagoQRCodeException(exception);

        // Entao
        final var errorMap = response.getBody();
        assertEquals(response.getStatusCode(), (HttpStatus.BAD_REQUEST));
        assert errorMap != null;
        assertEquals(400, errorMap.getStatus());
    }
}