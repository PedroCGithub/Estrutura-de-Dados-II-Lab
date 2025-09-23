import java.util.LinkedList;
import java.util.Queue;

public class ex3_nivel {
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


    public static void mostrar_nivel(No raiz) {
        if (raiz == null) return;

        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        int nivel = 0;

        while (!fila.isEmpty()) {
            int tamanho = fila.size(); 
            System.out.print("Nível " + nivel + ": ");

            for (int i = 0; i < tamanho; i++) {
                No atual = fila.poll();
                System.out.print(atual.chave + " ");

                if (atual.esq != null) fila.add(atual.esq);
                if (atual.dir != null) fila.add(atual.dir);
            }
            System.out.println();
            nivel++;
        }
    }
    
    public static void main(String[] args) {
        No raiz = null;
        raiz = inserir(raiz, 4);
        raiz = inserir(raiz, 2);
        raiz = inserir(raiz, 6);
        raiz = inserir(raiz, 1);
        raiz = inserir(raiz, 3);
        raiz = inserir(raiz, 5);
        raiz = inserir(raiz, 7);
        raiz = inserir(raiz, 8);
        mostrar_nivel(raiz);
    }
}
