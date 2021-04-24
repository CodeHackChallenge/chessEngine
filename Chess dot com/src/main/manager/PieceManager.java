package main.manager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import main.component.Board;
import main.util.Parameter;

public class PieceManager {
	
	
	public static final int CHESS_PIECES = 8;
	
	public static final int ON = 20,
	                        OFF = 21;
			
	public static final int WHITE = 1,
			                BLACK = 0;
	
	public static final int SIZE = 91;
	
	
	
	
	private static int playersTurn = WHITE;
	
	private int xSelectedSquare, ySelectedSquare; 
	
	private int pressCount;
	
	private int x, y;
	
	private int colorID;
	
	private int xOldSquare, yOldSquare,
	            xNewSquare, yNewSquare,
	            xBorderLoc, yBorderLoc;
	
	
	private boolean isLegalMoveActive;	
	private boolean isSelectedActive;
	
	private boolean isOldSquareActive,
	                isNewSquareActive,
	                isBorderActive;
	                
	private String notation;
	private String name;
	private String algebraic;
	
	private Rectangle[][] squareBounds;
	private Rectangle bounds;
	
	
	
	private BufferedImage image;
	
	
	protected Parameter legalSquares;
	protected Parameter legalMoves;
	
	
	public PieceManager() {}
	
	public PieceManager(BufferedImage image, String notation, int colorID) {
		setImage(image);
		setNotation(notation);
		setColorID(colorID);
		
	}
	
	protected void setup() {
		
		squareBounds = new Rectangle[CHESS_PIECES][CHESS_PIECES];
		for(int r = 0; r < CHESS_PIECES; r++)
			for(int c = 0; c < CHESS_PIECES; c++)				
				setSquareBounds(c, r, new Rectangle(c * SIZE, r * SIZE, SIZE, SIZE)); 
		
	}
	
	
	public void render(Graphics g) {		
		
		moveHighlight(g);		
		
		g.drawImage(image, getBounds().x, getBounds().y, null);
	}
	
	private void moveHighlight(Graphics g) {
		//initial selection 
		if(isSelectedActive()) {
			g.setColor(new Color(255, 255, 0, 120));
			g.fillRect(getSquareBounds(getxSelectedSquare(),  getySelectedSquare()).x, getSquareBounds(getxSelectedSquare(),  getySelectedSquare()).y, SIZE, SIZE);
		}	
		//border
		if(isBorderActive()) {
			g.setColor(new Color(255, 255, 255, 190));
			drawBorder(g, getSquareBounds(getxBorderLoc(),  getyBorderLoc()).x + 2, getSquareBounds(getxBorderLoc(),  getyBorderLoc()).y + 2, SIZE - 5, SIZE - 5, 4);
			
		}
		
	}
	
	private void drawBorder(Graphics g, int x, int y, int w, int h, double t) {
		
		Graphics2D g2D = (Graphics2D)g;
		Stroke old = g2D.getStroke();
		g2D.setStroke(new BasicStroke((float) t));
		g2D.drawRect(x, y, w, h);
		g2D.setStroke(old);
		
	}

	public int convertToFile(String notation) {
		
		
		String modified = notation;
		int xSquare = 8;
		
		if(notation.length() == 3)
			modified = notation.substring(1);
		
		if(modified.substring(0, 1).equals("a")) xSquare = 0;
		if(modified.substring(0, 1).equals("b")) xSquare = 1;
		if(modified.substring(0, 1).equals("c")) xSquare = 2;
		if(modified.substring(0, 1).equals("d")) xSquare = 3;
		if(modified.substring(0, 1).equals("e")) xSquare = 4;
		if(modified.substring(0, 1).equals("f")) xSquare = 5;
		if(modified.substring(0, 1).equals("g")) xSquare = 6;
		if(modified.substring(0, 1).equals("h")) xSquare = 7;
		
		return xSquare;
		
	}
	
