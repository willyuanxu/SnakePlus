// author: Yuan Xu 

package model;

import javafx.scene.image.Image;

public class SnakeCell extends GridCell {
	private Direction dir; 
	private boolean isHead; 
	private boolean isTail; 
	public SnakeCell(int x, int y, Image image, Direction dir, boolean isHead, boolean isTail) {
		super(x, y, image);
		this.dir = dir; 
		this.isHead = isHead;
		this.isTail = isTail;
	}
	
	public Direction getDirection() {
		return this.dir;
	}
	
	public void setDirection(Direction dir) {
		this.dir = dir;
	}
	
	public void setHead() {
		this.isHead = true;
		this.isTail = false;
	}
	
	public void setBody() {
		this.isHead = false;
		this.isTail = false;
	}
	
	public void setTail() {
		this.isHead = false;
		this.isTail = true;
	}
	
	public boolean isHead() {
		return this.isHead;
	}
	
	public boolean isTail() {
		return this.isTail;
	}
}
