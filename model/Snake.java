// author: Yuan Xu 

package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Snake {
	 
	private static Image headUp; 
	private static Image headDown;
	private static Image headLeft;
	private static Image headRight;
	private static Image horizontal;
	private static Image vertical;
	private static Image leftUp;
	private static Image leftDown;
	private static Image rightUp;
	private static Image rightDown;
	private static Image tailLeft;
	private static Image tailRight;
	private static Image tailUp;
	private static Image tailDown;
	private int length; 
	private List<SnakeCell> snake;
	private int speed = 1; 
	private Direction dir; 
	private int points;
	private int pointMult = 1;
	

	
	
	public Snake(int startX, int startY) {
		snake = new ArrayList<SnakeCell>(); 
		this.loadImages();
		this.length = 3;
		// snake inital length always 3 
		SnakeCell head = new SnakeCell(startX, startY, headUp, Direction.UP, true, false);
		SnakeCell body = new SnakeCell(startX, startY+1, vertical, Direction.UP, false, false);
		SnakeCell tail = new SnakeCell(startX, startY+2, tailDown, Direction.UP, false, true);
		snake.add(head);
		snake.add(body);
		snake.add(tail);
		// snake initial direction always up 
		this.dir = Direction.UP;
	}
	
	public int getScore() {
		return points;
	}
	
	
	private void growSnake() {
		// make the current head a body 
		Direction currHeadDir = snake.get(0).getDirection();
		if (currHeadDir == Direction.UP || currHeadDir == Direction.DOWN) {
			snake.get(0).setImage(this.vertical);
		} else {
			snake.get(0).setImage(this.horizontal);
		}
		snake.get(0).setBody();
		// now use updatecell to figure out what this body part should look like 
		this.updateCell(this.dir, currHeadDir, snake.get(0));
		int currX = snake.get(0).getX();
		int currY = snake.get(0).getY();
		
		// temporary snake head 
		SnakeCell newHead = new SnakeCell(currX, currY, null, this.dir, true, false);
		if (this.dir == Direction.UP) {
			newHead.setImage(this.headUp);
			currY -= 1;
		} else if (this.dir == Direction.DOWN) {
			newHead.setImage(this.headDown);
			currY += 1; 
		} else if (this.dir == Direction.LEFT) {
			newHead.setImage(this.headLeft);
			currX -= 1;
		} else {
			newHead.setImage(this.headRight);
			currX += 1;
		}
		// update the x and y 
		newHead.set(currX, currY);
		
		snake.add(0, newHead);
		this.checkBoundary();
	}
	
	// increment score 
	public void addPoint(int point) {
		this.points += point*this.pointMult;
	}
	
	// point multiplier for dragonfruit logic 
	public void setPointMultiplier(int mult) {
		this.pointMult = mult;
	}
	
	// make sure snake can travel through the sides of the screen
	private void checkBoundary() {
		if (snake.get(0).getY() == -1) {
			snake.get(0).setY(19);
		} else if (snake.get(0).getY() == 20) {
			snake.get(0).setY(0);
		}
		
		if (snake.get(0).getX() == -1) {
			snake.get(0).setX(19);
		} else if (snake.get(0).getX() == 20) {
			snake.get(0).setX(0);
		}
	}
	
	public boolean checkDeath() {
		// check if snake has died 
		for (int i = 1; i < snake.size(); i++) {
			// snake dies if it eats itself 
			if (snake.get(0).overlap(snake.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public void move(boolean grow) {
		if (grow) {
			this.growSnake();
			return;
			
		}
		Direction prevDir = this.dir;
		int prevX = 0; 
		int prevY = 0;

		
		for (int i = 0; i < snake.size(); i++) {
			int currX = snake.get(i).getX();
			int currY = snake.get(i).getY();
			Direction currDir = snake.get(i).getDirection();

			if (i == 0) {
				if (prevDir == Direction.UP || prevDir == Direction.DOWN) {
					// if the cell is moving up or down currently 
					snake.get(i).setY(snake.get(i).getY() + (prevDir == Direction.UP ? -1 : 1) * speed);
					
					
				} else {
					// if the cell is moving left or right currently
					snake.get(i).setX(snake.get(i).getX() + (prevDir == Direction.LEFT ? -1 : 1) * speed);
					
				}
				this.checkBoundary();
			} else {
				snake.get(i).set(prevX, prevY);
			}
			
			prevX = currX;
			prevY = currY;
			
			
			// the new direction for this cell should be determined by the current direction and ahead direction
			if (i != 0) {
				prevDir = this.getCurrDirection(snake.get(i-1), snake.get(i));
				
			}
			
			this.updateCell(prevDir, currDir, snake.get(i));
			
			prevDir = currDir;
		}
	}
	
	private Direction getCurrDirection(SnakeCell prev, SnakeCell curr) {
		// returns direction the current cell should be facing given current cell and previous cell
		if (prev.getX() < curr.getX()) {
			if (prev.getX() == 0 && curr.getX() == 19) {
				return Direction.RIGHT;
			}
			return Direction.LEFT;
		}
		if (prev.getX() > curr.getX()) {
			if (prev.getX() == 19 && curr.getX() == 0) {
				return Direction.LEFT;
			}
			return Direction.RIGHT;
		}
		if (prev.getY() < curr.getY()) {
			if (prev.getY() == 0 && curr.getY() == 19) {
				return Direction.DOWN;
			}
			return Direction.UP;
		}
		if (prev.getY() == 19 && curr.getY() == 0) {
			return Direction.UP;
		}
		return Direction.DOWN;
	}
	
	public void changeDirection(Direction dir) {
		this.dir = dir;
	}
	
	public Direction getDirection() {
		return this.dir;
	}
	
	public boolean overlapWithSnake(GridCell c) {
		// check if a grid overlaps with the snake 
		for (int i = 0; i < snake.size(); i++) {
			if (c.overlap(snake.get(i))) {
				return true;
			} 
		}
		return false;
	}
	
	private Image getHeadImage(Direction dir) {
		// logic to get correct head image 
		if (dir == Direction.UP) {
			return this.headUp;
		} else if (dir == Direction.DOWN) {
			return this.headDown; 
		} else if (dir == Direction.LEFT) {
			return this.headLeft;
		} else {
			return this.headRight;
		}
	}
	
	private Image getTailImage(Direction dir) {
		// logic to get correct tail image 
		if (dir == Direction.UP) {
			return this.tailDown;
		} else if (dir == Direction.DOWN) {
			return this.tailUp; 
		} else if (dir == Direction.LEFT) {
			return this.tailRight;
		} else {
			return this.tailLeft;
		}
	}
	
	private Image getBodyImage(Direction dir) {
		// logic to get correct body image 
		if (dir == Direction.LEFT || dir == Direction.RIGHT) {
			return this.horizontal;
		}
		return this.vertical;
	}
	
	
	private void updateCell(Direction prev, Direction curr, SnakeCell cell) {
		// called after each time snake moves, making sure each cell is how it's supposed to look like 
		cell.setDirection(prev);
		if (prev == curr && (!cell.isHead() && !cell.isTail())) {
			cell.setImage(this.getBodyImage(curr));
			return; 
		}
		
		if (cell.isTail()) {			
			cell.setImage(this.getTailImage(prev));
			
			return;
		}
		
		if (cell.isHead()) {
			cell.setImage(this.getHeadImage(prev));
			return;
		}
		
		// turn image scenarios 
		if ((prev == Direction.UP && curr == Direction.LEFT)|| (prev == Direction.RIGHT && curr == Direction.DOWN)) {
			 cell.setImage(this.rightUp);
			 return;
		}
		if ((prev == Direction.UP && curr == Direction.RIGHT) || (prev == Direction.LEFT && curr == Direction.DOWN)) {
			 cell.setImage(this.leftUp);
			 return;
		}
		
		if ((prev == Direction.DOWN && curr == Direction.RIGHT) || (prev == Direction.LEFT && curr == Direction.UP)) {
			 cell.setImage(this.leftDown);
			 return;
		}
		
		if ((prev == Direction.DOWN && curr == Direction.LEFT) || (prev == Direction.RIGHT && curr == Direction.UP)) {
			 cell.setImage(this.rightDown);
			 return;
		}
		
	}
	
	public int getLength() {
		return this.length;
	}
	
	public List<SnakeCell> getSnake(){
		return this.snake;
	}
	
	public void incrementLength() {
		this.length += 1;
	}
	
	public void decrementLength(int val) {
		this.length = this.length - val > 3 ? this.length - val : 3; 
	}
	
	private void loadImages() {
		// lazy loading images 
		if (headUp == null) {
			headUp = new Image("/images/snake/headUp.png");	
			
		}
		if (headDown == null) {
			headDown = new Image("/images/snake/headDown.png");			
		}
		if (headLeft == null) {
			headLeft = new Image("/images/snake/headLeft.png");			
		}
		if (headRight == null) {
			headRight = new Image("/images/snake/headRight.png");			
		}
		if (horizontal == null) {
			horizontal = new Image("/images/snake/horizontal.png");			
		}
		if (vertical == null) {
			vertical = new Image("/images/snake/vertical.png");			
		}
		if (leftUp == null) {
			leftUp = new Image("/images/snake/leftUp.png");			
		}
		if (leftDown == null) {
			leftDown = new Image("/images/snake/leftDown.png");			
		}
		if (rightUp == null) {
			rightUp = new Image("/images/snake/rightUp.png");			
		}
		if (rightDown == null) {
			rightDown = new Image("/images/snake/rightDown.png");			
		}
		if (tailLeft == null) {
			tailLeft = new Image("/images/snake/tailLeft.png");			
		}
		if (tailRight == null) {
			tailRight = new Image("/images/snake/tailRight.png");			
		}
		if (tailUp == null) {
			tailUp = new Image("/images/snake/tailUp.png");			
		}
		if (tailDown == null) {
			tailDown = new Image("/images/snake/tailDown.png");			
		}
		
	}

}
