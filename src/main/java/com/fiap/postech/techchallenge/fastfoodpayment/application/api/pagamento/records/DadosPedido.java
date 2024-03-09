package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosProduto;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.cliente.Cliente;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.Pagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.produto.Produto;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.vo.CPF;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.vo.Email;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Objects.isNull;

public record DadosPedido(@JsonInclude(NON_NULL) String numeroPedido, List<DadosProduto> produtos,

                          @JsonInclude(NON_NULL) DadosCliente cliente, DadosPagamento pagamento,

                          @NotNull @JsonSerialize(using = LocalDateTimeSerializer.class) @NotNull @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime dataCriacaoPedido,

                          @NotNull BigDecimal valorTotal,

                          @JsonInclude(NON_NULL) String qrCode) {

    public DadosPedido(Pedido pedido) {
        this(pedido.getNumeroPedido(), pedido.getProdutos().stream().map(DadosProduto::new).collect(Collectors.toList()), isNull(pedido.getCliente()) ? null : new DadosCliente(pedido.getCliente()), isNull(pedido.getPagamento()) ? null : new DadosPagamento(pedido.getPagamento()), pedido.getDataCriacaoPedido(), pedido.getValorTotal(), pedido.getQrCode());
    }

    public Pedido convertToPedido() {
        return new Pedido(numeroPedido, isNull(cliente) ? null : new Cliente(cliente.nome(), new CPF(cliente.cpf()), new Email(cliente.email())), buildProdutos(produtos), valorTotal,
                isNull(pagamento) ? null : new Pagamento(
                        pagamento.dataPagamento(),
                        pagamento.statusPagamento(),
                        pagamento.tipoPagamento(),
                        pagamento.totalPagamento()),
                dataCriacaoPedido);
    }

    private List<Produto> buildProdutos(List<DadosProduto> cadastroProdutos) {
        return cadastroProdutos.stream().map(cadastroProduto -> new Produto(cadastroProduto.id(), cadastroProduto.nome(), cadastroProduto.descricao(), cadastroProduto.categoria(), cadastroProduto.preco(), cadastroProduto.quantidade())).collect(Collectors.toList());
    }


}
