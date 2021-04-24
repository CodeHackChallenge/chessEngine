package main.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import main.manager.PieceManager;

public class Handler {
	
	
	private boolean isDrag;
	private boolean isRestrictedToOneCall;
	
	private int xPrevSelection,
	            yPrevSelection;
	
	private int xCurrentSelection, 
		        yCurrentSelection;
	
	private int xMouse,
			    yMouse,
			    sMouse;
	
	private Rectangle mouseBounds;
	
	private Board board;
	
	public Handler() {}
	
	
	public void render(Graphics g) {
		mouseBounds = new Rectangle(xMouse, yMouse, sMouse, sMouse);
		
		if(board != null)
			board.render(g);
		
		
		g.setColor(Color.BLUE);
		g.drawRect(mouseBounds.x, mouseBounds.y, sMouse, sMouse);
		
	}
	
	public void add(Board board) {
		this.board = board;
	}
	
	private void handleSquareHighlight() {
		
		
		board.chessPiece(xCurrentSelection, yCurrentSelection).setSelectedLoc(xCurrentSelection, yCurrentSelection);
		
		if(board.chessPiece(xPrevSelection, yPrevSelection) != null) {
			if(xCurrentSelection == xPrevSelection && yCurrentSelection ==  yPrevSelection) {
				System.out.println("xCurrentSelection "+xCurrentSelection+" yCurrentSelection "+yCurrentSelection+
						           " xPrevSelection "+xPrevSelection+" yPrevSelection "+yPrevSelection);
				
				//do nothing
			}else {
				board.chessPiece(xPrevSelection, yPrevSelection).switchActiveHighlights(PieceManager.OFF);				
				
				board.chessPiece(xCurrentSelection, yCurrentSelection).pressCount(0);
			}		
			
			
		}else {
			//board.chessPiece(xCurrentSelection, yCurrentSelection).pressCount(0);
		}
		
		
		board.chessPiece(xCurrentSelection, yCurrentSelection).switchActiveHighlights(PieceManager.ON);
		
		board.chessPiece(xCurrentSelection, yCurrentSelection).checkLegalSquares(board);
		
	}
	
	private void handleSelection() {
		
		Board.xPriority = xCurrentSelection;
		Board.yPriority = yCurrentSelection;
		
		
		handleSquareHighlight();
		
				
		
		
		
		
		
		xPrevSelection = xCurrentSelection;		
		yPrevSelection = yCurrentSelection;
	}
	
	public boolean movePieceToAnotherSquare(int xTargetSquare, int yTargetSquare, int xPrevLoc, int yPrevLoc) {

		
		if(PieceManager.playersTurn() != board.chessPiece(xPrevLoc, yPrevLoc).getColorID())
			return false;
		
		board.chessPiece(xPrevSelection, yPrevSelection).switchActiveHighlights(PieceManager.OFF);
		
		
		relocate(xPrevLoc, yPrevLoc , xTargetSquare, yTargetSquare);
		
		
		return true;
	}
	
	public void relocate(int xPrevLoc, int yPrevLoc, int xTargetSquare, int yTargetSquare) {
		
		board.relocate(board.chessPiece(xPrevLoc, yPrevLoc), xTargetSquare, yTargetSquare);
		board.isOccupied(xPrevLoc, yPrevLoc, false);
		board.remove(xPrevLoc, yPrevLoc);
		
		board.nextPlayersMove();
		
		
	}
	
	private void checkLegalSquare(int xSelection, int ySelection) {
		//System.out.println("checkLegalSquare()");
		if(board.chessPiece(xSelection, ySelection) == null)
			return;
		
		for(int i = 0; i < board.chessPiece(xSelection, ySelection).legalSquares().elements(); i++) {
			if(board.chessPiece(xSelection, ySelection).legalSquares().get(i).getComputedBounds().contains(mouseBounds)) {
				
				if(board.chessPiece(xSelection, ySelection).legalSquares().get(i).getY() <= 7 && 
				   board.chessPiece(xSelection, ySelection).legalSquares().get(i).getY() >= 0 &&
				   board.chessPiece(xSelection, ySelection).legalSquares().get(i).getX() <= 7 && 
				   board.chessPiece(xSelection, ySelection).legalSquares().get(i).getX() >= 0) {
						
				
					if(movePieceToAnotherSquare(board.chessPiece(xSelection, ySelection).legalSquares().get(i).getBounds().x, 
							                    board.chessPiece(xSelection, ySelection).legalSquares().get(i).getBounds().y,
							                    xSelection, ySelection)) {
						//System.out.println("checkLegalSquare() HERER ");
						
						break; //exit if the correct square is selected
						
					}//if(getBounds)
					
				}//if(legalSquares)
				
				
				
			}else{//if(contains)
				
				
				board.chessPiece(xSelection, ySelection).setBounds(xSelection * PieceManager.SIZE,	ySelection * PieceManager.SIZE); 
			}
			
			
		}//for(i)
		
	}
	
