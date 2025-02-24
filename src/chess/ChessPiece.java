package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	protected boolean isThereOpponentPiece(Position position) {
		if (!getBoard().thereIsAPiece(position)) {
			throw new ChessException("Não existe peça nessa posição!");
		}
		
		ChessPiece piece = (ChessPiece) getBoard().piecePosition(position);
		
		return getColor() != piece.color;

	}

}
