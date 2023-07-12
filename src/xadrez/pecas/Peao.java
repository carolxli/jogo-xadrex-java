package xadrez.pecas;

import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movPossivel() {
		boolean[][] mat = new boolean[getTabuleiro().getQtdLinhas()][getTabuleiro().getQtdColunas()];
		Posicao p = new Posicao(0, 0);
		if (getCor() == Cor.WHITE) {
			p.setValues(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() - 2, posicao.getColuna());
			Posicao pAux = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)
					&& getTabuleiro().posicaoExistente(pAux) && !getTabuleiro().existePeca(pAux) && getContMov() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			if (posicao.getLinha() == 3) {
				Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(esq) && pecaAdversaria(esq)
						&& getTabuleiro().peca(esq) == partidaXadrez.getenPassant()) {
					mat[esq.getLinha() - 1][esq.getColuna()] = true;
				}

				Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(dir) && pecaAdversaria(dir)
						&& getTabuleiro().peca(dir) == partidaXadrez.getenPassant()) {
					mat[dir.getLinha() - 1][dir.getColuna()] = true;
				}
			}
		} else {
			p.setValues(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() + 2, posicao.getColuna());
			Posicao pAux = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().existePeca(p)
					&& getTabuleiro().posicaoExistente(pAux) && !getTabuleiro().existePeca(pAux) && getContMov() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && pecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			if (posicao.getLinha() == 4) {
				Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(esq) && pecaAdversaria(esq)
						&& getTabuleiro().peca(esq) == partidaXadrez.getenPassant()) {
					mat[esq.getLinha() + 1][esq.getColuna()] = true;
				}

				Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(dir) && pecaAdversaria(dir)
						&& getTabuleiro().peca(dir) == partidaXadrez.getenPassant()) {
					mat[dir.getLinha() + 1][dir.getColuna()] = true;
				}
			}
		}

		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
