package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	private boolean check;
	private boolean checkMate;

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.BRANCAS;
		initialSetup();

	}

	public int getTurn() {
		return turn;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] matAux = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				matAux[i][j] = (ChessPiece) board.piecePosition(i, j);
			}
		}
		return matAux;
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.BRANCAS));
		placeNewPiece('b', 1, new Knight(board, Color.BRANCAS));
		placeNewPiece('c', 1, new Bishop(board, Color.BRANCAS));
		placeNewPiece('d', 1, new Queen(board, Color.BRANCAS));
		placeNewPiece('e', 1, new King(board, Color.BRANCAS, this));
		placeNewPiece('f', 1, new Bishop(board, Color.BRANCAS));
		placeNewPiece('g', 1, new Knight(board, Color.BRANCAS));
		placeNewPiece('h', 1, new Rook(board, Color.BRANCAS));

		// peões
		for (char coluna = 'a'; coluna <= 'h'; coluna++) {
			placeNewPiece(coluna, 2, new Pawn(board, Color.BRANCAS));
		}

		placeNewPiece('a', 8, new Rook(board, Color.PRETAS));
		placeNewPiece('b', 8, new Knight(board, Color.PRETAS));
		placeNewPiece('c', 8, new Bishop(board, Color.PRETAS));
		placeNewPiece('d', 8, new Queen(board, Color.PRETAS));
		placeNewPiece('e', 8, new King(board, Color.PRETAS, this));
		placeNewPiece('f', 8, new Bishop(board, Color.PRETAS));
		placeNewPiece('g', 8, new Knight(board, Color.PRETAS));
		placeNewPiece('h', 8, new Rook(board, Color.PRETAS));

		// peões
		for (char coluna = 'a'; coluna <= 'h'; coluna++) {
			placeNewPiece(coluna, 7, new Pawn(board, Color.PRETAS));
		}
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piecePosition(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("Você não pode se colocar em cheque!");
		}

		if (testCheck(oponnent(currentPlayer))) {
			check = true;
		} else {
			check = false;
		}

		if (testCheckMate(oponnent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}

		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece piece = (ChessPiece) board.removePiece(source);
		piece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(piece, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// roque pequeno
		if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}

		// roque maior
		if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece piece = (ChessPiece) board.removePiece(target);
		piece.decreaseMoveCount();
		board.placePiece(piece, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// roque pequeno
		if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();;
		}

		// roque maior
		if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem!");
		}
		if (!board.piecePosition(position).isThereAnyPossibleMove()) {
			throw new ChessException("Peça sem movimentos válidos!");
		}
		if (currentPlayer != ((ChessPiece) board.piecePosition(position)).getColor()) {
			throw new ChessException("Peça do jogador adversário!");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piecePosition(source).possibleMove(target)) {
			throw new ChessException("Movimento de destino inválido!");
		}
		;
	}

	private void nextTurn() {
		turn++;
		if (currentPlayer == Color.BRANCAS) {
			currentPlayer = Color.PRETAS;
		} else {
			currentPlayer = Color.BRANCAS;
		}
	}

	private Color oponnent(Color color) {
		if (color == Color.BRANCAS) {
			return Color.PRETAS;
		} else {
			return Color.BRANCAS;
		}
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece) piece).getColor() == color)
				.collect(Collectors.toList());

		for (Piece piece : list) {
			if (piece instanceof King) {
				return (ChessPiece) piece;
			}
		}
		throw new IllegalStateException("Não existe rei das peças " + color + " no tabuleiro!");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(piece -> ((ChessPiece) piece).getColor() == oponnent(color)).collect(Collectors.toList());

		for (Piece piece : opponentPieces) {
			boolean[][] matrizAux = piece.possibleMoves();
			if (matrizAux[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}

		List<Piece> playerPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece) piece).getColor() == color)
				.collect(Collectors.toList());

		for (Piece piece : playerPieces) {
			boolean[][] movimentosJogador = piece.possibleMoves();

			for (int i = 0; i < movimentosJogador.length; i++) {
				for (int j = 0; j < movimentosJogador[i].length; j++) {
					if (movimentosJogador[i][j]) {
						Position source = ((ChessPiece) piece).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);

						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

}
