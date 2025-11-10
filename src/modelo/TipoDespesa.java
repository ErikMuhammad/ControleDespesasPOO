package modelo;

public class TipoDespesa {
    // Atributo estático para manter a contagem e garantir IDs únicos
    private static int proximoId = 1; 

    private int id;
    private String nome;

    // Construtor
    public TipoDespesa(String nome) {
        this.id = proximoId++; 
        this.nome = nome;
    }

    // --- Getters e Setters ---
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Sobrescrita do toString() para persistência em arquivo (ID;NOME)
    @Override
    public String toString() {
        return id + ";" + nome;
    }
    
    // Método estático para redefinir o próximo ID ao carregar dados do arquivo
    public static void setProximoId(int ultimoId) {
        proximoId = ultimoId + 1;
    }
}