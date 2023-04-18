package chess.pieces;

import java.util.List;

import boardgame.Board;
import boardgame.Piece;
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
	
	private boolean positionInCheck(Position position,boolean[][] mat) {
		Color color = getColor() == Color.WHITE? Color.BLACK : Color.WHITE;
		List<Piece> pieces = getBoard().getPieces(color);
		
		for( Piece p : pieces ) {
			if( !(p instanceof King) ) {
				if( p.possibleMove(position) )
					return true;
			}
			else {
				Position pKingOpponent = ( (ChessPiece)p).getChessPosition().toPosition();
				Position pos =  new Position (pKingOpponent.getRow() , pKingOpponent.getColumn() );
				
				pos.setValues(pKingOpponent.getRow() -1, pKingOpponent.getColumn()-1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				
				pos.setValues(pKingOpponent.getRow() -1, pKingOpponent.getColumn());
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				
				pos.setValues(pKingOpponent.getRow() -1, pKingOpponent.getColumn()+1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				
				pos.setValues(pKingOpponent.getRow() , pKingOpponent.getColumn()-1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				
				pos.setValues(pKingOpponent.getRow() , pKingOpponent.getColumn()+1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				
				pos.setValues(pKingOpponent.getRow() + 1, pKingOpponent.getColumn()-1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				
				pos.setValues(pKingOpponent.getRow() + 1, pKingOpponent.getColumn());
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				
				pos.setValues(pKingOpponent.getRow() + 1, pKingOpponent.getColumn() + 1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
			}
		}
		
		return false;
	}
	
	
	private boolean canMove(Position position) {
		return getBoard().piece(position) == null || isThereOpponentPiece(position);
		}

	private void movement(boolean [][] mat,int increaseRow,int increaseColumn) {
		Position p = new Position (position.getRow() + increaseRow, position.getColumn() + increaseColumn);
		if ( getBoard().positionExists(p) && canMove(p)  && !positionInCheck(p,mat) )
			mat[p.getRow()][p.getColumn()] = true;
	}

}

