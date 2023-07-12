package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiroJogo.Peca;
import tabuleiroJogo.Posicao;
import tabuleiroJogo.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassant;
	private PecaXadrez promocao;
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

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrez getenPassant() {
		return enPassant;
	}

	public PecaXadrez getPromocao() {
		return promocao;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getQtdLinhas()][tabuleiro.getQtdColunas()];
		for (int i = 0; i < tabuleiro.getQtdLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getQtdColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] movPossivel(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.toPosicao();
		validaPosOrigem(posicao);
		return tabuleiro.peca(posicao).movPossivel();
	}

	public PecaXadrez movimentaPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validaPosOrigem(origem);
		validaPosDestino(origem, destino);
		Peca captaPeca = movimentar(origem, destino);

		if (testCheck(jogadorAtual)) {
			desfazer(origem, destino, captaPeca);
			throw new XadrezException("Você não pode se colocar em check!");
		}

		PecaXadrez movePeca = (PecaXadrez) tabuleiro.peca(destino);
		promocao = null;
		if (movePeca instanceof Peao) {
			if ((movePeca.getCor() == Cor.WHITE && destino.getLinha() == 0)
					|| (movePeca.getCor() == Cor.BLACK && destino.getLinha() == 7)) {
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = trocaPecaPromo("A");
			}
		}
		check = (testCheck(corOponente(jogadorAtual))) ? true : false;
		if (testCheckMate(corOponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proxTurno();
		}

		if (movePeca instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassant = movePeca;
		} else {
			enPassant = null;
		}

		return (PecaXadrez) captaPeca;
	}

	public PecaXadrez trocaPecaPromo(String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("Não há peça a ser promovida!");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("A") && !tipo.equals("T")) {
			throw new InvalidParameterException("Promoção de peça inválida!");
		}
		Posicao pos = promocao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasTabuleiro.remove(p);
		PecaXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.localPeca(novaPeca, pos);
		pecasTabuleiro.add(novaPeca);
		return novaPeca;
	}

	public PecaXadrez novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B"))
			return new Bispo(tabuleiro, cor);
		if (tipo.equals("C"))
			return new Cavalo(tabuleiro, cor);
		if (tipo.equals("A"))
			return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}

	private Peca movimentar(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
		p.addContMov();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.localPeca(p, destino);

		if (pecaCapturada != null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);

		}
		// roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.localPeca(torre, destinoTorre);
			torre.addContMov();
		}
		// roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.localPeca(torre, destinoTorre);
			torre.addContMov();
		}

		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void desfazer(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(destino);
		p.subtContMov();
		tabuleiro.localPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.localPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasTabuleiro.add(pecaCapturada);
		}

		// roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.localPeca(torre, origemTorre);
			torre.subtContMov();
		}
		// roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.localPeca(torre, origemTorre);
			torre.subtContMov();
		}

		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassant) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removePeca(destino);
				Posicao peaoPosicao;
				if (p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.localPeca(peao, peaoPosicao);
			}
		}

	}

	private void validaPosOrigem(Posicao posicao) {
		if (!tabuleiro.existePeca(posicao)) {
			throw new XadrezException("Não existe peça na posição de origem!");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua.");
		}
		if (!tabuleiro.peca(posicao).existeMovPossivel()) {
			throw new XadrezException("Não existe movimentos possíveis para a peça escolhida");
		}

	}

	private void validaPosDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para a posição de destino.");
		}
	}

	private void proxTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	private Cor corOponente(Cor cor) {
		return (cor == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o Rei da cor " + cor + " no tabuleiro.");
	}

	private boolean testCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == corOponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movPossivel();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Cor cor) {
		if (!testCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.movPossivel();
			for (int i = 0; i < tabuleiro.getQtdLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getQtdColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = movimentar(origem, destino);
						boolean testCheck = testCheck(cor);
						desfazer(origem, destino, pecaCapturada);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void novoLocalPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.localPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasTabuleiro.add(peca);
	}

	private void setupInicial() {
		novoLocalPeca('a', 1, new Torre(tabuleiro, Cor.WHITE));
		novoLocalPeca('b', 1, new Cavalo(tabuleiro, Cor.WHITE));
		novoLocalPeca('c', 1, new Bispo(tabuleiro, Cor.WHITE));
		novoLocalPeca('d', 1, new Rainha(tabuleiro, Cor.WHITE));
		novoLocalPeca('e', 1, new Rei(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('f', 1, new Bispo(tabuleiro, Cor.WHITE));
		novoLocalPeca('g', 1, new Cavalo(tabuleiro, Cor.WHITE));
		novoLocalPeca('h', 1, new Torre(tabuleiro, Cor.WHITE));
		novoLocalPeca('a', 2, new Peao(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('b', 2, new Peao(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('c', 2, new Peao(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('d', 2, new Peao(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('e', 2, new Peao(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('f', 2, new Peao(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('g', 2, new Peao(tabuleiro, Cor.WHITE, this));
		novoLocalPeca('h', 2, new Peao(tabuleiro, Cor.WHITE, this));

		novoLocalPeca('a', 8, new Torre(tabuleiro, Cor.BLACK));
		novoLocalPeca('b', 8, new Cavalo(tabuleiro, Cor.BLACK));
		novoLocalPeca('c', 8, new Bispo(tabuleiro, Cor.BLACK));
		novoLocalPeca('d', 8, new Rainha(tabuleiro, Cor.BLACK));
		novoLocalPeca('e', 8, new Rei(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('f', 8, new Bispo(tabuleiro, Cor.BLACK));
		novoLocalPeca('g', 8, new Cavalo(tabuleiro, Cor.BLACK));
		novoLocalPeca('h', 8, new Torre(tabuleiro, Cor.BLACK));
		novoLocalPeca('a', 7, new Peao(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('b', 7, new Peao(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('c', 7, new Peao(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('d', 7, new Peao(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('e', 7, new Peao(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('f', 7, new Peao(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('g', 7, new Peao(tabuleiro, Cor.BLACK, this));
		novoLocalPeca('h', 7, new Peao(tabuleiro, Cor.BLACK, this));
	}
}