package uml;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.*;

/**
 * Creates the GUI environment for the user when using the GUI version of the diagram
 *
 * @authors AlarmSquad
 */
public class GUIView extends Application {

    /**
     * Starts the initial window for the diagram
     *
     * @param stage the stage of the diagram
     */
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        root.getChildren().add(createDiagramMenu());
        Scene window = new Scene(root, Color.DIMGRAY);
        stage.setTitle("UML Editor");
        stage.setWidth(900);
        stage.setHeight(600);
        stage.setScene(window);
        stage.show();
    }

    /**
     * Creates the menu bar at the top of the screen, and gives the menu options functionality
     *
     * @return a VBox which encapsulates the menu
     */
    private VBox createDiagramMenu() {
        // create the menu bar
        MenuBar menuBar = new MenuBar();
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

    private void addClassWindow() {
        Stage stage = new Stage();
        Group root = new Group();
        Label label = new Label("Enter class name: ");
        Button add = new Button("Add");
        TextField text = new TextField();
        Button cancel = new Button("Cancel");
        String input = text.getText();
        add.setOnAction(GUIController.addClassAction(input));
        text.setPrefColumnCount(14);
        cancel.setOnAction(GUIController.cancelAction(stage));

        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(add, 0, 1);
        pane.add(cancel, 1, 1);

        BorderPane border = new BorderPane();
        border.setCenter(pane);

        root.getChildren().add(border);
        stage.setTitle("Add Class");
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setResizable(false);
        Scene window = new Scene(root);
        stage.setScene(window);
        stage.show();
    }

    private void saveWindow() {
        Stage stage = new Stage();
        Group root = new Group();

        Label label = new Label("Enter file name: ");
        Button save = new Button("Save");
        TextField text = new TextField();
        Button cancel = new Button("Cancel");
        save.setOnAction(GUIController.saveAction(text.getText()));
        text.setPrefColumnCount(14);
        cancel.setOnAction(GUIController.cancelAction(stage));

        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(10);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(save, 0, 1);
        pane.add(cancel, 1, 1);

        BorderPane border = new BorderPane();
        border.setCenter(pane);

        root.getChildren().add(border);
        stage.setTitle("Save Project");
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setResizable(false);
        Scene window = new Scene(root);
        stage.setScene(window);
        stage.show();
    }

    public static void popUpWindow(String title, String text) {
        Stage stage = new Stage();
        Group root = new Group();
        Label message = new Label(text);
        Button cancel = new Button("Okay");
        cancel.setOnAction(GUIController.cancelAction(stage));
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.add(message, 0, 0);
        pane.add(cancel, 1, 0);
        pane.setHgap(5);
        root.getChildren().add(pane);
        Scene window = new Scene(root);
        stage.setTitle(title);
        stage.setWidth(300);
        stage.setHeight(100);
        stage.setScene(window);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
