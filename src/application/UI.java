package application;

import xadrez.PecaXadrez;

public class UI {
    public static void retornaTabuleiro(PecaXadrez[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                retornaPeca(pecas[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void retornaPeca(PecaXadrez peca) {
        if (peca != null) {
            System.out.print(peca);
        } else {
            System.out.print("-");
        }
        System.out.print(" ");
    }
}
