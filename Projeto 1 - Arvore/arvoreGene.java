// nomes:
// Pedro Casas Pequeno JUnior RA: 10437031
// Pedro Henrique Saraiva Arruda RA: 10437747
// Tarik Nasser Ferreira Kanbour RA: 10435895

import java.io.*; //já importa todas as bibliotecas .io
import java.util.*;  //já importa todas as bibliotecas .util

public class arvoreGene {
    
    // classe Pessoa representa um nó na árvore genealógica
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

    // para achar a relação primo precisa fazer o LCA (Menor ancestral comun)
    // temos que encontrar o LCA, calcular a distancia de cada pessoa até ele, e determinar o primo e grau baseado nessas distancias
    // fontes que usamos para entender o LCA
    // https://www.geeksforgeeks.org/dsa/lowest-common-ancestor-binary-tree-set-1/
    // https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/
    public String relacaoPrimo(Pessoa pessoa1, Pessoa pessoa2) {
        Pessoa lca = encontrarLCA(pessoa1, pessoa2);
        
        if (lca == null) {
            return "sem relacao";
        }
        
        int distancia1 = distanciaAncestral(pessoa1, lca);
        int distancia2 = distanciaAncestral(pessoa2, lca);
        
        if (distancia1 == -1 || distancia2 == -1) {
            return "sem relacao";
        }
        
        // calcula grau de primo e grau
        int grauPrimo = Math.min(distancia1, distancia2) - 1;
        int grau = Math.abs(distancia1 - distancia2);
        
        // caso especial: irmãos (primo-0 grau 0)
        if (grauPrimo == 0 && grau == 0) {
            return "irmao";
        }
        
        // formata relação de primo
        if (grau == 0) {
            return "primo-" + grauPrimo;
        } else {
            return "primo-" + grauPrimo + " em grau " + grau;
        }
    }
    
    // encontra Ancestral Comum Mais Baixo (LCA) de duas pessoas
    private Pessoa encontrarLCA(Pessoa pessoa1, Pessoa pessoa2) {
        List<Pessoa> ancestrais1 = obterAncestrais(pessoa1);
        Pessoa atual = pessoa2;
        
        while (atual != null) {
            if (ancestrais1.contains(atual)) {
                return atual;
            }
            atual = atual.pai;
        }
        
        return null;
    }
    
    // obtém todos os ancestrais de uma pessoa
    private List<Pessoa> obterAncestrais(Pessoa pessoa) {
        List<Pessoa> ancestrais = new ArrayList<>();
        Pessoa atual = pessoa;
        
        while (atual != null) {
            ancestrais.add(atual);
            atual = atual.pai;
        }
        
        return ancestrais;
    }
    
    // calcula distância de uma pessoa até seu ancestral
    public int distanciaAncestral(Pessoa pessoa, Pessoa ancestral) {
        Pessoa atual = pessoa;
        int distancia = 0;
        
        while (atual != null) {
            if (atual.equals(ancestral)) {
                return distancia;
            }
            atual = atual.pai;
            distancia++;
        }
        
        return -1; // não é ancestral
    }

    // função principal que determina todos os graus de parentesco com os dois nomes informados
    public String encontrarParentesco(String nome1, String nome2) {
        Pessoa pessoa1 = buscarPessoa(nome1);
        Pessoa pessoa2 = buscarPessoa(nome2);
        
        if (pessoa1 == null || pessoa2 == null) {
            return "sem relacao";
        }
        
        if (pessoa1.equals(pessoa2)) {
            return "mesma pessoa";
        }
        
        // verifica relação descendente
        int nivelDescendente = nivelDescendente(pessoa1, pessoa2);
        if (nivelDescendente >= 0) {
            return relacaoDescendente(nivelDescendente);
        }
        
        // verifica relação ancestral
        int nivelAncestrall = nivelDescendente(pessoa2, pessoa1);
        if (nivelAncestrall >= 0) {
            return relacaoAncestral(nivelAncestrall);
        }
        
        // verifica relação de primo
        return relacaoPrimo(pessoa1, pessoa2);
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
            System.out.println("Informe consultas (dois nomes separados por espaço, ou 'sair' para terminar):");
            
            // Processa consultas
            while (true) {
                System.out.print("> ");
                String entrada = scanner.nextLine().trim();
                
                if (entrada.equalsIgnoreCase("sair")) {
                    break;
                }
                
                String[] nomes = entrada.split("\\s+");
                if (nomes.length == 2) {
                    String parentesco = arvore.encontrarParentesco(nomes[0], nomes[1]);
                    System.out.println(parentesco);
                } else {
                    System.out.println("Por favor, digite exatamente dois nomes separados por espaço");
                }
            }
            
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}