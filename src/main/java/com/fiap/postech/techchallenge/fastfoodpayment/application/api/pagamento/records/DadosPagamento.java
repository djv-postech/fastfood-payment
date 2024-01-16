package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.Pagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.TipoPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosPagamento(
        String id,
        BigDecimal totalPagamento,
        TipoPagamento tipoPagamento,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime dataPagamento,
        StatusPagamento statusPagamento) {

    public DadosPagamento(Pagamento pagamento) {
        this(
                pagamento.getId(),
                pagamento.getTotalPagamento(),
                pagamento.getTipoPagamento(),
                pagamento.getDataEHorarioPagamento(),
                pagamento.getStatusPagamento());
    }

    public Pagamento convertToPagamento() {
        return new Pagamento(id, totalPagamento, tipoPagamento, dataPagamento, statusPagamento);
    }

}
