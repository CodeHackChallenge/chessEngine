package main.engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.component.Board;
import main.component.Handler;
import main.manager.PieceManager;
import main.materials.Bishop;
import main.materials.King;
import main.materials.Knight;
import main.materials.Pawn;
import main.materials.Queen;
import main.materials.Rook;

public class Initializer {
	
	
	public static BufferedImage[][] pieceImage;
	
	
	
	private BufferedImage materials = loadImage("/chess_pieces_91x91.png");
	
	private BufferedImage boardImage = loadImage("/board_728x728.png");
	private BufferedImage whiteTransparent = loadImage("/white_transparent_728x728.png");
	
	
	private BufferedImage[] theme;
	
	
	private Handler handler;
	
	public Initializer() {
		
		setup();
	}

	private void setup() {
		 
		theme = new BufferedImage[2];
		theme[0] = boardImage;
		theme[1] = whiteTransparent;
		
		handleImage();
		
		Board board = new Board();
		board.setTheme(theme);
			
		
		//white
		Pawn wqrp = new Pawn(pieceImage[0][0], "WQRP", PieceManager.WHITE);
		Pawn wqnp = new Pawn(pieceImage[0][0], "WQNP", PieceManager.WHITE);
		Pawn wqbp = new Pawn(pieceImage[0][0], "WQBP", PieceManager.WHITE);
		Pawn wqp = new Pawn(pieceImage[0][0], "WQP", PieceManager.WHITE);
		Pawn wkp = new Pawn(pieceImage[0][0], "WKP", PieceManager.WHITE);
		Pawn wkbp = new Pawn(pieceImage[0][0], "WKBP", PieceManager.WHITE);
		Pawn wknp = new Pawn(pieceImage[0][0], "WKNP", PieceManager.WHITE);
		Pawn wkrp = new Pawn(pieceImage[0][0], "WKRP", PieceManager.WHITE);
		//black
		Pawn bqrp = new Pawn(pieceImage[0][1], "BQRP", PieceManager.BLACK);
		Pawn bqnp = new Pawn(pieceImage[0][1], "BQNP", PieceManager.BLACK);
		Pawn bqbp = new Pawn(pieceImage[0][1], "BQBP", PieceManager.BLACK);
		Pawn bqp = new Pawn(pieceImage[0][1], "BQP", PieceManager.BLACK);
		Pawn bkp = new Pawn(pieceImage[0][1], "BKP", PieceManager.BLACK);
		Pawn bkbp = new Pawn(pieceImage[0][1], "BKBP", PieceManager.BLACK);
		Pawn bknp = new Pawn(pieceImage[0][1], "BKNP", PieceManager.BLACK);
		Pawn bkrp = new Pawn(pieceImage[0][1], "BKRP", PieceManager.BLACK);
		//white rook
		Rook wqr = new Rook(pieceImage[1][0], "WQR", PieceManager.WHITE);
		Rook wkr = new Rook(pieceImage[1][0], "WKR", PieceManager.WHITE);
		//black rook
		Rook bqr = new Rook(pieceImage[1][1], "BQR", PieceManager.BLACK);
		Rook bkr = new Rook(pieceImage[1][1], "BKR", PieceManager.BLACK);
		//white knight
		Knight wqn= new Knight(pieceImage[2][0], "WQN", PieceManager.WHITE);
		Knight wkn= new Knight(pieceImage[2][0], "WKN", PieceManager.WHITE);
		//black knight
		Knight bqn= new Knight(pieceImage[2][1], "BQN", PieceManager.BLACK);
		Knight bkn= new Knight(pieceImage[2][1], "BKN", PieceManager.BLACK);
		//white bishop
		Bishop wqb = new Bishop(pieceImage[3][0], "WQB", PieceManager.WHITE);
		Bishop wkb = new Bishop(pieceImage[3][0], "WKB", PieceManager.WHITE);
		//black bishop
		Bishop bqb = new Bishop(pieceImage[3][1], "BQB", PieceManager.BLACK);
		Bishop bkb = new Bishop(pieceImage[3][1], "BKB", PieceManager.BLACK);
		//white queen
		Queen wq = new Queen(pieceImage[4][0], "WQ", PieceManager.WHITE);
		//black queen
		Queen bq = new Queen(pieceImage[4][1], "BQ", PieceManager.BLACK);
		//white king
		King wk = new King(pieceImage[5][0], "WK", PieceManager.WHITE);
		//black king
		King bk = new King(pieceImage[5][1], "BK", PieceManager.BLACK);
		
		
		
		 
		//WHITE
		board.add(wqrp, "a2");
		board.add(wqnp, "b2");
		board.add(wqbp, "c2");
		board.add(wqp, "d2");		
		board.add(wkp, "e2");
		board.add(wkbp, "f2");
		board.add(wknp, "g2");
		board.add(wkrp, "h2");
		//BLACK
		board.add(bqrp, "a7");
		board.add(bqnp, "b7");
		board.add(bqbp, "c7");
		board.add(bqp, "d7");		
		board.add(bkp, "e7");
		board.add(bkbp, "f7");
		board.add(bknp, "g7");
		board.add(bkrp, "h7");
		//rook
		board.add(wqr, "a1"); board.add(wkr, "h1");
		board.add(bqr, "a8"); board.add(bkr, "h8");
		//knight
		board.add(wqn, "b1"); board.add(wkn, "g1");
		board.add(bqn, "b8"); board.add(bkn, "g8");
		//bishop
		board.add(wqb, "c1"); board.add(wkb, "f1");
		board.add(bqb, "c8"); board.add(bkb, "f8");
		//queen
		board.add(wq, "d1"); board.add(bq, "d8");
		//king
		board.add(wk, "e1"); board.add(bk, "e8");
		 
		 
		/**
		board.add(wqrp, "a2");
		board.add(wqr, "a1");
		board.add(wqn, "b1");
		board.add(wqb, "c1");
		board.add(wq, "d1"); 
		board.add(wk, "e4");
		*/
		handler = new Handler();
		
		
		
		handler.add(board);
		
		
		
		
		
		
	}
	
	public void render(Graphics g) {
		
		//g.drawImage(boardImage, 0, 0, null);
		handler.render(g);
		
	}
	
	private BufferedImage loadImage(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return image;
	}
	
	private void handleImage() {
		
		pieceImage = new BufferedImage[6][2];
		for(int r = 0; r < 2; r++)
			for(int c = 0; c < 6; c++)
				pieceImage[c][r] = materials.getSubimage(c * PieceManager.SIZE, r * PieceManager.SIZE, PieceManager.SIZE, PieceManager.SIZE);
		
		
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	

}
