import java.util.LinkedList;
import java.util.Queue;

public class ex5_conta_interativo {
        public static class No {
            public int chave = 0;
            public No esq = null, dir = null;
            public No(){
                chave = 0; esq = null; dir = null;
            }
            public No(int v){
                chave = v; esq = null; dir = null;
            }
    }

        public static No inserir(No p, int valor) {
        if (p == null) {
            return new No(valor);
        }
        if (valor < p.chave) {
            p.esq = inserir(p.esq, valor);
        } else if (valor > p.chave) {
            p.dir = inserir(p.dir, valor);
        }
        return p;
    }
    
    public static int conta_elementos_iterativo(No raiz) {
        if (raiz == null) return 0;

        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        int contador = 0;

        while (!fila.isEmpty()) {
            No atual = fila.poll();
            contador++;

            if (atual.esq != null) fila.add(atual.esq);
            if (atual.dir != null) fila.add(atual.dir);
        }

        return contador;
    }

    public static void main(String[] args) {
            No raiz = null;
            // tem que sair 8
            raiz = inserir(raiz, 4);
            raiz = inserir(raiz, 2);
            raiz = inserir(raiz, 6);
            raiz = inserir(raiz, 1);
            raiz = inserir(raiz, 3);
            raiz = inserir(raiz, 5);
            raiz = inserir(raiz, 7);
            raiz = inserir(raiz, 8);
            System.out.println("\nNúmero total de elementos na árvore: " + conta_elementos_iterativo(raiz));
        }

}
