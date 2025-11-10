package repositorios;

import modelo.Despesa;
import modelo.TipoDespesa; 
import java.util.ArrayList;
import java.util.List;

// Importar as subclasses concretas será necessário para carregar/salvar corretamente
import modelo.DespesaTransporte; 

public class RepositorioDespesas {
    
    private List<Despesa> listaDespesas;
    private final String ARQUIVO_DADOS = "despesas.txt";

    public RepositorioDespesas() {
        this.listaDespesas = new ArrayList<>();
        // Futuramente: carregarDespesas();
    }

    // --- MÉTODOS CRUD ---
    public void adicionar(Despesa despesa) {
        this.listaDespesas.add(despesa);
        // Futuramente: salvarDespesas();
    }

    public List<Despesa> listarTodos() {
        return new ArrayList<>(listaDespesas);
    }
    
    // Método implementado para a opção 3 e 4 do menu
    public List<Despesa> listarPorStatus(boolean paga) {
        List<Despesa> filtrada = new ArrayList<>();
        for (Despesa d : listaDespesas) {
            if (d.isPaga() == paga) {
                filtrada.add(d);
            }
        }
        return filtrada;
    }

    // Futuramente, a lógica de persistência em arquivo será implementada aqui.
}