import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ex4_nivel {
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


    public static void mostrarPornivel(No raiz, int nivel) {
        if (raiz == null) return;

        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        int nivelAux = 0;
        int soma = 0;

        while (!fila.isEmpty()) {
            int tamanho = fila.size(); 
            if(nivelAux == nivel) {
            System.out.print("Nível " + nivel + ": ");

            for (int i = 0; i < tamanho; i++) {
                No atual = fila.poll();
                System.out.print(atual.chave + " ");
                soma += atual.chave;

                if (atual.esq != null) fila.add(atual.esq);
                if (atual.dir != null) fila.add(atual.dir);
            }
            System.out.println("\nSoma dos nós no nível " + nivel + ": " + soma);
            return;
            } else {
                for (int i = 0; i < tamanho; i++) {
                    No atual = fila.poll();
                    if (atual.esq != null) fila.add(atual.esq);
                    if (atual.dir != null) fila.add(atual.dir);
                }
                nivelAux++;
            }
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
        int nivel = 0; // K informado pelo usuario
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nível que deseja exibir os nós (0-3): ");
        nivel = scanner.nextInt();
        mostrarPornivel(raiz, nivel);
        scanner.close();
    }
}
