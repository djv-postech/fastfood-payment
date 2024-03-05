package com.fiap.postech.techchallenge.fastfoodpayment;


import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosCliente;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoHelper {


    public static DadosPedido gerarDadosPedido() {
        return new DadosPedido("1", List.of(ProdutoHelper.gerarDadosProduto()), new DadosCliente("Cliente", "123.456.789-01", "email@email.com"), PagamentoHelper.gerarDadosPagamento(), LocalDateTime.now(), BigDecimal.valueOf(50), "qrCode" );
    }

    public static DadosPedido gerarDadosPedidoComValorInvalido() {
        return new DadosPedido("1", List.of(ProdutoHelper.gerarDadosProduto()), null, PagamentoHelper.gerarDadosPagamento(),LocalDateTime.now(), BigDecimal.valueOf(1), "qrCode" );
    }

}
