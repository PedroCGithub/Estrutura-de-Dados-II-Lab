// 1. Escreva uma função recursiva para contar:
// (a) o número total de nós;
// NO ARQUIVO CONTA.JAVA


// (b) o número de folhas;

private int conta_folhas(No p) {
    if(p == null) return 0;
    if(p.esq == null && p.dir == null) return 1;
    return conta_folhas(p.esq) + conta_folhas(p.dir);
}

// (c) o número de nós internos (com pelo menos um filho).

private int conta_internor(No p) {
    if(p == null) return 0;
    if(p.esq == null && p.dir == null) return 0;
    if(p != raiz) return conta_internos(p.esq) + conta_internos(p.dir) + 1;
    return conta_internos(p.esq) + conta_internos(p.dir);
}

// 2. Escreva um método que devolve o clone da árvore.
// interativo
public Arvore clone() {
    Arvore arv = new Arvore();
    No p = raiz;
    Pilha <No> pilha = new Pilha<No>();
    while(p != null || !pilha_vazia()) {
        if(p != null) {
            arv.insere(p.chave);
            pilha.push(p);
            p = p.esq;
        } else {
            p = pilha.pop();
            p = p.dir;
        }
        return arv;
    }
}

//recursivo pre ordem
public Arvore clone() {
    Arvore arv = new Arvore();
    cloneR(raiz, arv);
    return arv;
}

private void cloneR(No p, Arvore arv) {
    if(p != null){
        arv.insere(p.chave);
        cloneR(p.esq, arv);
        cloneR(p.dir, arv);
    }
}
// 3. Escreva um método que devolve o espelho da árvore (o que está a esquerda da árvore
// original, estará a direita no espelho e vice-versa.

public Arvore espelho() {
    Arvore arv = clone() // o do ex anterior mesmo
    No p = arv.raiz;
    Pilha<No> pilha = new <No> Pilha();
    while(p != null & !pilha.vazia()) {
        if(p != null) {
            No aux = p.esq;
            p.esq = p.dir;
            p.dir = aux;
            p = p.esq;
        } else {
            p = pilha.pop();
            p = p.dir;
        }
    } return arv;
}

// 4. Dada uma árvore binária qualquer, implemente uma função que determine se ela é uma
// BST válida.


// 5. Escreva um método que remova todas as folhas da árvore.