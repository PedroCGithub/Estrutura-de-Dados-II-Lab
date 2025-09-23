public class ex1_50 {

    public static class No {
        public int chave;
        public No esq, dir;

        public No() {
            chave = 0; esq = null; dir = null;
        }

        public No(int v) {
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


    public static int nosMaioresQue50(No A) {
        if (A == null) return 0;
        if (A.chave > 50) {
            return nosMaioresQue50(A.esq) + nosMaioresQue50(A.dir) + 1;
        } else {
            return nosMaioresQue50(A.esq) + nosMaioresQue50(A.dir);
        }
    }

    public static void main(String[] args) {
        No raiz = null;

        // tem que sair 4
        raiz = inserir(raiz, 30);
        raiz = inserir(raiz, 70);
        raiz = inserir(raiz, 20);
        raiz = inserir(raiz, 40);
        raiz = inserir(raiz, 60);
        raiz = inserir(raiz, 80);
        raiz = inserir(raiz, 55);
        raiz = inserir(raiz, 10);

        int maiores = nosMaioresQue50(raiz);
        System.out.println("Quantidade de nós maiores que 50: " + maiores);
    }
}
