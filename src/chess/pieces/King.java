package chess.pieces;

import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	private ChessMatch match;

	
	public King(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
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

		// ---------------> movimento especial #Roque

		// verifica se o roque do lado do rei é valido
		Position posT1 = new Position(position.getRow(), position.getColumn()+3);
		Position p1 = new Position(position.getRow(), position.getColumn()+1);
		Position p2 = new Position(position.getRow(), position.getColumn()+2);
		if( getMoveCount() == 0 && testRookCastling(posT1) && !match.isCheck() ){
			if( getBoard().piece(p1) == null & getBoard().piece(p2) == null ) {
				if( !positionInCheck(p1, mat) && !positionInCheck(p2, mat) ) {
					mat[position.getRow()][position.getColumn()+2] = true;
				}
			}
		}
		
		// verifica se o roque do lado da rainha é valido
		Position posT2 = new Position(position.getRow(), position.getColumn()-4);
		Position p3 = new Position(position.getRow(), position.getColumn()-1);
		Position p4 = new Position(position.getRow(), position.getColumn()-2);
		Position p5 = new Position(position.getRow(), position.getColumn()-3);
		if( getMoveCount() == 0 && testRookCastling(posT2) && !match.isCheck() ){
			if( getBoard().piece(p3) == null & getBoard().piece(p4) == null && getBoard().piece(p5) == null) {
				if( !positionInCheck(p3, mat) && !positionInCheck(p4, mat) && !positionInCheck(p5, mat)) {
					mat[position.getRow()][position.getColumn()-2] = true;
				}
			}
		}

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
					//return true;
				
				pos.setValues(pKingOpponent.getRow() -1, pKingOpponent.getColumn());
				if ( getBoard().positionExists(pos) && canMove(pos)  )
					mat[pos.getRow()][pos.getColumn()] = false;
					//return true;
				
				pos.setValues(pKingOpponent.getRow() -1, pKingOpponent.getColumn()+1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
					mat[pos.getRow()][pos.getColumn()] = false;
					//return true;
				
				pos.setValues(pKingOpponent.getRow() , pKingOpponent.getColumn()-1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
					mat[pos.getRow()][pos.getColumn()] = false;
					//return true;
				
				pos.setValues(pKingOpponent.getRow() , pKingOpponent.getColumn()+1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				//	return true;
				
				pos.setValues(pKingOpponent.getRow() + 1, pKingOpponent.getColumn()-1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				//	return true;
				
				pos.setValues(pKingOpponent.getRow() + 1, pKingOpponent.getColumn());
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				//	return true;
				
				pos.setValues(pKingOpponent.getRow() + 1, pKingOpponent.getColumn() + 1);
				if ( getBoard().positionExists(pos) && canMove(pos)  )
						mat[pos.getRow()][pos.getColumn()] = false;
				//	return true;
			}
		}
		
		return false;
	}
	
	// testa se a torre na posicao especificada é valido para fazer o roque
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);

		//testa se existe uma peca, se existe se é uma torre
		// se for uma torre, é da mesma cor do rei e se nunca foi movida
		return p!=null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
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

