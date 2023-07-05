package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		while(true) {
			try {
				UI.clearScreen();
				UI.retornaTabuleiro(partidaXadrez.getPecas());
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaXadrez captaPeca = partidaXadrez.movimentaPeca(origem, destino);
			}catch(XadrezException ex) {
				System.out.println(ex.getMessage());
				sc.nextLine();
			}catch(InputMismatchException ex) {
				System.out.println(ex.getMessage());
				sc.nextLine();
			}
		}
		
	}

}