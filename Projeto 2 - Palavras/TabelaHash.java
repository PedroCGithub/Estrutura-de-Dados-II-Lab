import java.util.*;

public class TabelaHash<K, V> {
    private static final int CAPACIDADE_PADRAO = 16;
    private static final double FATOR_CARGA = 0.75;

    private List<Entry<K, V>>[] tabela;
    private int tamanho;
    private int capacidade;
    private int tipoHash;
    private int totalColisoes;

    public TabelaHash() {
        this(CAPACIDADE_PADRAO, 1);
    }

    public TabelaHash(int capacidade, int tipoHash) {
        this.capacidade = capacidade;
        this.tipoHash = tipoHash;
        this.tabela = new ArrayList[capacidade];
        this.totalColisoes = 0;
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ArrayList<>();
        }
    }

    public void put(K key, V value) {
        if ((double) tamanho / capacidade > FATOR_CARGA) {
            redimensionar();
        }

        int indice = calcularIndice(key);
        List<Entry<K, V>> bucket = tabela[indice];

        // Verificar se é uma colisão (bucket não está vazio para chave diferente)
        if (!bucket.isEmpty()) {
            boolean chaveExistente = false;
            for (Entry<K, V> entry : bucket) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    chaveExistente = true;
                    break;
                }
            }
            if (!chaveExistente) {
                totalColisoes++;
                bucket.add(new Entry<>(key, value));
                tamanho++;
            }
        } else {
            // Bucket vazio, sem colisão
            bucket.add(new Entry<>(key, value));
            tamanho++;
        }
    }

    public V get(K key) {
        int indice = calcularIndice(key);
        List<Entry<K, V>> bucket = tabela[indice];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (List<Entry<K, V>> bucket : tabela) {
            for (Entry<K, V> entry : bucket) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (List<Entry<K, V>> bucket : tabela) {
            for (Entry<K, V> entry : bucket) {
                values.add(entry.value);
            }
        }
        return values;
    }

    public int size() {
        return tamanho;
    }

    public int getTotalColisoes() {
        return totalColisoes;
    }

    public double getFatorCarga() {
        return (double) tamanho / capacidade;
    }

    public int getMaiorBucket() {
        int maior = 0;
        for (List<Entry<K, V>> bucket : tabela) {
            if (bucket.size() > maior) {
                maior = bucket.size();
            }
        }
        return maior;
    }

    public int getBucketsVazios() {
        int vazios = 0;
        for (List<Entry<K, V>> bucket : tabela) {
            if (bucket.isEmpty()) {
                vazios++;
            }
        }
        return vazios;
    }

    private int calcularIndice(K key) {
        int hash = calcularHash(key.toString());
        return Math.abs(hash) % capacidade;
    }

    private int calcularHash(String str) {
        if (tipoHash == 1) {
            // Função Hash 1: Multiplicação por primo
            int hash = 7;
            for (int i = 0; i < str.length(); i++) {
                hash = hash * 31 + str.charAt(i);
            }
            return hash;
        } else {
            // Função Hash 2: Shift e XOR
            int hash = 0;
            for (int i = 0; i < str.length(); i++) {
                hash = (hash << 5) ^ (hash >> 27) ^ str.charAt(i);
            }
            return hash;
        }
    }

    private void redimensionar() {
        int novaCapacidade = capacidade * 2;
        List<Entry<K, V>>[] novaTabela = new ArrayList[novaCapacidade];
        
        for (int i = 0; i < novaCapacidade; i++) {
            novaTabela[i] = new ArrayList<>();
        }

        List<Entry<K, V>>[] tabelaAntiga = tabela;
        tabela = novaTabela;
        capacidade = novaCapacidade;
        tamanho = 0;
        totalColisoes = 0; // Reset colisões no redimensionamento

        for (List<Entry<K, V>> bucket : tabelaAntiga) {
            for (Entry<K, V> entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public void setTipoHash(int tipoHash) {
        this.tipoHash = tipoHash;
        // Rehash de todos os elementos
        List<Entry<K, V>> todosElementos = new ArrayList<>();
        for (List<Entry<K, V>> bucket : tabela) {
            todosElementos.addAll(bucket);
        }
        
        this.tabela = new ArrayList[capacidade];
        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new ArrayList<>();
        }
        tamanho = 0;
        totalColisoes = 0; // Reset colisões
        
        for (Entry<K, V> entry : todosElementos) {
            put(entry.key, entry.value);
        }
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}