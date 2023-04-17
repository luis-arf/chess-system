package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {


	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possiblesMoves() {
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		// Peao Branco
		if( getColor() == Color.WHITE ) {
		
			// testa o movimento acima
			p.setValues(position.getRow()-1,position.getColumn() );

			if( getBoard().positionExists(p)  && !getBoard().thereIsAPiece(p) )
				mat[p.getRow()][p.getColumn()] = true;

			// se for o primeiro movimento do peao verifica se ele pode andar duas casas
			if( getMoveCount() == 0 ) {
				
				Position p2 = new Position(position.getRow()-1,position.getColumn());
				p.setRow(p.getRow()-1);

				if ( !getBoard().thereIsAPiece(p)  && getBoard().positionExists(p2)  && !getBoard().thereIsAPiece(p2))
					mat[p.getRow()][p.getColumn()] = true;
			}

			// verifica se as casas acima na diagonal tem peca que pode ser capturada

			p.setValues(position.getRow()-1,position.getColumn()-1 ); // diagonal acima e a esquerda
			if( getBoard().positionExists(p) && isThereOpponentPiece( p ) )
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow()-1, position.getColumn()+1); // diagonal acima e a direita
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) )
				mat[p.getRow()][p.getColumn()] = true;
		}
		else {
			// testa o movimento abaixo
			p.setValues(position.getRow()+1,position.getColumn() );

			if( getBoard().positionExists(p)  && !getBoard().thereIsAPiece(p) )
				mat[p.getRow()][p.getColumn()] = true;

			// se for o primeiro movimento do peao verifica se ele pode andar duas casas
			if( getMoveCount() == 0 ) {

				Position p2 = new Position(position.getRow()+1,position.getColumn());
				p.setRow(p.getRow()+1);
				if ( !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)  && !getBoard().thereIsAPiece(p2) )
					mat[p.getRow()][p.getColumn()] = true;
			}

			// verifica se as casas acima na diagonal tem peca que pode ser capturada

			p.setValues(position.getRow()+1,position.getColumn()-1 ); // diagonal abaixo e a esquerda
			if( getBoard().positionExists(p) && isThereOpponentPiece( p ) )
				mat[p.getRow()][p.getColumn()] = true;

			p.setValues(position.getRow()+1, position.getColumn()+1); // diagonal abaixo e a direita
			if( getBoard().positionExists(p) && isThereOpponentPiece(p) )
				mat[p.getRow()][p.getColumn()] = true;			
		}
		
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}
