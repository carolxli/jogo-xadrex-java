package tabuleiroJogo;

public class Tabuleiro {
	private int qtdLinhas;
	private int qtdColunas;
	private Peca[][] pecas;
	public Tabuleiro(int qtdLinhas, int qtdColunas) {
		if(qtdLinhas < 1 || qtdColunas < 1) {
			throw new TabuleiroException("Erro criando o tabuleiro: é necessário que haja ao menos 1 linha e 1 coluna."); 
		}
		this.qtdLinhas = qtdLinhas;
		this.qtdColunas = qtdColunas;
		pecas = new Peca[qtdLinhas][qtdColunas];
	}
	public int getQtdLinhas() {
		return qtdLinhas;
	}
	public int getQtdColunas() {
		return qtdColunas;
	}
	public Peca peca(int linha, int coluna) {
		if(!posicaoExistente(linha,coluna)) {
			throw new TabuleiroException("Posição não existe");
		}
		return pecas[linha][coluna];
	}
	public Peca peca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição não existe");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void localPeca(Peca peca, Posicao posicao) {
		if(existePeca(posicao)) {
			throw new TabuleiroException("Existe uma peça nessa posição: "+ posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	private boolean posicaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < qtdLinhas && coluna >= 0 && coluna < qtdColunas;
	}
	
	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(),posicao.getColuna());
	}
	
	public boolean existePeca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posição não existe");
		}
		return peca(posicao) != null;
	}
}