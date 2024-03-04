package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Categoria;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Produto;

import java.math.BigDecimal;

public record DadosProduto(Integer id, String nome, String descricao, BigDecimal preco, Categoria categoria,
                           Integer quantidade) {

    public DadosProduto(Produto dadosProduto) {
        this(dadosProduto.getId(), dadosProduto.getNome(), dadosProduto.getDescricao(),
                dadosProduto.getPreco(), dadosProduto.getCategoria(), dadosProduto.getQuantidade());
    }
}

