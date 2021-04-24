package main.util;

import java.awt.Rectangle;

import main.manager.PieceManager;

public class Parameter extends PieceManager{
	
	
	private int xLoc, yLoc;
	
	private int length;
	private int element;
	
	private Rectangle bounds;
	
	private Parameter[] container;
	
	public Parameter() {
		
		this(10);
	}
	
	public Parameter(int length) {
		this.length = length;
		
		container = new Parameter[length];
		element = 0;
		
	}
	
	public Parameter(int xLoc, int yLoc) {
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		
		setBounds();
		
	}
	
	
	public void add(int xLoc, int yLoc) {
		
		container[element++] = new Parameter(xLoc, yLoc);
		
		
	}
	
	public void addAll(Parameter object) {
		container[element++] = object;
				
	}
	
	public void removeAll() {
		container = null;
	}
	
	private void setBounds() {
		
		bounds = new Rectangle(xLoc, yLoc, SIZE, SIZE);
		
	}

	public Rectangle getBounds() {
		return bounds;
		
	}
	
	public Rectangle getComputedBounds() {
		return new Rectangle(xLoc * SIZE, yLoc * SIZE, SIZE, SIZE); 
	}
	
	
	public Parameter get(int index) {
		return container[index];
	}
	
	public int getX() {
		return xLoc;
	}
	
	public int getY() {
		return yLoc;
	}
	
	public int length() {
		return length;
	}
	
	public int elements() {
		return element;
	}
	
	
}
