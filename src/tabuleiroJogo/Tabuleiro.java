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
	
}
