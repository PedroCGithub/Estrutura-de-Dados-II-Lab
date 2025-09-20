import java.io.*; //já importa todas as bibliotecas .io
import java.util.*;  //já importa todas as bibliotecas .util

public class projeto1 {
    
    // Classe Pessoa representa um nó na árvore genealógica
    static class Pessoa {
        String nome;
        Pessoa pai;
        List<Pessoa> filhos;
        
        // cria uma nova pessoa
        public Pessoa(String nome) {
            this.nome = nome;
            this.pai = null;
            this.filhos = new ArrayList<>();
        }

        public boolean ehRaiz() {
            return pai == null;
        }
    }
}

