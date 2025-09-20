import java.io.*; //já importa todas as bibliotecas .io
import java.util.*;  //já importa todas as bibliotecas .util

public class projeto1 {
    
    // Classe Pessoa representa um nó na árvore genealógica
    static class Pessoa {
        String nome;
        Pessoa pai;
        List<Pessoa> filhos;
        
        // cria uma nova pessoa
        // todos os "Pessoa" do código basicamente quer dizer Nó
        public Pessoa(String nome) {
            this.nome = nome;
            this.pai = null;
            this.filhos = new ArrayList<>();
        }

        // verficacao se é raiz
        public boolean ehRaiz() {
            return pai == null;
        }
        // verificação se é folha
        public boolean ehFolha() {
            return filhos.isEmpty();
        }

        // cria o parentesco de pai e filho
        public void adicionarFilho(Pessoa filho) {
            if (filho != null && !filhos.contains(filho)) {
                filhos.add(filho);
                filho.pai = this;
            }
        }
    }
    
    public List<Pessoa> pessoas;

    public projeto1() {
        pessoas = new ArrayList<>();
    }

    // evita criar 2 vezes a mesma pessoa
    public Pessoa buscarOuCriarPessoa(String nome) {
        // procura na lista se já existe
        for (Pessoa pessoa : pessoas) {
            if (pessoa.nome.equals(nome)) {
                return pessoa;
            }
        }
        
        // se não encontrou criar uma nova
        Pessoa novaPessoa = new Pessoa(nome);
        pessoas.add(novaPessoa);
        return novaPessoa;
    }

    public void carregarArquivo(String nomeArquivo) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        
        // checa o arquivo todo
        while ((linha = leitor.readLine()) != null) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            
            // separa cada linha do arquivo em dois nomes, o da esquerda vira filho e o da direita pai
            String[] partes = linha.split("\\s+");
            if (partes.length == 2) {
                String nomeFilho = partes[0];
                String nomePai = partes[1];
                
                // checa se precisa criar
                Pessoa filho = buscarOuCriarPessoa(nomeFilho);
                Pessoa pai = buscarOuCriarPessoa(nomePai);
                
                // faz relação pai e filho
                pai.adicionarFilho(filho);
            }
        }
        leitor.close();
    }
     public static void main(String[] args) {
        System.out.println("Relacionamentos da Árvore Genealógica");
        Scanner scanner = new Scanner(System.in);
        projeto1 arvore = new projeto1();

        try {
            // carrega dados do arquivo
            System.out.print("Digite o nome do arquivo com as relações familiares: ");
            String nomeArquivo = scanner.nextLine();
            arvore.carregarArquivo(nomeArquivo);
            
            System.out.println("Árvore genealógica carregada com sucesso!");
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

