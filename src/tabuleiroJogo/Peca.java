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
	
	public boolean existeMovPossivel() {
		boolean[][]mat = movPossivel();
		for(int i=0;i<mat.length;i++) {
			for(int j=0;j<mat.length;j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
} 