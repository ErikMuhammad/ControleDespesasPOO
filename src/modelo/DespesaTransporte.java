package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DespesaTransporte extends Despesa {
    
    // Construtor 1 (Completo)
    public DespesaTransporte(String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo) {
        super(descricao, valorOriginal, dataVencimento, tipo);
    }
    
    // Construtor 2 (Leitura de Arquivo)
    public DespesaTransporte(int id, String descricao, double valorOriginal, LocalDate dataVencimento, TipoDespesa tipo, boolean paga, double valorPago, LocalDate dataPagamento) {
        super(id, descricao, valorOriginal, dataVencimento, tipo, paga, valorPago, dataPagamento);
    }

    // Sobrescrita: Implementa a regra de juros específica.
    @Override
    public double calcularJuros() {
        if (isPaga() || LocalDate.now().isBefore(dataVencimento) || LocalDate.now().isEqual(dataVencimento)) {
            return 0.0;
        }

        LocalDate hoje = LocalDate.now();
        // Juros de 0.5% ao dia após o vencimento
        long diasAtraso = ChronoUnit.DAYS.between(dataVencimento, hoje);
        double juros = valorOriginal * 0.005 * diasAtraso;
        return juros;
    }
}