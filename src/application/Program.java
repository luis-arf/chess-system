package application;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ChessConsole.UI;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc= new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
	//	List<ChessPiece> pieces = chessMatch.getPiecesOnTheBoard();
	//	for( ChessPiece p : pieces )
	//		p.print();
		
		 while( !chessMatch.isCheckMate() ) {
		 	
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch);
				System.out.println();
				
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(),chessMatch.possibleMoves(source));
				System.out.println();
			
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				chessMatch.performChessMove(source, target);
			}
			catch( ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch( InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessMatch);
		
	}
}
