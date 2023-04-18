/**
 * 
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * @author LUIS ARF
 *
 */
public class Knight extends ChessPiece {

	/**
	 * @param board
	 * @param color
	 */
	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possiblesMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		movement(mat,-2,-1); 	// acima e a esquerda
		movement(mat,-2, 1); 	// acima e a direita
		movement(mat,-1, 2); 	// a direita e acima
		movement(mat, 1, 2); 	// a direita e abaixo 
		movement(mat,-1,-2); 	// a esquerda e acima 
		movement(mat, 1,-2);	// a esquerda e abaixo 
		movement(mat, 2,-1);	// abaixo e a esquerda 
		movement(mat, 2, 1);	// abaixo e a direita

		return mat;
	}
	
	@Override
	public String toString() {
		return "N";
	}
	



	private boolean canMove(Position position) {
		return getBoard().piece(position) == null || isThereOpponentPiece(position) ;
	}
	
	private void movement(boolean [][] mat,int increaseRow,int increaseColumn) {
		Position p = new Position (position.getRow() + increaseRow, position.getColumn() + increaseColumn);
		if ( getBoard().positionExists(p) && canMove(p) )
			mat[p.getRow()][p.getColumn()] = true;
	}

}
