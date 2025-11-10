package modelo;

import java.time.LocalDate;

// Implementa Pagavel, definindo a assinatura dos métodos de pagamento.
public abstract class Despesa implements Pagavel {

    private static int proximoId = 1; 

    private int id;
    private String descricao;
    protected double valorOriginal; // Protected para acesso em subclasses
    protected LocalDate dataVencimento; // Protected para acesso em subclasses
    private TipoDespesa tipo;
    private boolean paga;
    
    private LocalDate dataPagamento;
    private double valorPago;

    // Construtor Sobrecarga 1: Completo
    public Despesa(String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo) {
        this.id = proximoId++;
        this.descricao = descricao;
        this.valorOriginal = valorOriginal;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.paga = false;
        this.valorPago = 0.0;
    }
    
    // Construtor Sobrecarga 2: Para leitura de arquivo (sem auto-incremento de ID)
    public Despesa(int id, String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo, boolean paga, double valorPago, LocalDate dataPagamento) {
        this.id = id;
        this.descricao = descricao;
        this.valorOriginal = valorOriginal;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.paga = paga;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        
        // Atualiza o ID estático
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }

    // Método Abstrato: Deve ser implementado por TODAS as subclasses. 
    public abstract double calcularJuros(); 

    // --- Implementação do Contrato Pagavel ---

    @Override
    public boolean anotarPagamento(double valorPago) {
        if (this.paga) {
            return false;
        }

        this.valorPago += valorPago;
        
        if (getValorPendente() <= 0.01) {
             this.dataPagamento = LocalDate.now();
             this.paga = true;
        }
        return true;
    }

    @Override
    public double getValorPendente() {
        double totalDevido = this.valorOriginal + this.calcularJuros();
        double pendente = totalDevido - this.valorPago;
        return (pendente > 0) ? pendente : 0.0; // Evita valores negativos
    }

    // --- Getters e Setters Comuns ---
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValorOriginal() { return valorOriginal; }
    public boolean isPaga() { return paga; }
    public TipoDespesa getTipo() { return tipo; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    // Os demais getters/setters podem ser adicionados conforme a necessidade
}