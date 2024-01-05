package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Produto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


public record DadosPedido(
        String id,
        List<DadosProduto> dadosProdutos,

        @NotNull @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime dataCriacaoPedido,

        BigDecimal valorTotal) {

    public Pedido convertToPedido() {
        return new Pedido(
                id,
               buildProdutos(dadosProdutos),
                valorTotal,
                dataCriacaoPedido);
    }

    private List<Produto> buildProdutos(List<DadosProduto> dadosProdutos) {
        return dadosProdutos.stream()
                .map(
                        dadosProduto ->
                                new Produto(
                                        dadosProduto.id(),
                                        dadosProduto.nome(),
                                        dadosProduto.descricao(),
                                        dadosProduto.preco(),
                                        dadosProduto.quantidade()))
                .collect(Collectors.toList());
    }
}
