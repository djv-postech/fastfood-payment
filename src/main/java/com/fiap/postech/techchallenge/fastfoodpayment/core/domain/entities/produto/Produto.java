package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto;


import java.math.BigDecimal;


public class Produto {

    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
    private Categoria categoria;

    public Produto(Integer id, String nome, String descricao, BigDecimal preco, Integer quantidade, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public Produto(Integer id, String nome, String descricao, Categoria categoria, BigDecimal preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
    }


    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

}
