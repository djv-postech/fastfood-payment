package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento;

import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.StatusPagamentoPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.Pagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.StatusPagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.AtualizacaoStatusDePagamentoMessageService;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConsultaDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json.ConfirmacaoDePagamentoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
@Tag(name = "Pagamentos", description = "Rest api para operações de pagamentos")
public class PagamentoController {

    private final CriacaoDePagamento criacaoDePagamento;

    private final AtualizacaoStatusDePagamentoMessageService atualizacaoStatusDePagamentoMessageService;



    private final ConsultaDePagamento consultaDePagamento;


    public PagamentoController(
            CriacaoDePagamento criacaoDePagamento, AtualizacaoStatusDePagamentoMessageService atualizacaoStatusDePagamentoMessageService, ConsultaDePagamento consultaDePagamento) {
        this.criacaoDePagamento = criacaoDePagamento;
        this.consultaDePagamento = consultaDePagamento;
        this.atualizacaoStatusDePagamentoMessageService = atualizacaoStatusDePagamentoMessageService;
    }

    @Operation(summary = "Gerar QRCode para dadosPagamento")
    @PostMapping
    public ResponseEntity<String> gerarPagamento(@Valid @RequestBody DadosPedido dadosPedido) {
        final Pedido pedido = dadosPedido.convertToPedido();
        final String qrCode = criacaoDePagamento.gerarQrCodeParaPagamento(pedido);

        return ResponseEntity.ok(qrCode);
    }

    @Operation(summary = "Consultar status do pagamento do pedido")
    @GetMapping("/{numeroPedido}/statusPagamento")
    public ResponseEntity<StatusPagamentoPedido> verificarStatusPagamentoPedido(
            @PathVariable String numeroPedido) {
        Pagamento pagamento = consultaDePagamento.consulta(numeroPedido);

        return ResponseEntity.ok(
                new StatusPagamentoPedido(numeroPedido, pagamento.getStatusPagamento()));
    }

    @Operation(summary = "Webhook para confirmação de pagamento")
    @PostMapping("/confirmacaoPagamento")
    public ResponseEntity<Void> confirmarPagamento(
            @RequestBody ConfirmacaoDePagamentoRequest confirmacaoDePagamentoRequest) {
        final String numeroPedido = confirmacaoDePagamentoRequest.getDadosDoPagamento().getId();

        atualizacaoStatusDePagamentoMessageService.atualizarPagamento(numeroPedido, confirmacaoDePagamentoRequest.getAcao());

        return ResponseEntity.ok().build();
    }
}

