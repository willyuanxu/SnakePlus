// author: Yuan Xu 

package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndController {
	
	private Main main;
	private Scene scene;
	
	public void initialize() {
		// set up button logics
		this.scene = new Scene(root);
		this.menuBtn.setOnMouseClicked(e -> {
			this.main.resetGame();
			this.main.showOpenScene();
		});
		this.replayBtn.setOnMouseClicked(e -> {
			this.main.resetGame();
			this.main.showGameScene();
		});
	}
	
	public void setScore(int score) {
		this.scoreLabel.setText(String.valueOf(score));
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	@FXML
	private Label scoreLabel;
	
	@FXML
	private Button menuBtn;
	
	@FXML
	private Button replayBtn;
	
	@FXML
	private VBox root; 
}
