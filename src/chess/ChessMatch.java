package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

/**
 * Representa as Regras de negocio de um jogo de xadrez
 * 
 * @author LUIS ARF
 *
 */
public class ChessMatch {
	private Board board; 	// tabuleiro do jogo
	private int turn;		// turno do jogo
	private Color currentPlayer;	// cor do jogador
	private boolean isCheck;		// indica se o jogo se encontra em xeque
	private boolean isCheckMate;	// indica se a um xeque-mate;
	
	private List<ChessPiece> capturedPieces = new ArrayList<>();	// lista de pecas capturadas
	private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();  // lista de pecas no tabuleiro
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
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

	/**
	 * 
	 * @return retorna o turno atual
	 */
	public int getTurn() {
		return turn;
	}
	
	public boolean isCheck() {
		return isCheck;
	}
	
	public boolean isCheckMate() {
		return isCheckMate;
	}

	/**
	 * 
	 * @return Retorna a cor do jogador atual
	 */
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validadeSourcePosition(source);
		validadeTargetPosition(source, target);
		Piece capturedPiece = makeMove(source,target);
		
		// Verifica se eu coloco meu rei em cheque
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);  // se sim desfaço o movimento
			throw new ChessException("You can´t put yourself in check!");
		}
		
		// Verifica se eu deixo o rei adversario em xeque
		isCheck = testCheck( opponent(currentPlayer) );
		isCheckMate = testCheckMate( opponent(currentPlayer));
		
		if( !isCheckMate)
			nextTurn();
		
		return (ChessPiece) capturedPiece;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		validadeSourcePosition( sourcePosition.toPosition() );
		return board.piece( sourcePosition.toPosition() ).possiblesMoves(); 
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if( capturedPiece != null ) {
			capturedPieces.add((ChessPiece) capturedPiece);
			piecesOnTheBoard.remove(capturedPiece);
		}
		p.increaseMoveCount();
		return capturedPiece;
	}

	
	// desfaz um movimento invalido (exemplo quando se poe seu proprio rei em xeque)
	private void undoMove(Position source, Position target,Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		board.placePiece(p, source);
		
		if( capturedPiece != null ) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}
		p.decreaseMoveCount();
	}

	// retora o rei da cor especificada
	private King king(Color color) {
		List<ChessPiece> list = piecesOnColor(color);
		
		for( ChessPiece p : list )
			if( p instanceof King )
				return (King) p;
		
		throw new IllegalStateException("Nao foi encontrado o rei da cor: " + color);
	}

	// Retorna uma lista de todas as pecas dessa cor que se encontra no tabuleiro
	private List<ChessPiece> piecesOnColor(Color color) {
		return piecesOnTheBoard.stream().filter( x -> x.getColor() == color).collect(Collectors.toList());
	}

	// retorna a cor do oponente
	private Color opponent(Color color) {
		return (color == Color.WHITE)? Color.BLACK : Color.WHITE;
	}

	// testa se o rei da referida cor se encontra em cheque
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); // posicao do rei na matriz
		List<ChessPiece> opponentPieces = piecesOnColor( opponent(color) );  // pecas de cor diferente do rei
		
		for( ChessPiece p : opponentPieces ) { // varre todas as peças do adversario
			boolean [][] mat = p.possiblesMoves(); // retorna todos os movimentos validos da peca especificada
			if( mat[kingPosition.getRow()][kingPosition.getColumn()] )	// se for um movimento valido retorna true
				return true; //e a peca se encontra em xeque
		}
		
		return false;
	}
	
	/** Retorna uma peca que estiver ocasionando o xeque
	 * Se a cor for Color.WHITE Retorna a peca da cor Color.BLACK
	 * se não houver xeque, retorna null 
	 */
	private ChessPiece getPieceInCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); // posicao do rei na matriz
		List<ChessPiece> opponentPieces = piecesOnColor( opponent(color) );  // pecas de cor diferente do rei
		
		for( ChessPiece p : opponentPieces ) { // varre todas as peças do adversario
			boolean [][] mat = p.possiblesMoves(); // retorna todos os movimentos validos da peca especificada
			if( mat[kingPosition.getRow()][kingPosition.getColumn()] ) { // se for um movimento valido retorna true
				return p;
			}
		}
		
		return null;
	}
	
	private boolean testCheckMate(Color color) {
		if( !testCheck(color) )
			return false;
		
		List<ChessPiece> pieces = piecesOnColor(color) ;
		
		for( ChessPiece p : pieces ) {
			boolean mat[][] = p.possiblesMoves();
			
			// percorrendo o tabuleiro
			for(int i=0; i< board.getRows(); i++)
				for(int j=0; j<board.getColumns(); j++) {
					
					if( mat[i][j] ) { // se for uma posicao valida
						Position source = p.getChessPosition().toPosition();
						Position target = new Position(i,j);
						ChessPiece piece = (ChessPiece) makeMove(source, target);
						if( !testCheck( color ) ) {
							undoMove(source, target, piece);
							return false;
						}
						undoMove(source, target, piece);
					}
				}
			
		}
		
		return true;
		
		
	}
	private void printTable(String msg,ChessPiece p) {
		if( p == null ) {
			System.out.println(" p == null");
			return;
		}
		boolean mat[][] = p.possiblesMoves();
		System.out.print(msg + ": ");
		for(int i=0; i<mat.length; i++)
			for( int j=0; j<mat[i].length; j++)
				if(mat[i][j])
					System.out.print("[" + i + "," + j + "]" + mat[i][j] );
		
		System.out.println();
	}

	/**
	 * Valida se a peça de origem existe e se existir verifica se ela tem movimentos possiveis
	 * verifica tambem se a peca e da cor do jogador
	 * @param source Posicao da peca de origem
	 */
	private void validadeSourcePosition(Position source) {
		if( !board.thereIsAPiece(source) )
			throw new ChessException("There is no piece on source position");
		if( currentPlayer != ((ChessPiece) board.piece(source)).getColor() )
			throw new ChessException("The chosen piece is not yours");
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

	/**
	 * Adiciona uma nova peca no tabuleiro
	 * @param column
	 * @param row
	 * @param piece
	 */
	private void placeNewPiece(char column,int row,ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board,Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board,Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board,Color.WHITE));
		placeNewPiece('e', 1 , new King(board, Color.WHITE));
	
		placeNewPiece('e', 5, new Bishop(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

		placeNewPiece('a', 8, new Rook(board,Color.BLACK));
		placeNewPiece('h', 8, new Rook(board,Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board,Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board,Color.BLACK));
		placeNewPiece('e', 8, new King (board,Color.BLACK));

	
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK));

	}

	public List<ChessPiece> getCapturedPieces() {
		return capturedPieces;
	}

	public List<ChessPiece> getPiecesOnTheBoard(){
		return piecesOnTheBoard;
	}
}
