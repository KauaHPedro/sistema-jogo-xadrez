# Sistema de Jogo de Xadrez

## 📌 Sobre o Projeto
Este projeto é um jogo de xadrez implementado em Java, utilizando conceitos fundamentais da Programação Orientada a Objetos (POO). O sistema permite a execução de partidas com regras completas do xadrez, incluindo captura de peças, xeque, xeque-mate, roque, en passant e promoção de peões.

## 🎯 Funcionalidades
- Gerenciamento de um tabuleiro de xadrez 8x8.
- Movimentação das peças respeitando as regras oficiais do jogo.
- Controle de turnos entre jogadores.
- Identificação de xeque, xeque-mate e empate por afogamento.
- Histórico de peças capturadas.
- Suporte a jogadas especiais como **roque, en passant** e **promoção de peão**.

## 🛠️  Tecnologias Utilizadas
- **Java** (Linguagem principal)
- **Paradigma Programação Orientada a Objetos (POO)**

## 🏗️  Arquitetura do Projeto
O projeto é estruturado de forma modular, respeitando os princípios da POO:

### 🔹 **Encapsulamento e Organização em Pacotes**
A estrutura do código foi organizada em diferentes pacotes para garantir a separação de responsabilidades:

- **`boardgame`** → Responsável pela lógica do tabuleiro.
  - `Board.java`: Representa o tabuleiro e suas posições.
  - `Piece.java`: Classe abstrata que define uma peça genérica no tabuleiro.
  - `Position.java`: Representa uma posição (linha, coluna) dentro do tabuleiro. Nessa classe, representamos a posição na forma de matriz.

- **`chess`** → Responsável pela lógica específica do jogo de xadrez.
  - `ChessMatch.java`: Gerencia a partida de xadrez (turnos, regras, estado do jogo).
  - `ChessPiece.java`: Subclasse de `Piece`, representa uma peça de xadrez com características próprias (cor, contador de movimentos, etc.).
  - `ChessPosition.java`: Converte coordenadas do xadrez (ex: "e4") para posições no tabuleiro.

- **`chess.pieces`** → Contém todas as peças do xadrez como classes individuais, garantindo a separação de regras de movimento:
  - `King.java`, `Queen.java`, `Rook.java`, `Bishop.java`, `Knight.java`, `Pawn.java`

## 📦 Como Executar o Projeto
### 🔹 **Método 1: Executando o Arquivo JAR**
1. Baixe o arquivo `chessgame.jar`.
2. Execute no terminal ou CMD:
   ```sh
   java -jar chessgame.jar
   ```

### 🔹 **Método 2: Compilação Manual**
Caso queira compilar o projeto manualmente:
1. Compile o código:
   ```sh
   javac -d bin src/chess/*.java src/boardgame/*.java src/chess/pieces/*.java
   ```
2. Gere o arquivo JAR:
   ```sh
   jar cfe chessgame.jar chess.Main -C bin .
   ```
3. Execute com:
   ```sh
   java -jar chessgame.jar
   ```

## 📌 Melhorias Futuras
- Implementação de interface gráfica.
- Suporte a partidas online (multiplayer via rede).


