/**
 * 
 */
package chess.pieces;

import javax.swing.JOptionPane;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * @author LUIS ARF
 *
 */
public class Bishop extends ChessPiece {

	/**
	 * @param board
	 * @param color
	 */
	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possiblesMoves() {
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		// ---------------------------> a esquerda e acima
		p.setValues(position.getRow()-1, position.getColumn()-1);
		
		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()-1, p.getColumn()-1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && isThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;
	
		
		// ---------------------------> a esquerda e abaixo
		p.setValues(position.getRow()+1, position.getColumn()-1);

		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()+1, p.getColumn()-1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && isThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;
				
		// ------------------------> a direita e acima
		p.setValues(position.getRow()-1, position.getColumn()+1);

		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()-1, p.getColumn()+1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && isThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;
		
		
		// -------------------> a direita e abaixo
		p.setValues(position.getRow()+1, position.getColumn()+1);
		
		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setValues(p.getRow()+1, p.getColumn()+1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && isThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;
		
		return mat;
	}


	@Override 
	public String toString() {
		return "B";
	}
}
