package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "♕";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] posicoes = new boolean[getBoard().getRows()][getBoard().getColumns()];
		//combinação das movimentacoes da torre e do bispo
		int[][] direcoes = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}, 
				{-1, -1}, {-1, 1}, {1, -1}, {1, 1} };
		
		for(int[] direcao : direcoes) {
			Position posicao = new Position(position.getRow(), position.getColumn());
			posicao.setValues(position.getRow() + direcao[0], position.getColumn() + direcao[1]);
			
			while(getBoard().positionExists(posicao) && !getBoard().thereIsAPiece(posicao)) {
				posicoes[posicao.getRow()][posicao.getColumn()] = true;
				posicao.setValues(posicao.getRow() + direcao[0], posicao.getColumn() + direcao[1]);
			}
			
			if(getBoard().positionExists(posicao) && isThereOpponentPiece(posicao)) {
				posicoes[posicao.getRow()][posicao.getColumn()] = true;
			}
		}
		
		return posicoes;
		
	}

}
