package com.fiap.postech.techchallenge.fastfoodpayment;


import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.TipoPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json.ConfirmacaoDePagamentoRequest;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json.DadosDoPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagamentoHelper {

    public static DadosPagamento gerarDadosPagamento() {
        return new DadosPagamento("1", new BigDecimal(10), TipoPagamento.QRCODE, LocalDateTime.now(), StatusPagamento.PROCESSANDO);
    }

    public static ConfirmacaoDePagamentoRequest gerarConfirmacaoDePagamentoRequest(){
        return new ConfirmacaoDePagamentoRequest("1", "confirmar", LocalDateTime.now(), new DadosDoPagamento("1"));
    }

}
