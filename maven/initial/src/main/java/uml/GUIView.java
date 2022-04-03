package uml;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
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
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("DanglingJavadoc")
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
    // diagram width and height for window
    static final double DIAGRAM_WIDTH = 950;
    static final double DIAGRAM_HEIGHT = 640;
    // coordinates for draging objects
    static double startDragX;
    static double startDragY;
    // class box and relationship line lists
    static ArrayList<ClassBox> classBoxList = new ArrayList<>();
    static ArrayList<RelLine> lineList = new ArrayList<>();
    static ArrayList<ArrayList<Line>> arrowList = new ArrayList<>();
    static Map<String, List<Double>> coordinateMap = new HashMap<>();
    static AffineTransform affineTransform = new AffineTransform();
    static FontRenderContext frc = new FontRenderContext(affineTransform, true, true);
    static Font font = new Font("Arial", Font.PLAIN, 12);

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

        //TODO refactor rename attribute to change?

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
        // disable all menu items that cannot be used until updateMenu is called
        addField.setDisable(true);
        addMethod.setDisable(true);
        addRel.setDisable(true);
        addParam.setDisable(true);
        deleteClass.setDisable(true);
        deleteField.setDisable(true);
        deleteMethod.setDisable(true);
        deleteRel.setDisable(true);
        renameClass.setDisable(true);
        renameField.setDisable(true);
        renameMethod.setDisable(true);
        changeParam.setDisable(true);
        changeRelType.setDisable(true);
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
        Stage stage = new Stage();
        // create a file chooser and set the text to represent the right file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        // get the selected file
        File file = fileChooser.showSaveDialog(stage);
        // if the file is not null, run the save action method
        if (file != null) {
            GUIController.saveAction(file, stage);
        }
    }

    /**
     * Creates a window for the load method and its data
     */
    private void loadWindow() {
        Stage stage = new Stage();
        // create a file chooser
        FileChooser fileChooser = new FileChooser();
        // get the selected file
        File file = fileChooser.showOpenDialog(stage);
        // if the file is not null, run the load action method and update the menus
        if (file != null) {
            GUIController.loadAction(file, stage);
            updateMenus();
        }
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
        add.setOnAction(event -> {
            GUIController.addClassAction(text.getText(), stage);
            updateMenus();
        });
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
        add.setOnAction(event -> {
            GUIController.addFieldAction(className.getText(), fieldName.getText(), fieldType.getText(), stage);
            updateMenus();
        });
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
        add.setOnAction(event -> {
            GUIController.addMethodAction(className.getText(), methodName.getText(), returnType.getText(), stage);
            updateMenus();
        });
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
        add.setOnAction(event -> {
            GUIController.addRelationshipAction(srcName.getText(),
                    destName.getText(), relType.getText(), stage);
            updateMenus();
        });
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
        add.setOnAction(event -> {
            GUIController.addParameterAction(className.getText(), methodName.getText(),
                    paramName.getText(), paramType.getText(), stage);
            updateMenus();
        });
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
        delete.setOnAction(event -> {
            GUIController.deleteClassAction(text.getText(), stage);
            updateMenus();
        });
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
        delete.setOnAction(event -> {
            GUIController.deleteFieldAction(className.getText(), fieldName.getText(), stage);
            updateMenus();
        });
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
        delete.setOnAction(event -> {
            GUIController.deleteMethodAction(className.getText(), methodName.getText(), stage);
            updateMenus();
        });
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
        delete.setOnAction(event -> {
            GUIController.deleteRelAction(srcName.getText(),
                    destName.getText(), stage);
            updateMenus();
        });
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
        rename.setOnAction(event -> {
            GUIController.renameClassAction(classToRename.getText(), newClassName.getText(), stage);
            updateMenus();
        });
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
        // create a label for the text box for the field type
//        Label fieldTypeText = new Label("Field type: ");
//        // create the text box for the class with the field
        TextField givenClass = new TextField();
        givenClass.setPrefColumnCount(14);
        // create the text box for the field to be renamed
        TextField fieldToRename = new TextField();
        fieldToRename.setPrefColumnCount(14);
        // create the text box for the field type
        TextField fieldType = new TextField();
        fieldType.setPrefColumnCount(14);
        // create the text box for the new field name
        TextField newFieldName = new TextField();
        newFieldName.setPrefColumnCount(14);
        // create a button to add the field with the inputted name
        Button rename = new Button("Rename");
        rename.setOnAction(event -> {
            GUIController.renameFieldAction(givenClass.getText(),
                    fieldToRename.getText(),
                    newFieldName.getText(), stage);
            updateMenus();
        });
        // create a button to cancel out of the window and enable menu again
        Button cancel = new Button("Cancel");
        cancel.setOnAction(event -> GUIController.exitAction(stage));
        // create the pane for the objects and add them all
        GridPane pane = new GridPane();
        pane.add(givenClassLabel, 0, 0);
        pane.add(fieldLabel, 0, 1);
        //pane.add(fieldTypeText, 0, 2);
        pane.add(nameLabel, 0, 2);
        pane.add(givenClass, 1, 0);
        pane.add(fieldToRename, 1, 1);
        //pane.add(fieldType, 1, 2);
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
        rename.setOnAction(event -> {
            GUIController.renameMethodAction(givenClass.getText(),
                    methodToRename.getText(), newMethodName.getText(), stage);
            updateMenus();
        });
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
        change.setOnAction(event -> {
            GUIController.changeParameterAction(givenClass.getText(), givenMethod.getText(),
                    paramToChange.getText(), newParamName.getText(), newParamType.getText(), stage);
            updateMenus();
        });
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
        rename.setOnAction(event -> {
            GUIController.changeRelTypeAction(srcName.getText(),
                    destName.getText(), oldRelTypeName.getText(), newRelTypeName.getText(), stage);
            updateMenus();
        });
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

    //***************************************//
    //*********** Drawing Objects ***********//
    //***************************************//

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
        // place class box in correct area
        classBox.getClassPane().setTranslateX(classBox.getBoxWidth() + xOffset);
        classBox.getClassPane().setTranslateY(classBox.getBoxHeight() + yOffset);
        // set the x and y to the right coordinates
        classBox.setX(classBox.getClassPane().getTranslateX());
        classBox.setY(classBox.getClassPane().getTranslateY());
        // when the box is clicked on begin drag with mouse
        classBox.getClassPane().setOnMouseDragEntered(event -> {
            startDragX = event.getSceneX();
            startDragY = event.getSceneY();
        });
        // keep box under mouse as the mouse continues to drag
        classBox.getClassPane().setOnMouseDragged(event -> {
            classBox.getClassPane().setTranslateX(event.getSceneX() - startDragX);
            classBox.getClassPane().setTranslateY(event.getSceneY() - startDragY);
            // set the x and y to the right coordinates
            classBox.setX(classBox.getClassPane().getTranslateX());
            classBox.setY(classBox.getClassPane().getTranslateY());
        });
        // Increases box width if className is too big for the default
        if((int)(font.getStringBounds(className, frc)).getWidth() + 30 > classBox.getBoxWidth())
        {
            classBox.setBoxWidth((int)(font.getStringBounds(className, frc)).getWidth() + 30);
        }
    }

    /**
     * Moves all the classes in the diagram to their saved x and y fields
     */
    public static void moveClassBoxes() {
        // iterate through the class box list and set the translate x and y to the right value
        for (ClassBox cbObj : classBoxList) {
            Double X = coordinateMap.get(cbObj.getClassBoxName()).get(0);
            Double Y = coordinateMap.get(cbObj.getClassBoxName()).get(1);
            cbObj.setX(X);
            cbObj.setY(Y);
            cbObj.getClassPane().setTranslateX(cbObj.getX());
            cbObj.getClassPane().setTranslateY(cbObj.getY());
        }
    }

    public static void drawFieldBox(int fieldListSize, int methListSize, Field field, String className) {
        ClassBox box = findClassBox(className);
        box.setBoxHeight(box.getBoxHeight() + 15);
        box.addText(field, fieldListSize, methListSize, false);

        // Increases box size if the field will extend past it
        if((int)(font.getStringBounds(field.getAttName() + " : " + field.getFieldType(), frc)).getWidth() + 30
                > box.getBoxWidth())
        {
            box.setBoxWidth((int)(font.getStringBounds(field.getAttName() + " : " + field.getFieldType(), frc))
                    .getWidth() + 30);
        }
    }

    public static void drawMethodBox(int fieldListSize, int methListSize, Method meth, String className) {
        ClassBox box = findClassBox(className);
        box.setBoxHeight(box.getBoxHeight() + 15);
        box.addText(meth, fieldListSize, methListSize, false);

        // Calculates pixel width of type
        String methType = "";
        if (!meth.getReturnType().equals("void"))
        {
            methType = meth.getReturnType();
        }

        // Caculate pixel length of all parameters in the current method
        String param = "";
        for (Parameter tempParam : meth.getParamList())
        {
            param += tempParam.getAttName() + " : " + tempParam.getFieldType();
        }

        // Increases box size if the method will extend past it
        if((int)(font.getStringBounds(meth.getAttName() + " : " + meth.getReturnType(), frc))
                .getWidth() + 30 > box.getBoxWidth())
        {
            box.setBoxWidth((int)(font.getStringBounds(meth.getAttName() + " : " +
                    meth.getReturnType(), frc)).getWidth() + 30);
        }
    }

    public static void resizeMethod(Method meth, String className) {
        ClassBox box = findClassBox(className);

        // Calculates pixel width of type
        String methType = "";
        if (!meth.getReturnType().equals("void"))
        {
            methType = meth.getReturnType();
        }

        // Caculate pixel length of all parameters in the current method
        String param = "";
        for (Parameter tempParam : meth.getParamList())
        {
            param += ", " + tempParam.getAttName() + " : " + tempParam.getFieldType();
        }

        // Increases box size if the method will extend past it
        if((int)(font.getStringBounds(meth.getAttName() + "(" + param + ") " + meth.getReturnType(), frc))
                .getWidth() > box.getBoxWidth())
        {
            box.setBoxWidth((int)(font.getStringBounds(meth.getAttName() + "(" + param + ") " +
                    meth.getReturnType(), frc)).getWidth());
        }
    }

    /**
     * Takes in the source and destination class names and a type
     * Draws a line with a style depending on the type of relationship
     *  @param src the source class name
     * @param dest the destination class name
     * @param relType the relationship type
     */
    public static void drawLine(String src, String dest, String relType){
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
        // draw a new line that connects to the source and destination class boxes
        RelLine newRelLine = new RelLine(source, destination, relType);
        // create dashed line with empty arrowhead
        switch (relLineStyle(newRelLine.getRelType())) {
            case "EA" -> {
                newRelLine.getLine().setStrokeWidth(0);
                arrowList.add(emptyArrow(newRelLine));
            }
            // create line with filled in arrowhead
            case "FA" -> arrowList.add(filledArrow(newRelLine));

            // create line with filled in diamond
            case "FD" -> arrowList.add(filledDiamond(newRelLine));

            // create line with empty diamond
            case "ED" -> {
                newRelLine.getLine().setStrokeWidth(0);
                arrowList.add(emptyDiamond(newRelLine));
            }
        }
        // add the new line to the line list and the super root
        lineList.add(newRelLine);
        superRoot.getChildren().add(0, newRelLine.getLine());
    }

    //***************************************//
    //******* Manipulation of Objects *******//
    //***************************************//

    /**
     * Takes in a relationship line
     * Draws an empty arrowhead shape
     * (Realization)
     *
     * @param line the relationship line
     */
    public static ArrayList<Line> emptyArrow(RelLine line){
        // top(right) and bottom(left) arrows
        Line topArrow = new Line();
        topArrow.setStrokeWidth(5);
        topArrow.setStroke(Color.BLACK);
        Line bottomArrow = new Line();
        bottomArrow.setStrokeWidth(5);
        bottomArrow.setStroke(Color.BLACK);
        // completes the arrow and fills in diamond
        Line middleArrow = new Line();
        middleArrow.setStrokeWidth(5);
        middleArrow.setStroke(Color.BLACK);
        // for replacement line bindings setup
        Line tBackArrow = new Line();
        tBackArrow.setStrokeWidth(0);
        // Set up arrow's shape
        InvalidationListener update = observable -> {
            double endX = line.getLine().getEndX();
            double endY = line.getLine().getEndY();
            double startX = line.getLine().getStartX();
            double startY = line.getLine().getStartY();
            // end of arrow head is the end of the line
            topArrow.setEndX(endX);
            topArrow.setEndY(endY);
            bottomArrow.setEndX(endX);
            bottomArrow.setEndY(endY);
            // get arrowhead angle
            double lengthM = 25 / Math.hypot(startX - endX, startY - endY);
            double widthM = 9 / Math.hypot(startX - endX, startY - endY);
            // arrow shape regarding direction of main line
            double dirX = (startX - endX) * lengthM;
            double dirY = (startY - endY) * lengthM;
            // arrow shape regarding orthogonal of main line
            double orthX = (startX - endX) * widthM;
            double orthY = (startY - endY) * widthM;
            //replacement variables to get dashed line closer to arrow
            double rLength = 7 / Math.hypot(startX - endX, startY - endY);
            double rDirX = (startX - endX) * rLength;
            double rDirY = (startY - endY) * rLength;
            // set proper arrow shape
            topArrow.setStartX(endX + dirX - orthY);
            topArrow.setStartY(endY + dirY + orthX);
            bottomArrow.setStartX(endX + dirX + orthY);
            bottomArrow.setStartY(endY + dirY - orthX);
            tBackArrow.setStartX(topArrow.getStartX());
            tBackArrow.setStartY(topArrow.getStartY());
            tBackArrow.setEndX(topArrow.getStartX() + rDirX + orthY);
            tBackArrow.setEndY(topArrow.getStartY() + rDirY - orthX);
            middleArrow.setStartX(bottomArrow.getStartX() - .5);
            middleArrow.setStartY(bottomArrow.getStartY() - .5);
            middleArrow.setEndX(topArrow.getStartX() - .5);
            middleArrow.setEndY(topArrow.getStartY() - .5);
        };
        // bindings for replacement line to invisible original line
        DoubleBinding startXBind = line.getCBSrc().getClassPane().translateXProperty().add(line.getCBSrc()
                .getClassPane().widthProperty().divide(2));
        DoubleBinding startYBind = line.getCBSrc().getClassPane().translateYProperty().add(line.getCBSrc()
                .getClassPane().heightProperty().divide(2));
        DoubleBinding endXBind = tBackArrow.translateXProperty().add(tBackArrow.endXProperty());
        DoubleBinding endYBind = tBackArrow.translateYProperty().add(tBackArrow.endYProperty());
        // replacement line for real line, set bindings
        Line rLine = new Line();
        rLine.setStroke(Color.BLACK);
        rLine.setStrokeWidth(9.5);
        rLine.setStrokeLineCap(StrokeLineCap.BUTT);
        rLine.getStrokeDashArray().addAll(15d);
        rLine.setStrokeDashOffset(10);
        rLine.startXProperty().bind(startXBind);
        rLine.startYProperty().bind(startYBind);
        rLine.endXProperty().bind(endXBind);
        rLine.endYProperty().bind(endYBind);
        // update to arrow shape when moving line
        line.getLine().startXProperty().addListener(update);
        line.getLine().startYProperty().addListener(update);
        line.getLine().endXProperty().addListener(update);
        line.getLine().endYProperty().addListener(update);
        //same update listener for replacement lines
        rLine.startXProperty().addListener(update);
        rLine.startYProperty().addListener(update);
        rLine.endXProperty().addListener(update);
        rLine.endYProperty().addListener(update);
        update.invalidated(null);
        // add lines to the super root to show up in gui
        // (DO NOT change these line below to an addAll call for superRoot.getChildren, the order matters)
        superRoot.getChildren().add(1, topArrow);
        superRoot.getChildren().add(2, bottomArrow);
        superRoot.getChildren().add(3, middleArrow);
        superRoot.getChildren().add(4, tBackArrow);
        superRoot.getChildren().add(5, rLine);
        // add lines to array list to be manipulated(accessed or deleted)
        ArrayList<Line> arrowShape = new ArrayList<>();
        arrowShape.add(topArrow);
        arrowShape.add(bottomArrow);
        arrowShape.add(middleArrow);
        arrowShape.add(tBackArrow);
        arrowShape.add(rLine);
        return arrowShape;
    }

    /**
     * Takes in a relationship line
     * Draws a filled in arrowhead shape
     * (Inheritance)
     *
     * @param line the relationship line
     */
    public static ArrayList<Line> filledArrow(RelLine line){
        // top(right) and bottom(left) arrows
        Line topArrow = new Line();
        topArrow.setStrokeWidth(6);
        topArrow.setStroke(Color.BLACK);
        Line bottomArrow = new Line();
        bottomArrow.setStrokeWidth(6);
        bottomArrow.setStroke(Color.BLACK);
        // completes the arrow and fills in diamond
        Line middleArrow = new Line();
        middleArrow.setStrokeWidth(6);
        middleArrow.setStroke(Color.BLACK);
        // for filled in arrow or diamond
        Line fillArrow = new Line();
        fillArrow.setStrokeWidth(7);
        fillArrow.setStroke(Color.BLACK);
        // Set up arrow's shape
        InvalidationListener update1 = observable -> {
            double endX = line.getLine().getEndX();
            double endY = line.getLine().getEndY();
            double startX = line.getLine().getStartX();
            double startY = line.getLine().getStartY();
            // end of arrow head is the end of the line
            topArrow.setEndX(endX);
            topArrow.setEndY(endY);
            bottomArrow.setEndX(endX);
            bottomArrow.setEndY(endY);
            // get arrowhead angle
            double lengthM = 25 / Math.hypot(startX - endX, startY - endY);
            double widthM = 9 / Math.hypot(startX - endX, startY - endY);
            // arrow shape regarding direction of main line
            double dirX = (startX - endX) * lengthM;
            double dirY = (startY - endY) * lengthM;
            // arrow shape regarding orthogonal of main line
            double orthX = (startX - endX) * widthM;
            double orthY = (startY - endY) * widthM;
            // set proper arrow shape
            topArrow.setStartX(endX + dirX - orthY);
            topArrow.setStartY(endY + dirY + orthX);
            bottomArrow.setStartX(endX + dirX + orthY);
            bottomArrow.setStartY(endY + dirY - orthX);
            middleArrow.setStartX(bottomArrow.getStartX() - .5);
            middleArrow.setStartY(bottomArrow.getStartY() - .5);
            middleArrow.setEndX(topArrow.getStartX() - .5);
            middleArrow.setEndY(topArrow.getStartY() - .5);
            fillArrow.setStartX(((middleArrow.getStartX() + middleArrow.getEndX()) / 2));
            fillArrow.setStartY(((middleArrow.getStartY() + middleArrow.getEndY()) / 2));
            fillArrow.setEndX(topArrow.getEndX());
            fillArrow.setEndY(topArrow.getEndY());
        };
        // update to arrow shape when moving line
        line.getLine().startXProperty().addListener(update1);
        line.getLine().startYProperty().addListener(update1);
        line.getLine().endXProperty().addListener(update1);
        line.getLine().endYProperty().addListener(update1);
        update1.invalidated(null);
        // add lines to the super root to show up in gui
        // (DO NOT change these line below to an addAll call for superRoot.getChildren, the order matters)
        superRoot.getChildren().add(1, topArrow);
        superRoot.getChildren().add(2, bottomArrow);
        superRoot.getChildren().add(3, middleArrow);
        superRoot.getChildren().add(4, fillArrow);
        // add lines to array list to be manipulated(accessed or deleted)
        ArrayList<Line> arrowShape = new ArrayList<>();
        arrowShape.add(topArrow);
        arrowShape.add(bottomArrow);
        arrowShape.add(middleArrow);
        arrowShape.add(fillArrow);
        return arrowShape;
    }

    /**
     * Takes in a relationship line
     * Draws a filled in diamond shape
     * (Composition)
     *
     * @param line the relationship line
     */
    public static ArrayList<Line> filledDiamond(RelLine line){
        // top(right) and bottom(left) arrows
        Line topArrow = new Line();
        topArrow.setStrokeWidth(8);
        topArrow.setStroke(Color.BLACK);
        Line bottomArrow = new Line();
        bottomArrow.setStrokeWidth(8);
        bottomArrow.setStroke(Color.BLACK);
        // for filled in arrow or diamond
        Line fillArrow = new Line();
        fillArrow.setStrokeWidth(7);
        fillArrow.setStroke(Color.BLACK);
        // for diamond Shape
        Line tBackArrow = new Line();
        tBackArrow.setStrokeWidth(8);
        tBackArrow.setStroke(Color.BLACK);
        Line bBackArrow = new Line();
        bBackArrow.setStrokeWidth(8);
        bBackArrow.setStroke(Color.BLACK);
        // Set up arrow's shape
        InvalidationListener update2 = observable -> {
            double startX = line.getLine().getStartX();
            double startY = line.getLine().getStartY();
            double endX = line.getLine().getEndX();
            double endY = line.getLine().getEndY();
            // end of arrow head is the end of the line
            topArrow.setEndX(endX);
            topArrow.setEndY(endY);
            bottomArrow.setEndX(endX);
            bottomArrow.setEndY(endY);
            // get arrowhead angle
            double lengthM = 20 / Math.hypot(startX - endX, startY - endY);
            double widthM = 9 / Math.hypot(startX - endX, startY - endY);
            // arrow shape regarding direction of main line
            double dirX = (startX - endX) * lengthM;
            double dirY = (startY - endY) * lengthM;
            // arrow shape regarding orthogonal of main line
            double orthX = (startX - endX) * widthM;
            double orthY = (startY - endY) * widthM;
            // set proper arrow shape
            topArrow.setStartX(endX + dirX - orthY);
            topArrow.setStartY(endY + dirY + orthX);
            bottomArrow.setStartX(endX + dirX + orthY);
            bottomArrow.setStartY(endY + dirY - orthX);
            tBackArrow.setStartX(topArrow.getStartX());
            tBackArrow.setStartY(topArrow.getStartY());
            tBackArrow.setEndX(topArrow.getStartX() + dirX + orthY);
            tBackArrow.setEndY(topArrow.getStartY() + dirY - orthX);
            bBackArrow.setStartX(bottomArrow.getStartX());
            bBackArrow.setStartY(bottomArrow.getStartY());
            bBackArrow.setEndX(bottomArrow.getStartX() + dirX - orthY);
            bBackArrow.setEndY(bottomArrow.getStartY() + dirY + orthX);
            fillArrow.setStartX(tBackArrow.getEndX());
            fillArrow.setStartY(tBackArrow.getEndY());
            fillArrow.setEndX(topArrow.getEndX());
            fillArrow.setEndY(topArrow.getEndY());

        };
        // update to arrow shape when moving line
        line.getLine().startXProperty().addListener(update2);
        line.getLine().startYProperty().addListener(update2);
        line.getLine().endXProperty().addListener(update2);
        line.getLine().endYProperty().addListener(update2);
        update2.invalidated(null);
        // add lines to the super root to show up in gui
        // (DO NOT change these line below to an addAll call for superRoot.getChildren, the order matters)
        superRoot.getChildren().add(1, topArrow);
        superRoot.getChildren().add(2, bottomArrow);
        superRoot.getChildren().add(3, fillArrow);
        superRoot.getChildren().add(4, tBackArrow);
        superRoot.getChildren().add(5, bBackArrow);
        // add lines to array list to be manipulated(accessed or deleted)
        ArrayList<Line> arrowShape = new ArrayList<>();
        arrowShape.add(topArrow);
        arrowShape.add(bottomArrow);
        arrowShape.add(fillArrow);
        arrowShape.add(tBackArrow);
        arrowShape.add(bBackArrow);
        return arrowShape;
    }

    /**
     * Takes in a relationship line
     * Draws a filled in diamond shape
     * (Aggregation)
     *
     * @param line the relationship line
     */
    public static ArrayList<Line> emptyDiamond(RelLine line){
        // top(right) and bottom(left) arrows
        Line topArrow = new Line();
        topArrow.setStrokeWidth(6);
        topArrow.setStroke(Color.BLACK);
        Line bottomArrow = new Line();
        bottomArrow.setStrokeWidth(6);
        bottomArrow.setStroke(Color.BLACK);
        // for diamond Shape
        Line tBackArrow = new Line();
        tBackArrow.setStrokeWidth(6);
        tBackArrow.setStroke(Color.BLACK);
        Line bBackArrow = new Line();
        bBackArrow.setStrokeWidth(6);
        bBackArrow.setStroke(Color.BLACK);
        // Set up arrow's shape
        InvalidationListener update3 = observable -> {
            double endX = line.getLine().getEndX();
            double endY = line.getLine().getEndY();
            double startX = line.getLine().getStartX();
            double startY = line.getLine().getStartY();
            // end of arrow head is the end of the line
            topArrow.setEndX(endX);
            topArrow.setEndY(endY);
            bottomArrow.setEndX(endX);
            bottomArrow.setEndY(endY);
            // get arrowhead angle
            double lengthM = 25 / Math.hypot(startX - endX, startY - endY);
            double widthM = 11 / Math.hypot(startX - endX, startY - endY);
            // arrow shape regarding direction of main line
            double dirX = (startX - endX) * lengthM;
            double dirY = (startY - endY) * lengthM;
            // arrow shape regarding orthogonal of main line
            double orthX = (startX - endX) * widthM;
            double orthY = (startY - endY) * widthM;
            // set proper arrow shape
            topArrow.setStartX(endX + dirX - orthY);
            topArrow.setStartY(endY + dirY + orthX);
            bottomArrow.setStartX(endX + dirX + orthY);
            bottomArrow.setStartY(endY + dirY - orthX);
            tBackArrow.setStartX(topArrow.getStartX());
            tBackArrow.setStartY(topArrow.getStartY());
            tBackArrow.setEndX(topArrow.getStartX() + dirX + orthY);
            tBackArrow.setEndY(topArrow.getStartY() + dirY - orthX);
            bBackArrow.setStartX(bottomArrow.getStartX());
            bBackArrow.setStartY(bottomArrow.getStartY());
            bBackArrow.setEndX(bottomArrow.getStartX() + dirX - orthY);
            bBackArrow.setEndY(bottomArrow.getStartY() + dirY + orthX);
        };
        // bindings for replacement line to invisible original line
        DoubleBinding startXBind = line.getCBSrc().getClassPane().translateXProperty().add(line.getCBSrc()
                .getClassPane().widthProperty().divide(2));
        DoubleBinding startYBind = line.getCBSrc().getClassPane().translateYProperty().add(line.getCBSrc()
                .getClassPane().heightProperty().divide(2));
        DoubleBinding endXBind = tBackArrow.translateXProperty().add(tBackArrow.endXProperty());
        DoubleBinding endYBind = tBackArrow.translateYProperty().add(tBackArrow.endYProperty());
        // replacement line for real line, set bindings
        Line rLine = new Line();
        rLine.setStroke(Color.BLACK);
        rLine.setStrokeWidth(9.5);
        rLine.startXProperty().bind(startXBind);
        rLine.startYProperty().bind(startYBind);
        rLine.endXProperty().bind(endXBind);
        rLine.endYProperty().bind(endYBind);
        // update to arrow shape when moving line
        line.getLine().startXProperty().addListener(update3);
        line.getLine().startYProperty().addListener(update3);
        line.getLine().endXProperty().addListener(update3);
        line.getLine().endYProperty().addListener(update3);
        //same update listener for replacement lines
        rLine.startXProperty().addListener(update3);
        rLine.startYProperty().addListener(update3);
        rLine.endXProperty().addListener(update3);
        rLine.endYProperty().addListener(update3);
        update3.invalidated(null);
        // add lines to the super root to show up in gui
        // (DO NOT change these line below to an addAll call for superRoot.getChildren, the order matters)
        superRoot.getChildren().add(1, topArrow);
        superRoot.getChildren().add(2, bottomArrow);
        superRoot.getChildren().add(3, tBackArrow);
        superRoot.getChildren().add(4, bBackArrow);
        superRoot.getChildren().add(5, rLine);
        // add lines to array list to be manipulated(accessed or deleted)
        ArrayList<Line> arrowShape = new ArrayList<>();
        arrowShape.add(topArrow);
        arrowShape.add(bottomArrow);
        arrowShape.add(tBackArrow);
        arrowShape.add(bBackArrow);
        arrowShape.add(rLine);
        return arrowShape;
    }

    /**
     * Takes in the source and destination class box objects
     * finds and returns the relationship line corresponding to
     * the src and dest
     *
     * @param src the source class box object
     * @param dest the destination class box object
     * @return the relationship line
     */
    public static RelLine findRelLine(ClassBox src, ClassBox dest){
        for(RelLine line : lineList){
            if(line.getCBSrc().equals(src)) {
                if (line.getCBDest().equals(dest)) {
                    return line;
                }
            }
        }
        return null;
    }

    /**
     * Takes in a relationship line
     * returns the arrow shape (list of lines)
     *
     * @param line the relationship line
     * @return the arrow shape
     */
    public static ArrayList<Line> findArrow(RelLine line){
        for(ArrayList<Line> arrows : arrowList){
            for(Line arrow : arrows){
                if(arrow.getEndX() == line.getLine().getEndX()){
                    return arrows;
                }
            }
        }
        return null;
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
     * Takes in a class box name(class name) and removes it from the super root
     * and class box list(removes the class from the view)
     *
     * @param classBoxName the name of the class box(name of class)
     */
    public static void deleteClassBox (String classBoxName){
        for (ClassBox cbObj : GUIView.classBoxList) {
            if(cbObj.getClassBoxName().equals(classBoxName)) {
                GUIView.superRoot.getChildren().remove(cbObj.getClassPane());
            }
        }
        classBoxList.remove(findClassBox(classBoxName));
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
        for(ArrayList<Line> arrows : arrowList){
            if(arrows.equals(findArrow(findRelLine(src, dest)))) {
                for (Line arrow : arrows) {
                    superRoot.getChildren().remove(arrow);
                }
                superRoot.getChildren().remove(arrows);
                arrowList.remove(arrows);
                break;
            }
        }
        superRoot.getChildren().remove(findRelLine(src, dest).getLine());
        lineList.remove(findRelLine(src, dest).getLine());
        lineList.remove(findRelLine(src, dest));
    }

    /**
     * Takes in a relLine and a relType
     * Styles the relationship line depending on the relationship type
     *
     * @param relType the relationship type
     * @return the type of arrowhead shape (arrow or diamond, filled or empty)
     */
    public static String relLineStyle(String relType){
        switch (relType) {
            case "aggregation" -> {
                return "ED"; // empty diamond
            }
            case "composition" -> {
                return "FD"; // filled in diamond
            }
            case "inheritance" -> {
                return "FA"; // filled in arrow
            }
            case "realization" -> {
                return "EA"; // empty arrow
            }
        }
        return null;
    }

    private void updateMenus() {
        // get the list of items in the edit menu object
        ObservableList<MenuItem> editList = menuBar.getMenus().get(1).getItems();
        // get the add menu object
        Menu add = (Menu) editList.get(0);
        // get the add attribute menu object
        Menu addAttribute = (Menu) add.getItems().get(1);
        // get the delete menu object
        Menu delete = (Menu) editList.get(1);
        // get the delete attribute menu object
        Menu deleteAttribute = (Menu) delete.getItems().get(1);
        // get the rename menu object
        Menu rename = (Menu) editList.get(2);
        // get the rename attribute menu object
        Menu renameAttribute = (Menu) rename.getItems().get(1);
        // get the change menu object
        Menu change = (Menu) editList.get(3);

        // enable/disable
        if (!GUIController.getClassList().isEmpty()) {
            // enable the add field menu item
            addAttribute.getItems().get(0).setDisable(false);
            // enable the add method menu item
            addAttribute.getItems().get(1).setDisable(false);
            // enable the delete class menu item
            delete.getItems().get(0).setDisable(false);
            // enable the rename class menu item
            rename.getItems().get(0).setDisable(false);

            boolean fieldExists = false;
            boolean methodExists = false;
            boolean paramExists = false;
            // check every object and see if at least one of them has either a field or method
            for (UMLClass classObj : GUIController.getClassList()) {
                if (!classObj.getFieldList().isEmpty()) {
                    fieldExists = true;
                }
                if (!classObj.getMethodList().isEmpty()) {
                    methodExists = true;
                    for (Method methObj : classObj.getMethodList()) {
                        if (!methObj.getParamList().isEmpty()) {
                            paramExists = true;
                        }
                    }
                }
            }
            // if there is at least one field in the list, enable the menu items
            // otherwise, disable them
            if (fieldExists) {
                deleteAttribute.getItems().get(0).setDisable(false);
                renameAttribute.getItems().get(0).setDisable(false);
            } else {
                deleteAttribute.getItems().get(0).setDisable(true);
                renameAttribute.getItems().get(0).setDisable(true);
            }
            // if there is at least one method in the list, enable the menu items
            // otherwise, disable them
            if (methodExists) {
                deleteAttribute.getItems().get(1).setDisable(false);
                renameAttribute.getItems().get(1).setDisable(false);
                add.getItems().get(3).setDisable(false);
            } else {
                deleteAttribute.getItems().get(1).setDisable(true);
                renameAttribute.getItems().get(1).setDisable(true);
                add.getItems().get(3).setDisable(true);
            }
            // if there is at least one parameter in the list, enable the menu items
            // otherwise, disable them
            if (paramExists) {
                change.getItems().get(0).setDisable(false);
            } else {
                change.getItems().get(0).setDisable(true);
            }
            // if there are at least 2 classes in the class list, enable add relationship
            // otherwise, disable it
            if (GUIController.getClassList().size() > 1) {
                add.getItems().get(2).setDisable(false);
            } else {
                add.getItems().get(2).setDisable(true);
            }
            // if there are at least one relationship in the relationship list, enable the menu items
            // otherwise, disable them
            if (!GUIController.getRelationshipList().isEmpty()) {
                delete.getItems().get(2).setDisable(false);
                change.getItems().get(1).setDisable(false);
            } else {
                delete.getItems().get(2).setDisable(true);
                change.getItems().get(1).setDisable(true);
            }
        } else {
            // disable all the menu items
            addAttribute.getItems().get(0).setDisable(true);
            addAttribute.getItems().get(1).setDisable(true);
            delete.getItems().get(0).setDisable(true);
            rename.getItems().get(0).setDisable(true);
            deleteAttribute.getItems().get(0).setDisable(true);
            renameAttribute.getItems().get(0).setDisable(true);
            deleteAttribute.getItems().get(1).setDisable(true);
            renameAttribute.getItems().get(1).setDisable(true);
            add.getItems().get(3).setDisable(true);
            change.getItems().get(0).setDisable(true);
        }
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
     * Adds a key value pair to the coordinate map, where the value is a list of the x and y
     *
     * @param className the key, the name of the class
     * @param X the x value for the class box
     * @param Y the y value for the class box
     */
    public static void addToCoordinateMap(String className, Double X, Double Y) {
        List<Double> coordinateList = new ArrayList<Double>();
        coordinateList.add(X);
        coordinateList.add(Y);
        coordinateMap.put(className, coordinateList);
    }

    //***************************************//
    //*********** GUI View Main *************//
    //***************************************//

    // launch the gui
    public static void main(String[] args) {
        launch(args);
    }
}
