import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java Main <diretorio_documentos> <limiar> <modo> [argumentos_opcionais]");
            System.out.println("Modos: lista, topK, busca");
            System.out.println("Exemplo: java Main ./documentos 0.75 lista");
            System.out.println("Exemplo: java Main ./documentos 0.8 topK 5");
            System.out.println("Exemplo: java Main ./documentos 0.0 busca doc1.txt doc4.txt");
            System.out.println("Opção: adicione 'hash2' no final para usar a segunda função hash");
            return;
        }

        String diretorio = args[0];
        double limiar = Double.parseDouble(args[1]);
        String modo = args[2];

        try {
            // Configurar saída para arquivo e console
            PrintStream console = System.out;
            PrintStream fileOut = new PrintStream(new FileOutputStream("resultado.txt"));
            System.setOut(new DualStream(console, fileOut));

            SimilaridadeDocumentos simil = new SimilaridadeDocumentos();
            simil.carregarStopWords("stopwords.txt");
            
            // Verificar se o usuário quer usar a segunda função hash
            if (args.length > 3 && "hash2".equals(args[args.length - 1])) {
                simil.setFuncaoHash(2);
                System.out.println("=== USANDO FUNÇÃO HASH 2 (hashShiftXOR) ===");
            } else {
                System.out.println("=== USANDO FUNÇÃO HASH 1 (hashMultiplicativo) ===");
            }

            switch (modo) {
                case "lista":
                    if (args.length > 3 && !"hash2".equals(args[3])) {
                        System.out.println("Argumento opcional ignorado: " + args[3]);
                    }
                    simil.processarDiretorio(diretorio);
                    simil.exibirParesAcimaLimiar(limiar);
                    break;
                    
                case "topK":
                    if (args.length < 4) {
                        System.out.println("Erro: Modo topK requer o parâmetro K");
                        return;
                    }
                    int k = Integer.parseInt(args[3]);
                    simil.processarDiretorio(diretorio);
                    simil.exibirTopK(k);
                    break;
                    
                case "busca":
                    if (args.length < 5) {
                        System.out.println("Erro: Modo busca requer dois arquivos");
                        return;
                    }
                    String arquivo1 = args[3];
                    String arquivo2 = args[4];
                    simil.compararDoisArquivos(diretorio + "/" + arquivo1, diretorio + "/" + arquivo2);
                    break;
                    
                default:
                    System.out.println("Modo inválido: " + modo);
                    System.out.println("Modos válidos: lista, topK, busca");
            }

            fileOut.close();
            System.setOut(console);
            System.out.println("Resultado salvo em resultado.txt");

        } catch (NumberFormatException e) {
            System.err.println("Erro: Limiar deve ser um número (ex: 0.75)");
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo não encontrado - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro de E/S: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class DualStream extends PrintStream {
    private PrintStream console;
    private PrintStream file;

    public DualStream(PrintStream console, PrintStream file) {
        super(file);
        this.console = console;
        this.file = file;
    }

    public void write(byte[] buf, int off, int len) {
        try {
            console.write(buf, off, len);
            file.write(buf, off, len);
        } catch (Exception e) {
        }
    }

    public void flush() {
        console.flush();
        file.flush();
    }
}