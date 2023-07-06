package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getQtdLinhas()][getTabuleiro().getQtdColunas()];
		Posicao p = new Posicao(0, 0);
		// verificando linha reta p/ cima
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {// posicao existe , esta vaga e
																						// vazia
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// verificando para esquerda
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {// posicao existe , esta vaga e
																						// vazia
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// verificando para direita
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {// posicao existe , esta vaga e
																						// vazia
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// verificando linha reta p/ baixo
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {// posicao existe , esta vaga e
																						// vazia
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
