// author: Yuan Xu 

package model;

public enum Direction {
	UP(-1), DOWN(0), LEFT(1), RIGHT(2);
	private final int val; 
	private Direction(int val) {
		this.val = val;
	}
	public int getDirection() {
		return this.val;
	}
}
