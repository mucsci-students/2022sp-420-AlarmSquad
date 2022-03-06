package uml;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
     * @param stage    the working stage
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
     * @param stage    the working stage
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
     * @param stage     the working stage
     */
    public static void addClassAction(String className, Stage stage) {
        // if any string is empty, pop an error up
        if (className.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class name is not a valid input, pop an error up
            if (UMLModel.isNotValidInput(className)) {
                GUIView.popUpWindow("Error", "The class name is invalid");
            // check if the class exists
            } else {
                // if the class does not exist, add it and close
                if (UMLModel.findClass(className) == null) {
                    UMLClass newClass = new UMLClass(className);
                    UMLModel.addClass(newClass);
                    stage.close();
                    // Draw the new box for the class
                    GUIView.drawClassBox(className);
                    // if the class does exist, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "Class already exists");
                }
            }
        }
    }

    /**
     * Creates and adds a field to a class if the given strings are nonempty and the class exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to add the field to
     * @param fieldName the name of the field to be added
     * @param fieldType the type of the field to be added
     * @param stage     the working stage
     */
    public static void addFieldAction(String className, String fieldName, String fieldType, Stage stage) {
        // if any string is empty, pop an error up
        if (className.isEmpty() || fieldName.isEmpty() || fieldType.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the field name is not a valid string, pop an error up
            } else if (UMLModel.isNotValidInput(fieldName)) {
                GUIView.popUpWindow("Error", "The field name is invalid");
            // if the field type is not a valid type, pop an error up
            } else if (UMLModel.isNotValidType(fieldType)) {
                GUIView.popUpWindow("Error", "The field type is invalid");
            // check if the field exists
            } else {
                // if the field does not exist, add it and close
                if (UMLModel.findClass(className).findField(fieldName) == null) {
                    Field newField = new Field(fieldName, fieldType);
                    UMLModel.findClass(className).addField(newField);
                    stage.close();
                    GUIView.drawFieldBox(UMLModel.findClass(className).getFieldList().size(),
                            UMLModel.findClass(className).getMethodList().size(), newField, className);
                // if the field does exist, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "Field already exists");
                }
            }
        }
    }

    /**
     * Creates and adds a method to a class if the given strings are nonempty and the class exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to add the method to
     * @param methodName the name of the method to be added
     * @param returnType the return type for the method
     * @param stage the working stage
     */
    public static void addMethodAction(String className, String methodName, String returnType, Stage stage){
        // if any string is empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || returnType.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the method name is not a valid string, pop an error up
            } else if (UMLModel.isNotValidInput(methodName)) {
                GUIView.popUpWindow("Error", "The method name is invalid");
            // if the return type is not a valid type, pop an error up
            } else if (UMLModel.isNotValidReturnType(returnType)) {
                GUIView.popUpWindow("Error", "The return type is invalid");
            // check if the method exists
            } else {
                // if the method does not exist, add it and close
                if (UMLModel.findClass(className).findMethod(methodName) == null) {
                    Method newMethod = new Method(methodName, returnType);
                    UMLModel.findClass(className).addMethod(newMethod);
                    stage.close();
                    GUIView.drawMethodBox(UMLModel.findClass(className).getFieldList().size(),
                            UMLModel.findClass(className).getMethodList().size(), newMethod, className);
                    // if the method does exist, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "Field already exists");
                }
            }
        }
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
        // if any string is empty, pop an error up
        if (srcName.isEmpty() || destName.isEmpty() || relType.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the source does not exist, pop an error up
            if (UMLModel.findClass(srcName) == null) {
                GUIView.popUpWindow("Error", "Source does not exist");
            // if the destination does not exist, pop an error up
            } else if (UMLModel.findClass(destName) == null) {
                GUIView.popUpWindow("Error", "Destination does not exist");
            // check if the relationship exists
            } else {
                // if the relationship does not exist, add it and close
                if (UMLModel.findRelationship(srcName, destName, relType) == null &&
                        !UMLModel.isRelated(srcName, destName)) {
                    UMLClass src = UMLModel.findClass(srcName);
                    UMLClass dest = UMLModel.findClass(destName);
                    Relationship newRel = new Relationship(src, dest, relType);
                    UMLModel.addRel(newRel);
                    relTypeLine(srcName, destName, UMLModel.findRelType(srcName, destName));
                    stage.close();
                // if the relationship does exist, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "Relationship already exists");
                }
            }
        }
    }

    /**
     * Creates and adds a parameter to a method if the given strings are nonempty,
     * and the class and method exists, pops an error up otherwise
     *
     * @param className the class containing the method
     * @param methodName the method containing the parameter
     * @param paramName the name of the parameter to add
     * @param paramType the type of parameter to add
     * @param stage the working stage
     */
    public static void addParameterAction(String className, String methodName, String paramName, String paramType,
                                          Stage stage) {
        // if any string is empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || paramName.isEmpty() || paramType.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the method does not exist, pop an error up
            } else if (UMLModel.findClass(className).findMethod(methodName) == null) {
                GUIView.popUpWindow("Error", "Method does not exist");
            // if the parameter name is not a valid string, pop an error up
            } else if (UMLModel.isNotValidInput(paramName)) {
                GUIView.popUpWindow("Error", "The parameter name is invalid");
            // if the parameter type is not a valid type, pop an error up
            } else if (UMLModel.isNotValidType(paramType)) {
                GUIView.popUpWindow("Error", "The parameter type is invalid");
            } else {
                // if the parameter does not exist, add it and close
                if (UMLModel.findClass(className).findMethod(methodName).findParameter(paramName) == null) {
                    Parameter newParam = new Parameter(paramName, paramType);
                    UMLModel.findClass(className).findMethod(methodName).addParameter(newParam);
                    stage.close();
                // if the method does exist, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "Field already exists");
                }
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
        // if any string is empty, pop an error up
        if (className.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the class does exist, delete it and close
            } else {
                UMLClass classToDelete = UMLModel.findClass(className);
                UMLModel.deleteClass(classToDelete);
                stage.close();
            }
        }
    }

    /**
     * Deletes a field from a class if the given string is nonempty and the field exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to delete the field from
     * @param fieldName the name of the field to delete
     * @param stage the working stage
     */
    public static void deleteFieldAction(String className, String fieldName, Stage stage){
        // if any string is empty, pop an error up
        if (className.isEmpty() || fieldName.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the field does not exist, pop an error up
            } else if (UMLModel.findClass(className).findField(fieldName) == null) {
                GUIView.popUpWindow("Error", "Field does not exist");
            // if the field does exist, delete it and close
            } else {
                Field fieldToDelete = UMLModel.findClass(className).findField(fieldName);
                UMLModel.findClass(className).deleteField(fieldToDelete);
                stage.close();
            }
        }
    }

    /**
     * Deletes a method from a class if the given string is nonempty and the method exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to delete the method from
     * @param methodName the name of the method to delete
     * @param stage the working stage
     */
    public static void deleteMethodAction(String className, String methodName, Stage stage){
        // if any string is empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the method does not exist, pop an error up
            } else if (UMLModel.findClass(className).findField(methodName) == null) {
                GUIView.popUpWindow("Error", "Method does not exist");
            // if the method does exist, delete it and close
            } else {
                Method methodToDelete = UMLModel.findClass(className).findMethod(methodName);
                UMLModel.findClass(className).deleteMethod(methodToDelete);
                stage.close();
            }
        }
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
        // if any string is empty, pop an error up
        if (srcName.isEmpty() || destName.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        // if the relationship between the two classes in src->dest order does
        // exist, display error message
        } else if (UMLModel.findRelationship(srcName, destName,
                    UMLModel.findRelType(srcName, destName)) == null) {
            GUIView.popUpWindow("Error", "Relationship does not exist");
        // delete the relationship, update the view and close
        } else {
            Relationship relToDelete = UMLModel.findRelationship(srcName, destName,
                    UMLModel.findRelType(srcName, destName));
            GUIView.deleteRelLine(GUIView.findClassBox(srcName), GUIView.findClassBox(destName));
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
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the class name is not a valid string, pop an error up
            } else if (UMLModel.isNotValidInput(newClassName)) {
                GUIView.popUpWindow("Error", "The class name is invalid");
            } else {
                // if the class name is not already in use, rename the field
                if (UMLModel.findClass(newClassName) == null) {
                    UMLModel.findClass(className).setClassName(newClassName);
                    stage.close();
                // otherwise, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "The field name is already in use");
                }
            }
        }
    }

    /**
     * Renames a field to a given string in a class if the given string is nonempty and the class and field exist,
     * pops an error up otherwise
     *
     * @param className the name of the class with the field
     * @param fieldName the name of the field to be renamed
     * @param newFieldName the new name for the field
     * @param stage the working stage
     */
    public static void renameFieldAction(String className, String fieldName, String newFieldName, Stage stage) {
        // if any strings are empty, pop an error up
        if (className.isEmpty() || fieldName.isEmpty() || newFieldName.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
                // if the field does not exist, pop an error up
            } else if (UMLModel.findClass(className).findField(fieldName) == null) {
                GUIView.popUpWindow("Error", "Field does not exist");
                // if the field name is not a valid string, pop an error up
            } else if (UMLModel.isNotValidInput(newFieldName)) {
                GUIView.popUpWindow("Error", "The field name is invalid");
            } else {
                // if the field name is not already in use, rename the field
                if (UMLModel.findClass(className).findField(newFieldName) == null) {
                    UMLModel.findClass(className).findField(fieldName).setAttName(newFieldName);
                    stage.close();
                    // otherwise, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "The field name is already in use");
                }
            }
        }
    }

    /**
     * Renames a method to a given string in a class if the given string is nonempty and the class and method exist,
     * pops an error up otherwise
     *
     * @param className the name of the class with the method
     * @param methodName the name of the method to be renamed
     * @param newMethodName the new name for the method
     * @param stage the working stage
     */
    public static void renameMethodAction(String className, String methodName, String newMethodName, Stage stage) {
        // if any strings are empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || newMethodName.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
            // if the method does not exist, pop an error up
            } else if (UMLModel.findClass(className).findMethod(methodName) == null) {
                GUIView.popUpWindow("Error", "Method does not exist");
            // if the method name is not a valid string, pop an error up
            } else if (UMLModel.isNotValidInput(newMethodName)) {
                GUIView.popUpWindow("Error", "The method name is invalid");
            } else {
                // if the method name is not already in use, rename the method
                if (UMLModel.findClass(className).findMethod(newMethodName) == null) {
                    UMLModel.findClass(className).findMethod(methodName).setAttName(newMethodName);
                    stage.close();
                // otherwise, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "The method name is already in use");
                }
            }
        }
    }

    /**
     * Changes a parameter to a given string in a class if the given string is nonempty, and the class, method,
     * and parameter exist, pops an error up otherwise
     *
     * @param className the class containing the method
     * @param methodName the method containing the parameter
     * @param paramName the parameter to be changed
     * @param newParamName the new name for the parameter
     * @param newParamType the new type for the parameter
     * @param stage the working stage
     */
    public static void changeParameterAction(String className, String methodName, String paramName,
                                             String newParamName, String newParamType, Stage stage) {
        // if any strings are empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || paramName.isEmpty() || newParamName.isEmpty()) {
            GUIView.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (UMLModel.findClass(className) == null) {
                GUIView.popUpWindow("Error", "Class does not exist");
                // if the method does not exist, pop an error up
            } else if (UMLModel.findClass(className).findMethod(methodName) == null) {
                GUIView.popUpWindow("Error", "Method does not exist");
                // if the parameter does not exist, pop an error up
            } else if (UMLModel.findClass(className).findMethod(methodName).findParameter(paramName) == null) {
                GUIView.popUpWindow("Error", "Parameter does not exist");
                // if the parameter name is not a valid string, pop an error up
            } else if (UMLModel.isNotValidInput(newParamName)) {
                GUIView.popUpWindow("Error", "The parameter name is invalid");
                // if the parameter type is not a valid string, pop an error up
            } else if (UMLModel.isNotValidType(newParamType)) {
                GUIView.popUpWindow("Error", "The parameter type is invalid");
            } else {
                // if the parameter name is not already in use, change the parameter
                if (UMLModel.findClass(className).findMethod(methodName).findParameter(newParamName) == null) {
                    UMLModel.findClass(className).findMethod(methodName).findParameter(paramName).setAttName(newParamName);
                    UMLModel.findClass(className).findMethod(methodName).findParameter(newParamName)
                            .setFieldType(newParamType);
                    stage.close();
                    // otherwise, pop an error up
                } else {
                    GUIView.popUpWindow("Error", "The parameter name is already in use");
                }
            }
        }
    }

    /**
     * Takes in the source class, destination class, old relationship type,
     * new relationship type and the working stage to change an existing
     * relationship type to a new type
     *
     * @param src the name of the source class
     * @param dest the name of the destination class
     * @param oldReltype the old relationship type
     * @param newRelType the new relationship type
     * @param stage the working stage
     */
    public static void changeRelTypeAction(String src, String dest, String oldReltype, String newRelType, Stage stage){
        // if any strings are empty, display error message
        if(oldReltype.isEmpty() || newRelType.isEmpty()){
            GUIView.popUpWindow("Error", "All fields are required");
        }
        // if source class does not exist, display error message
        else if(UMLModel.findClass(src) == null){
            GUIView.popUpWindow("Error", "Class does not exist");
        }
        // if dest class does not exist, display error message
        else if(UMLModel.findClass(dest) == null){
            GUIView.popUpWindow("Error", "Class does not exist");
        }
        // if the source and destination are not related, display error message
        else if(!UMLModel.isRelated(src, dest)){
            GUIView.popUpWindow("Error", "Classes not related");
        }
        // if the old relationship type does not match the type from the relation
        // display error message
        else if(!UMLModel.findRelType(src, dest).equals(oldReltype)){
            GUIView.popUpWindow("Error", "Type not present");
        }
        // if the new relationship type is not a valid type, display error message
        else if(!UMLModel.checkType(newRelType)){
            GUIView.popUpWindow("Error", "Type not valid");
        }
        // if the old type and the new type are not the same, change the type
        else if(!oldReltype.equals(newRelType)){
            // update the view
            GUIView.deleteRelLine(GUIView.findClassBox(src), GUIView.findClassBox(dest));
            relTypeLine(src, dest, newRelType);
            //
            UMLModel.findRelationship(src, dest, oldReltype).setRelType(newRelType);
            stage.close();
        }
        // else display error message
        else {
            GUIView.popUpWindow("Error", "Type are the same");
        }
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

    public static void relTypeLine(String src, String dest, String reltype){
        switch (reltype) {
            case "aggregation" -> GUIView.drawLine(src, dest, Color.GREEN);
            case "composition" -> GUIView.drawLine(src, dest, Color.YELLOW);
            case "inheritance" -> GUIView.drawLine(src, dest, Color.BLUE);
            case "realization" -> GUIView.drawLine(src, dest, Color.RED);
        }
    }

    public static void checkLineColor(){

    }

    public static void main(String[] args) {
        GUIView.main(args);
    }

}
