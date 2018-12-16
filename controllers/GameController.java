// author: Yuan Xu 

package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Direction;
import model.FruitCell;
import model.Snake;
import model.SnakeCell;
import views.GameView;


public class GameController {
	private GameView view; 
	private Snake snake;
	private MyTimer timer;
	private Scene scene;
	private FruitCell fruit; // current fruit on the grid 
	// images for all fruits 
	private static Image apple;
	private static Image dragonfruit;
	private static Image durian;
	private static Image pomegranate;
	private static Image seed;
	
	private List<FruitCell> pomSeeds; 
	private boolean pomTime = false; 

	private PomTimer pomTimer; 
	private DurianTimer durianTimer;
	private boolean durianTime = false;
	
	private DragonTimer dragonTimer; 
	private boolean dragonTime = false;
	private Main main;
	
	
	public GameController() {
		view = new GameView();
		loadImages();
		
		this.resetGame();
		this.scene = new Scene(view.getRoot(), 800, 860);

		
		setUpKeyControl();

	}
	
	public void resetGame() {
		// create snake
		snake = new Snake(10, 10);
				
		// create fruit
		createFruit();
				
		// initialize pomseeds arraylist 
		pomSeeds = new ArrayList<FruitCell>();
				
		showAll();
				
		timer = new MyTimer();
	}
	
	
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	private void createFruit() {
		// create random fruit with differnt probabilities 
		Random r = new Random();
		int x = r.nextInt(20);
		int y = r.nextInt(20);
		int fruitSpecial = r.nextInt(10);
		if (fruitSpecial < 4 && !this.dragonTime && !this.durianTime && !this.pomTime) {
			// special fruit 
			int specialFruitValue = r.nextInt(3);
			switch(specialFruitValue) {
			case 0:
				fruit = new FruitCell(x, y, this.dragonfruit, 0, specialFruitValue);
				break;
			case 1:
				fruit = new FruitCell(x, y, this.pomegranate, 0, specialFruitValue);
				break;
			case 2: 
				fruit = new FruitCell(x, y, this.durian, 0, specialFruitValue);
//				
				break;
			}
		} else {
			// normal fruit
			fruit = new FruitCell(x, y, this.apple, 5, 3);
//			fruit = new FruitCell(x, y, this.dragonfruit, 0, 0);
		}
		
		// making sure fruit is not overlapping with current player
		this.setFruitToValidLoc(fruit);
		
		this.view.getGrid().add(fruit.getImage(), x, y);
	}
	
	private void setFruitToValidLoc(FruitCell fruit) {
		// function to check if fruit is at a valid grid, if not change up the spawn location 
		Random r = new Random();
		while (fruitOverlapWithSnake()) {
			fruit.set(r.nextInt(20), r.nextInt(20));
		}
	}
	
	private void loadImages() {
		// load up images only once 
		if (apple == null) {
			apple = new Image("/images/apple.png");
			dragonfruit = new Image("images/dragonfruit.png");
			durian = new Image("/images/durian.png");
			pomegranate = new Image("/images/pomegranate.png");
			seed = new Image("/images/pomegranateSeed.png");
		}
	}
	
	private boolean fruitOverlapWithSnake() {
		if (fruit == null) {
			return false;
		}
		return this.snake.overlapWithSnake(fruit);
	}
	
	private int pomSeedOverlapWithSnake() {
		// if snake overlaps with a pom seed return its index 
		for (int i = 0; i < this.pomSeeds.size(); i++) {
			if (this.pomSeeds.get(i) != null &&this.snake.overlapWithSnake(this.pomSeeds.get(i))) {
				return i;
			}
			
		}
		return -1;
	}
	
	
	
