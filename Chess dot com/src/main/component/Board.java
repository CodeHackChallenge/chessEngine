package main.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import main.manager.PieceManager;
import main.util.Container;

public class Board extends PieceManager{
	
	
	public static int xPriority = 8,
			          yPriority = 8; 
	
	private boolean[][] isOccupied;
	
	
	
	private BufferedImage[] theme;
	
	private Container chessPieces;
	
	
	public Board() {
		
		setup();
		
		chessPieces = new Container(CHESS_PIECES, CHESS_PIECES);
		isOccupied = new boolean[CHESS_PIECES][CHESS_PIECES];
		
	}
	
	public void setTheme(BufferedImage[] theme) {
		this.theme = theme;
		
	}
	
	public void render(Graphics g) {
		//board		
		g.drawImage(theme[0], 0, 0, null);
		
		//highlights
		moveHighlight(g);
			
		
		//rank and file
		g.drawImage(theme[1], 0, 0, null);
		
		for(int r = 0; r < CHESS_PIECES; r++) {
			for(int c = 0; c < CHESS_PIECES; c++) {
				
				if(isOccupied[c][r]) {
					
					if(c == xPriority && r == yPriority)
						continue;
						
					chessPieces.get(c, r).render(g);
					
				}//if(isOccupied)
				
				//g.setColor(Color.RED);
				//g.drawRect(getSquareBounds(c, r).x, getSquareBounds(c, r).y, SIZE, SIZE);
			}//for(c)
			
		
		}//for(r)
		
		
		if(xPriority < 8 && yPriority < 8) {
			if(chessPieces.get(xPriority, yPriority) != null) {
				chessPieces.get(xPriority, yPriority).render(g);
			}
			
		}
	}
	
	private void moveHighlight(Graphics g) {
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int r = 0; r < CHESS_PIECES; r++) {
			for(int c = 0; c < CHESS_PIECES; c++) {
				
				if(isOccupied[c][r]) {
					if(chessPieces.get(c, r).isLegalMoveActive()) {
						
						
						for(int i = 0; i < chessPieces.get(c, r).legalMoves().elements(); i++) {
							
							if((chessPieces.get(c, r).legalMoves().get(i).getX() + chessPieces.get(c, r).legalMoves().get(i).getY()) % 2 == 0  )								
								g.setColor(new Color(213, 213, 188, 180));
							else if((chessPieces.get(c, r).legalMoves().get(i).getX() + chessPieces.get(c, r).legalMoves().get(i).getY()) % 2 == 1  )	
								g.setColor(new Color(106, 134, 77, 180));
							
							g.fillOval(chessPieces.get(c, r).legalMoves().get(i).getX() * SIZE + (SIZE / 3) / 2 + (SIZE / 3) / 2, 
									   chessPieces.get(c, r).legalMoves().get(i).getY() * SIZE + (SIZE / 3) / 2 + (SIZE / 3) / 2, 
									   SIZE / 3, SIZE / 3);
							
						}//for(i)
						
						
					}//if(isLegalMoveActive)
					
					
					
				}//if(isOccupied)
				
				
				
			}//for(c)
		}//for(r)
		
	}
	
	
	public void add(PieceManager piece, String notation) {
		
		int xSquare = convertToFile(notation);
		int ySquare = convertToRank(notation);
		
		//System.out.println("add() xSquare "+xSquare+" ySquare "+ySquare);
		
		//System.out.println(xSquare+""+ySquare);
		
		piece.setPieceLocation(getSquareBounds(xSquare, ySquare).x, getSquareBounds(xSquare, ySquare).y);
		piece.creatBounds();
		
		piece.createLegalSquares(piece.getName());
		
		
		
		isOccupied[xSquare][ySquare] = true;
		chessPieces.add(piece, xSquare, ySquare);
		
		
		
		
	}
	
	public void relocate(PieceManager piece, int xTargetLoc, int yTargetLoc) {
		
	

		algebraic(piece.getName(), xTargetLoc, yTargetLoc);
		
		add(piece, algebraic());
		
		
				
		
	}
	
	public void nextPlayersMove() {
		/*
		if(PieceManager.playersTurn() == WHITE)
			PieceManager.playersTurn(BLACK);
		else
			PieceManager.playersTurn(WHITE);
		*/
		
		System.out.println("Player "+PieceManager.playersTurn());
		int turn = (PieceManager.playersTurn() == WHITE) ? BLACK : WHITE;
		
		PieceManager.playersTurn(turn);
		
		System.out.println("Next Player "+PieceManager.playersTurn());
	}
	
	public void remove(int xPrevLoc, int yPrevLoc) {
	
		chessPieces.remove(xPrevLoc, yPrevLoc);
	}
	

	
	public boolean isOccupied(int xSquare, int ySquare) {
		return isOccupied[xSquare][ySquare];
	}
	
	public boolean isOccupied(int xSquare, int ySquare, boolean status) {
		return isOccupied[xSquare][ySquare] = status;
	}

	public PieceManager chessPiece(int xSquare, int ySquare) {
		return chessPieces.get(xSquare, ySquare);
		
	}
}
