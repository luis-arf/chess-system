package boardgame;

public class Board {
	private int rows;
	private int columns;
	Piece [][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1 )
			throw new BoardException("Error creatin Board: there must be a last 1 row and 1 column");
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	
	public Piece piece (int row,int column) {
		if (!positionExists(row, column))
			throw new BoardException("Position not on the board");
		
		return pieces[row][column];
	}
	
	public Piece piece (Position pos) {
		return piece(pos.getRow(),pos.getColumn());
	}

	public void placePiece(Piece piece, Position pos) {
		if (thereIsAPiece(pos))
			throw new BoardException("There is already a piece on position " + pos);
		
		pieces[pos.getRow()][pos.getColumn()] = piece;
		piece.position = pos;
	}
	
	public Piece removePiece(Position pos) {
		if( !positionExists(pos) )
			throw new BoardException("Position not on the board");
		
		if (thereIsAPiece(pos)) {
			Piece p = piece(pos);
			p.position = null;
			pieces[pos.getRow()][pos.getColumn()] = null;
			return p;
		}
		
		return null;
	}
	
	public boolean positionExists(Position pos) {
		return positionExists(pos.getRow(),pos.getColumn());
		
	}
	
	public boolean thereIsAPiece(Position pos) {
		if (!positionExists(pos) )
			throw new BoardException("Position not on the board");
		
		return piece(pos) != null;
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row<= rows && column >=0 && column <= columns; 
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	

}