	private void setUpKeyControl() {
		// set up key controls for snake 
		this.scene.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
			if (this.durianTime) {
				// durian reverses keys temporarily
				if (ke.getCode() == KeyCode.DOWN) {
					if (this.snake.getDirection() != Direction.DOWN) {
						this.snake.changeDirection(Direction.UP);
					}
				} else if (ke.getCode() == KeyCode.UP) {
					if (this.snake.getDirection() != Direction.UP) {
						this.snake.changeDirection(Direction.DOWN);
					}
				} else if (ke.getCode() == KeyCode.RIGHT) {
					if (this.snake.getDirection() != Direction.RIGHT) {
						this.snake.changeDirection(Direction.LEFT);
					}
				} else if (ke.getCode() == KeyCode.LEFT) {
					if (this.snake.getDirection() != Direction.LEFT) {
						this.snake.changeDirection(Direction.RIGHT);
					}
				}
			} else {
				if (ke.getCode() == KeyCode.UP) {
					if (this.snake.getDirection() != Direction.DOWN) {
						this.snake.changeDirection(Direction.UP);
					}
				} else if (ke.getCode() == KeyCode.DOWN) {
					if (this.snake.getDirection() != Direction.UP) {
						this.snake.changeDirection(Direction.DOWN);
					}
				} else if (ke.getCode() == KeyCode.LEFT) {
					if (this.snake.getDirection() != Direction.RIGHT) {
						this.snake.changeDirection(Direction.LEFT);
					}
				} else if (ke.getCode() == KeyCode.RIGHT) {
					if (this.snake.getDirection() != Direction.LEFT) {
						this.snake.changeDirection(Direction.RIGHT);
					}
				}
			}
			
		});
	}
	
	public void startGame() {
		timer.start();
	}
	
	private void showAll() {
		// show everything in the scene, called every update 
		this.view.getGrid().getChildren().clear();
		// always show fruit first
		if (fruit != null) {
			this.view.getGrid().add(fruit.getImage(), fruit.getX(), fruit.getY());
		}
		if (pomSeeds.size()!= 0) {
			for (int i = 0; i < this.pomSeeds.size(); i++) {
				if (pomSeeds.get(i) != null) {
					this.view.getGrid().add(pomSeeds.get(i).getImage(), pomSeeds.get(i).getX(), pomSeeds.get(i).getY());
				}
			}
		}
		List<SnakeCell> snakeCells = this.snake.getSnake();
		for (int i = 0 ; i < snakeCells.size(); i++) {
			SnakeCell curr = snakeCells.get(i);
			this.view.getGrid().add(curr.getImage(), curr.getX(), curr.getY());
		}
		this.view.updateScore(this.snake.getScore());
		
		
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	private void fruitAction() {
		// set up the logic for snake interacting with fruit 
		switch(fruit.getFruitValue()) {
		case 0:
			// dragonfruit
			snake.move(false);
			this.dragonTime = true;
			// dragonfruit logic 
			this.timer.decreaseInterval();
			this.snake.setPointMultiplier(2);
			this.view.dragonInstruction();
			this.dragonTimer = new DragonTimer();
			this.dragonTimer.start();
			break;
		case 1:
			// pom
			snake.move(false);
			this.pomTime = true;
			this.view.pomInstruction();
			this.pomTimer = new PomTimer();
			this.pomTimer.start();
			break;

		case 2:
			// durian
			snake.move(false);
			this.view.durianInstruction();
			this.durianTimer = new DurianTimer();
			this.durianTimer.start();
			
			// durian changes background color 
			this.view.getGrid().setStyle("-fx-background-color: black;");
			this.durianTime = true;
			break;
		
		default:
			// apple 
			snake.move(true);
		}
		this.snake.addPoint(fruit.getPoint());
		if (fruit != null && fruit.getFruitValue() != 1) {
			this.createFruit();
		} else {
			fruit = null;
		}
	}

	public void setSnakeSpeed(int speed) {
		this.timer.setSpeed(speed);
	}

	
	private boolean checkDeath() {
		if (snake.checkDeath()) {
			this.main.playDeathSound();
			return true;
		}
		return false;
	}
	
	
	// timer for snake 
	class MyTimer extends AnimationTimer {
		private long lastUpdated = 0;
		private long updateInterval = 50_000_000; 
		
		@Override
		public void handle(long now) {
			// wait 0.1 second every time 
			if ((now - lastUpdated) >=  updateInterval) {
				if (!pomTime) {
					if (fruitOverlapWithSnake()) {
						main.playEatSound();
						fruitAction();
						
					} else {
						snake.move(false);
					}
				} else {
					// pomegranate requires special logic 
					int index = pomSeedOverlapWithSnake();
					if (index != -1) {
						main.playEatSound();
						pomSeeds.set(index, null);
						snake.addPoint(1);
					}
					snake.move(false);
				}
				showAll();
				lastUpdated = now;
				if (checkDeath()) {
					this.stop();
					main.showEndScene(snake.getScore());
				}
			}
		}
		
		// for setting up initial speed
		public void setSpeed(int speed) {
			this.updateInterval = 50_000_000 * (5-speed);
		}
		
		// intervals are changed to increase/decrease speed 
		public void increaseInterval() {
			this.updateInterval *=2;
		}
		public void decreaseInterval() {
			this.updateInterval /=2;
		}
	}
	
	// timer for dragon fruit logic, runs concurrently with main snake timer 
	class DragonTimer extends AnimationTimer {

		private long lastUpdated = 0; 
		private int counter = 0;
		private boolean done = false;

		@Override
		public void handle(long now) {
			if (now - lastUpdated >= 100_000_000) {
				if (counter >= 100) {
					this.done = true;
					this.stop();
					
					// set everything back to normal 
					view.normalizeScreen();
					dragonTime = false;
					timer.increaseInterval();
					snake.setPointMultiplier(1);
					view.normalInstruction();
					dragonTimer = null;
				}
				
				if (!done) {
					counter++; 
					lastUpdated = now; 
					view.flash();
				}
			}
		} 
		
	}

	// timer that  handles pomegranate special scenario , runs concurrently with main snake timer 
	class PomTimer extends AnimationTimer {
		private long lastUpdated = 0;
		private int countX = 0;
		private int countY = 0; 
		private boolean done = false;
		
		private int deleteIndex = 0;
		
		
		@Override
		public void handle(long now) {
			// wait 0.1 second every time 
			if ((now - lastUpdated) >=  100_000_000) {
				if (!done) {
					// make pom cell at x and y 
					FruitCell pomSeed = new FruitCell(countX, countY, seed, 1, -1);
					pomSeeds.add(pomSeed);
					countX++;
					if (countX > 19) {
						countY++; 
						countX = 0;
					}
					if (countY > 19) {
						done = true;
						countX = 0; 
						countY = 0;
					}
				} else {
					// make all pom cell disappear if they are not eaten already 
					if (this.deleteIndex < pomSeeds.size()) {
						if (pomSeeds.get(deleteIndex) != null) {
							pomSeeds.set(deleteIndex, null);
						}
						deleteIndex++;
					} else {
						pomSeeds = new ArrayList<FruitCell>();
						createFruit();
						pomTime = false;
						this.stop();
						view.normalInstruction();
						pomTimer = null;
					}
					
				}
			}
		}
		
		
	}
	
	// handles durian special scenario
	class DurianTimer extends AnimationTimer {
		private long lastUpdated = 0; 
		private int counter = 0;
		private boolean done = false;

		@Override
		public void handle(long now) {
			// TODO Auto-generated method stub
			if (now - lastUpdated > 1_000_000_000) {
				if (counter == 5) {
					done = true;
					this.stop();
					view.getGrid().setStyle("-fx-background-color: white;");
					durianTime = false;
					view.normalInstruction();
					durianTimer = null;
				}
				counter++; 
				lastUpdated = now; 
			}
			
		} 
		
	}
}
