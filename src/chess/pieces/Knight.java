package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{

	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "♘";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] posicoes = new boolean[getBoard().getRows()][getBoard().getColumns()];
		int [][] direcoes = { {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
				{-1, -2}, {-1, 2}, {1, -2}, {1, 2}   };
		
		for(int[] direcao : direcoes) {
			Position posicao = new Position(position.getRow(), position.getColumn());
			posicao.setValues(position.getRow() + direcao[0], position.getColumn() + direcao [1]);
			
			if(getBoard().positionExists(posicao) && !getBoard().thereIsAPiece(posicao)) {
				posicoes[posicao.getRow()][posicao.getColumn()] = true;
			}
			
			if(getBoard().positionExists(posicao) && isThereOpponentPiece(posicao)) {
				posicoes[posicao.getRow()][posicao.getColumn()] = true;
			}
		}
		
		return posicoes;
	}

}
