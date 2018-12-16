// author: Yuan Xu 

package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class InstructionController {
	private Main main; 
	private Scene scene;
	
	public void initialize(){
		// set up all button logic s
		this.scene = new Scene(root);
		this.startBtn.setOnMouseClicked(e -> {
			this.main.showGameScene();
		});
		this.backBtn.setOnMouseClicked(e -> {
			this.main.showOpenScene();
		});
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	private Button startBtn;
	@FXML
	private Button backBtn;
	@FXML
	private VBox root;
	

}
