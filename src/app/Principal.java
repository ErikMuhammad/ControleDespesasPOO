package app;

import java.util.Scanner;
import java.util.List;
import java.time.LocalDate; 
import java.time.format.DateTimeParseException; // Para tratar erros de data

import modelo.TipoDespesa;
import modelo.Despesa;
import modelo.DespesaTransporte;
import repositorios.RepositorioTiposDespesa;
import repositorios.RepositorioDespesas;

public class Principal {
    
    private static RepositorioTiposDespesa repoTipos = new RepositorioTiposDespesa();
    private static RepositorioDespesas repoDespesas = new RepositorioDespesas();
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
            System.out.println("2. Anotar Pagamento (Pendente)");
            System.out.println("3. Listar Despesas em Aberto (Pendentes)");
            System.out.println("4. Listar Despesas Pagas");
            System.out.println("5. Gerenciar Tipos de Despesa");
            System.out.println("6. Gerenciar Usuários (Pendente)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada inválida. Tente novamente com um número.");
                scanner.next(); 
                opcao = -1; 
                continue;
            }

            switch (opcao) {
                case 1:
                    entrarDespesa();
                    break;
                case 3:
                    listarDespesas(false); // Pendentes
                    break;
                case 4:
                    listarDespesas(true); // Pagas
                    break;
                case 5:
                    menuGerenciarTiposDespesa();
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida ou função pendente. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    // ----------------------------------------------------------------------
    //                           FUNÇÃO 1: ENTRAR DESPESA
    // ----------------------------------------------------------------------

    private static void entrarDespesa() {
        List<TipoDespesa> tipos = repoTipos.listarTodos();
        
        if (tipos.isEmpty()) {
            System.out.println("\nERRO: Cadastre primeiro um Tipo de Despesa (Opção 5)!");
            return;
        }
        
        System.out.println("\n--- ENTRAR NOVA DESPESA ---");
        
        // 1. Seleção do Tipo
        System.out.println("Selecione o Tipo de Despesa:");
        for (TipoDespesa tipo : tipos) {
            System.out.println(tipo.getId() + ". " + tipo.getNome());
        }
        System.out.print("Digite o ID do Tipo: ");
        int idTipo = scanner.nextInt();
        scanner.nextLine();
        
        TipoDespesa tipoSelecionado = repoTipos.buscarPorId(idTipo);
        
        if (tipoSelecionado == null) {
            System.out.println("Tipo de Despesa inválido.");
            return;
        }
        
        // 2. Detalhes da Despesa
        System.out.print("Descrição da Despesa: ");
        String descricao = scanner.nextLine();
        
        System.out.print("Valor Original (ex: 150.75): ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        
        LocalDate vencimento;
        try {
            System.out.print("Data de Vencimento (AAAA-MM-DD): ");
            vencimento = LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Despesa não cadastrada.");
            return;
        }
        
        // 3. Criação do Objeto (Usando a subclasse DespesaTransporte como exemplo de POO)
        Despesa novaDespesa = new DespesaTransporte(
            descricao, 
            valor, 
            vencimento, 
            tipoSelecionado
        );
        
        repoDespesas.adicionar(novaDespesa);
        
        System.out.println("Despesa de " + tipoSelecionado.getNome() + " cadastrada com sucesso! ID: " + novaDespesa.getId());
    }
    
    // ----------------------------------------------------------------------
    //                         FUNÇÕES DE LISTAGEM (3 e 4)
    // ----------------------------------------------------------------------

    private static void listarDespesas(boolean paga) {
        String status = paga ? "PAGAS" : "PENDENTES";
        List<Despesa> despesas = repoDespesas.listarPorStatus(paga);
        
        if (despesas.isEmpty()) {
            System.out.println("\nNenhuma Despesa " + status + " encontrada.");
            return;
        }
        
        System.out.println("\n--- LISTA DE DESPESAS " + status + " ---");
        for (Despesa d : despesas) {
            double totalDevido = d.getValorOriginal() + d.calcularJuros();
            
            System.out.printf("ID: %d | Descrição: %s | Vencimento: %s | Tipo: %s%n",
                              d.getId(), d.getDescricao(), d.getDataVencimento(), d.getTipo().getNome());
            
            System.out.printf("   Valor Original: R$%.2f | Juros: R$%.2f | TOTAL: R$%.2f | Pendente: R$%.2f%n",
                              d.getValorOriginal(), d.calcularJuros(), totalDevido, d.getValorPendente());
        }
        // Futuramente, aqui viria o sub-menu: Editar/Excluir/Voltar
    }

    // ----------------------------------------------------------------------
    //                           FUNÇÃO 5: GERENCIAR TIPOS
    // ----------------------------------------------------------------------
    
    private static void menuGerenciarTiposDespesa() {
        int opcao;
        
        do {
            System.out.println("\n--- GERENCIAR TIPOS DE DESPESA ---");
            System.out.println("1. Criar novo Tipo de Despesa");
            System.out.println("2. Listar todos os Tipos de Despesa");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next(); 
                opcao = -1; 
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
                    System.out.println("Opção inválida ou pendente.");
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