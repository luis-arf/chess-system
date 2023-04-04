package boardgame;

public class Board {
	private int rows;
	private int columns;
	Piece [][] pieces;

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	
	public Piece piece (int row,int column) {
		return pieces[row][column];
	}
	
	public Piece piece (Position pos) {
		return piece(pos.getRow(),pos.getColumn());
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	

}
