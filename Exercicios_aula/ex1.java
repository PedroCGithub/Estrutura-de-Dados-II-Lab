public class ex1 {
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

    public static int calculoAltura (No no) {
        if (no == null) {
            return -1;
        } else {
            int esquerda = calculoAltura(no.esq);
            int direita = calculoAltura(no.dir);

            if (esquerda > direita) {
                esquerda ++;
                return esquerda;
            } else if (direita > esquerda){
                direita ++;
                return direita;
            } else {
                int altura = esquerda + 1;
                return altura;
            }
        }
    }

     public static void main(String[] args) {
        No raiz = new No(1);
        raiz.esq = new No(2);
        raiz.dir = new No(3);
        System.out.println("A altura da arvore é: " + calculoAltura(raiz));
     }
}