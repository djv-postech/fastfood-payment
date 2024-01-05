package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento;


import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.StatusPagamentoPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.gateway.feign.mercadopago.json.ConfirmacaoDePagamentoRequest;
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

    public PagamentoController(
            CriacaoDePagamento criacaoDePagamento) {
        this.criacaoDePagamento = criacaoDePagamento;
    }


    @Operation(summary = "Gerar QRCode para dadosPagamento")
    @PostMapping
    public ResponseEntity<String> gerarPagamento(@Valid @RequestBody DadosPedido dadosPedido) {
        final Pedido pedido = dadosPedido.convertToPedido();
        final String qrCode = criacaoDePagamento.gerarQrCodeParaPagamento(pedido);

        return ResponseEntity.ok(qrCode);
    }

    // FIXME: Ao testar os serviços, verificar a melhor forma de consultar o status do pagamento
//    @Operation(summary = "Consultar status do pagamento do pedido")
//    @GetMapping("/{numeroPedido}/statusPagamento")
//    public ResponseEntity<StatusPagamentoPedido> verificarStatusPagamentoPedido(
//            @PathVariable String numeroPedido) {
//        Pedido pedido = listagemDePedidoPorNumeroDePedido.listarPedidoPorNumeroPedido(numeroPedido);
//
//        return ResponseEntity.ok(
//                new StatusPagamentoPedido(numeroPedido, pedido.getPagamento().getStatusPagamento()));
//    }

    @Operation(summary = "Webhook para confirmação de pagamento")
    @PostMapping("/confirmacaoPagamento")
    public ResponseEntity<StatusPagamentoPedido> confirmarPagamento(
            @RequestBody ConfirmacaoDePagamentoRequest confirmacaoDePagamentoRequest) {
        final String numeroPedido = confirmacaoDePagamentoRequest.getDadosDoPagamento().getId();


        //FIXME: Ao receber a confirmaçao de pagamento, chamar api para atualizar status
        return ResponseEntity.ok(
                new StatusPagamentoPedido(numeroPedido, null));
    }
}

