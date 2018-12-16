// author: Yuan Xu 

package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class OpeningController {
	
	private Main main; 
	private Scene scene;
	
	
	
	public Scene getScene() {
		return this.scene;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void initialize() {
		// set up logic for the opening scene 
		this.scene = new Scene(rootBox);
		startButton.setOnMouseClicked(e -> {
			if (!this.instructionCheckbox.isSelected()) {
				this.main.showGameScene();
			} else {
				this.main.showInstructionScene();
			}
			
		});
	}
	
	public int getSpeed() {
		return (int) this.slider.getValue();
	}
	
	@FXML
	private CheckBox instructionCheckbox;
	
	@FXML
	private Button startButton;
	
	@FXML
	private VBox rootBox;
	
	@FXML
	private Slider slider; 

}