	public int convertToRank(String notation) {
		
		
		String modified = notation;
		int ySquare = 0;
		
		if(notation.length() == 3) {
			modified = notation.substring(2);
		
			if(modified.equals("1")) ySquare = CHESS_PIECES - 1;
			if(modified.equals("2")) ySquare = CHESS_PIECES - 2;
			if(modified.equals("3")) ySquare = CHESS_PIECES - 3;
			if(modified.equals("4")) ySquare = CHESS_PIECES - 4;
			if(modified.equals("5")) ySquare = CHESS_PIECES - 5;
			if(modified.equals("6")) ySquare = CHESS_PIECES - 6;
			if(modified.equals("7")) ySquare = CHESS_PIECES - 7;
			if(modified.equals("8")) ySquare = CHESS_PIECES - 8;
			
		}else {
			
			if(modified.substring(1, 2).equals("1")) ySquare = CHESS_PIECES - 1;
			if(modified.substring(1, 2).equals("2")) ySquare = CHESS_PIECES - 2;
			if(modified.substring(1, 2).equals("3")) ySquare = CHESS_PIECES - 3;
			if(modified.substring(1, 2).equals("4")) ySquare = CHESS_PIECES - 4;
			if(modified.substring(1, 2).equals("5")) ySquare = CHESS_PIECES - 5;
			if(modified.substring(1, 2).equals("6")) ySquare = CHESS_PIECES - 6;
			if(modified.substring(1, 2).equals("7")) ySquare = CHESS_PIECES - 7;
			if(modified.substring(1, 2).equals("8")) ySquare = CHESS_PIECES - 8;
			
		}
		
		
		return ySquare;	
		
	}
	
	public String algebraic(String name, int xCurrentLoc, int yCurrentLoc) {
		
		String file ="", 
			   piece ="";
		
		int rank = CHESS_PIECES - yCurrentLoc;
		
		if(xCurrentLoc == 0) file = "a";
		if(xCurrentLoc == 1) file = "b";
		if(xCurrentLoc == 2) file = "c";
		if(xCurrentLoc == 3) file = "d";
		if(xCurrentLoc == 4) file = "e";
		if(xCurrentLoc == 5) file = "f";
		if(xCurrentLoc == 6) file = "g";
		if(xCurrentLoc == 7) file = "h";
		
		
		if(name.equals("PAWN")) piece = "";
		if(name.equals("ROOK")) piece = "R";
		if(name.equals("KNIGHT")) piece = "N";
		if(name.equals("BISHOP")) piece = "B";
		if(name.equals("QUEEN")) piece = "Q";
		if(name.equals("KING")) piece = "K"; 
		
		algebraic = piece+file+Integer.toString(rank);
		
		return algebraic;
		
	}
	
	public String algebraic() {
		return algebraic;
	}
	
	public void checkLegalSquares(Board board) {}

