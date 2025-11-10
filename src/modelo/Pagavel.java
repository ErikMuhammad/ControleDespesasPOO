package modelo;

// Interface Pagavel: Define o contrato para qualquer item que possa receber um pagamento.
public interface Pagavel {

    /**
     * Marca a despesa como paga e registra o valor/data do pagamento.
     * @param valorPago O valor efetivamente pago.
     * @return true se o pagamento for bem-sucedido e a despesa for conciliada.
     */
    boolean anotarPagamento(double valorPago);

    /**
     * Retorna o valor que ainda está pendente de pagamento (saldo devedor).
     * @return O valor pendente.
     */
    double getValorPendente();
    
}