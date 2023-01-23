package com.example.javafxfsm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		try
		{
		FXMLLoader fxmlLoader;
			fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			primaryStage.setTitle("Test");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
