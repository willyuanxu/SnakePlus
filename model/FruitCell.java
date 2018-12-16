// author: Yuan Xu 
package model;

import javafx.scene.image.Image;

public class FruitCell extends GridCell {
	private int pointValue; // keep track of how many points the fruit is for
	private int fruitValue; // keep track of what fruit it is 
	public FruitCell(int x, int y, Image image, int point, int fruitValue) {
		super(x, y, image);
		this.pointValue = point;
		this.fruitValue = fruitValue;
	}
	
	public int getFruitValue() {
		return fruitValue;
	}
	
	
	public int getPoint() {
		return pointValue;
	}
}
