import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random gerador = new Random();
        int A[][] = new int[2][4];
        int B[][] = new int[2][4];
        int C[][] = new int[2][4];
        for (int i = 0; i < 2; i++) {
            for ( int j = 0; j < 4; j++) {
                System.out.println(A[i][j] = gerador.nextInt(100));
                System.out.println(B[i][j] = gerador.nextInt(100));
            }
        }
        for (int i = 0; i < 2; i++) {
            for ( int j = 0; j < 4; j++) {
                C[i][j] += A[i][j] * B[i][j];
            }
        }
        System.out.println(Arrays.deepToString(C));
    }
}