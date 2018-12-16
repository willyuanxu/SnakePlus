// author: Yuan Xu 

package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GridCell {
	int x;
	int y; 
	private ImageView image; 

	public GridCell(int x, int y, Image image) {
		this.set(x, y);
		this.image = new ImageView();
		this.image.setFitHeight(40);
		this.image.setFitWidth(40);
		
		
		this.setImage(image);
		
	}
	public void set(int x, int y) {
		this.x = x;
		this.y = y; 
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setImage(Image image) {
		
		this.image.setImage(image);
		
	}
	
	// check if another cell overlaps with current one 
	public boolean overlap(GridCell cell) {
		if (this.x == cell.getX() && this.y == cell.getY()) {
			return true;
		}
		return false;
	}
	
	public ImageView getImage() {
		return this.image;
	}
	
	
}
