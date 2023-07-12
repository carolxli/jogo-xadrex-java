# Jogo de Xadrez em Java

Este é um jogo de xadrez desenvolvido em Java utilizando todos os princípios e paradigmas da orientação a objetos. O jogo foi criado com o auxílio do professor Nelio Alves no curso de Java da plataforma Udemy.

## Requisitos

- Java Development Kit (JDK) 8 ou superior instalado
- Git Bash (ou qualquer outro terminal de sua preferência)

## Executando o jogo

Para executar o jogo com as cores adequadas, siga as instruções abaixo:

1. Abra o terminal Git Bash.
2. Navegue até a pasta `bin` do arquivo do jogo.
3. Execute o comando abaixo no terminal para iniciar o jogo:

   ```
   java application/Program
   ```

   Certifique-se de que o arquivo `Program` contém a classe `main` necessária para iniciar o jogo.

## Funcionalidades do jogo

- O jogo segue as regras básicas do xadrez.
- Os jogadores podem fazer movimentos válidos com as peças de acordo com as regras do xadrez.
- O jogo possui detecção de xeque-mate, xeque e empate.
- O jogo mantém o controle das jogadas indicando as casas permitidas de acordo com o tipo da peça.
- O jogo mostra as peças capturadas por cada jogador.
- O jogo indica o turno vigente e o jogador da vez.

## Estrutura do projeto

O projeto do jogo de xadrez está estruturado utilizando os seguintes pacotes e classes:

- `xadrez`: Pacote principal do jogo.
  - `PartidaXadrez`: Classe responsável por controlar o fluxo da partida de xadrez.
  - `PecaXadrez`: Classe abstrata que representa uma peça de xadrez.
  - `PosicaoXadrez`: Classe que representa a posição de uma peça no tabuleiro.
  - `XadrezException`: Classe que trata exceções específicas do jogo de xadrez.

- `xadrez.pecas`: Pacote que contém as classes para cada tipo de peça.
  - Classes para cada tipo de peça (ex: `Rei`, `Rainha`, `Torre`, `Bispo`, `Cavalo`, `Peão`).

- `tabuleiroJogo`: Pacote com classes genéricas relacionadas ao tabuleiro de jogo.
  - `Tabuleiro`: Classe que representa o tabuleiro do jogo (matriz 8x8).
  - `Peca`: Classe abstrata que representa uma peça genérica no tabuleiro.
  - `Posicao`: Classe que representa uma posição no tabuleiro.
  - `TabuleiroException`: Classe que trata exceções específicas do tabuleiro.

- `application`: Pacote que contém a classe principal para executar o jogo.
  - `Program`: Classe com o método `main` para iniciar o jogo.
  - `UI`: Classe responsável pelo controle de cores na aplicação.

## Contribuição

Este jogo de xadrez foi desenvolvido como parte de um curso de Java e não é aceito contribuições externas por questão de autoria. No entanto, sinta-se à vontade para utilizar e modificar o código para fins de aprendizado e diversão pessoal.

## Agradecimentos

Agradeço ao professor Nelio Alves pelo excelente curso de Java na plataforma Udemy, que me proporcionou conhecimento e prática na construção deste jogo de xadrez.
