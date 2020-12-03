package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Scene scene;
	private bishuGUI bishuGUI;
	
	
	public static void main(String[] args) throws IOException{
		
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("bishuWindow.fxml"));
		bishuGUI = new bishuGUI(stage);
		
		loader.setController(bishuGUI);
		Parent root = loader.load();

		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Bishu and his girlfrind");
		stage.show();
		stage.sizeToScene();
		
	}

}
