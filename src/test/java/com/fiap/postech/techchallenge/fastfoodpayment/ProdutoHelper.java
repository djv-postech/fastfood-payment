package com.fiap.postech.techchallenge.fastfoodpayment;


import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosProduto;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Categoria;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Produto;

import java.math.BigDecimal;

public class ProdutoHelper {


    public static DadosProduto gerarDadosProduto() {
        return new DadosProduto(1, "Hamburguer", "Descricao Big Mac", new BigDecimal(10), Categoria.LANCHE, 5);
    }

}
