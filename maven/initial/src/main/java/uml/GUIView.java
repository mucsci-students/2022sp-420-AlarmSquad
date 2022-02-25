package uml;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUIView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene window = new Scene(root, Color.BLACK);

        stage.setTitle("UML Editor");
        stage.setWidth(420);
        stage.setHeight(420);

        stage.setScene(window);
        stage.show();
    }
}
