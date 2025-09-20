// ex1: contar os elementos
public int conta_elementos(No p) {
    if (p == null) return 0;
    else if (p.esq == null && p.dir == null) return 1;
    else return conta_elementos(p.esq) + conta_elementos(p.dir) + 1;
}

// ex 2: mostrar apenas os pares
public void mostra_pares(No p) {
    if (p == null) return;
    else {
        if (p.chave%2 == 0) {System.out.println(p.chave)} //pre ordem
        mostra_pares(p.esq);
        mostra_pares(p.dir);
    }
}

// ex 3: comparar 2 arvores se são iguais, tamanho, valores e estrutura
public boolean saoIguais(No p , No q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    if (p.chave != q.chave) return false;
    return saoIguais(p.esq, q.esq) & saoIguais(p.dir, q.dir); // pre ordem
}

// ex 4: devolva true se a árvore é estritamente binária, false caso contrário
// estritamente binária: se cada nó possui 2 ou 0 filhos
public boolean estritamenteBin(No p) {
    if (p == null) return true;
    if (p.esq == null && p.dir == null) return true;
    if (p.esq == null || p.dir == null) return false;
    return estritamenteBin(p.esq) & estritamenteBin(p.dir);
}

// Pré-ordem: A B D E F C G H J I K
// In-ordem: I F D B A G J H I C K
// Pos-ordem: 