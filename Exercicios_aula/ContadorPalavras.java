import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ContadorPalavras {
    public static void main(String[] args) {
        // Caminho do arquivo texto (você pode mudar aqui)
        String caminhoArquivo = "texto.txt";

        // Mapa para armazenar palavra -> frequência
        Map<String, Integer> frequencia = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Remove pontuação e coloca tudo em minúsculas
                linha = linha.replaceAll("[^a-zA-Zà-úÀ-Ú0-9 ]", "").toLowerCase();

                // Divide em palavras
                String[] palavras = linha.split("\\s+");
                for (String palavra : palavras) {
                    if (!palavra.isEmpty()) {
                        frequencia.put(palavra, frequencia.getOrDefault(palavra, 0) + 1);
                    }
                }
            }

            // Ordena por frequência decrescente
            List<Entry<String, Integer>> lista = new ArrayList<>(frequencia.entrySet());
            lista.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

            // Exibe o resultado
            for (Entry<String, Integer> entrada : lista) {
                System.out.println(entrada.getKey() + " (" + entrada.getValue() + ")");
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}