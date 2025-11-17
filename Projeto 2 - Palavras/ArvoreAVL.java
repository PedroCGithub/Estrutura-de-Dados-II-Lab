import java.util.*;

public class ArvoreAVL {
    private NoAVL raiz;
    private int rotacoesSimples;
    private int rotacoesDuplas;

    public ArvoreAVL() {
        this.rotacoesSimples = 0;
        this.rotacoesDuplas = 0;
    }

    public void inserir(double similaridade, Resultado resultado) {
        raiz = inserir(raiz, similaridade, resultado);
    }

    private NoAVL inserir(NoAVL no, double similaridade, Resultado resultado) {
        if (no == null) {
            return new NoAVL(similaridade, resultado);
        }

        if (Math.abs(similaridade - no.similaridade) < 1e-9) {
            // Similaridade igual, adiciona à lista
            no.resultados.add(resultado);
        } else if (similaridade < no.similaridade) {
            no.esquerda = inserir(no.esquerda, similaridade, resultado);
        } else {
            no.direita = inserir(no.direita, similaridade, resultado);
        }

        return balancear(no);
    }

    private NoAVL balancear(NoAVL no) {
        if (no == null) return no;

        int balance = getBalanceamento(no);

        // Rotação direita simples
        if (balance > 1 && getBalanceamento(no.esquerda) >= 0) {
            rotacoesSimples++;
            return rotacaoDireita(no);
        }

        // Rotação esquerda simples
        if (balance < -1 && getBalanceamento(no.direita) <= 0) {
            rotacoesSimples++;
            return rotacaoEsquerda(no);
        }

        // Rotação esquerda-direita (dupla)
        if (balance > 1 && getBalanceamento(no.esquerda) < 0) {
            rotacoesDuplas++;
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Rotação direita-esquerda (dupla)
        if (balance < -1 && getBalanceamento(no.direita) > 0) {
            rotacoesDuplas++;
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    private int altura(NoAVL no) {
        return no == null ? 0 : no.altura;
    }

    private int getBalanceamento(NoAVL no) {
        return no == null ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    public List<Resultado> getParesDecrescente() {
        List<Resultado> resultados = new ArrayList<>();
        percursoInverso(raiz, resultados);
        return resultados;
    }

    private void percursoInverso(NoAVL no, List<Resultado> resultados) {
        if (no != null) {
            percursoInverso(no.direita, resultados);
            resultados.addAll(no.resultados);
            percursoInverso(no.esquerda, resultados);
        }
    }

    public int getRotacoesSimples() {
        return rotacoesSimples;
    }

    public int getRotacoesDuplas() {
        return rotacoesDuplas;
    }

    private static class NoAVL {
        double similaridade;
        List<Resultado> resultados;
        int altura;
        NoAVL esquerda, direita;

        NoAVL(double similaridade, Resultado resultado) {
            this.similaridade = similaridade;
            this.resultados = new ArrayList<>();
            this.resultados.add(resultado);
            this.altura = 1;
        }
    }
}