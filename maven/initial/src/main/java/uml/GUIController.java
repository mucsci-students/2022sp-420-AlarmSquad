package uml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GUIController {

    /******************************************************************
     *
     * Start of view action methods
     *
     ******************************************************************/

    /**
     * Creates and adds a class to the classList if the given string is nonempty,
     * pops an error up otherwise
     *
     * @param className the name of the class to be added
     * @param stage the working stage
     */
    public static void addClassAction(String className, Stage stage) {
        // if the string is empty, pop an error up
        if (className.isEmpty()) {
            GUIView.popUpWindow("Error", "Class name is required");
        // otherwise, create and add the class and exit the window
        } else {
            UMLClass newClass = new UMLClass(className);
            UMLModel.addClass(newClass);
            GUIView.updateClass();
            stage.close();
        }
    }

    /**
     * Saves file if the given string is nonempty, pops an error up otherwise
     *
     * @param fileName the name of the file to be saved
     * @param stage the working stage
     */
    public static void saveAction(String fileName, Stage stage) {
        // if the string is empty, pop an error up
        if (fileName.isEmpty()) {
            GUIView.popUpWindow("Error", "File name is required");
            // otherwise, save the file and exit the window
        } else {
            JSON.save(fileName);
            stage.close();
        }
    }

    /**
     * Exits the current window if called
     *
     * @param stage the working stage
     * @return an event handler telling the window to close
     */
    public static EventHandler<ActionEvent> cancelAction(Stage stage) {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.close();
            }
        };
    }

    /******************************************************************
     *
     * End of view action methods
     *
     ******************************************************************/

    public static void main(String[] args) {
        GUIView.main(args);
    }

}
