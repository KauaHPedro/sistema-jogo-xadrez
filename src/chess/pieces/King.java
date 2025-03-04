package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "♔";
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
		
		//SpecialMove Castling (Roque)
		//roque menor
		if(getMoveCount() == 0 && !chessMatch.isCheck()) {
			Position posRook1 = new Position (position.getRow(), position.getColumn() + 3);
			
			if (testRookCastling(posRook1)) {
				Position right1 = new Position(position.getRow(), position.getColumn() + 1);
				Position right2 = new Position(position.getRow(), position.getColumn() + 2);
				
				if (!getBoard().thereIsAPiece(right1) && !getBoard().thereIsAPiece(right2)) {
					posicoes[right2.getRow()][right2.getColumn()] = true;
				}
			}
			
			//roque grande
			Position posRook2 = new Position (position.getRow(), position.getColumn() - 4);
			if(testRookCastling(posRook2)) {
				Position left1 = new Position(position.getRow(), position.getColumn() - 1);
				Position left2 = new Position(position.getRow(), position.getColumn() - 2);
				Position left3 = new Position(position.getRow(), position.getColumn() - 3);
				
				if(!getBoard().thereIsAPiece(left1) && !getBoard().thereIsAPiece(left2) &&
						!getBoard().thereIsAPiece(left3)) {
					posicoes[left2.getRow()][left2.getColumn()] = true;
				}
			}
		}
		
		return posicoes;
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piecePosition(position);
		return piece != null && piece instanceof Rook && 
				piece.getMoveCount() == 0 && piece.getColor() == getColor();
	}

}
