package com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido;

import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

        private String numeroPedido;

        private final List<Produto> produtos;

        private final BigDecimal valorTotal;
        ;
        private final LocalDateTime dataCriacaoPedido;

        public Pedido(
                String numeroPedido,

                List<Produto> produtos,
                BigDecimal valorTotal,

                LocalDateTime dataCriacaoPedido) {

            this.produtos = produtos;

            this.numeroPedido = numeroPedido;

            this.valorTotal = valorTotal;

            this.dataCriacaoPedido = dataCriacaoPedido;
        }

        public String getNumeroPedido() {
            return numeroPedido;
        }

        public List<Produto> getProdutos() {
            return produtos;
        }


        public BigDecimal getValorTotal() {
            return valorTotal;
        }


        public LocalDateTime getDataCriacaoPedido() {
            return dataCriacaoPedido;
        }
 }

