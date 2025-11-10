package modelo;

public class TipoDespesa {
    // Atributo estático para manter a contagem e garantir IDs únicos
    // Isso é útil para simular um auto-incremento de banco de dados
    private static int proximoId = 1; 

    private int id;
    private String nome;

    // Construtor
    public TipoDespesa(String nome) {
        // Atribui o ID atual e incrementa o próximoId
        this.id = proximoId++; 
        this.nome = nome;
    }

    // --- Métodos Getters (Acesso) ---
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // --- Métodos Setters (Modificação) ---
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Sobrescrita do método toString() para facilitar a gravação em arquivo
    // Formato: ID;NOME
    @Override
    public String toString() {
        // Usamos um separador (como ponto e vírgula) para armazenar em arquivo de texto
        return id + ";" + nome;
    }
    
    // Método estático para redefinir o próximo ID ao carregar dados do arquivo
    // É essencial para que a contagem não comece sempre do 1 ao rodar o programa
    public static void setProximoId(int ultimoId) {
        // Define o próximo ID como o último ID lido + 1
        proximoId = ultimoId + 1;
    }
}
