package application;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		while(!chessMatch.isCheckMate() && !chessMatch.isStalemate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Destino: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted() != null) {
					System.out.print("Digite a peça para ser promovida (B/N/R/Q): ");
					String promotedPiece = sc.nextLine().toLowerCase();
					
					while((!promotedPiece.equalsIgnoreCase("b") && !promotedPiece.equalsIgnoreCase("n")
				&& !promotedPiece.equalsIgnoreCase("r") && !promotedPiece.equalsIgnoreCase("q"))) {
						System.out.print("Valor inválido! Digite novamente "
								+ "a peça para ser promovida (B/N/R/Q): ");
						promotedPiece = sc.nextLine().toLowerCase();
					}
					
					chessMatch.replacePromotedPiece(promotedPiece);
				}
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e){
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InvalidParameterException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
		sc.close();
		
	}

}
