package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void printBoard(ChessPiece[][] pieces) {

		for (int i = 0; i < pieces.length; i++) {

			System.out.print((8 - i) + "  ");
			for (int j = 0; j < pieces[i].length; j++) {
				printPiece(pieces[i][j], false);
			}

			System.out.println();
		}
		System.out.println();
		System.out.println("   a b c d e f g h");

	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {

		for (int i = 0; i < pieces.length; i++) {

			System.out.print((8 - i) + "  ");
			for (int j = 0; j < pieces[i].length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}

			System.out.println();
		}
		System.out.println();
		System.out.println("   a b c d e f g h");

	}

	private static void printPiece(ChessPiece piece, boolean background) {
		
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.BRANCAS) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println("Turno: " + chessMatch.getTurn());
		System.out.println("Esperando o jogador das peças " + chessMatch.getCurrentPlayer());
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String position = sc.nextLine();
			char column = position.toLowerCase().charAt(0);
			int row = Integer.parseInt(position.substring(1));

			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo posição de xadrez! Valores válidos: " + "a1 até h8");
		}
		
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured) {
		
		List<ChessPiece> white = captured.stream().filter(piece -> piece.getColor() == 
				Color.BRANCAS).collect(Collectors.toList());
		
		List<ChessPiece> black = captured.stream().filter(piece -> piece.getColor() == 
				Color.PRETAS).collect(Collectors.toList());
		
		System.out.println("Peças capturadas: ");
		
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		for (ChessPiece chessPiece : white) {
			System.out.print(chessPiece + " ");
		}
		System.out.println();
		System.out.print(ANSI_RESET);
		
		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		for (ChessPiece chessPiece : black) {
			System.out.print(chessPiece + " ");
		}
		System.out.println();
		System.out.print(ANSI_RESET);
		
		System.out.println();
	}
}
