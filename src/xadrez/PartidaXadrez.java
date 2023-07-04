package xadrez;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		setupInicial();
	}
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getQtdLinhas()][tabuleiro.getQtdColunas()];
		for(int i=0;i<tabuleiro.getQtdLinhas();i++) {
			for(int j=0;j<tabuleiro.getQtdColunas();j++) {
				mat[i][j] = (PecaXadrez)tabuleiro.peca(i,j);
			}
		}
		return mat;
	}
	
	private void setupInicial() {
		tabuleiro.localPeca(new Torre(tabuleiro, Cor.WHITE), new Posicao(2, 1));
		tabuleiro.localPeca(new Rei(tabuleiro, Cor.BLACK), new Posicao(0, 4));
		tabuleiro.localPeca(new Rei(tabuleiro, Cor.WHITE), new Posicao(7, 4));
	}
}
