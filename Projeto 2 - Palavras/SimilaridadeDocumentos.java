import java.io.*;
import java.util.*;

public class SimilaridadeDocumentos {
    private Set<String> stopWords;
    private TabelaHash<String, Documento> documentos;
    private ArvoreAVL arvoreSimilaridades;
    private int totalParesComparados;
    private int funcaoHashUtilizada;

    public SimilaridadeDocumentos() {
        this.stopWords = new HashSet<>();
        this.documentos = new TabelaHash<>();
        this.arvoreSimilaridades = new ArvoreAVL();
        this.totalParesComparados = 0;
        this.funcaoHashUtilizada = 1;
    }

    public void setFuncaoHash(int tipoHash) {
        this.funcaoHashUtilizada = tipoHash;
        this.documentos.setTipoHash(tipoHash);
    }

    public void carregarStopWords(String arquivoStopWords) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoStopWords))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim().toLowerCase();
                
                // Pular linhas de comentário e vazias
                if (linha.isEmpty() || linha.startsWith("//") || linha.startsWith("*") || 
                    linha.startsWith("stopwords_br") || linha.startsWith("#")) {
                    continue;
                }
                
                // Remover pontuação e separar palavras
                linha = linha.replaceAll("[^a-zA-Zà-úÀ-Ú\\s]", " ");
                String[] palavras = linha.split("\\s+");
                
                for (String palavra : palavras) {
                    palavra = palavra.trim();
                    if (!palavra.isEmpty() && palavra.length() > 1) { // Ignorar palavras muito curtas
                        stopWords.add(palavra);
                    }
                }
            }
        }
        System.out.println("Stop words carregadas: " + stopWords.size());
    }

    public void processarDiretorio(String caminhoDiretorio) throws IOException {
        File diretorio = new File(caminhoDiretorio);
        File[] arquivos = diretorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        
        if (arquivos == null) {
            throw new IOException("Diretório não encontrado: " + caminhoDiretorio);
        }

        for (File arquivo : arquivos) {
            processarDocumento(arquivo);
        }

        calcularSimilaridades();
    }

    private void processarDocumento(File arquivo) throws IOException {
        Documento doc = new Documento(arquivo.getName());
        
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.replaceAll("[^a-zA-Zà-úÀ-Ú0-9 ]", " ").toLowerCase();
                
                String[] palavras = linha.split("\\s+");
                for (String palavra : palavras) {
                    palavra = palavra.trim();
                    if (!palavra.isEmpty() && !stopWords.contains(palavra)) {
                        doc.adicionarPalavra(palavra);
                    }
                }
            }
        }
        
        documentos.put(arquivo.getName(), doc);
    }

    private void calcularSimilaridades() {
        List<String> nomesDocumentos = new ArrayList<>(documentos.keySet());
        totalParesComparados = 0;
        
        for (int i = 0; i < nomesDocumentos.size(); i++) {
            for (int j = i + 1; j < nomesDocumentos.size(); j++) {
                String doc1 = nomesDocumentos.get(i);
                String doc2 = nomesDocumentos.get(j);
                
                double similaridade = calcularSimilaridadeCosseno(documentos.get(doc1), documentos.get(doc2));
                
                Resultado resultado = new Resultado(doc1, doc2, similaridade);
                arvoreSimilaridades.inserir(similaridade, resultado);
                totalParesComparados++;
            }
        }
    }

    private double calcularSimilaridadeCosseno(Documento doc1, Documento doc2) {
        Set<String> todasPalavras = new HashSet<>();
        todasPalavras.addAll(doc1.getPalavras());
        todasPalavras.addAll(doc2.getPalavras());

        double produtoEscalar = 0;
        double norma1 = 0;
        double norma2 = 0;

        for (String palavra : todasPalavras) {
            int freq1 = doc1.getFrequencia(palavra);
            int freq2 = doc2.getFrequencia(palavra);

            produtoEscalar += freq1 * freq2;
            norma1 += freq1 * freq1;
            norma2 += freq2 * freq2;
        }

        if (norma1 == 0 || norma2 == 0) return 0;
        
        return produtoEscalar / (Math.sqrt(norma1) * Math.sqrt(norma2));
    }

    public void exibirParesAcimaLimiar(double limiar) {
        List<Resultado> todosPares = arvoreSimilaridades.getParesDecrescente();
        
        List<Resultado> paresAcimaLimiar = new ArrayList<>();
        List<Resultado> paresAbaixoLimiar = new ArrayList<>();
        
        for (Resultado resultado : todosPares) {
            if (resultado.similaridade >= limiar) {
                paresAcimaLimiar.add(resultado);
            } else {
                paresAbaixoLimiar.add(resultado);
            }
        }
        
        // Exibir TODOS os pares no formato solicitado
        System.out.println("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
        System.out.println("Total de documentos processados: " + documentos.size());
        System.out.println("Total de pares comparados: " + totalParesComparados);
        System.out.println("Função hash utilizada: " + getNomeFuncaoHash());
        System.out.println("Métrica de similaridade: Cosseno");
        
        if (!paresAcimaLimiar.isEmpty()) {
            System.out.println("Pares com similaridade >= " + limiar + ":");
            System.out.println("---------------------------------");
            for (Resultado resultado : paresAcimaLimiar) {
                System.out.printf("%s <-> %s = %.4f\n", 
                    resultado.doc1, resultado.doc2, resultado.similaridade);
            }
        }
        
        // Mostrar TODOS os pares abaixo do limiar
        if (!paresAbaixoLimiar.isEmpty()) {
            System.out.println("Pares com menor similaridade:");
            System.out.println("---------------------------------");
            for (Resultado resultado : paresAbaixoLimiar) {
                System.out.printf("%s <-> %s = %.4f\n", 
                    resultado.doc1, resultado.doc2, resultado.similaridade);
            }
        }
        
        // Se não houver pares acima do limiar, mostrar todos os pares
        if (paresAcimaLimiar.isEmpty()) {
            System.out.println("Todos os pares de documentos:");
            System.out.println("---------------------------------");
            for (Resultado resultado : todosPares) {
                System.out.printf("%s <-> %s = %.4f\n", 
                    resultado.doc1, resultado.doc2, resultado.similaridade);
            }
        }
        
        exibirEstatisticas();
    }

    public void exibirTopK(int k) {
        List<Resultado> todosPares = arvoreSimilaridades.getParesDecrescente();
        
        System.out.println("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
        System.out.println("Total de documentos processados: " + documentos.size());
        System.out.println("Total de pares comparados: " + totalParesComparados);
        System.out.println("Função hash utilizada: " + getNomeFuncaoHash());
        System.out.println("Métrica de similaridade: Cosseno");
        
        System.out.println("Top " + k + " pares mais similares:");
        System.out.println("---------------------------------");
        for (int i = 0; i < Math.min(k, todosPares.size()); i++) {
            Resultado resultado = todosPares.get(i);
            System.out.printf("%s <-> %s = %.4f\n", 
                resultado.doc1, resultado.doc2, resultado.similaridade);
        }
        
        System.out.println("Todos os pares de documentos:");
        System.out.println("---------------------------------");
        for (Resultado resultado : todosPares) {
            System.out.printf("%s <-> %s = %.4f\n", 
                resultado.doc1, resultado.doc2, resultado.similaridade);
        }
        
        exibirEstatisticas();
    }

    public void compararDoisArquivos(String caminho1, String caminho2) throws IOException {
        File arquivo1 = new File(caminho1);
        File arquivo2 = new File(caminho2);
        
        Documento doc1 = new Documento(arquivo1.getName());
        Documento doc2 = new Documento(arquivo2.getName());
        
        processarDocumentoArquivo(arquivo1, doc1);
        processarDocumentoArquivo(arquivo2, doc2);
        
        double similaridade = calcularSimilaridadeCosseno(doc1, doc2);
        
        System.out.println("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
        System.out.println("Comparando: " + doc1.getNome() + " <-> " + doc2.getNome());
        System.out.printf("Similaridade calculada: %.4f\n", similaridade);
        System.out.println("Métrica utilizada: Cosseno");
        System.out.println("Função hash utilizada: " + getNomeFuncaoHash());
    }

    private void processarDocumentoArquivo(File arquivo, Documento doc) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.replaceAll("[^a-zA-Zà-úÀ-Ú0-9 ]", " ").toLowerCase();
                
                String[] palavras = linha.split("\\s+");
                for (String palavra : palavras) {
                    palavra = palavra.trim();
                    if (!palavra.isEmpty() && !stopWords.contains(palavra)) {
                        doc.adicionarPalavra(palavra);
                    }
                }
            }
        }
    }

    private String getNomeFuncaoHash() {
        return funcaoHashUtilizada == 1 ? "hashMultiplicativo" : "hashShiftXOR";
    }

    private void exibirEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS DETALHADAS ---");
        System.out.println("Rotações simples na AVL: " + arvoreSimilaridades.getRotacoesSimples());
        System.out.println("Rotações duplas na AVL: " + arvoreSimilaridades.getRotacoesDuplas());
        System.out.println("Stop words carregadas: " + stopWords.size());
        
        // ← NOVAS ESTATÍSTICAS DA TABELA HASH
        System.out.println("--- ESTATÍSTICAS DA TABELA HASH ---");
        System.out.println("Total de colisões: " + documentos.getTotalColisoes());
        System.out.println("Fator de carga: " + String.format("%.2f", documentos.getFatorCarga()));
        System.out.println("Maior bucket: " + documentos.getMaiorBucket() + " elementos");
        System.out.println("Buckets vazios: " + documentos.getBucketsVazios() + "/" + 
                          documentos.size() + " (" + 
                          String.format("%.1f", (documentos.getBucketsVazios() * 100.0 / documentos.size())) + "%)");
    }
}

class Documento {
    private String nome;
    private TabelaHash<String, Integer> frequencias;

    public Documento(String nome) {
        this.nome = nome;
        this.frequencias = new TabelaHash<>();
    }

    public void adicionarPalavra(String palavra) {
        Integer freq = frequencias.get(palavra);
        if (freq == null) {
            frequencias.put(palavra, 1);
        } else {
            frequencias.put(palavra, freq + 1);
        }
    }

    public int getFrequencia(String palavra) {
        Integer freq = frequencias.get(palavra);
        return freq == null ? 0 : freq;
    }

    public Set<String> getPalavras() {
        return frequencias.keySet();
    }

    public String getNome() {
        return nome;
    }
}

class Resultado {
    String doc1;
    String doc2;
    double similaridade;

    public Resultado(String doc1, String doc2, double similaridade) {
        this.doc1 = doc1;
        this.doc2 = doc2;
        this.similaridade = similaridade;
    }
}