	private void handleMousePressed() {
		
		if(board.isOccupied(xCurrentSelection, yCurrentSelection)) {			
			//System.out.println("You selected "+board.chessPiece(xCurrentSelecton, yCurrentSelecton).getNotation());
			if(board.chessPiece(xCurrentSelection, yCurrentSelection).getBounds().intersects(mouseBounds)) {
				//System.out.println("You selected "+board.chessPiece(xCurrentSelection, yCurrentSelection).getNotation());
				
				board.chessPiece(xCurrentSelection, yCurrentSelection).setBounds((xMouse - PieceManager.SIZE / 2) + 2,
						                                                       yMouse - PieceManager.SIZE / 2); 
				
				//temporary
				//board.chessPiece(xCurrentSelection, yCurrentSelection).isLegalMoveActive(true);
				
				
				isDrag = true;
				isRestrictedToOneCall = false;
				
				handleSelection();
			}
			
		//not occupied	
		}else {
			
			checkLegalSquare(xPrevSelection, yPrevSelection);
			
			
			if(board.chessPiece(xPrevSelection, yPrevSelection) == null)
				return;
			
			board.chessPiece(xPrevSelection, yPrevSelection).switchActiveHighlights(PieceManager.OFF);
			
			
		}
	}
	
	
	//mouse pressed
	public void mousePressed(MouseEvent e) { 
		
		if(e.getButton() == MouseEvent.BUTTON1) { //System.out.println("mousePressed");
			//System.out.println("xCurrentSelecton "+xCurrentSelecton+" yCurrentSelecton "+yCurrentSelecton);

			xCurrentSelection = xMouse / PieceManager.SIZE;
			yCurrentSelection = yMouse / PieceManager.SIZE;
			
			handleMousePressed();
			
						
			
			
					
		}//MouseEvent.BUTTON1
		
		
		
		
		
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
		
		if(e.getButton() == MouseEvent.BUTTON1) {//System.out.println("mouseReleased ");
			isDrag = false;
			
			if(!isRestrictedToOneCall) {
				isRestrictedToOneCall = true;
				
				checkLegalSquare(xCurrentSelection, yCurrentSelection);				
			}
			
			
			
			//temporary
			//board.chessPiece(xCurrentSelection, yCurrentSelection).isLegalMoveActive(false);
				
		}
		
	}
	//mouse clicked
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1) {//System.out.println("mouseClicked ");
		
			if(board.chessPiece(xCurrentSelection, yCurrentSelection) == null)
				return;
			
			board.chessPiece(xCurrentSelection, yCurrentSelection).pressCount();
		}
		
		
	}             
	
	//mouse moved
	public void mouseMoved(MouseEvent e) {
		xMouse = e.getX() - PieceManager.SIZE / 32;
		yMouse = e.getY();
		sMouse = 4;
		
		mouseBounds = new Rectangle(xMouse, yMouse, sMouse, sMouse);
		
	}
	
	public void mouseDragged(MouseEvent e) {
		xMouse = e.getX() - PieceManager.SIZE / 32;
		yMouse = e.getY();
		sMouse = 4;
		
		mouseBounds = new Rectangle(xMouse, yMouse, sMouse, sMouse);
		
		if(isDrag) {
			board.chessPiece(xCurrentSelection, yCurrentSelection).setBounds((xMouse - PieceManager.SIZE / 2) + 2,
                                                                              yMouse - PieceManager.SIZE / 2); 			
			
			board.chessPiece(xCurrentSelection, yCurrentSelection).pressCount(0);
			
			if(board.chessPiece(xCurrentSelection, yCurrentSelection).isBorderActive()) {
				for(int r = 0; r < PieceManager.CHESS_PIECES; r++) {
					for(int c = 0; c < PieceManager.CHESS_PIECES; c++) {
						if(board.getSquareBounds(c, r).contains(mouseBounds)) {
							board.chessPiece(xCurrentSelection, yCurrentSelection).setBorderLoc(c, r);
						}
						
					}
				}
			}//if(isBorderActive)
			
		}
		
	}
	
}
