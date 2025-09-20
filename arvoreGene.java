import java.io.*; //já importa todas as bibliotecas .io
import java.util.*;  //já importa todas as bibliotecas .util

public class arvoreGene {
    
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

        // calculo do nivel da pessoa
        // raiz = 0, filhos = 1, etc
        public int obterNivel() {
            if (ehRaiz()) {
                return 0;
            }
            return pai.obterNivel() + 1;
        }

        // isso faz o equals funcionar mesmo com sendo objetos diferentes
        // sem isso ele falaria que 2 nós com o mesmo nome seriam 2 diferentes pq são objetos diferentes mesmo tendo o mesmo nome
        // desse jeito a comparação se torna somente por nome
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Pessoa pessoa = (Pessoa) obj;
            return nome.equals(pessoa.nome);
        }
        
        // já esse transforma o objeto Pessoa em uma string
        @Override
        public String toString() {
            return nome;
        }
    }
    
    public List<Pessoa> pessoas;

    public arvoreGene() {
        pessoas = new ArrayList<>();
    }

    // evita criar 2 vezes a mesma pessoa
    private Pessoa buscarOuCriarPessoa(String nome) {
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

    // busca parecido com o de cima, mas é apenas a busca, sem a criação. vai ser util mais para frente
    public Pessoa buscarPessoa(String nome) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.nome.equals(nome)) {
                return pessoa;
            }
        }
        return null;
    }

    // define quantas gerações exitem entre 2 pessoass
    public int nivelDescendente(Pessoa descendente, Pessoa ancestral) {
        Pessoa atual = descendente;
        int nivel = 0;
        
        while (atual != null) {
            if (atual.pai != null && atual.pai.equals(ancestral)) {
                return nivel;
            }
            atual = atual.pai;
            nivel++;
        }
        
        return -1; // não é descendente
    }

    // usa o numero do de cima para definir o parentesco dos mais novos
    public String relacaoDescendente(int nivel) {
        switch (nivel) {
            case 0: return "filho";
            case 1: return "neto";
            case 2: return "bisneto";
            default: 
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < nivel - 2; i++) {
                    sb.append("tata");
                }
                sb.append("raneto");
                return sb.toString();
        }
    }

    // mesma coisa que o de cima mas para os mais velhos
    public String relacaoAncestral(int nivel) {
        switch (nivel) {
            case 0: return "pai";
            case 1: return "avo";
            case 2: return "bisavo";
            default:
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < nivel - 2; i++) {
                    sb.append("tata");
                }
                sb.append("ravo");
                return sb.toString();
        }
    }

    // carrega os dados
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
        arvoreGene arvore = new arvoreGene();

        try {
            // pede o nome do arquivo
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

