import java.util.Arrays;

public class MetodoGauss {
    
    /**
     * A: Matriz dos coeficientes 3x3
     * B: Vetor dos termos independentes
     * returna Vetor solução [x, y, z]
     */
    public static double[] resolverSistema(double[][] A, double[] B) {
        // Verifica se a matriz é 3x3
        if (A.length != 3 || A[0].length != 3 || B.length != 3) {
            throw new IllegalArgumentException("O sistema deve ser 3x3");
        }
        
        // Cria cópias para não modificar os originais
        double[][] matriz = copiarMatriz(A);
        double[] termos = Arrays.copyOf(B, B.length);
        
        System.out.println("Sistema original:");
        imprimirSistema(matriz, termos);
        
        // Fase 1: Eliminação progressiva (forma escalonada)
        System.out.println("\n--- FASE DE ELIMINAÇÃO ---");
        for (int pivo = 0; pivo < 2; pivo++) {
            System.out.println("\nPivô na linha " + (pivo + 1));
            
            if (Math.abs(matriz[pivo][pivo]) < 1e-10) {
                // Procura uma linha para trocar
                boolean trocou = false;
                for (int i = pivo + 1; i < 3; i++) {
                    if (Math.abs(matriz[i][pivo]) > 1e-10) {
                        trocarLinhas(matriz, termos, pivo, i);
                        trocou = true;
                        System.out.println("Troca linha " + (pivo + 1) + " com linha " + (i + 1));
                        break;
                    }
                }
                if (!trocou) {
                    throw new ArithmeticException("Sistema singular - não possui solução única");
                }
            }
            
            // Eliminação das linhas abaixo do pivô
            for (int linha = pivo + 1; linha < 3; linha++) {
                double fator = matriz[linha][pivo] / matriz[pivo][pivo];
                System.out.printf("Eliminando linha %d: fator = %.2f\n", 
                                 linha + 1, fator);
                
                for (int coluna = pivo; coluna < 3; coluna++) {
                    matriz[linha][coluna] -= fator * matriz[pivo][coluna];
                }
                termos[linha] -= fator * termos[pivo];
            }
            
            imprimirSistema(matriz, termos);
        }
        
        // Fase 2: Substituição
        System.out.println("\n--- FASE DE SUBSTITUIÇÃO ---");
        double[] solucao = new double[3];
        
        // Resolve a última equação (z)
        solucao[2] = termos[2] / matriz[2][2];
        System.out.printf("z = %.2f / %.2f = %.2f\n", 
                         termos[2], matriz[2][2], solucao[2]);
        
        // Resolve a segunda equação (y)
        solucao[1] = (termos[1] - matriz[1][2] * solucao[2]) / matriz[1][1];
        System.out.printf("y = (%.2f - %.2f × %.2f) / %.2f = %.2f\n", termos[1], matriz[1][2], solucao[2], matriz[1][1], solucao[1]);
        
        // Resolve a primeira equação (x)
        solucao[0] = (termos[0] - matriz[0][1] * solucao[1] - matriz[0][2] * solucao[2]) / matriz[0][0];
        System.out.printf("x = (%.2f - %.2f × %.2f - %.2f × %.2f) / %.2f = %.2f\n", 
                         termos[0], matriz[0][1], solucao[1], matriz[0][2], solucao[2], 
                         matriz[0][0], solucao[0]);
        
        return solucao;
    }
    
    // Troca duas linhas da matriz e do vetor de termos
    private static void trocarLinhas(double[][] matriz, double[] termos, int linha1, int linha2) {
        // Troca as linhas da matriz
        double[] tempLinha = matriz[linha1];
        matriz[linha1] = matriz[linha2];
        matriz[linha2] = tempLinha;
        
        // Troca os termos independentes
        double tempTermo = termos[linha1];
        termos[linha1] = termos[linha2];
        termos[linha2] = tempTermo;
    }
    
    private static double[][] copiarMatriz(double[][] original) {
        double[][] copia = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            copia[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copia;
    }
    
    private static void imprimirSistema(double[][] A, double[] B) {
        for (int i = 0; i < 3; i++) {
            System.out.printf("%.2fx + %.2fy + %.2fz = %.2f\n", A[i][0], A[i][1], A[i][2], B[i]);
        }
    }
    
    // Verifica a solução substituindo no sistema original
    public static void verificarSolucao(double[][] A, double[] B, double[] solucao) {
        System.out.println("\n--- VERIFICAÇÃO DA SOLUÇÃO ---");
        double[] residuo = new double[3];
        for (int i = 0; i < 3; i++) {
            double calculado = A[i][0] * solucao[0] + A[i][1] * solucao[1] + A[i][2] * solucao[2];
            residuo[i] = B[i] - calculado;
            System.out.printf("Equação %d: %.2f = %.2f (erro: %.6f)\n", i + 1, calculado, B[i], residuo[i]);
        }
    }
    
    public static void main(String[] args) {
        // Exemplo de uso
        double[][] A1 = {
            {2, 1, -1},
            {-3, -1, 2},
            {-2, 1, 2}
        };
        double[] B1 = {8, -11, -3};
        
        double[] solucao1 = resolverSistema(A1, B1);
        System.out.println("\nSolução:");
        System.out.printf("x = %.2f, y = %.2f, z = %.2f\n", solucao1[0], solucao1[1], solucao1[2]);
        verificarSolucao(A1, B1, solucao1);
        
    }
}