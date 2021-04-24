package main.util;

import main.manager.PieceManager;

public class Container extends PieceManager {
	
	
	
	
	private int length;
	private int element;
	
	
	private PieceManager[][] container2D;
	
	
	public Container(int col, int row) {
		length = col * row;
		
		container2D = new PieceManager[col][row];
		
	}
	
	public void add(PieceManager piece, int xLoc, int yLoc) {
		element++;
		
		container2D[xLoc][yLoc] = piece;
		
	}
	
	public void remove(int xIndex, int yIndex) {
		container2D[xIndex][yIndex] = null;
		element--;
		
	}
	
	
	public PieceManager get(int col, int row) {
		return container2D[col][row];
	}
	
	

}
