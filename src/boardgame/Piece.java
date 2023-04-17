package boardgame;

import chess.Color;

/**
 * Representa uma Pe�a em um jogo de tabuleiro
 * @author LUIS ARF
 *
 */
public abstract class Piece {
	
	protected Position position;	// Posicao a que se refere a peca no tabuleiro
	private Board board;			// Tabuleiro onde se encontra a peca
	private Color color;
	
	public Piece(Board board,Color color) {
		this.board = board;
		this.color = color;
	}

	/**
	 * 
	 * @return Retorna o tabuleiro que se encontra a pe�a
	 */
	public Board getBoard() {
		return board;
	}
	
	public Color getColor() {
		return color;
	}
	
	/**
	 * 
	 * @return 	Retorna uma matriz onde cada elemento representa se a pe�a pode se mover pra 
	 * 			essa coordenada
	 */
	public abstract boolean [][] possiblesMoves();

	/**
	 * 
	 * @param position Posicao a ser verificada
	 * @return Retorna true se a pe�a pode ser movida pra essa posi��o
	 */
	public boolean possibleMove(Position position) {
		boolean [][] mat = possiblesMoves();
		return mat[position.getRow()][position.getColumn()];	
	}
	
	/**
	 * 
	 * @return Retorna true se existe ao menos uma posicao onde essa peca pode ser movida
	 */
	public boolean isThereAnyPossibleMove() {
		boolean [][] mat = possiblesMoves();
		
		for(boolean []i : mat)
			for(boolean j : i)
				if( j )
					return true;
		
		return false;
	}

}
