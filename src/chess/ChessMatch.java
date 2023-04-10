package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * Representa as Regras de negocio de um jogo de xadrez
 * 
 * @author LUIS ARF
 *
 */
public class ChessMatch {
	private Board board; // tabuleiro do jogo
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	/**
	 *
	 * @return Retorna todas as peças de um jogo. 
	 */
	public ChessPiece[][] getPieces(){
		ChessPiece[][]mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i =0; i<mat.length; i++)
			for(int j=0; j<mat[i].length; j++)
				mat[i][j] = (ChessPiece) board.piece(i,j);
			
		return mat;
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validadeSourcePosition(source);
		validadeTargetPosition(source, target);
		return (ChessPiece) makeMove(source,target);
		
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}

	/**
	 * Valida se a peça de origem existe e se existir verifica se ela tem movimentos possiveis
	 * @param source Posicao da peca de origem
	 */
	private void validadeSourcePosition(Position source) {
		if( !board.thereIsAPiece(source) )
			throw new ChessException("There is no piece on source position");
		if( !board.piece(source).isThereAnyPossibleMove() )
			throw new ChessException("There is no possibles moves for the chosen piece");
	}
	
	/**
	 * 
	 * Valida se a peça de origem pode ser movida para o destino

	 * @param source Origem da peça
	 * @param target Destino da peca
	 */
	private void validadeTargetPosition (Position source, Position target) {
		if( !board.piece(source).possibleMove(target) )
			throw new ChessException("The chosen piece can´t move to target position");
		
	}

	private void placeNewPiece(char column,int row,ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
