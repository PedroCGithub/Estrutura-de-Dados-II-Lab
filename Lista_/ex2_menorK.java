import java.util.Scanner;
import java.util.Stack;

public class ex2_menorK {
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


    public static int menorK(No raiz, int k) {
        if (raiz == null || k <= 0) return -1;

        Stack<No> pilha = new Stack<>();
        No atual = raiz;
        int contador = 0;

        while (atual != null || !pilha.isEmpty()) {
            while (atual != null) {
                pilha.push(atual);
                atual = atual.esq;
            }

            atual = pilha.pop();
            contador++;

            if (contador == k) {
                return atual.chave; // achou o K
            }

            atual = atual.dir;
        }

        return -1;
    }

    public static void main(String[] args) {
        No raiz = null;
        raiz = inserir(raiz, 14);
        raiz = inserir(raiz, 51);
        raiz = inserir(raiz, 27);
        raiz = inserir(raiz, 33);
        raiz = inserir(raiz, 39);
        raiz = inserir(raiz, 5);
        raiz = inserir(raiz, 12);
        raiz = inserir(raiz, 3);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a posição K (1-8) do menor elemento que deseja buscar na árvore: ");
        int valor_menor = scanner.nextInt();
        scanner.close();

        int resultado = menorK(raiz, valor_menor);
        System.out.println("O " + valor_menor + "º menor valor da árvore é: " + resultado);
    }
}
