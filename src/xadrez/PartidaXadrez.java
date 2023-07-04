package xadrez;
 
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
	private void novoLocalPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.localPeca(peca,new PosicaoXadrez(coluna, linha).toPosicao());
	}
	private void setupInicial() {
		novoLocalPeca('c', 1, new Torre(tabuleiro, Cor.WHITE));
		novoLocalPeca('c', 2, new Torre(tabuleiro, Cor.WHITE));
		novoLocalPeca('d', 2, new Torre(tabuleiro, Cor.WHITE));
		novoLocalPeca('e', 2, new Torre(tabuleiro, Cor.WHITE));
		novoLocalPeca('e', 1, new Torre(tabuleiro, Cor.WHITE));
		novoLocalPeca('d', 1, new Rei(tabuleiro, Cor.WHITE));

		novoLocalPeca('c', 7, new Torre(tabuleiro, Cor.BLACK));
		novoLocalPeca('c', 8, new Torre(tabuleiro, Cor.BLACK));
		novoLocalPeca('d', 7, new Torre(tabuleiro, Cor.BLACK));
        novoLocalPeca('e', 7, new Torre(tabuleiro, Cor.BLACK));
        novoLocalPeca('e', 8, new Torre(tabuleiro, Cor.BLACK));
        novoLocalPeca('d', 8, new Rei(tabuleiro, Cor.BLACK));
	}
}
