package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private int aux;
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
		
		//en passant
		if ((getColor() == Color.BRANCAS && position.getRow() == 3) || 
				(getColor() == Color.PRETAS && position.getRow() == 4)) {
			
			Position left = new Position(position.getRow(), position.getColumn() - 1);
			if(getBoard().positionExists(left) && isThereOpponentPiece(left)
					&& getBoard().piecePosition(left) == chessMatch.getEnPassantVulnerable()) {
				posicoes[left.getRow() + aux][left.getColumn()] = true;
			}
			Position right = new Position(position.getRow(), position.getColumn() + 1);;
			if(getBoard().positionExists(right) && isThereOpponentPiece(right)
					&& getBoard().piecePosition(right) == chessMatch.getEnPassantVulnerable()) {
				posicoes[right.getRow() + aux][right.getColumn()] = true;
			}
		}
		
		return posicoes;
	}

}
