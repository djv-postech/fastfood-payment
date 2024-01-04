package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;

import java.math.BigDecimal;

public record DadosProduto(Integer id, String nome, String descricao, BigDecimal preco,
                           Integer quantidade) {

}

