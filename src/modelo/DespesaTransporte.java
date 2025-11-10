package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Herança: DespesaTransporte herda todos os atributos e métodos de Despesa.
public class DespesaTransporte extends Despesa {
    
    // Construtor 1 (Completo) - Chama o construtor pai (super)
    public DespesaTransporte(String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo) {
        super(descricao, valorOriginal, dataVencimento, tipo);
    }
    
    // Construtor 2 (Leitura de Arquivo) - Chama o construtor pai (super)
    public DespesaTransporte(int id, String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo, boolean paga, double valorPago, LocalDate dataPagamento) {
        super(id, descricao, valorOriginal, dataVencimento, tipo, paga, valorPago, dataPagamento);
    }

    // Sobrescrita de Método Abstrato: Implementa a regra de juros específica para Transporte.
    @Override
    public double calcularJuros() {
        if (isPaga()) {
            return 0.0;
        }

        LocalDate hoje = LocalDate.now();
        
        // Juros de 0.5% ao dia após o vencimento
        if (hoje.isAfter(getDataVencimento())) {
            long diasAtraso = ChronoUnit.DAYS.between(getDataVencimento(), hoje);
            double juros = getValorOriginal() * 0.005 * diasAtraso;
            return juros;
        }
        return 0.0;
    }
    
    // Getter para getDataVencimento, pois ele é private na classe pai (Despesa)
    public LocalDate getDataVencimento() {
        // Usamos um getter para acessar o atributo da classe pai
        // Em POO, o atributo privado do pai só pode ser acessado pelo getter/setter do pai,
        // ou se o atributo fosse 'protected'.
        return super.dataVencimento; 
    }
}