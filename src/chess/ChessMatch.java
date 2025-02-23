package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
		
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] matrizAux = new ChessPiece[board.getRows()][board.getColumns()];
		
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				matrizAux[i][j] = (ChessPiece) board.piecePosition(i, j);
			}
		}
		return matrizAux;
	}
	
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(9, 1));
		board.placePiece(new King(board, Color.WHITE), new Position(5, 5));
	}

}
