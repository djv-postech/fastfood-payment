package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto;


import java.math.BigDecimal;

public class Produto {

    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;

    public Produto(Integer id, String nome, String descricao, BigDecimal preco, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getNome(){
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

}
