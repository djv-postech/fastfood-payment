package com.fiap.postech.techchallenge.fastfoodpayment;


import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoHelper {


    public static DadosPedido gerarDadosPedido() {
        return null;
      //  return new DadosPedido("1", List.of(ProdutoHelper.gerarDadosProduto()), LocalDateTime.now(), PagamentoHelper.gerarDadosPagamento(), BigDecimal.valueOf(50) );
    }

    public static DadosPedido gerarDadosPedidoComValorInvalido() {
        return null;
    //    return new DadosPedido("1", List.of(ProdutoHelper.gerarDadosProduto()), LocalDateTime.now(), PagamentoHelper.gerarDadosPagamento(), BigDecimal.valueOf(1) );
    }

}
