package tabuleiroJogo;

public class Tabuleiro {
	private int qtdLinhas;
	private int qtdColunas;
	private Peca[][] pecas;
	public Tabuleiro(int qtdLinhas, int qtdColunas) {
		this.qtdLinhas = qtdLinhas;
		this.qtdColunas = qtdColunas;
		pecas = new Peca[qtdLinhas][qtdColunas];
	}
	public int getQtdLinhas() {
		return qtdLinhas;
	}
	public void setQtdLinhas(int qtdLinhas) {
		this.qtdLinhas = qtdLinhas;
	}
	public int getQtdColunas() {
		return qtdColunas;
	}
	public void setQtdColunas(int qtdColunas) {
		this.qtdColunas = qtdColunas;
	}
	public Peca peca(int linha, int coluna) {
		return pecas[linha][coluna];
	}
	public Peca peca(Posicao posicao) {
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void localPeca(Peca peca, Posicao posicao) {
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
}
