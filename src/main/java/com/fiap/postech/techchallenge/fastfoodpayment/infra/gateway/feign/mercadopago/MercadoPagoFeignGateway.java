package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.MercadoPagoGateway;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.converter.QRCodeRequestConverter;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.exception.MercadoPagoQRCodeException;
import feign.FeignException;

public class MercadoPagoFeignGateway implements MercadoPagoGateway {

    private final MercadoPagoFeignClient feignClient;
    private final MercadoPagoClientProperties properties;

    private final QRCodeRequestConverter converter;

    private static final String REQUEST = ", Request: ";
    private static final String RESPONSE = ", Response: ";

    public MercadoPagoFeignGateway(
            MercadoPagoFeignClient feignClient,
            MercadoPagoClientProperties properties,
            QRCodeRequestConverter converter) {
        this.feignClient = feignClient;
        this.properties = properties;
        this.converter = converter;
    }

    @Override
    public String gerarQRCode(Pedido pedido) {
        try {
            return converter.convertFrom(
                    feignClient.gerarQRCode(
                            properties.getAuthToken(),
                            properties.getUserId(),
                            properties.getExternalPosId(),
                            converter.convertFrom(pedido)));

        } catch (FeignException e) {
            String message =
                    e.getMessage()
                            .concat(REQUEST)
                            .concat(converter.convertFrom(pedido).toString())
                            .concat(RESPONSE)
                            .concat(e.contentUTF8());
            throw new MercadoPagoQRCodeException(message);
        }
    }
}
