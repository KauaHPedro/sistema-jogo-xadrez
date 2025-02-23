package chess;

import boardgame.Board;

public class ChessMatch {
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		
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

}
