package main.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 91 * 8; //728 x 728
	private final int HEIGHT = WIDTH;
	
	private boolean isRunning;
	
	private Thread thread;
	private BufferStrategy bStrategy;
	
	
	private JFrame frame;
	private Container container;
	 
	private Initializer initializer;
	
	public Engine() {
		
		
		createFrame(); 
		
		initializer = new Initializer();
	}
	
	private void createFrame() {
		
		//frame
		frame = new JFrame("Code Hack Challenge - Chess dot Com ep.2");
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//container
		container = new Container();
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		container.setBackground(Color.DARK_GRAY);
		container.add(this);
			
		addMouseListener(new MouseInput());
		addMouseMotionListener(new MouseMove());
		
		
		frame.setVisible(true);
		frame.pack();
		
		createBufferStrategy(3);
		
		
	}
	
	private void render() {
		
		bStrategy = getBufferStrategy();
		Graphics2D g  = (Graphics2D) bStrategy.getDrawGraphics();
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//***********DRAW EVERYTHING HERE***********************//
		
		initializer.render(g);
		 
		
		//**************END*********************//
		
		g.dispose();
		bStrategy.show();
		
	}
	
	@Override
	public void run() {

		
		while(isRunning) {
			
			
			render();
		}//while
		
		
		
		
		
	}

	private synchronized void start() {
		if(isRunning)
			return;
		
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		
		
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Hello Code Hack Challenge");
		 
		new Engine().start();
		
	}

	//private inner class
	private class MouseMove extends MouseAdapter{
		
		
		public void mouseMoved(MouseEvent e) {
			try {
				initializer.getHandler().mouseMoved(e);
			}catch(Exception error) {}
			
			
		}
		
		public void mouseDragged(MouseEvent e) {
			try {
				initializer.getHandler().mouseDragged(e);
			}catch(Exception error) {}
			
		}
		
		
	}
	
	private class MouseInput extends MouseAdapter{
		
		
		public void mousePressed(MouseEvent e) {
			initializer.getHandler().mousePressed(e);
		}
		
		public void mouseReleased(MouseEvent e) {
			initializer.getHandler().mouseReleased(e);
		}
		
		public void mouseClicked(MouseEvent e) {
			initializer.getHandler().mouseClicked(e);
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
