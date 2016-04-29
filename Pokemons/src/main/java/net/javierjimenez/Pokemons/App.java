package net.javierjimenez.Pokemons;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/pokemons.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/pokemons.css").toExternalForm());
			primaryStage.setTitle("Combat Pokémon");
			primaryStage.setScene(scene);
			primaryStage.show();
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
