package app;

import java.util.Scanner;
import java.util.List; // <<< Adicione esta linha!
import modelo.TipoDespesa;
import repositorios.RepositorioTiposDespesa;

public class Principal {
    
    // Instancia o repositório para ser usado em todo o sistema
    private static RepositorioTiposDespesa repoTipos = new RepositorioTiposDespesa();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- SISTEMA DE CONTROLE DE DESPESAS ---");
        exibirMenuPrincipal();
    }

    private static void exibirMenuPrincipal() {
        int opcao;
        
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Entrar Despesa");
            System.out.println("2. Anotar Pagamento");
            System.out.println("3. Listar Despesas em Aberto no período");
            System.out.println("4. Listar Despesas Pagas no período");
            System.out.println("5. Gerenciar Tipos de Despesa");
            System.out.println("6. Gerenciar Usuários");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a linha nova

            switch (opcao) {
                case 1:
                    System.out.println("Função Entrar Despesa (Ainda não implementada).");
                    break;
                case 5:
                    menuGerenciarTiposDespesa();
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    // --- FUNÇÕES DE NEGÓCIO IMPLEMENTADAS ---
    
    private static void menuGerenciarTiposDespesa() {
        int opcao;
        
        do {
            System.out.println("\n--- GERENCIAR TIPOS DE DESPESA ---");
            System.out.println("1. Criar novo Tipo de Despesa");
            System.out.println("2. Listar todos os Tipos de Despesa");
            System.out.println("3. Editar Tipo de Despesa (Ainda não implementada)");
            System.out.println("4. Excluir Tipo de Despesa (Ainda não implementada)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); // Descarta a entrada inválida
                opcao = -1; // Garante que o loop continue
                continue;
            }

            switch (opcao) {
                case 1:
                    criarTipoDespesa();
                    break;
                case 2:
                    listarTiposDespesa();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    private static void criarTipoDespesa() {
        System.out.print("Digite o nome do novo Tipo de Despesa (ex: 'Alimentação'): ");
        String nome = scanner.nextLine();
        
        TipoDespesa novoTipo = new TipoDespesa(nome);
        repoTipos.adicionar(novoTipo);
        
        System.out.println("Tipo de Despesa cadastrado com sucesso! (ID: " + novoTipo.getId() + ")");
    }
    
    private static void listarTiposDespesa() {
        List<TipoDespesa> tipos = repoTipos.listarTodos();
        
        if (tipos.isEmpty()) {
            System.out.println("Nenhum Tipo de Despesa cadastrado.");
            return;
        }
        
        System.out.println("\n--- LISTA DE TIPOS DE DESPESA ---");
        for (TipoDespesa tipo : tipos) {
            System.out.println("ID: " + tipo.getId() + " | Nome: " + tipo.getNome());
        }
    }
}