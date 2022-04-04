package uml;

import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("DanglingJavadoc")
public class GUIController {

    private GUIView view;
    private UMLModel model;
    private Caretaker caretaker;

    public GUIController(GUIView view, UMLModel model) {
        this.view = view;
        this.model = model;
        this.caretaker = new Caretaker();
    }

    /******************************************************************
     *
     * Start of view action methods
     *
     ******************************************************************/

    /**
     * Saves file to the given file
     *
     * @param file the given file to be saved
     * @param stage the working stage
     */
    public void saveAction(File file, Stage stage) {
        JSON json = new JSON(this.model, this.view);
        json.saveGUI(file);
        stage.close();
    }

    /**
     * Load the given file
     *
     * @param file the given file to be loaded
     * @param stage the working stage
     */
    public void loadAction(File file, Stage stage) {
        try {
            JSON json = new JSON(this.model, this.view);
            setState();
            this.model = json.loadGUI(file);
            clearDiagram();
            uploadDiagram(this.model.getClassList(), this.model.getRelationshipList());
            stage.close();
        } catch (Exception e) {
            this.view.popUpWindow("Error", "File name is required");
        }
    }

    //***************************************//
    //************ Add Actions **************//
    //***************************************//

    /**
     * Creates and adds a class to the classList if the given string is nonempty,
     * pops an error up otherwise
     *
     * @param className the name of the class to be added
     * @param stage     the working stage
     */
    public void addClassAction(String className, Stage stage) {
        // if any string is empty, pop an error up
        if (className.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class name is not a valid input, pop an error up
            if (this.model.isNotValidInput(className)) {
                this.view.popUpWindow("Error", "The class name is invalid");
            // check if the class exists
            } else {
                // if the class does not exist, add it and close
                if (this.model.findClass(className) == null) {
                    UMLClass newClass = new UMLClass(className);
                    setState();
                    this.model.addClass(newClass);
                    stage.close();
                    int multiplier = ((this.model.getClassList().size() - 1) / 8);
                    int yOffset = 20 + (multiplier * 200);
                    int xOffset = (-85) + (((this.model.getClassList().size() - 1) % 8) * 115);
                    // Draw the new box for the class
                    this.view.drawClassBox(className, xOffset, yOffset);
                    // if the class does exist, pop an error up
                } else {
                    this.view.popUpWindow("Error", "Class already exists");
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
    public void addFieldAction(String className, String fieldName, String fieldType, Stage stage) {
        // if any string is empty, pop an error up
        if (className.isEmpty() || fieldName.isEmpty() || fieldType.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the field name is not a valid string, pop an error up
            } else if (this.model.isNotValidInput(fieldName)) {
                this.view.popUpWindow("Error", "The field name is invalid");
            // check if the field exists
            } else {
                // if the field does not exist, add it and close
                if (this.model.findClass(className).findField(fieldName) == null) {
                    Field newField = new Field(fieldName, fieldType);
                    setState();
                    this.model.findClass(className).addField(newField);
                    stage.close();
                    this.view.drawFieldBox(this.model.findClass(className).getFieldList().size(),
                            this.model.findClass(className).getMethodList().size(), newField, className);
                // if the field does exist, pop an error up
                } else {
                    this.view.popUpWindow("Error", "Field already exists");
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
    public void addMethodAction(String className, String methodName, String returnType, Stage stage){
        // if any string is empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || returnType.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the method name is not a valid string, pop an error up
            } else if (this.model.isNotValidInput(methodName)) {
                this.view.popUpWindow("Error", "The method name is invalid");
            // check if the method exists
            } else {
                // if the method does not exist, add it and close
                if (this.model.findClass(className).findMethod(methodName) == null) {
                    Method newMethod = new Method(methodName, returnType);
                    setState();
                    this.model.findClass(className).addMethod(newMethod);
                    stage.close();
                    this.view.drawMethodBox(this.model.findClass(className).getFieldList().size(),
                            this.model.findClass(className).getMethodList().size(), newMethod, className);
                    // if the method does exist, pop an error up
                } else {
                    this.view.popUpWindow("Error", "Method already exists");
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
    public void addRelationshipAction(String srcName, String destName, String relType, Stage stage) {
        // if any string is empty, pop an error up
        if (srcName.isEmpty() || destName.isEmpty() || relType.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the source does not exist, pop an error up
            if (this.model.findClass(srcName) == null) {
                this.view.popUpWindow("Error", "Source does not exist");
            // if the destination does not exist, pop an error up
            } else if (this.model.findClass(destName) == null) {
                this.view.popUpWindow("Error", "Destination does not exist");
            // check if the relationship exists
            } else if (this.model.checkType(relType)){
                // if the relationship does not exist, add it and close
                if (this.model.findRelationship(srcName, destName, relType) == null &&
                        !this.model.isRelated(srcName, destName)) {
                    UMLClass src = this.model.findClass(srcName);
                    UMLClass dest = this.model.findClass(destName);
                    Relationship newRel = new Relationship(src, dest, relType);
                    setState();
                    this.model.addRel(newRel);
                    this.view.drawLine(srcName, destName, this.model.findRelType(srcName, destName));
                    stage.close();
                // if the relationship does exist, pop an error up
                } else {
                    this.view.popUpWindow("Error", "Relationship already exists");
                }
            }
            else {
                this.view.popUpWindow("Error", "Relationship type " + relType + " does not exist");
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
    public boolean addParameterAction(String className, String methodName, String paramName, String paramType,
                                          Stage stage) {
        // if any string is empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || paramName.isEmpty() || paramType.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the method does not exist, pop an error up
            } else if (this.model.findClass(className).findMethod(methodName) == null) {
                this.view.popUpWindow("Error", "Method does not exist");
            // if the parameter name is not a valid string, pop an error up
            } else if (this.model.isNotValidInput(paramName)) {
                this.view.popUpWindow("Error", "The parameter name is invalid");
            } else {
                // if the parameter does not exist, add it and close
                if (this.model.findClass(className).findMethod(methodName).findParameter(paramName) == null) {
                    Parameter newParam = new Parameter(paramName, paramType);
                    setState();
                    this.model.findClass(className).findMethod(methodName).addParameter(newParam);
                    stage.close();
                    this.view.findClassBox(className).addText(this.model.findClass(className).findMethod(methodName),
                            this.model.findClass(className).getFieldList().size(),
                            this.model.findClass(className).getMethodList().size(), true);
                    this.view.resizeMethod(this.model.findClass(className).findMethod(methodName), className);
                    return true;
                // if the method does exist, pop an error up
                } else {
                    this.view.popUpWindow("Error", "Field already exists");
                }
            }
        }
        return false;
    }

    //***************************************//
    //*********** Delete Actions ************//
    //***************************************//

    /**
     * Deletes a class to the classList if the given string is nonempty and the class exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to be deleted
     * @param stage the working stage
     */
    public void deleteClassAction(String className, Stage stage) {
        // if any string is empty, pop an error up
        if (className.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the class does exist, delete it and close
            } else {
                UMLClass classToDelete = this.model.findClass(className);
                setState();
                // update the view
                stage.close();
                // for each relationship object in the relationship list
                for(Relationship r : this.model.getRelationshipList()){
                    // if the className is the name of the source in a relationship
                    if(r.getSource().getClassName().equals(className)){
                        // delete the relationship line in the diagram
                        this.view.deleteRelLine(this.view.findClassBox(className),
                                this.view.findClassBox(r.getDestination().getClassName()));
                    }
                    // if className is the name of the destination in a relationship
                    if(r.getDestination().getClassName().equals(className)){
                        // delete the relationship line in the diagram
                        this.view.deleteRelLine(this.view.findClassBox(r.getSource().getClassName()),
                                this.view.findClassBox(className));
                    }
                    else{
                        break;
                    }
                }
                // delete the classbox from the view
                this.view.deleteClassBox(className);
                // update the model
                this.model.updateRelationshipList(className);
                this.model.deleteClass(classToDelete);
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
    public void deleteFieldAction(String className, String fieldName, Stage stage){
        // if any string is empty, pop an error up
        if (className.isEmpty() || fieldName.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the field does not exist, pop an error up
            } else if (this.model.findClass(className).findField(fieldName) == null) {
                this.view.popUpWindow("Error", "Field does not exist");
            // if the field does exist, delete it and close
            } else {
                Field fieldToDelete = this.model.findClass(className).findField(fieldName);
                setState();
                this.model.findClass(className).deleteField(fieldToDelete);
                // go through fieldList in ClassBox, remove the field from the fieldList, then
                // remove field from flow
                for (int i = 0; i < this.view.findClassBox(className).getFieldTextList().size(); ++i) {
                    if (this.view.findClassBox(className).getFieldTextList().get(i).getText().
                            startsWith("\n- " + fieldName)) {
                        this.view.findClassBox(className).getFlow().getChildren().remove(
                                this.view.findClassBox(className).getFieldTextList().get(i));
                        this.view.findClassBox(className).getFieldTextList().remove(i);
                    }
                }
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
    public void deleteMethodAction(String className, String methodName, Stage stage){
        // if any string is empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the method does not exist, pop an error up
            } else if (this.model.findClass(className).findMethod(methodName) == null) {
                this.view.popUpWindow("Error", "Method does not exist");
            // if the method does exist, delete it and close
            } else {
                Method methodToDelete = this.model.findClass(className).findMethod(methodName);
                setState();
                this.model.findClass(className).deleteMethod(methodToDelete);
                // go through fieldList in ClassBox, remove the field from the fieldList, then
                // remove field from flow
                for (int i = 0; i < this.view.findClassBox(className).getMethTextList().size(); ++i) {
                    if (this.view.findClassBox(className).getMethTextList().get(i).getText().
                            startsWith("\n+ " + methodName)) {
                        this.view.findClassBox(className).getFlow().getChildren().remove(
                                this.view.findClassBox(className).getMethTextList().get(i));
                        this.view.findClassBox(className).getMethTextList().remove(i);
                    }
                }
                stage.close();
            }
        }
    }

    /**
     * Deletes a field from a class if the given string is nonempty and the field exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to delete the field from
     * @param methodName the name of the method to search for parameters
     * @param paramName the name of the parameter to delete
     * @param stage the working stage
     */
    public boolean deleteParamAction(String className, String methodName, String paramName, Stage stage){
        // if any string is empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || paramName.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
                // if the method does not exist, pop an error up
            } else if (this.model.findClass(className).findMethod(methodName) == null) {
                this.view.popUpWindow("Error", "Method does not exist");
                // if the parameter does not exist, pop an error up
            } else if (this.model.findClass(className).findMethod(methodName).findParameter(paramName) == null) {
                this.view.popUpWindow("Error", "Parameter does not exist");
                // if the parameter name is not a valid string, pop an error up
            } else {
                setState();
                Parameter paramToDelete = this.model.findClass(className).findMethod(methodName).findParameter(paramName);
                this.model.findClass(className).findMethod(methodName).deleteParameter(paramToDelete);
                // go through methlist in ClassBox, edit text of method in that methlist, remove the
                // old method, add the new, renamed method at the bottom
                for (int i = 0; i < this.view.findClassBox(className).getMethTextList().size(); ++i) {
                    if (this.view.findClassBox(className).getMethTextList().get(i).getText().
                            startsWith("\n+ " + methodName)) {
                        this.view.findClassBox(className).getMethTextList().remove(i);
                        this.view.findClassBox(className).addText(this.model.findClass(className).
                                        findMethod(methodName),
                                this.model.findClass(className).getFieldList().size(),
                                this.model.findClass(className).getMethodList().size(), true);
                    }
                }
                stage.close();
                return (this.model.findClass(className).findMethod(methodName).getParamList().size() > 0);
            }
        }
        return false;
    }

    /**
     * Deletes a relationship in the relationship list if the given strings
     * are nonempty and the relationship exists, display error otherwise
     *
     * @param srcName the name of the source class
     * @param destName the name of the destination class
     * @param stage the working stage
     */
    public void deleteRelAction(String srcName, String destName, Stage stage) {
        // if any string is empty, pop an error up
        if (srcName.isEmpty() || destName.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        // if the relationship between the two classes in src->dest order does
        // exist, display error message
        } else if (this.model.findRelationship(srcName, destName,
                this.model.findRelType(srcName, destName)) == null) {
            this.view.popUpWindow("Error", "Relationship does not exist");
        // delete the relationship, update the view and close
        } else {
            setState();
            Relationship relToDelete = this.model.findRelationship(srcName, destName,
                    this.model.findRelType(srcName, destName));
            // update view
            this.view.deleteRelLine(this.view.findClassBox(srcName), this.view.findClassBox(destName));
            // update model
            this.model.deleteRel(relToDelete);
            stage.close();
        }
    }

    //***************************************//
    //********** Rename Actions *************//
    //***************************************//

    /**
     * Renames a class to a given string in the classList if the given string is nonempty and the class exists,
     * pops an error up otherwise
     *
     * @param className the name of the class to be rename
     * @param newClassName the new name for the class
     * @param stage the working stage
     */
    public void renameClassAction(String className, String newClassName, Stage stage) {
        // if the string is empty, pop an error up
        if (className.isEmpty()) {
            this.view.popUpWindow("Error", "Class name is required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the class name is not a valid string, pop an error up
            } else if (this.model.isNotValidInput(newClassName)) {
                this.view.popUpWindow("Error", "The class name is invalid");
            } else {
                // if the class name is not already in use, rename the field
                if (this.model.findClass(newClassName) == null) {
                    setState();
                    // update model
                    this.model.findClass(className).setClassName(newClassName);
                    // update view
                    stage.close();
                    this.view.findClassBox(className).getFlow().getChildren().remove(0);
                    Text newTextName = new Text(newClassName);
                    newTextName.setFont(Font.font(newTextName.getFont().getName(), FontWeight.BOLD, 12));
                    this.view.findClassBox(className).getFlow().getChildren().add(0, newTextName);
                    this.view.findClassBox(className).setClassTitle(newTextName);
                    this.view.findClassBox(className).setClassBoxName(newClassName);
                // otherwise, pop an error up
                } else {
                    this.view.popUpWindow("Error", "The field name is already in use");
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
    public void renameFieldAction(String className, String fieldName, String newFieldName, Stage stage) {
        // if any strings are empty, pop an error up
        if (className.isEmpty() || fieldName.isEmpty() || newFieldName.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
                // if the field does not exist, pop an error up
            } else if (this.model.findClass(className).findField(fieldName) == null) {
                this.view.popUpWindow("Error", "Field does not exist");
                // if the field name is not a valid string, pop an error up
            } else if (this.model.isNotValidInput(newFieldName)) {
                this.view.popUpWindow("Error", "The field name is invalid");
                // if the field exists and the type matches, rename the field
            }
            // if the field name is not already in use, rename the field
            if (this.model.findClass(className).findField(newFieldName) == null) {
                setState();
                // update model
                this.model.findClass(className).findField(fieldName).setAttName(newFieldName);
                // update view
                stage.close();
                // go through fieldList in ClassBox, edit text of field in that fieldlist, remove the
                // old text, add the new, renamed field at the bottom
                for (int i = 0; i < this.view.findClassBox(className).getFieldTextList().size(); ++i) {
                    if (this.view.findClassBox(className).getFieldTextList().get(i).getText().
                            startsWith("\n- " + fieldName)) {
                        this.view.findClassBox(className).getFieldTextList().get(i).getText().
                                replace("\n- " + fieldName, "\n- " + newFieldName);
                        this.view.findClassBox(className).getFieldTextList().remove(i);
                        this.view.findClassBox(className).addText(this.model.findClass(className).findField(newFieldName),
                                this.model.findClass(className).getFieldList().size(),
                                this.model.findClass(className).getMethodList().size(), true);
                    }
                }
                // otherwise, pop an error up
            } else {
                this.view.popUpWindow("Error", "The field name is already in use");
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
    public void renameMethodAction(String className, String methodName, String newMethodName, Stage stage) {
        // if any strings are empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || newMethodName.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
            // if the method does not exist, pop an error up
            } else if (this.model.findClass(className).findMethod(methodName) == null) {
                this.view.popUpWindow("Error", "Method does not exist");
            // if the method name is not a valid string, pop an error up
            } else if (this.model.isNotValidInput(newMethodName)) {
                this.view.popUpWindow("Error", "The method name is invalid");
            } else {
                // if the method name is not already in use, rename the method
                if (this.model.findClass(className).findMethod(newMethodName) == null) {
                    setState();
                    this.model.findClass(className).findMethod(methodName).setAttName(newMethodName);
                    stage.close();
                    // go through methlist in ClassBox, edit text of method in that methlist, remove the
                    // old method, add the new, renamed method at the bottom
                    for (int i = 0; i < this.view.findClassBox(className).getMethTextList().size(); ++i) {
                        if (this.view.findClassBox(className).getMethTextList().get(i).getText().
                                startsWith("\n+ " + methodName)) {
                            this.view.findClassBox(className).getMethTextList().get(i).getText().
                                    replace("\n+ " + methodName, "\n+ " + newMethodName);
                            this.view.findClassBox(className).getMethTextList().remove(i);
                            this.view.findClassBox(className).addText(this.model.findClass(className).
                                            findMethod(newMethodName),
                                    this.model.findClass(className).getFieldList().size(),
                                    this.model.findClass(className).getMethodList().size(), true);
                        }
                    }
                // otherwise, pop an error up
                } else {
                    this.view.popUpWindow("Error", "The method name is already in use");
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
    public boolean changeParameterAction(String className, String methodName, String paramName,
                                             String newParamName, String newParamType, Stage stage) {
        // if any strings are empty, pop an error up
        if (className.isEmpty() || methodName.isEmpty() || paramName.isEmpty() || newParamName.isEmpty()) {
            this.view.popUpWindow("Error", "All fields are required");
        } else {
            // if the class does not exist, pop an error up
            if (this.model.findClass(className) == null) {
                this.view.popUpWindow("Error", "Class does not exist");
                // if the method does not exist, pop an error up
            } else if (this.model.findClass(className).findMethod(methodName) == null) {
                this.view.popUpWindow("Error", "Method does not exist");
                // if the parameter does not exist, pop an error up
            } else if (this.model.findClass(className).findMethod(methodName).findParameter(paramName) == null) {
                this.view.popUpWindow("Error", "Parameter does not exist");
                // if the parameter name is not a valid string, pop an error up
            } else if (this.model.isNotValidInput(newParamName)) {
                this.view.popUpWindow("Error", "The parameter name is invalid");
            } else {
                // if the parameter name is not already in use, change the parameter
                if (this.model.findClass(className).findMethod(methodName).findParameter(newParamName) == null) {
                    setState();
                    this.model.findClass(className).findMethod(methodName).findParameter(paramName).
                            setAttName(newParamName);
                    this.model.findClass(className).findMethod(methodName).findParameter(newParamName)
                            .setFieldType(newParamType);
                    stage.close();
                    // go through methlist in ClassBox, edit text of method in that methlist, remove the
                    // old method, add the new, renamed method at the bottom
                    for (int i = 0; i < this.view.findClassBox(className).getMethTextList().size(); ++i) {
                        if (this.view.findClassBox(className).getMethTextList().get(i).getText().
                                startsWith("\n+ " + methodName)) {
                            this.view.findClassBox(className).getMethTextList().remove(i);
                            this.view.findClassBox(className).addText(this.model.findClass(className).
                                            findMethod(methodName),
                                    this.model.findClass(className).getFieldList().size(),
                                    this.model.findClass(className).getMethodList().size(), true);
                            this.view.resizeMethod(this.model.findClass(className).findMethod(methodName), className);
                        }
                    }
                    return true;
                    // otherwise, pop an error up
                } else {
                    this.view.popUpWindow("Error", "The parameter name is already in use");
                }
            }
        }
        return false;
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
    public void changeRelTypeAction(String src, String dest, String oldReltype, String newRelType, Stage stage){
        // if any strings are empty, display error message
        if(oldReltype.isEmpty() || newRelType.isEmpty()){
            this.view.popUpWindow("Error", "All fields are required");
        }
        // if source class does not exist, display error message
        else if(this.model.findClass(src) == null){
            this.view.popUpWindow("Error", "Class does not exist");
        }
        // if dest class does not exist, display error message
        else if(this.model.findClass(dest) == null){
            this.view.popUpWindow("Error", "Class does not exist");
        }
        // if the source and destination are not related, display error message
        else if(!this.model.isRelated(src, dest)){
            this.view.popUpWindow("Error", "Classes not related");
        }
        // if the old relationship type does not match the type from the relation
        // display error message
        else if(!this.model.findRelType(src, dest).equals(oldReltype)){
            this.view.popUpWindow("Error", "Type not present");
        }
        // if the new relationship type is not a valid type, display error message
        else if(!this.model.checkType(newRelType)){
            this.view.popUpWindow("Error", "Type not valid");
        }
        // if the old type and the new type are not the same, change the type
        else if(!oldReltype.equals(newRelType)){
            setState();
            // update the view
            this.view.deleteRelLine(this.view.findClassBox(src), this.view.findClassBox(dest));
            this.view.drawLine(src, dest, newRelType);
            // set the relationship type
            this.model.findRelationship(src, dest, oldReltype).setRelType(newRelType);
            stage.close();
        }
        // else display error message
        else {
            this.view.popUpWindow("Error", "Type are the same");
        }
    }

    /**
     * Stores the current state in the state stack
     */
    public void setState() {
        Memento currState = new Memento(new UMLModel(this.model.getClassList(), this.model.getRelationshipList(), this.model.getCoordinateMap()));
        caretaker.pushToUndoStack(currState);
    }

    public void undoAction() {
        // if the stack is empty, return false, otherwise perform the undo and return
        if (this.caretaker.undoStackIsEmpty()) {
            this.view.popUpWindow("Error", "No actions to undo.");
        } else {
            // get the current state the model is in
            Memento currState = new Memento(new UMLModel(this.model.getClassList(), this.model.getRelationshipList(), this.model.getCoordinateMap()));
            // get the previous state on the state stack, and pass the helper the current state for the redo stack
            Memento prevState = caretaker.undoHelper(currState);
            // make this current model the previous model
            this.model = prevState.getState();
            clearDiagram();
            uploadDiagram(this.model.getClassList(), this.model.getRelationshipList());
        }
    }

    public void redoAction() {
        // if the stack is empty, return false, otherwise perform the redo and return
        if (this.caretaker.redoStackIsEmpty()) {
            this.view.popUpWindow("Error", "No actions to redo.");
        } else {
            // get the current state the model is in
            Memento currState = new Memento(new UMLModel(this.model.getClassList(), this.model.getRelationshipList(), this.model.getCoordinateMap()));
            // get the previous state on the state stack, and pass the helper the current state for the undo stack
            Memento prevState = caretaker.redoHelper(currState);
            // make this current model the previous model
            this.model = prevState.getState();
            clearDiagram();
            uploadDiagram(this.model.getClassList(), this.model.getRelationshipList());
        }
    }

    /******************************************************************
     *
     * End of view action methods
     *
     ******************************************************************/

    /**
     * Returns the class list
     *
     * @return the class list
     */
    public ArrayList<UMLClass> getClassList() {
        return this.model.getClassList();
    }

    /**
     * Returns the relationship list
     *
     * @return the relationship list
     */
    public ArrayList<Relationship> getRelationshipList() {
        return this.model.getRelationshipList();
    }

    /**
     * Exits the current window if called
     *
     * @param stage the working stage
     */
    public void exitAction(Stage stage) {
        stage.close();
    }

    /**
     * Clear the diagram of all classboxes and relationship lines
     */
    public void clearDiagram() {
        for (ClassBox cbObj : this.view.getClassBoxList()) {
            this.view.getSuperRoot().getChildren().remove(cbObj.getClassPane());
        }
        for (RelLine relLineObj : this.view.getLineList()) {
            this.view.getSuperRoot().getChildren().remove(relLineObj.getLine());
        }
        for(ArrayList<Line> arrows : this.view.getArrowList()){
            for (Line arrow : arrows) {
                this.view.getSuperRoot().getChildren().remove(arrow);
            }
            this.view.getSuperRoot().getChildren().remove(arrows);
        }
        this.view.getClassBoxList().clear();
        this.view.getLineList().clear();
        this.view.getArrowList().clear();
    }

    /**
     * Takes in all the data from the model and draws an entirely new diagram
     *
     * @param classList the list of classes in the diagram
     * @param relList the list of relationships in the diagram
     */
    public void uploadDiagram(ArrayList<UMLClass> classList, ArrayList<Relationship> relList) {
        int classListSize = 0;
        for (UMLClass classObj : classList) {
            // formats the class boxes
            classListSize += 1;
            int multiplier = (((int) classListSize - 1) / (int) 8);
            int yOffset = 20 + (multiplier * 200);
            int xOffset = (-85) + (((classListSize - 1) % 8) * 115);
            int fieldListSize = 0;
            int methodListSize = 0;
            // draws class boxes with fields, methods and parameters
            this.view.drawClassBox(classObj.getClassName(), xOffset, yOffset);
            for (Field fieldObj : classObj.getFieldList()) {
                fieldListSize += 1;
                this.view.drawFieldBox(fieldListSize, methodListSize,
                        fieldObj, classObj.getClassName());
            }
            // move all of the class boxes to the right positions
            this.view.moveClassBoxes();
            for (Method methodObj : classObj.getMethodList()) {
                methodListSize += 1;
                this.view.drawMethodBox(fieldListSize, methodListSize,
                        methodObj, classObj.getClassName());
                this.view.resizeMethod(methodObj, classObj.getClassName());
                this.view.findClassBox(classObj.getClassName()).addText(this.model.findClass(
                        classObj.getClassName()).findMethod(methodObj.getAttName()),
                        this.model.findClass(classObj.getClassName()).getFieldList().size(),
                        this.model.findClass(classObj.getClassName()).getMethodList().size(), true);
            }
        }
        // re-draws relationship lines
        for (Relationship relObj : relList) {
            this.view.drawLine(relObj.getSource().getClassName(),
                    relObj.getDestination().getClassName(), relObj.getRelType());
        }
    }

}
