package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento;


import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pedido.Pedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.CriacaoDePagamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dadosPagamento")
@Tag(name = "Pagamentos", description = "Rest api para operações de pagamentos")
public class PagamentoController {

    private final CriacaoDePagamento criacaoDePagamento;

    public PagamentoController(
            CriacaoDePagamento criacaoDePagamento){
        this.criacaoDePagamento = criacaoDePagamento;
    }


    @Operation(summary = "Gerar QRCode para dadosPagamento")
    @PostMapping
    public ResponseEntity<String> gerarPagamento(@Valid @RequestBody DadosPedido dadosPedido) {
        final Pedido pedido = dadosPedido.convertToPedido();
        final String qrCode = criacaoDePagamento.gerarQrCodeParaPagamento(pedido);

        return ResponseEntity.ok(qrCode);
    }
}
