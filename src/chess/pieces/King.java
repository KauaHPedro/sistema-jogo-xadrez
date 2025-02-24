package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] posicoes = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		//Cima, baixo, esquerda, direita e diagonais
		int[][] direcoes = { {-1, 0}, {1, 0}, {0, -1}, {0, 1},
				{-1, 1}, {-1, -1}, {1, 1}, {1, -1}
		};
		
		for(int[] direcao : direcoes) {
			Position posicao = new Position(position.getRow(), position.getColumn());
			posicao.setValues(posicao.getRow() + direcao[0], posicao.getColumn() + direcao[1]);
			
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
