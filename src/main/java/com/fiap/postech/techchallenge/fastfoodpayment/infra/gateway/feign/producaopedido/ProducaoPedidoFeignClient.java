package com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "producaoPedido",
        url = "${apis.fastfood.producao-pedido.client.url}",
        configuration = ProducaoPedidoClientConfig.class)
public interface ProducaoPedidoFeignClient {

    @PutMapping("producao/pedido/{numeroPedido}/statusPagamento/{statusPagamento}")
    DadosPedido atualizarStatusPagamentoPedido(
            @PathVariable("numeroPedido") String numeroPedido,
            @PathVariable("statusPagamento") StatusPagamento statusPagamento);

    @GetMapping("producao/pedido/{numeroPedido}")
    DadosPedido listarPedido(@PathVariable String numeroPedido);
}