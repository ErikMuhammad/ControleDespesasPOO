package modelo;

import java.time.LocalDate;

// Classe Abstrata: Não pode ser instanciada diretamente, serve como base para subclasses.
// Implementa a Interface Pagavel, definindo a assinatura dos métodos de pagamento.

public abstract class Despesa implements Pagavel {

    // Atributo estático para contagem global e unicidade de ID
    private static int proximoId = 1; 

    private int id;
    private String descricao;
    private double valorOriginal;
    private LocalDate dataVencimento;
    private TipoDespesa tipo;
    private boolean paga;
    
    // Atributos de Pagamento
    private LocalDate dataPagamento;
    private double valorPago;

    // Construtor Sobrecarga 1: Construtor completo
    public Despesa(String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo) {
        this.id = proximoId++;
        this.descricao = descricao;
        this.valorOriginal = valorOriginal;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.paga = false;
        this.valorPago = 0.0;
    }
    
    // Construtor Sobrecarga 2: Construtor simplificado (para leitura de arquivo)
    public Despesa(int id, String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo, boolean paga, double valorPago, LocalDate dataPagamento) {
        this.id = id;
        this.descricao = descricao;
        this.valorOriginal = valorOriginal;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.paga = paga;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        
        // Atualiza o ID estático para garantir que novos objetos comecem do ID correto
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }

    // Método Abstrato: Deve ser implementado por TODAS as subclasses. 
    // Garante que cada tipo de despesa defina sua própria regra de cálculo de juros/multa.
    public abstract double calcularJuros(); 

    // Implementação do Contrato Pagavel (Métodos Concretos)

    @Override
    public boolean anotarPagamento(double valorPago) {
        if (this.paga) {
            return false; // Já está paga
        }

        double totalDevido = this.valorOriginal + this.calcularJuros();

        if (valorPago >= totalDevido) {
            this.valorPago = valorPago;
            this.dataPagamento = LocalDate.now();
            this.paga = true;
            return true;
        }
        
        // Em um sistema real, você registraria pagamentos parciais aqui.
        // Por simplicidade, vamos exigir o pagamento total, ou pelo menos o valor pendente.
        this.valorPago += valorPago;
        // Se o pagamento parcial for o último, marque como paga.
        if (getValorPendente() <= 0.01) { // Margem de erro para double
             this.dataPagamento = LocalDate.now();
             this.paga = true;
        }
        return true;
    }

    @Override
    public double getValorPendente() {
        double totalDevido = this.valorOriginal + this.calcularJuros();
        return totalDevido - this.valorPago;
    }

    // --- Getters e Setters Comuns (Encapsulamento) ---
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValorOriginal() { return valorOriginal; }
    public boolean isPaga() { return paga; }
    public TipoDespesa getTipo() { return tipo; }
    // ... (Outros Getters e Setters necessários) ...
}