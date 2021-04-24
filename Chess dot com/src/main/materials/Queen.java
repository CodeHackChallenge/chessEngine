package main.materials;

import java.awt.image.BufferedImage;

import main.component.Board;
import main.manager.PieceManager;

public class Queen extends PieceManager{
	 
	public Queen(BufferedImage image, String notation, int colorID) {
		
		super(image, notation, colorID);
		
		setName("QUEEN");
		
		setup();
		
	}
	
	public void checkLegalSquares(Board board) {
		
		
		legalMoves(legalSquares().length());
		
		
		
		for(int i = 0; i < legalSquares().length(); i++) {
			if(legalSquares().get(i).getY() <= 7 && legalSquares().get(i).getY() >= 0 &&
			   legalSquares().get(i).getX() <= 7 && legalSquares().get(i).getX() >= 0) {
				
				
				if(board.isOccupied(legalSquares().get(i).getX(), legalSquares().get(i).getY())) {
					
					if(getColorID() != board.chessPiece(legalSquares().get(i).getX(), legalSquares().get(i).getY()).getColorID()) {
						
						//do something
						
					}//if(getColorID)
					
				//not occupied	
				}else{//if(isOccupied)
					
					legalMoves().add(legalSquares().get(i).getX(), legalSquares().get(i).getY());
					
					
				}//if(isOccupied)
				
				
				
			}//if(legalSquares)
			
			
			
			
			
		}//for(i)

		
		
	}


}