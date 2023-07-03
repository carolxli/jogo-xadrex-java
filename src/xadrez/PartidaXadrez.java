package xadrez;

import tabuleiroJogo.Tabuleiro;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
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
}
