package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private int moveCount;
	

	public ChessPiece(Board board, Color color) {
		super(board,color);
	}

	

	protected void increaseMoveCount() {
		moveCount++;
	}
	
	protected void decreaseMoveCount() {
		moveCount--;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	/**
	 * 
	 * @param position Posicao da peça a ser testata
	 * @return Retorna true se for uma peça adversaria 
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);
		return piece != null && getColor() != piece.getColor() ;
	}
	
	
	public void print() {
		System.out.println("Piece: " + this + ", Position: (" + position + "): Color: " + getColor());
	}
}
