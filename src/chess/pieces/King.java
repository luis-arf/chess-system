package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possiblesMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		movement(mat,-1, 0); 	// acima
		movement(mat, 1, 0); 	// abaixo
		movement(mat, 0,-1); 	// a esquerda
		movement(mat, 0, 1); 	// a direita
		movement(mat,-1,-1); 	// acima e a esquerda
		movement(mat,-1, 1);	// acima e a direita
		movement(mat, 1,-1);	// abaixo e a esquerda
		movement(mat, 1, 1);	// abaixo e a direita
	
		return mat;
	}

	private boolean canMove(Position position) {
		return getBoard().piece(position) == null || IsThereOpponentPiece(position);
	}

	private void movement(boolean [][] mat,int increaseRow,int increaseColumn) {
		Position p = new Position (position.getRow() + increaseRow, position.getColumn() + increaseColumn);
		if ( getBoard().positionExists(p) && canMove(p) )
			mat[p.getRow()][p.getColumn()] = true;
	}

}

