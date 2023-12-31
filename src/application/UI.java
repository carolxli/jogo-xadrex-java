package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class UI {
	// cores
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, row);
		} catch (RuntimeException ex) {
			throw new InputMismatchException("Erro! Valores válidos são de a1 até h8");
		}
	}

	public static void retornaPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturada) {
		retornaTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		retornaPecaCapturada(capturada);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		if (!partidaXadrez.getCheckMate()) {
			System.out.println("Jogador: " + partidaXadrez.getJogadorAtual());
			if (partidaXadrez.getCheck()) {
				System.out.println("CHECK!");
			}
		}
		else {
			System.out.println("C H E C K M A T E!!!");
			System.out.println("Vencedor: "+ partidaXadrez.getJogadorAtual());
		}
	}

	public static void retornaTabuleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				retornaPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void retornaTabuleiro(PecaXadrez[][] pecas, boolean[][] movPossivel) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				retornaPeca(pecas[i][j], movPossivel[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void retornaPeca(PecaXadrez peca, boolean colorir) {
		if (colorir) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor.WHITE) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	private static void retornaPecaCapturada(List<PecaXadrez> capturada) {
		List<PecaXadrez> white = capturada.stream().filter(x -> x.getCor() == Cor.WHITE).collect(Collectors.toList());
		List<PecaXadrez> black = capturada.stream().filter(x -> x.getCor() == Cor.BLACK).collect(Collectors.toList());
		System.out.println("Peças capturadas: ");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}
}
