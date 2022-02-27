package uml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GUIController {

    public static void main(String[] args) {
        GUIView.main(args);
    }

    // TODO fix the issue with not passing className through the other method
    public static EventHandler<ActionEvent> saveAction(String fileName) {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (fileName.isEmpty()) {
                    GUIView.popUpWindow("Error", "File name is required");
                } else {
                    JSON.save(fileName);
                }
            }
        };
    }

    // TODO fix the issue with not passing className through the other method
    public static EventHandler<ActionEvent> addClassAction(String className) {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (className.isEmpty()) {
                    GUIView.popUpWindow("Error", "Class name is required");
                } else {
                    UMLClass newClass = new UMLClass(className);
                    UMLModel.addClass(newClass);
                }
            }
        };
    }

    public static EventHandler<ActionEvent> cancelAction(Stage stage) {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.close();
            }
        };
    }
}
