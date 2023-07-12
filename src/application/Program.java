package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>(); 
		while(!partidaXadrez.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.retornaPartida(partidaXadrez,capturada);
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				boolean[][] movPossivel = partidaXadrez.movPossivel(origem);
				UI.clearScreen();
				UI.retornaTabuleiro(partidaXadrez.getPecas(),movPossivel);
				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez captaPeca = partidaXadrez.movimentaPeca(origem, destino);
				if(captaPeca != null) {
					capturada.add(captaPeca);
				}
				
				if(partidaXadrez.getPromocao()!= null) {
					System.out.print("[A]Rainha | [B]Bispo | [C]Cavalo | [T]Torre: ");
					String tipo = sc.nextLine();
					partidaXadrez.trocaPecaPromo(tipo);
				}
			}catch(XadrezException ex) {
				System.out.println(ex.getMessage());
				sc.nextLine();
			}catch(InputMismatchException ex) {
				System.out.println(ex.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.retornaPartida(partidaXadrez, capturada);
	}

}