package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;


import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.cliente.Cliente;

public record DadosCliente(String nome, String cpf, String email) {

  public DadosCliente(Cliente dadosCliente) {
    this(dadosCliente.getNome(), dadosCliente.getCpf(), dadosCliente.getEmail());
  }
}
