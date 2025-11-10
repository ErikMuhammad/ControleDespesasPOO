package repositorios;

import modelo.TipoDespesa;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class RepositorioTiposDespesa {
    
    private List<TipoDespesa> listaTipos;
    private final String ARQUIVO_DADOS = "tipos_despesa.txt";

    public RepositorioTiposDespesa() {
        this.listaTipos = new ArrayList<>();
        carregarTipos();
    }

    // --- MÉTODOS DE PERSISTÊNCIA ---
    public void carregarTipos() {
        int ultimoId = 0;
        listaTipos.clear(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_DADOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    
                    // Nota: Para simular o ID do arquivo sem duplicar, precisaríamos de uma 
                    // forma de setar o ID diretamente. Aqui, estamos simplificando a lógica
                    // de controle estático do ID para fins didáticos.
                    TipoDespesa tipo = new TipoDespesa(nome);
                    
                    ultimoId = id;
                    listaTipos.add(tipo);
                }
            }
            // Corrige o ID estático no modelo após carregar
            TipoDespesa.setProximoId(ultimoId); 
            
        } catch (FileNotFoundException e) {
            // Ignora se o arquivo não existir
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de tipos de despesa: " + e.getMessage());
        }
    }

    public void salvarTipos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (TipoDespesa tipo : listaTipos) {
                bw.write(tipo.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de tipos de despesa: " + e.getMessage());
        }
    }

    // --- MÉTODOS CRUD ---
    public void adicionar(TipoDespesa tipo) {
        this.listaTipos.add(tipo);
        salvarTipos();
    }

    public List<TipoDespesa> listarTodos() {
        return new ArrayList<>(listaTipos);
    }
    
    public TipoDespesa buscarPorId(int id) {
         return listaTipos.stream()
            .filter(t -> t.getId() == id)
            .findFirst()
            .orElse(null);
    }
}