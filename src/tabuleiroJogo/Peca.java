package tabuleiroJogo;

public abstract class Peca {
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	public abstract boolean[][] movPossivel();
	
	public boolean movPossivel(Posicao posicao) {
		return movPossivel()[posicao.getLinha()][posicao.getColuna()];
	}
	
} 