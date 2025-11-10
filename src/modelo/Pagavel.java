package modelo;

// Interface: Define o contrato para qualquer item que possa receber um pagamento.
public interface Pagavel {

    boolean anotarPagamento(double valorPago);

    double getValorPendente();
    
}