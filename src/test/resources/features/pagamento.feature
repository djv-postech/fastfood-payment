# language: pt

  Funcionalidade: Pagamento

    Cenario: Gerar qrCode com sucesso
      Quando solicitar um novo qr code para pagamento
      Entao o qrCode é gerado sucesso
      E deve ser retornado qrCode

    Cenario: Gerar qrCode com erro
      Quando solicitar um novo qr code para pagamento com dados inválidos
      Entao o qrCode não é gerado
      E deve ser retornada mensagem de erro

    Cenario:Confirmar pagamento com erro
      Quando confirmar um pagamento quando o pedido não é encontrado
      Entao deve retornar status de erro

    Cenario:Confirmar pagamento com sucesso
      Dado que um pagamento foi confirmado
      Quando confirmar pagamento com sucesso
      Entao deve retornar status ok

#    Cenario:Consultar status de pagamento existente
#      Dado que o pedido consultado existe e possui informações do pagamento
#      Quando consultar o status do pagamento
#      Entao deve retornar informações do pagamento
