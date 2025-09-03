public class ex3 {

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

    public static void imprimirLateral(No p, String espaco){
        if (p != null) {
            imprimirLateral(p.esq, espaco + "    ");
            System.out.println(espaco + p.chave);
            imprimirLateral(p.dir, espaco + "    ");
        }
    }

    public static void main(String[] args) {
        // Constrói a árvore de exemplo.
        No raiz = new No(1);
        raiz.esq = new No(2);
        raiz.dir = new No(3);
        raiz.esq.esq = new No(4);
        raiz.esq.dir = new No(5);
        raiz.dir.esq = new No(6);
        raiz.dir.dir = new No(7);

        System.out.println("Impressão Lateral da Árvore:");
        imprimirLateral(raiz, "");
    }
}