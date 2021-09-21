package application;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.stage.*;
import java.io.IOException;
import controller.AppManager;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Scene scene = new Scene(new StackPane());

		AppManager AppManager = new AppManager(scene);
		AppManager.showLoginScreen();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
