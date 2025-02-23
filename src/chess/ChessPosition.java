package chess;

import boardgame.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(int row, char column) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Valores válidos vão de a1 até h8!");
		}
		
		this.row = row;
		this.column = column;
	}
	
	
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	protected Position toPosition() {
		return new Position(8 - this.row, this.column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition(8 - position.getRow(), (char)('a' - 
				position.getColumn()));
	}

}
