// Classe Nó para lista encadeada
class No {
    int chave;
    No prox;

    public No(int chave) {
        this.chave = chave;
        this.prox = null;
    }
}

// Classe Lista Encadeada para encadeamento externo
class ListaEncadeada {
    private No inicio;

    public ListaEncadeada() {
        inicio = null;
    }

    public void inserir(int chave) {
        No novo = new No(chave);
        novo.prox = inicio;
        inicio = novo;
    }

    public boolean buscar(int chave) {
        No atual = inicio;
        while (atual != null) {
            if (atual.chave == chave)
                return true;
            atual = atual.prox;
        }
        return false;
    }

    public boolean remover(int chave) {
        No atual = inicio;
        No anterior = null;

        while (atual != null && atual.chave != chave) {
            anterior = atual;
            atual = atual.prox;
        }

        if (atual == null) return false; // não achou

        if (anterior == null)
            inicio = atual.prox;
        else
            anterior.prox = atual.prox;

        return true;
    }

    public void imprimir() {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.chave + " -> ");
            atual = atual.prox;
        }
        System.out.println("null");
    }
}

// Classe Tabela Hash com método da divisão e encadeamento externo
public class TabelaHash {
    private ListaEncadeada[] tabela;
    private int tamanho;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new ListaEncadeada[tamanho];

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new ListaEncadeada();
        }
    }

    private int hash(int chave) {
        return chave % tamanho;
    }

    public void inserir(int chave) {
        int indice = hash(chave);
        tabela[indice].inserir(chave);
    }

    public boolean buscar(int chave) {
        int indice = hash(chave);
        return tabela[indice].buscar(chave);
    }

    public boolean remover(int chave) {
        int indice = hash(chave);
        return tabela[indice].remover(chave);
    }

    public void imprimirTabela() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print("Posição " + i + ": ");
            tabela[i].imprimir();
        }
    }

    // Exemplo de uso
    public static void main(String[] args) {
        TabelaHash th = new TabelaHash(7); // tamanho da tabela = 7 (número primo ideal)

        th.inserir(10); // 10 % 7 = 3
        th.inserir(11); // 11 % 7 = 4
        th.inserir(12); // 12 % 7 = 5
        th.inserir(13); // 13 % 7 = 6
        th.inserir(14); // 14 % 7 = 0
        th.inserir(15); // 15 % 7 = 1
        th.inserir(16); // 16 % 7 = 2


        System.out.println("Tabela após inserções:");
        th.imprimirTabela();

        System.out.println("Removendo 16...");
        th.remover(16);


        System.out.println("\nTabela final:");
        th.imprimirTabela();
    }
}
