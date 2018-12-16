// author: Yuan Xu 

package views;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {
	private GridPane grid; // snake game board 
	private BorderPane root;  
	private Label scoreLabel; // shows score 
	private Label gameMessage; // shows game state related msg 
	public GameView() {
		root = new BorderPane();
		setUpAuxBoard();
		setUpGrid();
		root.getTop().maxHeight(100);
		root.getTop().minHeight(100);
	}
	
	// aux board shows score and game state related stuff 
	private void setUpAuxBoard() {
		HBox box = new HBox();
		// prettify 
		box.maxHeight(60);
		box.minHeight(60);
		box.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		box.setSpacing(20);
		box.setPadding(new Insets(20, 20, 20, 20));
		
		box.setAlignment(Pos.CENTER_LEFT);
		
		
		Label scoreIndicator = new Label("Score:");
		scoreLabel = new Label("0");
		gameMessage = new Label();
		
		// set sizes for labels 
		scoreIndicator.setFont(Font.font(20));
		scoreLabel.setFont(Font.font(20));
		gameMessage.setFont(Font.font(20));
		
		// set instruction to normal one
		this.normalInstruction();
		
		box.getChildren().addAll(scoreIndicator, scoreLabel, gameMessage);	
		this.root.setTop(box);
	}
	
	private void setUpGrid() {
		grid = new GridPane();
		grid.setPrefSize(800, 800);
		grid.setMinSize(800, 800);
		grid.setMaxSize(800, 800);
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(true);
		// make a fixed grid 
		int numRows = 20;
		int numCols = 20;
		for (int i = 0; i < numCols; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0/numCols);
			grid.getColumnConstraints().add(colConst);
		}
		
		for (int i = 0; i < numRows; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0/numRows);
			grid.getRowConstraints().add(rowConst);
		}
		this.root.setCenter(grid);
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	public GridPane getGrid() {
		return grid;
	}
	
	public void durianInstruction() {
		this.gameMessage.setText("Control reversed for 5 seconds!");
		this.gameMessage.setTextFill(Color.GREEN);
	}
	
	public void dragonInstruction() {
		this.gameMessage.setText("Speed x2 and score x2!!");
	}
	
	public void normalInstruction() {
		this.gameMessage.setText("Eat fruits to gain score and grow!");
		this.gameMessage.setTextFill(Color.BLACK);
	}
	
	public void pomInstruction() {
		this.gameMessage.setText("Eat the pomegranate seeds before they disappear!");
		this.gameMessage.setTextFill(Color.RED);
	}
	
	public void updateScore(int score) {
		this.scoreLabel.setText(String.valueOf(score));
	}
	
	// background changes to random color 
	public void flash() {
		Random rand = new Random();
		
		Color newColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		this.grid.setBackground(new Background(new BackgroundFill(newColor, CornerRadii.EMPTY, Insets.EMPTY)));
		this.gameMessage.setTextFill(newColor);
	}
	
	public void normalizeScreen() {
		this.grid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

}
