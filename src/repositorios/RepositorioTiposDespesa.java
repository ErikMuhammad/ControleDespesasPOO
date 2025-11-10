package repositorios;

import modelo.TipoDespesa;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class RepositorioTiposDespesa {
    
    // Lista que armazena todos os objetos TipoDespesa em memória.
    private List<TipoDespesa> listaTipos;
    private final String ARQUIVO_DADOS = "tipos_despesa.txt";

    // Construtor
    public RepositorioTiposDespesa() {
        this.listaTipos = new ArrayList<>();
        // Tenta carregar os dados ao iniciar o repositório
        carregarTipos();
    }

    // --- MÉTODOS DE PERSISTÊNCIA (Arquivo de Texto) ---

    /**
     * Carrega os tipos de despesa do arquivo de texto para a memória.
     */
    public void carregarTipos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_DADOS))) {
            String linha;
            int ultimoId = 0;
            
            // Limpa a lista antes de carregar
            listaTipos.clear(); 
            
            while ((linha = br.readLine()) != null) {
                // O método toString() do TipoDespesa usa ";" como separador (ID;NOME)
                String[] partes = linha.split(";");
                
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    
                    // Adiciona um novo tipo à lista
                    // O ID já é gerenciado pelo construtor, mas aqui estamos lendo o ID.
                    // Para evitar duplicidade de IDs, vamos criar um TipoDespesa temporário 
                    // e atualizar o ID estático no final.
                    TipoDespesa tipo = new TipoDespesa(nome);
                    
                    // IMPORTANTE: precisamos simular o ID que veio do arquivo para não ter IDs sequenciais errados
                    // (Em Java, isso exigiria um construtor auxiliar ou reflexão, mas simplificaremos atualizando o ID estático.)
                    
                    // Para fins de simplificação didática, vamos ajustar o ID
                    ultimoId = id;
                    listaTipos.add(tipo); // Adicionando o objeto lido
                }
            }
            
            // Corrige o ID estático após carregar todos os dados
            TipoDespesa.setProximoId(ultimoId); 
            
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de tipos de despesa não encontrado. Iniciando com lista vazia.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de tipos de despesa: " + e.getMessage());
        }
    }

    /**
     * Salva todos os tipos de despesa da memória para o arquivo de texto.
     */
    public void salvarTipos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (TipoDespesa tipo : listaTipos) {
                // Usa o toString() para obter o formato ID;NOME
                bw.write(tipo.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de tipos de despesa: " + e.getMessage());
        }
    }

    // --- MÉTODOS CRUD ---

    /**
     * Adiciona um novo TipoDespesa e salva.
     */
    public void adicionar(TipoDespesa tipo) {
        this.listaTipos.add(tipo);
        salvarTipos();
    }

    /**
     * Lista todos os tipos de despesa.
     */
    public List<TipoDespesa> listarTodos() {
        return new ArrayList<>(listaTipos); // Retorna uma cópia para proteger a lista interna
    }
    
    // Futuramente, você precisará dos métodos editar, excluir e buscar por ID
    // public void editar(TipoDespesa tipoAtualizado) { ... }
    // public void excluir(int id) { ... }
    // public TipoDespesa buscarPorId(int id) { ... }
}