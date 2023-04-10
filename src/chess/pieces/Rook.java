package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possiblesMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
	
		Position p = new Position(0,0);
		
		//above (movimentos acima)
		p.setValues(position.getRow()-1, position.getColumn());
		
		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow()-1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && IsThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;
		
		
		// left (Movimentos a esquerda
		p.setValues(position.getRow(), position.getColumn()-1);
		
		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn()-1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && IsThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;
		
		// Right (Movimentos a direita)
		p.setValues(position.getRow(), position.getColumn()+1);
		
		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn()+1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && IsThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;

		//below (movimentos abaixo)
		p.setValues(position.getRow()+1, position.getColumn());
		
		// enquanto a posicao existe e não tem uma peça
		while( getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow()+1);
		}
		
		// testa se existe mais uma posicao e se essa posicao é de uma peca adversaria
		if( getBoard().positionExists(p) && IsThereOpponentPiece(p) )
			mat[p.getRow()][p.getColumn()] = true;
		
		
			
		return mat;
	}

	@Override
	public String toString() {
		return "R";
	}
	
}
