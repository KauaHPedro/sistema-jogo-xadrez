package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.BRANCAS;
		initialSetup();
		
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] matAux = new ChessPiece[board.getRows()][board.getColumns()];
		
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
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
        placeNewPiece('e', 1, new King(board, Color.BRANCAS));
        placeNewPiece('f', 1, new Bishop(board, Color.BRANCAS));
        placeNewPiece('h', 1, new Rook(board, Color.BRANCAS));
        placeNewPiece('g', 1, new Knight(board, Color.BRANCAS));

        placeNewPiece('c', 7, new Rook(board, Color.PRETAS));
        placeNewPiece('c', 8, new Rook(board, Color.PRETAS));
        placeNewPiece('d', 7, new Rook(board, Color.PRETAS));
        placeNewPiece('e', 7, new Rook(board, Color.PRETAS));
        placeNewPiece('e', 8, new Rook(board, Color.PRETAS));
        placeNewPiece('d', 8, new King(board, Color.PRETAS));
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piecePosition(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, 
			ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece piece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		
		board.placePiece(piece, target);
		
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem!");
		}
		if(!board.piecePosition(position).isThereAnyPossibleMove()) {
			throw new ChessException("Peça sem movimentos válidos!");
		}		 
		 if(currentPlayer != ((ChessPiece)board.piecePosition(position)).getColor()) {
			 throw new ChessException("Peça do jogador adversário!");
		 }
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piecePosition(source).possibleMove(target)) {
			throw new ChessException("Movimento de destino inválido!");
		};
	}
	
	private void nextTurn() {
		turn++;
		if (currentPlayer == Color.BRANCAS) {
			currentPlayer = Color.PRETAS;
		} else {
			currentPlayer = Color.BRANCAS;
		}
	}
	
	

}
