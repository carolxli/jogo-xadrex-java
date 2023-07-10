package xadrez;
 

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	
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
	
	public boolean getCheck() {
		return check;
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
		if(testCheck(jogadorAtual)) {
			desfazer(origem, destino, captaPeca);
			throw new XadrezException("Você não pode se colocar em check!");
		}
		check = (testCheck(corOponente(jogadorAtual))) ? true : false;
		proxTurno();
		return (PecaXadrez)captaPeca;
	}
	
	private Peca movimentar(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p,destino);
		
		if(pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
			
		}
		return pecaCapturada;
	}
	
	private void desfazer(Posicao origem, Posicao destino, Peca pecaCapturada) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p,origem);
		if(pecaCapturada != null) {
			tabuleiro.localPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasTabuleiro.add(pecaCapturada);
		}
	}
	
	private void validaPosOrigem(Posicao posicao) {
		if(!tabuleiro.existePeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem!");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua.");
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
	
	private Cor corOponente(Cor cor) {
		return (cor==Cor.WHITE)? Cor.BLACK:Cor.WHITE;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return (PecaXadrez)p;
			}   
		}
		throw new IllegalStateException("Não existe o Rei da cor "+cor+" no tabuleiro.");
	}
	
	private boolean testCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == corOponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movPossivel();
			if(mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	
	private void novoLocalPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.localPeca(peca,new PosicaoXadrez(coluna, linha).toPosicao());
		pecasTabuleiro.add(peca); 
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
