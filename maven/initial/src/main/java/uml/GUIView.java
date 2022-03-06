package uml;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Creates the GUI environment for the user when using the GUI version of the diagram
 *
 * @author AlarmSquad
 */
public class GUIView extends Application {

    // create the menu bar
    private final MenuBar menuBar = new MenuBar();
    // initialize the root
    static Group superRoot = new Group();

    static final double DIAGRAM_WIDTH = 950;
    static final double DIAGRAM_HEIGHT = 640;

    static double boxWidth = 100;
    static double boxHeight = 20;

    static double startDragX;
    static double startDragY;

    static ArrayList<ClassBox> classBoxList = new ArrayList<>();
    static ArrayList<RelLine> lineList = new ArrayList<RelLine>();

    /**
     * Starts the initial window for the diagram
     *
     * @param stage the stage of the diagram
     */
    @Override
    public void start(Stage stage) {

        // add the menu to the root
        superRoot.getChildren().add(createDiagramMenu());
        // initialize the window, and set the color
        Scene window = new Scene(superRoot, Color.DIMGRAY);
        // set the title, height, and width of the window
        stage.setTitle("UML Editor");
        stage.setWidth(DIAGRAM_WIDTH);
        stage.setHeight(DIAGRAM_HEIGHT);
        stage.setResizable(false);
        // set the stage and show it
        stage.setScene(window);
        stage.setAlwaysOnTop(false);
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        stage.show();
        stage.setResizable(false);
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
        save.setOnAction(event -> saveWindow());
        MenuItem load = new MenuItem("Load");
        // if the load button is pressed, open a new window to load
        load.setOnAction(event -> loadWindow());
        file.getItems().addAll(save, load);

        //***************************************//
        //********** Add Menu Items *************//
        //***************************************//

        // create the add menu and its menu items
        Menu add = new Menu("Add");
        MenuItem addClass = new MenuItem("Add Class");
        // if the add class button is pressed, open a new window to add class
        addClass.setOnAction(event -> addClassWindow());
        // create the add attribute menu and its menu items
        Menu addAttribute = new Menu("Add Attribute");
        // if the add field button is pressed, open a new window to add field
        MenuItem addField = new MenuItem("Add Field");
        addField.setOnAction(event -> addFieldWindow());
        // if the add method button is pressed, open a new window to add method
        MenuItem addMethod = new MenuItem("Add Method");
        addMethod.setOnAction(event -> addMethodWindow());
        addAttribute.getItems().addAll(addField, addMethod);
        // if the add relationship button is pressed, open a new window to add relationship
        MenuItem addRel = new MenuItem("Add Relationship");
        addRel.setOnAction(event -> addRelWindow());
        // if the add parameter button is pressed, open a new window to add parameter
        MenuItem addParam = new MenuItem("Add Parameter(s)");
        addParam.setOnAction(event -> addParameterWindow());
        add.getItems().addAll(addClass, addAttribute, addRel, addParam);

        //***************************************//
        //********** Delete Menu Items **********//
        //***************************************//

        // create the delete menu and its menu items
        Menu delete = new Menu("Delete");
        // Menu item for deleting a class, when clicked, launch delete class window
        MenuItem deleteClass = new MenuItem("Delete Class");
        deleteClass.setOnAction(event -> deleteClassWindow());
        // create the delete attribute menu and its menu items
        Menu deleteAttribute = new Menu("Delete Attribute");
        // if the delete field button is pressed, open a new window to delete field
        MenuItem deleteField = new MenuItem("Delete Field");
        deleteField.setOnAction(event -> deleteFieldWindow());
        // if the delete method button is pressed, open a new window to delete method
        MenuItem deleteMethod = new MenuItem("Delete Method");
        deleteMethod.setOnAction(event -> deleteMethodWindow());
        deleteAttribute.getItems().addAll(deleteField, deleteMethod);
        // Menu item for deleting a relationship,
        // when clicked, launch delete relationship window
        MenuItem deleteRel = new MenuItem("Delete Relationship");
        deleteRel.setOnAction(event -> deleteRelWindow());

        delete.getItems().addAll(deleteClass, deleteAttribute, deleteRel);

        //***************************************//
        //******* Rename/Change Menu Items ******//
        //***************************************//

        // create the rename menu and its menu items
        Menu rename = new Menu("Rename");
        MenuItem renameClass = new MenuItem("Rename Class");
        // if the rename class button is pressed, open a new window to rename class
        renameClass.setOnAction(event -> renameClassWindow());
        // create the rename attribute menu and its menu items
        Menu renameAttribute = new Menu("Rename Attribute");
        // if the rename field button is pressed, open a new window to rename field
        MenuItem renameField = new MenuItem("Rename Field");
        renameField.setOnAction(event -> renameFieldWindow());
        // if the rename method button is pressed, open a new window to rename method
        MenuItem renameMethod = new MenuItem("Rename Method");
        renameMethod.setOnAction(event -> renameMethodWindow());
        renameAttribute.getItems().addAll(renameField, renameMethod);

        // adds all the rename menu items to rename option
        rename.getItems().addAll(renameClass, renameAttribute);

        Menu change = new Menu("Change");
        // open new window for changing a parameter when the option is pressed
        MenuItem changeParam = new MenuItem("Change Parameter(s)");
        changeParam.setOnAction(event -> changeParamWindow());
        // open new window for changing the relType when the option is pressed
        MenuItem changeRelType = new MenuItem("Change Relationship Type");
        changeRelType.setOnAction(event -> changeRelTypeWindow());
        // all of the change options as part of the edit menu
        change.getItems().addAll(changeParam, changeRelType);

        // create the edit menu and add the 'add', 'delete', and 'rename' menus to it
        Menu edit = new Menu("Edit");
        // all of the options that are part of the edit menu
        edit.getItems().addAll(add, delete, rename, change);

        // create the help menu and its menu item
        Menu help = new Menu("Help");
        MenuItem showCommands = new MenuItem("Show Commands");
        showCommands.setOnAction(event -> showCommandsWindow());
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
        cancel.setOnAction(event -> GUIController.exitAction(stage));
        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(save, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Save Project", 120, 300);
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
        cancel.setOnAction(event -> GUIController.exitAction(stage));
        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(load, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Load Project", 120, 300);
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
        cancel.setOnAction(event -> GUIController.exitAction(stage));
        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(add, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Add Class", 120, 300);
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
        add.setOnAction(event -> GUIController.addFieldAction(className.getText(), fieldName.getText(), fieldType.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

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
        add.setOnAction(event -> GUIController.addMethodAction(className.getText(), methodName.getText(), returnType.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

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
        cancel.setOnAction(event -> GUIController.exitAction(stage));

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
     * Creates a window for the add parameter method and its data
     */
    private void addParameterWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class
        Label classNameText = new Label("Class name: ");
        // create a label for the text box for the method name
        Label methodNameText = new Label("Method name: ");
        // create a label for the text box for the parameter name
        Label paramText = new Label("Parameter name: ");
        // create a label for the text box for the parameter type
        Label paramTypeText = new Label("Parameter type: ");
        // create the text box for the class name
        TextField className = new TextField();
        className.setPrefColumnCount(14);
        // create the text box for the method name
        TextField methodName = new TextField();
        methodName.setPrefColumnCount(14);
        // create the text box for the parameter name
        TextField paramName = new TextField();
        paramName.setPrefColumnCount(14);
        // create the text box for the parameter type
        TextField paramType = new TextField();
        paramType.setPrefColumnCount(14);
        // create a button to add the method with the inputted name
        Button add = new Button("Add");
        add.setOnAction(event -> GUIController.addParameterAction(className.getText(), methodName.getText(),
                paramName.getText(), paramType.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(classNameText, 0, 0);
        pane.add(methodNameText, 0, 1);
        pane.add(paramText, 0, 2);
        pane.add(paramTypeText, 0, 3);
        pane.add(className, 1, 0);
        pane.add(methodName, 1, 1);
        pane.add(paramName, 1, 2);
        pane.add(paramType, 1, 3);
        pane.add(add, 0, 4);
        pane.add(cancel, 1, 4);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Add Parameter(s)", 225, 300);
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
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(label, 0, 0);
        pane.add(text, 1, 0);
        pane.add(delete, 0, 1);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Delete Class", 120, 300);
    }

    /**
     * Creates a window for the delete field method and its data
     */
    private void deleteFieldWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class
        Label classNameText = new Label("Class name: ");
        // create a label for the text box for the field name
        Label fieldNameText = new Label("Field name: ");
        // create the text box for the class name
        TextField className = new TextField();
        className.setPrefColumnCount(16);
        // create the text box for the field name
        TextField fieldName = new TextField();
        fieldName.setPrefColumnCount(16);
        // create a button to add the field with the inputted name
        Button delete = new Button("Delete");
        delete.setOnAction(event -> GUIController.deleteFieldAction(className.getText(), fieldName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(classNameText, 0, 0);
        pane.add(fieldNameText, 0, 1);
        pane.add(className, 1, 0);
        pane.add(fieldName, 1, 1);
        pane.add(delete, 0, 2);
        pane.add(cancel, 1, 2);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Delete Field", 155, 300);
    }

    /**
     * Creates a window for the delete method method and its data
     */
    private void deleteMethodWindow() {
    // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class
        Label classNameText = new Label("Class name: ");
        // create a label for the text box for the method name
        Label fieldNameText = new Label("Method name: ");
        // create the text box for the class name
        TextField className = new TextField();
        className.setPrefColumnCount(15);
        // create the text box for the method name
        TextField methodName = new TextField();
        methodName.setPrefColumnCount(15);
        // create a button to add the field with the inputted name
        Button delete = new Button("Delete");
        delete.setOnAction(event -> GUIController.deleteMethodAction(className.getText(), methodName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(classNameText, 0, 0);
        pane.add(fieldNameText, 0, 1);
        pane.add(className, 1, 0);
        pane.add(methodName, 1, 1);
        pane.add(delete, 0, 2);
        pane.add(cancel, 1, 2);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Delete Method", 155, 300);
    }

    /**
     * Creates a window for the delete relationship method and its data
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
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(srcNameText, 0, 0);
        pane.add(destNameText, 0, 1);
        pane.add(srcName, 1, 0);
        pane.add(destName, 1, 1);
        pane.add(delete, 0, 3);
        pane.add(cancel, 1, 3);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Delete Relationship", 165, 300);
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
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(classLabel, 0, 0);
        pane.add(nameLabel, 0, 1);
        pane.add(classToRename, 1, 0);
        pane.add(newClassName, 1, 1);
        pane.add(rename, 0, 2);
        pane.add(cancel, 1, 2);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Rename Class", 155, 300);
    }

    /**
     * Creates a window for the rename field method and its data
     */
    private void renameFieldWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class with the field
        Label givenClassLabel = new Label("Class name: ");
        // create a label for the text box for the old field name
        Label fieldLabel = new Label("Old field name: ");
        // create a label for the text box for the new field name
        Label nameLabel = new Label("New field name: ");
        // create the text box for the class with the field
        TextField givenClass = new TextField();
        givenClass.setPrefColumnCount(14);
        // create the text box for the field to be renamed
        TextField fieldToRename = new TextField();
        fieldToRename.setPrefColumnCount(14);
        // create the text box for the new field name
        TextField newFieldName = new TextField();
        newFieldName.setPrefColumnCount(14);
        // create a button to add the field with the inputted name
        Button rename = new Button("Rename");
        rename.setOnAction(event -> GUIController.renameFieldAction(givenClass.getText(),
                                    fieldToRename.getText(), newFieldName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(givenClassLabel, 0, 0);
        pane.add(fieldLabel, 0, 1);
        pane.add(nameLabel, 0, 2);
        pane.add(givenClass, 1, 0);
        pane.add(fieldToRename, 1, 1);
        pane.add(newFieldName, 1, 2);
        pane.add(rename, 0, 3);
        pane.add(cancel, 1, 3);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Rename Field", 190, 300);
    }

    /**
     * Creates a window for the rename method method and its data
     */
    private void renameMethodWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class with the method
        Label givenClassLabel = new Label("Class name: ");
        // create a label for the text box for the old method name
        Label methodLabel = new Label("Old method name: ");
        // create a label for the text box for the new method name
        Label nameLabel = new Label("New method name: ");
        // create the text box for the class with the method
        TextField givenClass = new TextField();
        givenClass.setPrefColumnCount(12);
        // create the text box for the method to be renamed
        TextField methodToRename = new TextField();
        methodToRename.setPrefColumnCount(12);
        // create the text box for the new method name
        TextField newMethodName = new TextField();
        newMethodName.setPrefColumnCount(12);
        // create a button to add the method with the inputted name
        Button rename = new Button("Rename");
        rename.setOnAction(event -> GUIController.renameMethodAction(givenClass.getText(),
                methodToRename.getText(), newMethodName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(givenClassLabel, 0, 0);
        pane.add(methodLabel, 0, 1);
        pane.add(nameLabel, 0, 2);
        pane.add(givenClass, 1, 0);
        pane.add(methodToRename, 1, 1);
        pane.add(newMethodName, 1, 2);
        pane.add(rename, 0, 3);
        pane.add(cancel, 1, 3);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Rename Method", 190, 300);
    }

    /**
     * Creates a window for the change parameter method and its data
     */
    private void changeParamWindow() {
        // initialize the stage and root
        Stage stage = new Stage();
        Group root = new Group();
        stage.setAlwaysOnTop(true);
        // create a label for the text box for the class with the method
        Label givenClassLabel = new Label("Class name: ");
        // create a label for the text box for the method with the parameter
        Label givenMethodLabel = new Label("Method name: ");
        // create a label for the text box for the old parameter name
        Label paramLabel = new Label("Old parameter name: ");
        // create a label for the text box for the new parameter name
        Label nameLabel = new Label("New parameter name: ");
        // create a label for the text box for the new parameter type
        Label typeLabel = new Label("New parameter type: ");
        // create the text box for the class with the method
        TextField givenClass = new TextField();
        givenClass.setPrefColumnCount(11);
        // create the text box for the method with the parameter
        TextField givenMethod = new TextField();
        givenMethod.setPrefColumnCount(11);
        // create the text box for the parameter to change
        TextField paramToChange = new TextField();
        paramToChange.setPrefColumnCount(11);
        // create the text box for the new parameter name
        TextField newParamName = new TextField();
        newParamName.setPrefColumnCount(11);
        // create the text box for the new parameter type
        TextField newParamType = new TextField();
        newParamType.setPrefColumnCount(11);
        // create a button to add the method with the inputted name
        Button change = new Button("Change");
        change.setOnAction(event -> GUIController.changeParameterAction(givenClass.getText(), givenMethod.getText(),
                paramToChange.getText(), newParamName.getText(), newParamType.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(givenClassLabel, 0, 0);
        pane.add(givenMethodLabel, 0, 1);
        pane.add(paramLabel, 0, 2);
        pane.add(nameLabel, 0, 3);
        pane.add(typeLabel, 0, 4);
        pane.add(givenClass, 1, 0);
        pane.add(givenMethod, 1, 1);
        pane.add(paramToChange, 1, 2);
        pane.add(newParamName, 1, 3);
        pane.add(newParamType, 1, 4);
        pane.add(change, 0, 5);
        pane.add(cancel, 1, 5);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Change Parameter(s)", 260, 300);
    }

    /**
     * Creates a window for the change relationship method and its data
     */
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
        srcName.setPrefColumnCount(16);
        // create the text box for the new type name
        TextField destName = new TextField();
        destName.setPrefColumnCount(16);
        // create the text box for the type to be renamed
        TextField oldRelTypeName = new TextField();
        oldRelTypeName.setPrefColumnCount(16);
        // create the text box for the new type name
        TextField newRelTypeName = new TextField();
        newRelTypeName.setPrefColumnCount(16);
        // create a button to add the class with the inputted name
        Button rename = new Button("Change");
        rename.setOnAction(event -> GUIController.changeRelTypeAction(srcName.getText(),
                        destName.getText(), oldRelTypeName.getText(), newRelTypeName.getText(), stage));
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));

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
        finalizeWindow(stage, root, pane, "Change Relationship Type", 225, 300);
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
        cancel.setOnAction(event -> GUIController.exitAction(stage));

        // create the pane for the object and adds it
        GridPane pane = new GridPane();
        pane.add(commandList, 0, 0);
        pane.add(cancel, 1, 1);

        // finalize the window with standard formatting
        finalizeWindow(stage, root, pane, "Show Commands", 400, 615);
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
        stage.initModality(Modality.APPLICATION_MODAL);
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

    /**
     * Takes in the source and destination class names and a line color
     *
     * @param src the source class name
     * @param dest the destination class name
     * @param color the line color
     */
    public static void drawLine(String src, String dest, Color color){
        ClassBox source = null;
        ClassBox destination = null;
        // search through the class box list for the source and destination classes
        for(ClassBox box : classBoxList){
            if(box.getClassBoxName().equals(src)){
                source = box;
            }
            if(box.getClassBoxName().equals(dest)){
                destination = box;
            }
        }

        assert source != null;
        assert destination != null;
        //source.getClassPane().setTranslateX(source.getBoxWidth() / 2);
        //destination.getClassPane().setTranslateX(destination.getBoxWidth() / 2);
        // draw a new line that connects to the source and destination class boxes
        RelLine newRelLine = new RelLine(source, destination, color);
        // add the new line to the line list and the super root
        lineList.add(newRelLine);
        superRoot.getChildren().add(0, newRelLine.getLine());
    }

    /**
     * Takes in a class name and draws a new class box object
     *
     * @param className the name of the class
     */
    public static void drawClassBox(String className, int xOffset, int yOffset)  {
        // create a class box object, add it to the class box list and the super root
        ClassBox classBox = new ClassBox(className);
        superRoot.getChildren().add(classBox.getClassPane());
        classBoxList.add(classBox);

        classBox.getClassPane().setTranslateX(classBox.getBoxWidth() + xOffset);
        classBox.getClassPane().setTranslateY(classBox.getBoxHeight() + yOffset);
        // when the box is clicked on begin drag with mouse
        classBox.getClassPane().setOnMouseDragEntered(event -> {
            startDragX = event.getSceneX();
            startDragY = event.getSceneY();
        });

        // keep box under mouse as the mouse continues to drag
        classBox.getClassPane().setOnMouseDragged(event -> {
            classBox.getClassPane().setTranslateX(event.getSceneX() - startDragX);
            classBox.getClassPane().setTranslateY(event.getSceneY() - startDragY);
        });
    }

    public static void drawFieldBox(int fieldListSize, int methListSize, Field field, String className) {
        ClassBox box = findClassBox(className);
        box.setBoxHeight(box.getBoxHeight() + 15);
        box.addText(field, fieldListSize, methListSize, false);
    }

    public static void drawMethodBox(int fieldListSize, int methListSize, Method meth, String className) {
        ClassBox box = findClassBox(className);
        box.setBoxHeight(box.getBoxHeight() + 15);
        box.addText(meth, fieldListSize, methListSize, false);
    }

    /**
     * Takes in a class name from the classlist, searches through the
     * class box list for a class box object with the same name as className
     *
     * @param className the name of the class
     * @return the classbox object with the same class name
     */
    public static ClassBox findClassBox(String className){
        ClassBox classbox = null;
        for(ClassBox box : classBoxList) {
            if (box.getClassBoxName().equals(className)) {
                classbox = box;
            }
        }
        return classbox;
    }

    /**
     * Takes in a source class box object and a destination class
     * box object, searches through the list of relationship lines
     * and deletes the line that matches the source and destination
     *
     * @param src the source class box object
     * @param dest the destination class box object
     */
    public static void deleteRelLine (ClassBox src, ClassBox dest){
        for(int i = 0; i < lineList.size(); ++i){
            if(lineList.get(i).getCBSrc().equals(src)){
                if(lineList.get(i).getCBDest().equals(dest)){
                    superRoot.getChildren().remove(lineList.get(i).getLine());
                    lineList.remove(lineList.get(i));
                }
            }
        }
    }

    //***************************************//
    //*********** GUI View Main *************//
    //***************************************//

    // launch the gui
    public static void main(String[] args) {
        launch(args);
    }
}
