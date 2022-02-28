package uml;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Creates the GUI environment for the user when using the GUI version of the diagram
 *
 * @author AlarmSquad
 */
public class GUIView extends Application {

    // create the menu bar
    private MenuBar menuBar = new MenuBar();

    /**
     * Starts the initial window for the diagram
     *
     * @param stage the stage of the diagram
     */
    @Override
    public void start(Stage stage) {
        // initialize the root
        Group root = new Group();
        // add the menu to the root
        root.getChildren().add(createDiagramMenu());
        // initialize the window, and set the color
        Scene window = new Scene(root, Color.DIMGRAY);
        // set the title, height, and width of the window
        stage.setTitle("UML Editor");
        stage.setWidth(900);
        stage.setHeight(600);
        // set the stage and show it
        stage.setScene(window);
        stage.show();
    }

    /**
     * Creates the menu bar at the top of the screen, and gives the menu options functionality
     *
     * @return a VBox which encapsulates the menu
     */
    private VBox createDiagramMenu() {
        // create the file menu and its menu items
        Menu file = new Menu("File");
        MenuItem save = new MenuItem("Save");
        // if the save button is pressed, open a new window to save
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                saveWindow();
            }
        });
        MenuItem load = new MenuItem("Load");
        file.getItems().addAll(save, load);

        // create the add menu and its menu items
        Menu add = new Menu("Add");
        MenuItem addClass = new MenuItem("Add Class");
        // if the add class button is pressed, open a new window to add class
        addClass.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                addClassWindow();
            }
        });
        MenuItem addAttribute = new MenuItem("Add Attribute");
        MenuItem addRelationship = new MenuItem("Add Relationship");
        add.getItems().addAll(addClass, addAttribute, addRelationship);

        // create the delete menu and its menu items
        Menu delete = new Menu("Delete");
        MenuItem deleteClass = new MenuItem("Delete Class");
        MenuItem deleteAttribute = new MenuItem("Delete Attribute");
        MenuItem deleteRelationship = new MenuItem("Delete Relationship");
        delete.getItems().addAll(deleteClass, deleteAttribute, deleteRelationship);

        // create the rename menu and its menu items
        Menu rename = new Menu("Rename");
        MenuItem renameClass = new MenuItem("Rename Class");
        MenuItem renameAttribute = new MenuItem("Rename Attribute");
        MenuItem renameRelationship = new MenuItem("Rename Relationship");
        rename.getItems().addAll(renameClass, renameAttribute, renameRelationship);

        // create the edit menu and add the add, delete, and rename menus to it
        Menu edit = new Menu("Edit");
        edit.getItems().addAll(add, delete, rename);

        // create the help menu and its menu item
        Menu help = new Menu("Help");
        MenuItem showCommands = new MenuItem("Show Commands");
        help.getItems().add(showCommands);

        // adds all menus to the menu bar
        menuBar.getMenus().addAll(file, edit, help);

        // create the vbox using the menu bar
        VBox vBox = new VBox(menuBar);

        return vBox;
    }

    /******************************************************************
     *
     * Start of window methods
     *
     ******************************************************************/

    /**
     * Creates a window for the add class method and its data
     */
    private void addClassWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        // create a label for the text box
        Label label = new Label("Enter class name: ");
        // create the text box for user input
        TextField text = new TextField();
        text.setPrefColumnCount(13);
        // create a button to add the class with the inputted name
        Button add = new Button("Add");
        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                GUIController.addClassAction(text.getText(), stage);
            }
        });
        // create a button to cancel out of the window
        Button cancel = new Button("Cancel");
        cancel.setOnAction(GUIController.cancelAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(add, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Add Class", 125, 300);
    }

    /**
     * Creates a window for the save method and its data
     */
    private void saveWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();

        // create a label for the text box
        Label label = new Label("Enter file name: ");
        // create a text box for user input
        TextField text = new TextField();
        text.setPrefColumnCount(14);
        // create a button to save the diagram to a file with the inputted name
        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                GUIController.saveAction(text.getText(), stage);
            }
        });
        // create a button to cancel out of the window
        Button cancel = new Button("Cancel");
        cancel.setOnAction(GUIController.cancelAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(save, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Save Project", 125, 300);
    }

    /**
     * Creates a standard popup window for telling the user something
     *
     * @param title the title of the popup window
     * @param text  the text to be displayed in the popup
     */
    public static void popUpWindow(String title, String text) {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        // create a message for the user
        Label message = new Label(text);
        // create a button to cancel out of the window
        Button cancel = new Button("Okay");
        cancel.setOnAction(GUIController.cancelAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(message, 0, 0);
        pane.add(cancel, 1, 0);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, title, 100, 300);
    }

    /******************************************************************
     *
     * End of window methods
     *
     ******************************************************************/

    public void disableMenu(Stage stage) {
        for (Menu m : menuBar.getMenus()) {
            for (MenuItem mi : m.getItems()) {
                if (stage.isShowing()) {
                    mi.setDisable(true);
                } else {
                    mi.setDisable(false);
                }
            }
        }
    }

    public static void updateClass() {

    }

    public static void updateRelationships() {

    }

    /**
     * Takes in many parameters from the window and gives it standardized formatting,
     * then finalizes and shows it
     *
     * @param stage the stage of the window
     * @param root the group of objects in the window
     * @param pane the pane of objects
     * @param title the title of the window
     * @param height the height of the window
     * @param width the width of the window
     */
    private static void finalizeWindow(Stage stage, Group root, GridPane pane, String title, int height, int width) {
        pane.setHgap(5);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        stage.setTitle(title);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);
        root.getChildren().add(pane);
        Scene window = new Scene(root);
        stage.setScene(window);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
