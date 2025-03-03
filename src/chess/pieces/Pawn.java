package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private int aux;

	public Pawn(Board board, Color color) {
		super(board, color);
		if (color == Color.BRANCAS) {
			aux = -1;
		} else {
			aux = 1;
		}
	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] posicoes = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position posicao = new Position(0, 0);
		
		if(getMoveCount() == 0) { 
			posicao.setValues(position.getRow() + (2 * aux), position.getColumn());
			Position posicaoAux = new Position(position.getRow() + (1 * aux), position.getColumn());
			if (getBoard().positionExists(posicaoAux) && getBoard().positionExists(posicao) 
					&& !getBoard().thereIsAPiece(posicaoAux) && !getBoard().thereIsAPiece(posicao)) {
				posicoes[posicao.getRow()][posicao.getColumn()] = true;
	        }
		}
		
		posicao.setValues(position.getRow() + aux, position.getColumn());
		if(getBoard().positionExists(posicao) && !getBoard().thereIsAPiece(posicao)) {
			posicoes[posicao.getRow()][posicao.getColumn()] = true;
		}
		
		posicao.setValues(position.getRow() + aux, position.getColumn() - 1);
		if(getBoard().positionExists(posicao) && isThereOpponentPiece(posicao)) {
			posicoes[posicao.getRow()][posicao.getColumn()] = true;
		}
		
		posicao.setValues(position.getRow() + aux, position.getColumn() + 1);
		if(getBoard().positionExists(posicao) && isThereOpponentPiece(posicao)) {
			posicoes[posicao.getRow()][posicao.getColumn()] = true;
		}
		
		return posicoes;
	}

}
