## Critérios de Avaliação (Checklist UTFPR)

### PRINCIPAIS
- [X] Desenvolvimento da tela principal
- [X] Desenvolvimento da tela de listagem
- [X] Consistência dos campos de entrada da tela principal (lançamento) - precisa adicionar validações no saveTransaction() da model
- [X] Persistência de dados no banco de dados 
- [X] Navegabilidade entre telas
- [X] Organização do código (MVC ou MVVM)

### PLUS
- [x] Apresentação dos dados na lista utilizando adapter - PLUS (não utilizamos adapter por conta de estar usando compose)
- [x] Uso de datepicker - PLUS
- [x] Diferenciação de crédito e débito na tela de listagem - PLUS
- [x] Apresentação do saldo - PLUS

### TODOs

- [X] Adicionar ícone ao projeto
- [ ] Componentização da ListTransactionScreen
    - [ ] Extrair `SummaryCard` para um arquivo próprio.
    - [ ] Extrair `TransactionCardSimple` para um arquivo próprio.
    - [ ] Rever lógicas nestes componentes também.Ppor exemplo passar amount e date já formatados para TransactionCardSimple
- [X] Sanitização do Campo de Valor
    - [X] Verificar melhor forma de sanitizar o campo - como convertamos para double podemos ter problemas com números como 23300,00 - 23.300,00
    - [X] Implementar máscara de entrada para dinheiro (ex: formatar "1000" para "R$ 10,00" em tempo real - verificar como fica com números como 23300,00)
- [X] Melhorar as validações que temos na inserção da transação - retornar mensagem par a UI indicando sucesso ou erro 
- [ ] Verificar se temos lugares onde está sendo usado os tipos de transaction de forma hardcoded (e não o enum)
- [X] Verificar necessidade de padronizar os nomes de componentes
- [X] Validar se estamos contemplando o padrão MVVM como esperado (antes da entrega)
- [ ] SE DER - melhorar a forma como filtramos as transações - trazer tudo filtrado do banco
- [ ] SE DER - passar a usar LazyColumn no componente que tem a lista de transações
- [ ] SE DER - Implementar `@Preview` para todos os componentes criados
- [ ] SE DER - Mover strings fixas para o arquivo `strings.xml`