package xadrez;
 

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.WHITE;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
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
	
	public boolean[][] movPossivel(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validaPosOrigem(posicao);
		return tabuleiro.peca(posicao).movPossivel();
	}
	
	public PecaXadrez movimentaPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validaPosOrigem(origem);
		validaPosDestino(origem,destino);
		Peca captaPeca = movimentar(origem,destino);
		proxTurno();
		return (PecaXadrez)captaPeca;
	}
	
	private Peca movimentar(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p,destino);
		return pecaCapturada;
	}
	
	private void validaPosOrigem(Posicao posicao) {
		if(!tabuleiro.existePeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem!");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peã escolhida não é sua.");
		}
		if(!tabuleiro.peca(posicao).existeMovPossivel()) {
			throw new XadrezException("Não existe movimentos possíveis para a peça escolhida");
		}
		
	}
	
	private void validaPosDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para a posição de destino.");
		}
	}
	
	private void proxTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.WHITE)? Cor.BLACK : Cor.WHITE;
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
