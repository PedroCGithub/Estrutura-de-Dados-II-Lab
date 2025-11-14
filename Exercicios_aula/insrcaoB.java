class NoB {
    int n;            
    int[] chaves;     
    NoB[] filhos;     
    boolean folha;    

    public NoB(int ordem, boolean folha) {
        this.folha = folha;
        this.chaves = new int[2 * ordem - 1];
        this.filhos = new NoB[2 * ordem];
        this.n = 0;
    }
}

public class ArvoreB {
    private NoB raiz;
    private int ordem; //ordem da árvore

    public ArvoreB(int ordem) {
        this.ordem = ordem;
        this.raiz = new NoB(ordem, true);
    }

    // inserção arv B
    public void inserir(int k) {
        NoB r = raiz;
        if (r.n == 2 * ordem - 1) {  // raiz cheia
            NoB s = new NoB(ordem, false);
            s.filhos[0] = r;
            dividirFilho(s, 0, r);
            int i = 0;
            if (k > s.chaves[0]) i++;
            inserirNaoCheio(s.filhos[i], k);
            raiz = s;
        } else {
            inserirNaoCheio(r, k);
        }
    }

    private void dividirFilho(NoB x, int i, NoB y) {
        NoB z = new NoB(ordem, y.folha);
        z.n = ordem - 1;

        // copia as chaves da metade superior
        for (int j = 0; j < ordem - 1; j++)
            z.chaves[j] = y.chaves[j + ordem];

        // copia os filhos se não for folha
        if (!y.folha) {
            for (int j = 0; j < ordem; j++)
                z.filhos[j] = y.filhos[j + ordem];
        }

        y.n = ordem - 1;

        // abrir espaço
        for (int j = x.n; j >= i + 1; j--)
            x.filhos[j + 1] = x.filhos[j];
        x.filhos[i + 1] = z;

        // move chaves de x
        for (int j = x.n - 1; j >= i; j--)
            x.chaves[j + 1] = x.chaves[j];

        // sobe a chave do meio
        x.chaves[i] = y.chaves[ordem - 1];
        x.n++;
    }

    private void inserirNaoCheio(NoB x, int k) {
        int i = x.n - 1;

        if (x.folha) {
            // abrir espaço
            while (i >= 0 && k < x.chaves[i]) {
                x.chaves[i + 1] = x.chaves[i];
                i--;
            }
            x.chaves[i + 1] = k;
            x.n++;
        } else {
            while (i >= 0 && k < x.chaves[i])
                i--;
            i++;

            if (x.filhos[i].n == 2 * ordem - 1) {
                dividirFilho(x, i, x.filhos[i]);
                if (k > x.chaves[i]) i++;
            }
            inserirNaoCheio(x.filhos[i], k);
        }
    }
}
