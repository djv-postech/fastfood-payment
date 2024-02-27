package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public record DadosPagamentoGerado(String numeroPedido, String idPagamento,
                                   @JsonSerialize(using = LocalDateTimeSerializer.class)
                                   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                                   LocalDateTime dataPagamento, String qrCode) {
    public DadosPagamentoGerado(String numeroPedido, String idPagamento,
    LocalDateTime dataPagamento, String qrCode) {
        this.numeroPedido = numeroPedido;
        this.idPagamento = idPagamento;
        this.dataPagamento = dataPagamento;
        this.qrCode = qrCode;
    }
}