	public void createLegalSquares(String piece) {
		
		switch(piece) {
		
			case "PAWN":
				pawnLegalSquare();
				
				break;
				
			case "ROOK":
				rookLegalSquare();
				
				break;
				
			case "KNIGHT":
				knightLegalSquare();
				
				break;
				
			case "BISHOP":
				bishopLegalSquare();
				
				break;
				
			case "QUEEN":
				queenLegalSquare();
				
				break;
				
			case "KING":
				kingLegalSquare();
				
				break;
				
		}
	}
	
	
	protected void kingLegalSquare() {
		legalSquares = new Parameter();
		//north[0]
		legalSquares.add((getBounds().x / SIZE), (getBounds().y / SIZE) - 1);
		//north-east[1]
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE) - 1);
		//east[2]
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE));
		//south-east[3]
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE) + 1);
		//south[4]
		legalSquares.add((getBounds().x / SIZE), (getBounds().y / SIZE) + 1);
		//south-west[5]
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE) + 1);
		//west[6]
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE));
		//north-west[7]
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE) - 1);
		
		//long castle[8]
		legalSquares.add((getBounds().x / SIZE) - 2, (getBounds().y / SIZE));
		//short castle[9]
		legalSquares.add((getBounds().x / SIZE) + 2, (getBounds().y / SIZE));
				
					
	}

	protected void queenLegalSquare() {
		legalSquares = new Parameter(8 * 7);
		
		int n = 1, nn = 1, n3 = 1, e = 1, ee = 1, e3 = 1, s = 1, ss = 1, s3 = 1, w = 1, ww = 1, w3= 1;
		for(int i = 0; i < legalSquares.length(); i++) {
			
			//north-east
			if(i < 7) {
				legalSquares.add((getBounds().x / SIZE), (getBounds().y / SIZE) - n);
				n++;
								
			}//if(i < 7)
			//north-east
			if(i > 6 && i < 14) {
				legalSquares.add((getBounds().x / SIZE) + e, (getBounds().y / SIZE) - nn);
				e++;
				nn++;
				
			}//if(i > 6 && i < 14)
			//east
			if(i > 13 && i < 21) {
				legalSquares.add((getBounds().x / SIZE) + ee, (getBounds().y / SIZE));
				ee++;
				
			}//if(i > 13 && i < 21)
			//south-east
			if(i > 20 && i < 28) {
				legalSquares.add((getBounds().x / SIZE) + e3, (getBounds().y / SIZE) + s);
				e3++;
				s++;
				
			}//if(i > 20 && i < 27)
			//south
			if(i > 27 && i < 35) {
				legalSquares.add((getBounds().x / SIZE), (getBounds().y / SIZE) + ss);
				ss++;
				
			}//if(i > 27 && i < 35)
			//south-west
			if(i > 34 && i < 42) {
				legalSquares.add((getBounds().x / SIZE) - w, (getBounds().y / SIZE) + s3);
				w++;
				s3++;				
				
			}//if(i > 34 && i < 42)
			//west
			if(i > 41 && i < 49) {
				legalSquares.add((getBounds().x / SIZE) - ww, (getBounds().y / SIZE));
				ww++;
				
			}//if(i > 41 && i < 49)
			//north-west
			if(i > 48 && i < 56) {
				legalSquares.add((getBounds().x / SIZE) - w3, (getBounds().y / SIZE) - n3);
				w3++;
				n3++;
				
			}//if(i > 48 && i < 56)
			
			
			
		}
		
		
	}

	protected void bishopLegalSquare() { 
		legalSquares = new Parameter(7 * 4);
		
		int n = 1, nn = 1, e = 1, ee = 1, s = 1, w = 1, ww = 1, ss = 1;
		for(int i = 0; i < legalSquares.length(); i++) {
			//north-east
			if(i < 7) {
				legalSquares.add((getBounds().x / SIZE) + e, (getBounds().y / SIZE) - n);
				e++;
				n++;	 
								
			}//if(i < 7)
			//south-east
			if(i > 6 && i < 14) {
				legalSquares.add((getBounds().x / SIZE) + ee, (getBounds().y / SIZE) + s);
				ee++;
				s++;
				
			}//if(i > 6 && i < 14)
			//south-west
			if(i > 13 && i < 21) {
				legalSquares.add((getBounds().x / SIZE) - w, (getBounds().y / SIZE) + ss);
				w++;
				ss++;
				
			}//if(i > 13 && i < 21)
			//north-west			
			if(i > 20 && i < 28) {
				legalSquares.add((getBounds().x / SIZE) - ww, (getBounds().y / SIZE) - nn);
				ww++;
				nn++;
				
			}//if(i > 20 && i < 28)
			
			
			
		}
		
	}

	protected void knightLegalSquare() {
		legalSquares = new Parameter(8);
		//down-left
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE) + 2);
		//left-down
		legalSquares.add((getBounds().x / SIZE) - 2, (getBounds().y / SIZE) + 1);
		//left-up
		legalSquares.add((getBounds().x / SIZE) - 2, (getBounds().y / SIZE) - 1);
		//up-left
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE) - 2);
		//up-right
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE) - 2);
		//right-up
		legalSquares.add((getBounds().x / SIZE) + 2, (getBounds().y / SIZE) - 1);
		//right-down
		legalSquares.add((getBounds().x / SIZE) + 2, (getBounds().y / SIZE) + 1);														
		//down-right
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE) + 2);
																
		
	}

	protected void rookLegalSquare() {
		legalSquares = new Parameter(7 * 4);
		
		int n = 1, s = 1, e = 1, w = 1;
		for(int i = 0; i < legalSquares.length(); i++) {
			//north
			if(i < 7) {
				legalSquares.add(getBounds().x / SIZE, (getBounds().y / SIZE) - n);
				n++;
								
			}//if(i < 7)
			//south
			if(i > 6 && i < 14) {
				legalSquares.add(getBounds().x / SIZE, (getBounds().y / SIZE) + s);
				s++;
				
			}//if(i > 6 && i < 14) {
			//east
			if(i > 13 && i < 21) {
				legalSquares.add((getBounds().x / SIZE) + e, (getBounds().y / SIZE));
				e++;
				
 			}//if(i > 13 && i < 28)
			//west
			if(i > 20 && i < 28) {
				legalSquares.add((getBounds().x / SIZE) - w, (getBounds().y / SIZE));
				w++;
				
			}//if(i > 20 && i < 28) 
			
		}
		
		
	}

	protected void pawnLegalSquare() {
		// TODO Auto-generated method stub
		legalSquares = new Parameter();
		//white north single move[0]
		legalSquares.add(getBounds().x / SIZE, (getBounds().y / SIZE) - 1);
		//white north double move[1]
		legalSquares.add(getBounds().x / SIZE, (getBounds().y / SIZE) - 2);
		//white north left capture move[2]
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE) - 1);
		//white north right capture move[3]
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE) - 1);
		
		//black south single move[4]
		legalSquares.add(getBounds().x / SIZE, (getBounds().y / SIZE) + 1);
		//black south double move[5]
		legalSquares.add(getBounds().x / SIZE, (getBounds().y / SIZE) + 2);
		//black south left capture move[6]
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE) + 1);		
		//black south right capture move[7]
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE) + 1);		
		
		//en passant left move[8]
		legalSquares.add((getBounds().x / SIZE) - 1, (getBounds().y / SIZE));		
		//en passant right move[9]
		legalSquares.add((getBounds().x / SIZE) + 1, (getBounds().y / SIZE));		
				
				
	}
	
	public void switchActiveHighlights(int status) {
		
		
		switch(status) {
		
			case ON:
				
				if(!isSelectedActive()) {
					
					isSelectedActive(true);
					isBorderActive(true);
					isLegalMoveActive(true);
					
					pressCount();
					
				}else {
					
					if(pressCount == 0)
						pressCount = 2;
				}
				
				
				break;
				
			case OFF:
				
				isSelectedActive(false);
				isBorderActive(false);
				isLegalMoveActive(false);
				
				pressCount = 0;
				
				break;
		}
	}

	public void pressCount() {
	
		pressCount++;
		
		if(pressCount > 2) {
			pressCount = 0;
			
			switchActiveHighlights(OFF);
			
		}
		 
	}
	
	public void pressCount(int count) {
		pressCount = count;
	}
	
	public void creatBounds() {		
		bounds = new Rectangle(x, y, SIZE, SIZE);		
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(int xBounds, int yBounds) {
		bounds.x = xBounds;
		bounds.y = yBounds;
	}
	
	/*
	public void createSquareBounds(Rectangle[][] bounds) {
		
		squareBounds = bounds;
	}
	*/
	
	public void setSquareBounds(int xLoc, int yLoc, Rectangle bounds) {
		squareBounds[xLoc][yLoc] = bounds;
	}
	
	public Rectangle getSquareBounds(int xLoc, int yLoc) {
		return squareBounds[xLoc][yLoc];
	}
		
	public void setPieceLocation(int xLoc, int yLoc) {
		x = xLoc;
		y = yLoc;
		
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setNotation(String notation) {
		this.notation = notation;
	}
	
	public String getNotation() {
		return notation;
	}
	
	public void setColorID(int colorID) {
		this.colorID = colorID;
	}
	
	public int getColorID() {
		return colorID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Parameter legalSquares() {
		return legalSquares;
	}
	
	public void legalMoves(int length) {
		legalMoves = new Parameter(length);
	}
	
	public Parameter legalMoves() {
		return legalMoves;
	}
	
	public void isLegalMoveActive(boolean status) {
		isLegalMoveActive = status;
	}
	
	public boolean isLegalMoveActive() {
		return isLegalMoveActive;
	}
	
	public static void playersTurn(int whosMove) {
		PieceManager.playersTurn = whosMove;
	}
	
	public static int playersTurn() {
		return PieceManager.playersTurn;
	}
	
	public void isSelectedActive(boolean status) {
		isSelectedActive = status;
	}
	
	public boolean isSelectedActive() {
		return isSelectedActive;
	}
	
	public void setSelectedLoc(int xSquare, int ySquare) {		
		xSelectedSquare = xSquare;
		ySelectedSquare = ySquare;
		
		setBorderLoc(xSquare, ySquare);
		
	}
	
	public void setOldSquareLoc(int xSquare, int ySquare) {
		xOldSquare = xSquare;
		yOldSquare = ySquare;
		
	}
	
	public void setNewSquareLoc(int xSquare, int ySquare) {
		xNewSquare = xSquare;
		yNewSquare = ySquare;
	}
	
	public void setBorderLoc(int xLoc, int yLoc) {
		xBorderLoc = xLoc;
		yBorderLoc = yLoc;
	}
	
	public int getxBorderLoc() {
		return xBorderLoc;
	}
	
	public int getyBorderLoc() {
		return yBorderLoc;
	}
		
	public int getxNewSquare() {
		return xNewSquare;
	}
	
	public int getyNewSquare() {
		return yNewSquare;
	}
 	 	
	
	public int getxOldSquare() {
		return xOldSquare;
	}
	
	public int getyOldSquare() {
		return yOldSquare;
	}
 	 	
	public int getxSelectedSquare() {
		return xSelectedSquare;
	}
	
	public int getySelectedSquare() {
		return ySelectedSquare;
	}
 
	public void isOldSquareActive(boolean status) {
		isOldSquareActive = status;
	}
	 
	public boolean isOldSquareActive() {
		return isOldSquareActive;
	}
	
	public void isNewSquareActive(boolean status) {
		isNewSquareActive = status;
	}
	 
	public boolean isNewSquareActive() {
		return isNewSquareActive;
	}
	
	public void isBorderActive(boolean status) {
		isBorderActive = status;
	}
	 
	public boolean isBorderActive() {
		return isBorderActive;
	}
	
	
	
}
