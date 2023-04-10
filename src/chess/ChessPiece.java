package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * 
	 * @param position Posicao da peça a ser testata
	 * @return Retorna true se for uma peça adversaria 
	 */
	protected boolean IsThereOpponentPiece(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);
		return piece != null && color != piece.color ;
	}
}
