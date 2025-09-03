public class ex2 {
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

    public static void mostrar(No p, int nivel) {
        if (p != null) {
            System.out.println("Valor do nó: " + p.chave + "    " + "Nivel do Nó: " + nivel);
            mostrar(p.esq, nivel + 1);
            mostrar(p.dir, nivel + 1);
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
        int nivel = 0;
        mostrar(raiz, 0);
    }
}
