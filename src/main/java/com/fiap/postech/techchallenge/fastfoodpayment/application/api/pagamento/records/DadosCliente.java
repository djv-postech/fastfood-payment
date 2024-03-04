package com.fiap.postech.techchallenge.fastfoodpayment.application.api.pagamento.records;


import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.entities.cliente.Cliente;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.vo.CPF;
import com.fiap.postech.techchallenge.fastfoodpayment.core.domain.vo.Email;

public record DadosCliente(String nome, String cpf, String email) {

  public DadosCliente(Cliente dadosCliente) {
    this(dadosCliente.getNome(), dadosCliente.getCpf(), dadosCliente.getEmail());
  }

  public Cliente convertToCliente() {
    return new Cliente(nome, new CPF(cpf),
            new Email(email));
  }
}
