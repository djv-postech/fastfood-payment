package com.fiap.postech.techchallenge.fastfoodpayment.bdd;

import com.fiap.postech.techchallenge.fastfoodpayment.PagamentoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.PedidoHelper;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.PagamentoController;
import com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records.DadosPedido;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.pagamento.Pagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConfirmacaoDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.usecases.pagamento.ConsultaDePagamento;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.mercadopago.json.ConfirmacaoDePagamentoRequest;
import com.fiap.postech.techchallenge.fastfoodpayment.infra.gateway.feign.producaopedido.ProducaoPedidoFeignClient;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.mockito.Mockito.*;


public class DefinicaoPassosPagamento {

    private Response response;
    private final String BASE_URL = "http://localhost:8083";
    private final String ENDPOINT_API_PAGAMENTO = BASE_URL + "/pagamento";

    private final String ENDPOINT_API_CONFIRMACAO_PAGAMENTO = BASE_URL + "/pagamento/confirmacaoPagamento";
    private ConfirmacaoDePagamento confirmacaoDePagamento;

    private ConsultaDePagamento consultaDePagamento;

    @Before
    public void setUp() {
        confirmacaoDePagamento = mock(ConfirmacaoDePagamento.class);
        consultaDePagamento = mock(ConsultaDePagamento.class);
    }
    @Quando("solicitar um novo qr code para pagamento")
    public String qrCode_pagamento() {

        DadosPedido dadosPedido = PedidoHelper.gerarDadosPedido();

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dadosPedido)
                .when()
                .post(ENDPOINT_API_PAGAMENTO);

        return response.then().extract().asString();
    }

    @Entao("o qrCode é gerado sucesso")
    public void o_qrCode_é_gerado_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Entao("deve ser retornado qrCode")
    public void deve_ser_apresentado() {
        response.then()
                .body(not(isEmptyString()));
    }

@Quando("solicitar um novo qr code para pagamento com dados inválidos")
public String qrCode_pagamento_com_erro() {

    DadosPedido dadosPedido = PedidoHelper.gerarDadosPedidoComValorInvalido();

    response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(dadosPedido)
            .when()
            .post(ENDPOINT_API_PAGAMENTO);

    return response.then().extract().asString();
}

    @Entao("o qrCode não é gerado")
    public void o_qrCode_nao_é_gerado() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Entao("deve ser retornada mensagem de erro")
    public void deve_ser_apresentada_mensagem_de_erro() {
        response.then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(matchesJsonSchemaInClasspath("schemas/erro.schema.json"));
    }

    @Quando("confirmar um pagamento quando o pedido não é encontrado")
    public String confirmacao_pagamento_pedido_nao_encontrado() {

        ConfirmacaoDePagamentoRequest confirmacaoDePagamentoRequest = PagamentoHelper.gerarConfirmacaoDePagamentoRequest();

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(confirmacaoDePagamentoRequest)
                .when()
                .post(ENDPOINT_API_CONFIRMACAO_PAGAMENTO);

        return response.then().extract().asString();
    }

    @Entao("o pagamento não é confirmado")
    public void o_pagamento_não_é_confirmado() {
        response.then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Entao("deve retornar status de erro")
    public void devem_retornar_status_de_erro() {
        response.then()
                .body(not(isEmptyString()));
    }

    @Dado("que um pagamento foi confirmado")
    public void que_um_pagamento_foi_confirmado() {
        doNothing().when(confirmacaoDePagamento).confirmarPagamento(anyString());
    }

    @Quando("confirmar pagamento com sucesso")
    public String confirmacao_pagamento_com_sucesso() {

        ConfirmacaoDePagamentoRequest confirmacaoDePagamentoRequest = PagamentoHelper.gerarConfirmacaoDePagamentoRequest();

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(confirmacaoDePagamentoRequest)
                .when()
                .post(ENDPOINT_API_CONFIRMACAO_PAGAMENTO);

        return response.then().extract().asString();
    }

    @Entao("deve retornar status ok")
    public void deve_retornar_status_ok() {
        response.then()
                .body(not(isEmptyString()));
    }

    @Dado("que o pedido consultado existe e possui informações do pagamento")
    public void que_o_pedido_consultado_existe() {
        Pagamento pagamento = PagamentoHelper.gerarDadosPagamento().convertToPagamento();
        when(consultaDePagamento.consulta(anyString())).thenReturn(pagamento);
    }

    @Quando("consultar o status do pagamento")
    public String consultar_o_status_do_pagamento() {

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(ENDPOINT_API_PAGAMENTO + "/{numeroPedido}/statusPagamento", "1");


        return response.then().extract().asString();
    }

    @Entao("deve retornar informações do pagamento")
    public void deve_retornar_informações_do_pagamento() {
        response.then()
                .body((isEmptyString()));
    }

}
