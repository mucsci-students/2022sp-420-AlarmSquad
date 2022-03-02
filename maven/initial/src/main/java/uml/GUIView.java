package uml;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;

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
        stage.setAlwaysOnTop(false);
        menuBar.prefWidthProperty().bind(stage.widthProperty());

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
        save.setOnAction(event -> {
            saveWindow();
            // if the window is open, disable all other menu items until closed
            if(save.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        MenuItem load = new MenuItem("Load");
        // if the load button is pressed, open a new window to load
        load.setOnAction(event -> {
            loadWindow();
            // if the window is open, disable all other menu items until closed
            if(load.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        file.getItems().addAll(save, load);

        //***************************************//
        //********** Add Menu Items *************//
        //***************************************//

        // create the add menu and its menu items
        Menu add = new Menu("Add");
        MenuItem addClass = new MenuItem("Add Class");
        // if the add class button is pressed, open a new window to add class
        addClass.setOnAction(event -> {
            addClassWindow();
            // if the window is open, disable all other menu items until closed
            if(addClass.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        // create the add attribute menu and its menu items
        Menu addAttribute = new Menu("Add Attribute");
        // if the add field button is pressed, open a new window to add field
        MenuItem addField = new MenuItem("Add Field");
        addField.setOnAction(event -> {
            addFieldWindow();
            // if the window is open, disable all other menu items until closed
            if(addField.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        // if the add method button is pressed, open a new window to add method
        MenuItem addMethod = new MenuItem("Add Method");
        addMethod.setOnAction(event -> {
            addMethodWindow();
            // if the window is open, disable all other menu items until closed
            if(addMethod.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        addAttribute.getItems().addAll(addField, addMethod);
        // if the add relationship button is pressed, open a new window to add relationship
        MenuItem addRel = new MenuItem("Add Relationship");
        addRel.setOnAction(event -> {
            addRelWindow();
            // if the window is open, disable all other menu items until closed
            if(addRel.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        add.getItems().addAll(addClass, addAttribute, addRel);

        //***************************************//
        //********** Delete Menu Items **********//
        //***************************************//

        // create the delete menu and its menu items
        Menu delete = new Menu("Delete");
        // Menu item for deleting a class, when clicked, launch delete class window
        MenuItem deleteClass = new MenuItem("Delete Class");
        deleteClass.setOnAction(event -> {
            deleteClassWindow();
            // if the window is open, disable all other menu items until closed
            if(deleteClass.isVisible()) {
                menuBar.setDisable(true);
            }
        });

        MenuItem deleteAttribute = new MenuItem("Delete Attribute");
        // Menu item for deleting a relationship,
        // when clicked, launch delete relationship window
        MenuItem deleteRel = new MenuItem("Delete Relationship");
        deleteRel.setOnAction(event -> {
            deleteRelWindow();
            // if the window is open, disable all other menu items until closed
            if(deleteRel.isVisible()) {
                menuBar.setDisable(true);
            }
        });

        delete.getItems().addAll(deleteClass, deleteAttribute, deleteRel);

        //***************************************//
        //******* Rename/Change Menu Items ******//
        //***************************************//

        // create the rename menu and its menu items
        Menu rename = new Menu("Rename");
        MenuItem renameClass = new MenuItem("Rename Class");
        // if the rename class button is pressed, open a new window to rename class
        renameClass.setOnAction(event -> {
            renameClassWindow();
            // if the window is open, disable all other menu items until closed
            if(renameClass.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        MenuItem renameAttribute = new MenuItem("Rename Attribute");

        // adds all of the rename menu items to rename option
        rename.getItems().addAll(renameClass, renameAttribute);

        Menu change = new Menu("Change");
        MenuItem changeParam = new MenuItem("Parameter(s)");
        // open new window for changing the relType when the option is pressed
        MenuItem changeRelType = new MenuItem("Relationship Type");
        changeRelType.setOnAction(event -> {
            changeRelTypeWindow();
            // if the window is open, disable all other menu items until closed
            if(changeRelType.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        // all of the change options as part of the edit menu
        change.getItems().addAll(changeParam, changeRelType);

        // create the edit menu and add the 'add', 'delete', and 'rename' menus to it
        Menu edit = new Menu("Edit");
        // all of the options that are part of the edit menu
        edit.getItems().addAll(add, delete, rename, change);

        // create the help menu and its menu item
        Menu help = new Menu("Help");
        MenuItem showCommands = new MenuItem("Show Commands");
        showCommands.setOnAction(event -> {
            showCommandsWindow();
            // if the window is open, disable all other menu items until closed
            if(showCommands.isVisible()) {
                menuBar.setDisable(true);
            }
        });
        help.getItems().add(showCommands);

        // adds all menus to the menu bar
        menuBar.getMenus().addAll(file, edit, help);

        // return the vbox using the menu bar
        return new VBox(menuBar);
    }

    /******************************************************************
     *
     * Start of window methods
     *
     ******************************************************************/

    /**
     * Creates a window for the save method and its data
     */
    private void saveWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box
        Label label = new Label("File name: ");
        // create a text box for user input
        TextField text = new TextField();
        text.setPrefColumnCount(17);
        // create a button to save the diagram to a file with the inputted name
        Button save = new Button("Save");
        save.setOnAction(event -> GUIController.saveAction(text.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);
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
     * Creates a window for the load method and its data
     */
    private void loadWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box
        Label label = new Label("File name: ");
        // create a text box for user input
        TextField text = new TextField();
        text.setPrefColumnCount(17);
        // create a button to load the diagram of a file with the inputted name
        Button load = new Button("Load");
        load.setOnAction(event -> GUIController.loadAction(text.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);
        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(load, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Load Project", 125, 300);
    }

    /**
     * Creates a window for the add class method and its data
     */
    private void addClassWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box
        Label label = new Label("Class name: ");
        // create the text box for user input
        TextField text = new TextField();
        text.setPrefColumnCount(16);
        // create a button to add the class with the inputted name
        Button add = new Button("Add");
        add.setOnAction(event -> GUIController.addClassAction(text.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);
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
     * Creates a window for the add field method and its data
     */
    private void addFieldWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class
        Label classNameText = new Label("Class name: ");
        // create a label for the text box for the field name
        Label fieldNameText = new Label("Field name: ");
        // create a label for the text box for the field type
        Label fieldTypeText = new Label("Field type: ");
        // create the text box for the class name
        TextField className = new TextField();
        className.setPrefColumnCount(16);
        // create the text box for the field name
        TextField fieldName = new TextField();
        fieldName.setPrefColumnCount(16);
        // create the text box for the field type
        TextField fieldType = new TextField();
        fieldType.setPrefColumnCount(16);
        // create a button to add the field with the inputted name
        Button add = new Button("Add");
        add.setOnAction(event -> {
            //TODO create add field function in GUIController
            // GUIController.addFieldAction(className.getText(), fieldName.getText(), fieldType.getText(), stage);
        });
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(classNameText, 0, 0);
        pane.add(fieldNameText, 0, 1);
        pane.add(fieldTypeText, 0, 2);
        pane.add(className, 1, 0);
        pane.add(fieldName, 1, 1);
        pane.add(fieldType, 1, 2);
        pane.add(add, 0, 3);
        pane.add(cancel, 1, 3);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Add Field", 190, 300);
    }

    /**
     * Creates a window for the add method method and its data
     */
    private void addMethodWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class
        Label classNameText = new Label("Class name: ");
        // create a label for the text box for the method name
        Label methodNameText = new Label("Method name: ");
        // create a label for the text box for the return type
        Label returnTypeText = new Label("Return type: ");
        // create the text box for the class name
        TextField className = new TextField();
        className.setPrefColumnCount(14);
        // create the text box for the method name
        TextField methodName = new TextField();
        methodName.setPrefColumnCount(14);
        // create the text box for the return type
        TextField returnType = new TextField();
        returnType.setPrefColumnCount(14);
        // create a button to add the method with the inputted name
        Button add = new Button("Add");
        add.setOnAction(event -> {
            //TODO create add method function in GUIController
            // GUIController.addMethodAction(className.getText(), methodName.getText(), returnType.getText(), stage);
        });
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(classNameText, 0, 0);
        pane.add(methodNameText, 0, 1);
        pane.add(returnTypeText, 0, 2);
        pane.add(className, 1, 0);
        pane.add(methodName, 1, 1);
        pane.add(returnType, 1, 2);
        pane.add(add, 0, 3);
        pane.add(cancel, 1, 3);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Add Method", 190, 300);
    }

    /**
     * Creates a window for the add relationship method and its data
     */
    private void addRelWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the source class name
        Label srcNameText = new Label("Source: ");
        // create a label for the text box for the destination class name
        Label destNameText = new Label("Destination: ");
        // create a label for the text box for the relationship type
        Label relTypeText = new Label("Relationship type: ");
        // create the text box for the source class name
        TextField srcName = new TextField();
        srcName.setPrefColumnCount(13);
        // create the text box for the destination class name
        TextField destName = new TextField();
        destName.setPrefColumnCount(13);
        // create the text box for the type of relationship
        TextField relType = new TextField();
        relType.setPrefColumnCount(13);
        // create a button to add the relationship with the correct src and dest names
        Button add = new Button("Add");
        add.setOnAction(event -> GUIController.addRelationshipAction(srcName.getText(),
                                    destName.getText(), relType.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(srcNameText, 0, 0);
        pane.add(destNameText, 0, 1);
        pane.add(relTypeText, 0, 2);
        pane.add(srcName, 1, 0);
        pane.add(destName, 1, 1);
        pane.add(relType, 1, 2);
        pane.add(add, 0, 3);
        pane.add(cancel, 1, 3);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Add Relationship", 190, 300);
    }

    /**
     * Creates a window for the delete class method and its data
     */
    private void deleteClassWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box
        Label label = new Label("Class name: ");
        // create the text box for user input
        TextField text = new TextField();
        text.setPrefColumnCount(16);
        // create a button to delete the class with the inputted name
        Button delete = new Button("Delete");
        delete.setOnAction(event -> GUIController.deleteClassAction(text.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(delete, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Delete Class", 125, 300);
    }

    /**
     * Creates a window for the add relationship method and its data
     */
    private void deleteRelWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the source class name
        Label srcNameText = new Label("Source: ");
        // create a label for the text box for the destination class name
        Label destNameText = new Label("Destination: ");
        // create the text box for the source class name
        TextField srcName = new TextField();
        srcName.setPrefColumnCount(13);
        // create the text box for the destination class name
        TextField destName = new TextField();
        destName.setPrefColumnCount(13);
        // create a button to delete the relationship with the correct src and dest names
        Button delete = new Button("Delete");
        delete.setOnAction(event -> GUIController.deleteRelAction(srcName.getText(),
                destName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(srcNameText, 0, 0);
        pane.add(destNameText, 0, 1);
        pane.add(srcName, 1, 0);
        pane.add(destName, 1, 1);
        pane.add(delete, 0, 3);
        pane.add(cancel, 1, 3);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Delete Relationship", 180, 300);
    }

    /**
     * Creates a window for the rename class method and its data
     */
    private void renameClassWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the old class name
        Label classLabel = new Label("Old class name: ");
        // create a label for the text box for the new class name
        Label nameLabel = new Label("New class name: ");
        // create the text box for the class to be renamed
        TextField classToRename = new TextField();
        classToRename.setPrefColumnCount(14);
        // create the text box for the new class name
        TextField newClassName = new TextField();
        newClassName.setPrefColumnCount(14);
        // create a button to add the class with the inputted name
        Button rename = new Button("Rename");
        rename.setOnAction(event -> GUIController.renameClassAction(classToRename.getText(), newClassName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(classLabel, 0, 0);
        pane.add(nameLabel, 0, 1);
        pane.add(classToRename, 1, 0);
        pane.add(newClassName, 1, 1);
        pane.add(rename, 0, 2);
        pane.add(cancel, 1, 2);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Rename Class", 160, 300);
    }

    private void changeRelTypeWindow(){
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the next text box for the source class
        Label srcLabel = new Label("Source: ");
        // create a label for the next text box for the source class
        Label destLabel = new Label("Destination: ");
        // create a label for the text box for the old type name
        Label relTypeLabel = new Label("Old type: ");
        // create a label for the text box for the new type name
        Label newRelTypeLabel = new Label("New type: ");
        // create the text box for the type to be renamed
        TextField srcName = new TextField();
        srcName.setPrefColumnCount(18);
        // create the text box for the new type name
        TextField destName = new TextField();
        destName.setPrefColumnCount(18);
        // create the text box for the type to be renamed
        TextField oldRelTypeName = new TextField();
        oldRelTypeName.setPrefColumnCount(18);
        // create the text box for the new type name
        TextField newRelTypeName = new TextField();
        newRelTypeName.setPrefColumnCount(18);
        // create a button to add the class with the inputted name
        Button rename = new Button("Change");
        rename.setOnAction(event -> GUIController.changeRelTypeAction(oldRelTypeName.getText(), newRelTypeName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(srcLabel, 0, 0);
        pane.add(destLabel, 0, 1);
        pane.add(relTypeLabel, 0, 2);
        pane.add(newRelTypeLabel, 0, 3);
        pane.add(srcName, 1, 0);
        pane.add(destName, 1, 1);
        pane.add(oldRelTypeName, 1, 2);
        pane.add(newRelTypeName, 1, 3);
        pane.add(rename, 0, 4);
        pane.add(cancel, 1, 4);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Change Relationship Type", 240, 325);
    }

    /**
     * Creates a window for the help method and its data
     */
    private void showCommandsWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the command list
        Text commandList = new Text(UMLModel.getGUIHelpMenu());
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        closeWindow(stage, menuBar, cancel);

        // create the pane for the object and adds it
        GridPane pane = new GridPane();
        pane.add(commandList, 0, 0);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Show Commands", 445, 390);
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
        stage.setAlwaysOnTop(true);
        // create a message for the user
        Label message = new Label(text);
        // create a button to cancel out of the window
        Button cancel = new Button("Okay");
        // **DO NOT change this cancel action (this method is static and...
        // won't work with the closeWindow helper method)**
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(message, 0, 0);
        pane.add(cancel, 1, 0);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, title, 100, 300);
    }

    /******************************************************************
     *
     * End of window methods & Start of helper methods
     *
     ******************************************************************/

    /**
     * Takes in the stage, menubar and cancel button to close the window and
     * enable the menu again with either cancel or [X]
     *
     * @param stage the current open stage
     * @param bar the menubar from main UML Editor stage
     * @param cancel the cancel button on the window
     */
    public void closeWindow(Stage stage, MenuBar bar, Button cancel){
        cancel.setOnAction(event -> {
            menuBar.setDisable(false);
            GUIController.exitAction(stage);
        });
        // when [X] is pressed, enable menu
        stage.setOnCloseRequest(event -> menuBar.setDisable(false));
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

    //***************************************//
    //*********** GUI View Main *************//
    //***************************************//

    // launch the gui
    public static void main(String[] args) {
        launch(args);
    }
}
