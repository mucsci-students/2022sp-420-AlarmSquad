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
     * Load file if the given string is nonempty, pops an error up otherwise
     *
     * @param fileName the name of the file to be load
     * @param stage the working stage
     */
    public static void loadAction(String fileName, Stage stage) {
        // if the string is empty, pop an error up
        if (fileName.isEmpty()) {
            GUIView.popUpWindow("Error", "File name is required");
            // otherwise, load the file and exit the window
        } else {
            JSON.load(fileName);
            stage.close();
        }
    }

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
            // if the class does not exist, add it and close
            if (UMLModel.findClass(className) == null) {
                UMLClass newClass = new UMLClass(className);
                UMLModel.addClass(newClass);
                stage.close();
            // if the class does exist, pop an error up
            } else {
                GUIView.popUpWindow("Error", "Class already exists");
            }
        }
    }

    public static void addFieldAction(){

    }

    public static void addMethodAction(){

    }

    /**
     * Creates and adds a relationship to the relationshipList if the given strings are nonempty,
     * and the classes exist, pops an error up otherwise
     *
     * @param srcName the name of the source class
     * @param destName the name of the destination class
     * @param relType the type of relationship
     * @param stage the working stage
     */
    public static void addRelationshipAction(String srcName, String destName, String relType, Stage stage) {
        // if the source name, destination name, or relationship type are empty, pop an error up
        if (srcName.isEmpty() || destName.isEmpty() || relType.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        // otherwise, create and add the relationship and exit the window
        } else {
            // if the relationship does not exist, add it and close
            if (UMLModel.findRelationship(srcName, destName, relType) == null &&
                !UMLModel.isRelated(srcName, destName)) {
                if (UMLModel.findClass(srcName) == null) {
                    GUIView.popUpWindow("Error", "Source does not exist");
                } else if (UMLModel.findClass(destName) == null) {
                    GUIView.popUpWindow("Error", "Destination does not exist");
                } else {
                    UMLClass src = UMLModel.findClass(srcName);
                    UMLClass dest = UMLModel.findClass(destName);
                    Relationship newRel = new Relationship(src, dest, relType);
                    UMLModel.addRel(newRel);
                    stage.close();
                }
            // if the relationship does exist, error message will be displayed
            } else {
                GUIView.popUpWindow("Error", "Relationship already exists");
            }
        }
    }

    /**
     * Deletes a class to the classList if the given string is nonempty and the class exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to be deleted
     * @param stage the working stage
     */
    public static void deleteClassAction(String className, Stage stage) {
        // if the string is empty, pop an error up
        if (className.isEmpty()) {
            GUIView.popUpWindow("Error", "Class name is required");
            // otherwise, delete the class and exit the window
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the class does exist, delete it, update the view and close
            } else {
                UMLClass classToDelete = UMLModel.findClass(className);
                UMLModel.deleteClass(classToDelete);
                stage.close();
            }
        }
    }

    public static void deleteFieldAction(){

    }

    public static void deleteMethodAction(){

    }

    /**
     * Deletes a relationship in the relationship list if the given strings
     * are nonempty and the relationship exists, display error otherwise
     *
     * @param srcName the name of the source class
     * @param destName the name of the destination class
     * @param stage the working stage
     */
    public static void deleteRelAction(String srcName, String destName, Stage stage) {
        // if the source name is not given, display error message
        if(srcName.isEmpty()){
            GUIView.popUpWindow("Error", "Source name is required");
        }
        // if the destination name is not given, display error message
        else if(destName.isEmpty()){
            GUIView.popUpWindow("Error", "Destination name is required");
        }
        // if the relationship between the two classes in src->dest order does
        // exist, display error message
        else if(UMLModel.findRelationship(srcName, destName,
                    UMLModel.findRelType(srcName, destName)) == null){

                GUIView.popUpWindow("Error", "Relationship does not exist");
        }
        // delete the relationship, update the view and close
        else{
            Relationship relToDelete = UMLModel.findRelationship(srcName, destName,
                    UMLModel.findRelType(srcName, destName));
            UMLModel.deleteRel(relToDelete);
            stage.close();
        }
    }

    /**
     * Renames a class to a given string in the classList if the given string is nonempty and the class exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to be rename
     * @param newClassName the new name for the class
     * @param stage the working stage
     */
    public static void renameClassAction(String className, String newClassName, Stage stage) {
        // if the string is empty, pop an error up
        if (className.isEmpty()) {
            GUIView.popUpWindow("Error", "Class name is required");
            // otherwise, rename the class and exit the window
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the class does exist, rename it and close
            } else {
                UMLClass classToRename = UMLModel.findClass(className);
                classToRename.setClassName(newClassName);
                stage.close();
            }
        }
    }

    public static void renameFieldAction(String oldFieldName, String newFieldName, Stage stage){

    }

    public static void renameMethodAction(String oldFieldName, String newFieldName, Stage stage){

    }

    public static void changeRelTypeAction(String oldReltype, String newRelType, Stage stage){

    }

    /**
     * Exits the current window if called
     *
     * @param stage the working stage
     * @return an event handler telling the window to close
     */
    public static void exitAction(Stage stage) {
        stage.close();
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
