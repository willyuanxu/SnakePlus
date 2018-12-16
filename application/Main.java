// author: Yuan Xu 

package application;
	
import java.io.File;

import controllers.EndController;
import controllers.GameController;
import controllers.InstructionController;
import controllers.OpeningController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class Main extends Application {
	private MediaPlayer deathSoundPlayer; // for playing death sound 
	private MediaPlayer eatSoundPlayer;  // for playing eat sound 
	private OpeningController openControl; // controller for open scene 
	private GameController gameControl; // controller for main game 
	private InstructionController instructionControl;  // controller for instruction scene 
	private EndController endControl;	// controller for end scene 
	private Stage stage;
	
	// the different scenes
	private Scene openScene;
	private Scene instructionScene;
	private Scene gameScene;
	private Scene endScene;
	
	
	
	
	private static final String death = "src/sound/death.mp3";
	private static final String eat = "src/sound/eat.mp3";
	@Override
	public void start(Stage primaryStage) {
		try {
			this.stage = primaryStage;
			this.setUpOpeningControl();
			this.setUpInstructionControl();
			this.setUpGameControl();
			this.setUpEndControl();
			
			this.setUpSounds();			
			primaryStage.setScene(openControl.getScene());
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showGameScene() {
		// set the scene, reset snake speed and start the timer 
		this.stage.setScene(gameScene);
		this.gameControl.setSnakeSpeed(this.openControl.getSpeed());
		this.gameControl.startGame();
	}
	
	public void resetGame() {
		this.gameControl.resetGame();
	}
	
	public void showInstructionScene() {
		this.stage.setScene(instructionScene);
	}
	
	public void showOpenScene() {
		this.stage.setScene(openScene);
	}
	
	public void showEndScene(int score) {
		this.endControl.setScore(score);
		this.stage.setScene(endScene);
	}
	
	private void setUpGameControl() {
		gameControl = new GameController();
		gameControl.setMain(this);
		this.gameScene = gameControl.getScene();
	}

	
	private void setUpOpeningControl() {
		// load up the opening scene fxml and set up controller 
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/startView.fxml"));
			VBox openRoot = (VBox)loader.load();
			openControl = loader.getController();
			openControl.setMain(this);
			this.openScene = openControl.getScene();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setUpInstructionControl() {
		// load up the instruction scene fxml and set up controller 
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/instructionView.fxml"));
			VBox instructionRoot = (VBox)loader.load();
			instructionControl = loader.getController();
			instructionControl.setMain(this);
			this.instructionScene = instructionControl.getScene();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setUpEndControl() {
		// load up the end scene fxml and set up controller 
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/endView.fxml"));
			VBox endRoot = (VBox)loader.load();
			endControl = loader.getController();
			endControl.setMain(this);
			this.endScene = endControl.getScene();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUpSounds() {
		// load sound files 
		try{
			Media sound = new Media(new File(death).toURI().toString());
			deathSoundPlayer = new MediaPlayer(sound); 
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		try{
			Media sound = new Media(new File(eat).toURI().toString());
			eatSoundPlayer = new MediaPlayer(sound); 
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}
	
	public void playDeathSound() {
		this.deathSoundPlayer.play();
		this.deathSoundPlayer.setStartTime(this.deathSoundPlayer.getStartTime());
		this.deathSoundPlayer.seek(this.deathSoundPlayer.getStartTime());
	}
	
	public void playEatSound() {
		this.eatSoundPlayer.play();
		this.eatSoundPlayer.setStartTime(this.eatSoundPlayer.getStartTime());
		this.eatSoundPlayer.seek(this.eatSoundPlayer.getStartTime());
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
