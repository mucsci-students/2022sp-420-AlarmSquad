package uml;

import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.*;
@SuppressWarnings("DanglingJavadoc")

/**
 * The controller for the CLI version of the UML Diagram
 *
 * Authors: AlarmSquad
 */
public class CLIController {

    private UMLModel model;
    private Caretaker caretaker;
    private CLIView view;

    public CLIController(UMLModel model, Caretaker caretaker, CLIView view) {
        this.model = model;
        this.caretaker = caretaker;
        this.view = view;
    }
  
    //creates a line reader and a terminal
    private LineReader reader;
    private Terminal terminal;

    //our prompt string
    private String prompt = "> ";

    // Interprets user input
    public void run() {
        clearScreen();
        //builds console from cli view
        reader = this.view.buildConsole();
        terminal = reader.getTerminal();
        // intro to CLI for user
        String intro = "Welcome to ALARM Squad's UML editor!";
        intro += "\n\nType 'help' to list all commands and their accompanying descriptions.\n";
        terminal.writer().println(intro);
        //loop for reading and replacing input
        while (true) {
            //matches strings to regex and replaces them
            String line = reader.readLine(prompt).replaceAll("\\s+", " ").trim();
            if (line.equals("exit")) {
                return;
            } else if (line.equals("")) {
                continue;
            } else {
                //adds spaces between strings
                String[] input = line.split(" ");
                //passes input to switch statement
                interpretInput(input);
            }
        }
    }
    // Interprets user input
    public void interpretInput(String[] inputList) {
        try {
            // Check first word user inputted and act accordingly
            switch (inputList[0]) {

                //***************************************//
                //********* Terminal Commands ***********//
                //***************************************//

                    // clear screen
                    case "clear" -> {
                        clearScreen();
                    }
                    // save case
                    case "save" -> {
                        terminal.writer().print("Save file name: ");
                        String saveFileName = reader.readLine().trim();
                        JSON json = new JSON(this.model);
                        json.saveCLI(saveFileName);
                    }
                    // load case
                    case "load" -> {
                        JSON json = new JSON(this.model);
                        // if no files exist
                        if (json.ifDirIsEmpty()) {
                            terminal.writer().println("No save files found");
                        }
                        // load the file
                        else {
                            terminal.writer().print("Load file name: ");
                            String loadFileName = reader.readLine().trim();
                            UMLModel newModel = json.loadCLI(loadFileName);
                            if (newModel != null) {
                                // inform the user that the load succeeded
                                terminal.writer().println("Diagram has been loaded from \"" + loadFileName + "\"");
                                setState();
                                this.model = newModel;
                            } else {
                                terminal.writer().println("File does not exist");
                            }
                        }
                    }
                    // help case
                    case "help" -> {
                        terminal.writer().println(model.getCLIHelpMenu());
                    }
                    // undo case
                    case "undo" -> {
                        if (!undo()) {
                            terminal.writer().println("No actions to undo.");
                        } else {
                            terminal.writer().println("Last action undone.");
                        }
                    }
                    // undo case
                    case "redo" -> {
                        if (!redo()) {
                            terminal.writer().println("No actions to redo.");
                        } else {
                            terminal.writer().println("Last action redone.");
                        }
                    }

                //***************************************//
                //************** Add cases **************//
                //***************************************//

                case "add", "a" -> {
                    switch (inputList[1]) {
                        // add class
                        case "class" -> {
                            // Get user defined name for Class, then adds new Class to the classList
                            String className = getClassName("add", "");

                            // Edge cases for class name, checks validity
                            if (model.isNotValidInput(className)) {
                                break;
                            }

                            // Checks for duplicates
                            if(model.getClassList().stream().anyMatch(o -> o.getClassName().equals(className))){
                                terminal.writer().printf("Class %s already exists\n", className);
                            } else {
                                // Adds class to class list then prompts confirmation to use
                                UMLClass newUMLClass = new UMLClass(className);
                                setState();
                                model.addClass(newUMLClass);
                                terminal.writer().println("Added class \"" + className + "\"");
                            }
                        }
                        // add relationship
                        case "rel" -> {
                            // If user inputted "a rel" with no flag, ask for one
                            if (inputList.length == 2) {
                                if (!addRelFlag(convertStringList2ArrayList(inputList), "add")) {
                                    // If user tried to input an invalid flag, break
                                    break;
                                }
                            }
                            switch (inputList[2]) {
                                // relationship type flags
                                case "-a", "-c", "-i", "-r" -> {
                                    String relType = flagToString(inputList[2]);
                                    // if a flag was not given by user input
                                    if (!relType.equals("")) {
                                        terminal.writer().print("Enter source class name: ");
                                        String sourceName = reader.readLine().trim();
                                        // If source class is valid and exists get destination class
                                        if (model.findClass(sourceName) != (null)) {
                                            terminal.writer().print("Enter destination: ");
                                            String destinationName = reader.readLine().trim();
                                            // If destination class is valid and exists add
                                            // relationship to relationship array list
                                            if (model.findClass(destinationName) != (null)) {
                                                if (model.findRelationship(sourceName, destinationName, relType)
                                                        != null || model.isRelated(sourceName, destinationName)){
                                                    terminal.writer().println("Relationship already exists between " +
                                                            sourceName + " and " + destinationName + " with "
                                                            + model.findRelType(sourceName,destinationName) +
                                                            " type");
                                                    break;
                                                }
                                                Relationship newRelationship =
                                                        new Relationship(model.findClass(sourceName),
                                                                model.findClass(destinationName), relType);
                                                setState();
                                                model.addRel(newRelationship);
                                                terminal.writer().println("Relationship added between " + sourceName
                                                        + " and " + destinationName + " with " + relType + " type");
                                            }
                                            // If destination class does not exist, inform user and break
                                            else {
                                                terminal.writer().println("Class " + destinationName + " does not exist");
                                            }
                                        }
                                        // If source class does not exist, inform user and break
                                        else {
                                            terminal.writer().println("Class " + sourceName + " does not exist");
                                        }
                                    }
                                }
                                // If flag user entered is invalid, inform user and break
                                default -> {
                                    terminal.writer().println("Invalid flag");
                                }
                            }
                        }
                        // add attribute
                        case "att" -> {
                            // If user inputted "a att" with no flag, ask for one
                            if (inputList.length == 2) {
                                if (addAttFlag(convertStringList2ArrayList(inputList), "add")) {
                                    // If user tried to input an invalid flag, break
                                    break;
                                }
                            }
                            switch (inputList[2]) {
                                // add field or method
                                case "-f", "-m" -> {
                                    // Convert flag to "field" or "method".
                                    // Needed for method calls later on
                                    String attType = flagToString(inputList[2]);
                                    if (!attType.equals("")) {
                                        // Get name of class user wants to add attribute to and
                                        // ensure the class exists
                                        String classToAddAttName = getClassName("add", attType);
                                        UMLClass classToAddAtt = model.findClass(classToAddAttName);
                                        // If class exists...
                                        if (classToAddAtt != null) {
                                            // Get name of attribute user wants to add
                                            String attName = getAttName(attType);
                                            // Ensure attribute name is valid
                                            if (!model.isNotValidInput(attName)) {
                                                // If attribute doesn't already exist in class, add it to class
                                                if (classToAddAtt.getAttList(attType).stream()
                                                        .noneMatch(attObj -> attObj.getAttName().equals(attName))){
                                                    setState();
                                                    addAttribute(classToAddAtt, attType, attName);
                                                }
                                                // If attribute user entered already exists in class, inform user
                                                // and break
                                                else {
                                                    terminal.writer().println(attType + " " + attName +
                                                            " already exists in class " +
                                                            classToAddAtt.getClassName());
                                                }
                                            }
                                            // If class user entered does not exist, inform user and break
                                        } else {
                                            terminal.writer().println("Class " + classToAddAttName + " does not exist");
                                        }
                                    }
                                }
                                // add parameter
                                case "-p" -> {
                                    UMLClass classToAddParam;
                                    Method methodToAddParam;
                                    String paramName;
                                    if((classToAddParam = findClass("add", "parameter")) == null) {
                                        break;
                                    }
                                    if((methodToAddParam = findMethod(classToAddParam)) == null) {
                                        break;
                                    }

                                    String answer = "y";

                                    // While loop lets user to continuously add parameters until they choose not
                                    // to or an error occurs
                                    setState();
                                    while (answer.equals("y")) {
                                        if((paramName = findParamName(methodToAddParam)) == null) {
                                            break;
                                        }
                                        String paramType;
                                        if ((paramType = getAttType("parameter")) == null)
                                        {
                                            break;
                                        }
                                        Parameter param = new Parameter(paramName, paramType);
                                        setState();
                                        methodToAddParam.addParameter(param);
                                        terminal.writer().printf("Add another? [y/n]: ");
                                        answer = reader.readLine().toLowerCase().trim();
                                    }
                                }
                                // If flag user entered is invalid, inform user and break
                                default -> {
                                    terminal.writer().println("Invalid flag");
                                }
                            }
                        }
                        // If the user's command is not valid
                        default -> {
                            terminal.writer().println("Please enter a valid command");
                        }
                    }
                }

                //***************************************//
                //************ Delete cases *************//
                //***************************************//

                case "delete", "d" -> {
                    switch (inputList[1]) {
                        // delete class of the user's choice from the class list
                        case "class" -> {
                            // Get user input of Class name to be deleted
                            terminal.writer().print("Enter class name to delete: ");
                            String classDeleteInput = reader.readLine().trim();
                            UMLClass UMLClassToDel = model.findClass(classDeleteInput);
                            if (UMLClassToDel != null) {
                                // Copy classList into new ArrayList with deleted Class
                                setState();
                                model.setClassList(deleteClass(classDeleteInput));
                            }
                        }
                        // delete relationship
                        case "rel" -> {
                            terminal.writer().print("Enter relationship source name: ");
                            String sourceToFind = reader.readLine().trim();
                            UMLClass src = model.findClass(sourceToFind);
                            if (src != null) {
                                // If source name was found, proceed to find dest name
                                terminal.writer().print("Enter relationship destination name: ");
                                String destToFind = reader.readLine().trim();
                                UMLClass dest = model.findClass(destToFind);
                                if (dest != null) {
                                    // Find the type of relationship
                                    String relType = model.findRelType(sourceToFind, destToFind);
                                    // Find the relationship
                                    Relationship r = model.findRelationship(sourceToFind, destToFind, relType);
                                    if (r != null) {
                                        terminal.writer().print("Delete relationship between \"" +
                                                r.getSource().getClassName() + "\" and \"" +
                                                r.getDestination().getClassName() + "\"? (y/n): ");
                                        String rAnswer = reader.readLine().trim();
                                        // If the user wants to delete the relationship, proceed to do so
                                        if (rAnswer.equalsIgnoreCase("y")) {
                                            setState();
                                            model.deleteRel(r);
                                            terminal.writer().println("Relationship has been deleted");
                                        }
                                        // If not, prompt user and move on
                                        else if (rAnswer.equalsIgnoreCase("n")) {
                                            terminal.writer().println("Relationship has NOT been deleted");
                                        }
                                    }
                                }
                            }
                        }
                        // delete attribute
                        case "att" -> {
                            // If user inputted "a att" with no flag, ask for one.
                            if (inputList.length == 2) {
                                if (addAttFlag(convertStringList2ArrayList(inputList), "delete")) {
                                    // If user inputs invalid flag, break
                                    break;
                                }
                            }
                            // delete field or method
                            switch (inputList[2]) {
                                case "-f", "-m" -> {
                                    // Convert -f flag to the string "field",
                                    // and the same for -m flag, etc.
                                    String flagName = flagToString(inputList[2]);

                                    // If user entered a valid flag...
                                    if (!flagName.equals("")) {
                                        // Get name of class user wants to add attribute to and
                                        // ensure the class exists
                                        String classToDelAttName = getClassName("delete", flagName);
                                        UMLClass classToDelAtt = model.findClass(classToDelAttName);
                                        if (classToDelAtt != null) {
                                            setState();
                                            // Delete attribute within specified class
                                            delAttribute(classToDelAtt, flagName);
                                        }
                                        // If class was not found, inform user and break
                                        else {
                                            terminal.writer().println("Class " + classToDelAttName + " does not exist");
                                        }
                                    }
                                }
                                // delete parameter
                                case "-p" -> {
                                    UMLClass classToDeleteParam;
                                    Method methodToDeleteParam;
                                    Parameter param;
                                    if((classToDeleteParam = findClass("delete", "parameter")) == null) {
                                        break;
                                    }
                                    if((methodToDeleteParam = findMethod(classToDeleteParam)) == null) {
                                        break;
                                    }

                                    String answer = "y";

                                    // While loop lets user to continuously add parameters until they choose not
                                    // to or an error occurs
                                    while (answer.equals("y")) {
                                        if((param = findParam(methodToDeleteParam)) == null) {
                                            break;
                                        }
                                        String paramName = param.getAttName();
                                        terminal.writer().printf("Delete parameter \"%s\"? [y/N]: ", paramName);
                                        answer = reader.readLine().trim();
                                        // If the user wants to delete a parameter, proceed to do so
                                        if (answer.equalsIgnoreCase("y")) {
                                            setState();
                                            methodToDeleteParam.deleteParameter(param);
                                            if (methodToDeleteParam.getParamList().size() <= 0) {
                                                break;
                                            }
                                            terminal.writer().printf("Parameter \"%s\" has been deleted." +
                                                    " Delete another? [y/N]: ", paramName);
                                            answer = reader.readLine().trim();
                                        }
                                        // If user types n, confirm and return
                                        else {
                                            terminal.writer().printf("Parameter \"%s\" has NOT been deleted\n", paramName);
                                            break;
                                        }
                                    }
                                } // If user entered invalid flag, inform user and break
                                default -> {
                                    terminal.writer().println("Invalid flag");
                                    terminal.writer().println(prompt);
                                }
                            }
                        }
                        // If the user's command is not valid
                        default -> {
                            terminal.writer().println("Please enter a valid command");
                        }
                    }
                }

                //***************************************//
                //************ Rename cases *************//
                //***************************************//

                case "rename", "r" -> {
                    switch (inputList[1]) {
                        case "class" -> {
                            // Get user input of Class name to be renamed
                            terminal.writer().print("Enter class to rename: ");
                            String oldClassName = reader.readLine().trim();
                            // If Class exists, set Class name to user inputted name
                            UMLClass oldUMLClass = model.findClass(oldClassName);
                            // Prompt user to rename a class name
                            if (oldUMLClass != null) {
                                terminal.writer().print("Enter new name for class " + oldUMLClass.getClassName() + ": ");
                                String newName = reader.readLine().trim();
                                if (model.isNotValidInput(newName)) {
                                    terminal.writer().println("\"" + newName + "\" is not a valid identifier\n");
                                    break;
                                }
                                if (model.findClass(newName) != null) {
                                    terminal.writer().println("Class \"" + newName + "\" already exists\n");
                                    break;
                                }
                                setState();
                                oldUMLClass.setClassName(newName);
                                // Inform user of renamed Class
                                terminal.writer().println("The class \"" + oldClassName +
                                        "\" has been renamed to \"" + oldUMLClass.getClassName() + "\"");
                            }
                        }
                        // If the user's command is not valid
                        default -> {
                            terminal.writer().println("Please enter a valid command");
                        }
                    }
                }

                //***************************************//
                //************ Change cases *************//
                //***************************************//

                case "c", "change" -> {
                    switch (inputList[1]) {
                        // change attribute
                        case "att" -> {
                            switch (inputList[2]) {
                                // change field
                                case "-f" -> {
                                    // Show user classes and attributes before asking for input
                                    listClasses();
                                    terminal.writer().print("Enter class that contains field: ");
                                    String classWithFieldName = reader.readLine().trim();
                                    // Ensure class exists
                                    UMLClass UMLClassWithField = model.findClass(classWithFieldName);
                                    if (UMLClassWithField != null) {
                                        // List attributes in class before asking for input
                                        listClass(classWithFieldName);
                                        terminal.writer().print("Enter field to be renamed: ");
                                        String oldFieldName = reader.readLine().trim();
                                        // Ensure attribute exists
                                        Attribute newField = UMLClassWithField.findField(oldFieldName);
                                        if (newField != null) {
                                            // Rename attribute with user's new name
                                            terminal.writer().print("Enter new name for " + oldFieldName + ": ");
                                            String newFieldName = reader.readLine().trim();
                                            if (model.isNotValidInput(newFieldName)) {
                                                terminal.writer().println("\"" + newFieldName +
                                                        "\" is not a valid identifier\n");
                                                break;
                                            }
                                            if (UMLClassWithField.findField(newFieldName) != null) {
                                                terminal.writer().println("Field \"" + newFieldName +
                                                        "\" already exists in class \"" + classWithFieldName +
                                                        "\"\n");
                                                break;
                                            }
                                            //get fieldtype
                                            String newFieldType = getAttType(newFieldName);
                                            if(newFieldType == null){
                                                break;
                                            }
                                            //make a new field
                                            Field changedField = new Field(newFieldName, newFieldType);
                                            setState();
                                            //replace orignal field
                                            UMLClassWithField.changeField(oldFieldName, changedField);
                                        }
                                    }
                                }
                                // change method
                                case "-m" -> {
                                    // Show user classes and attributes before asking for input
                                    listClasses();
                                    terminal.writer().print("Enter class that contains method: ");
                                    String classWithMethodName = reader.readLine().trim();
                                    // Ensure class exists
                                    UMLClass UMLClassWithMethod = model.findClass(classWithMethodName);
                                    if (UMLClassWithMethod != null) {
                                        // List attributes in class before asking for input
                                        listClass(classWithMethodName);
                                        terminal.writer().print("Enter method to be renamed: ");
                                        String oldMethodName = reader.readLine().trim();
                                        // Ensure attribute exists
                                        Attribute newField = UMLClassWithMethod.findMethod(oldMethodName);
                                        if (newField != null) {
                                            // Rename attribute with user's new name
                                            terminal.writer().print("Enter new name for " + oldMethodName + ": ");
                                            String newMethodName = reader.readLine().trim();
                                            if (model.isNotValidInput(newMethodName)) {
                                                terminal.writer().println("\"" + newMethodName +
                                                        "\" is not a valid identifier\n");
                                                break;
                                            }
                                            if (UMLClassWithMethod.findMethodNoPrint(newMethodName) != null) {
                                                terminal.writer().println("Method \"" + newMethodName +
                                                        "\" already exists in class \"" + classWithMethodName +
                                                        "\"\n");
                                                break;
                                            }
                                            //get methodtype
                                            String newMethodReturnType = getAttReturnType(newMethodName);
                                            if (newMethodReturnType == null) {
                                                break;
                                            }
                                            if (!newMethodName.endsWith("()")){
                                                newMethodName += "()";
                                            }
                                            //make a new method
                                            Method changedMethod = new Method(newMethodName, newMethodReturnType);
                                            setState();
                                            //replace orginal method
                                            UMLClassWithMethod.changeMethod(oldMethodName, changedMethod);
                                        }
                                    }
                                }
                                // change parameter
                                case "-p" -> {
                                    UMLClass classToChangeParam;
                                    Method methodToChangeParam;
                                    Parameter param;
                                    if((classToChangeParam = findClass("change", "parameter")) == null) {
                                        break;
                                    }
                                    if((methodToChangeParam = findMethod(classToChangeParam)) == null) {
                                        break;
                                    }
                                    String answer = "y";
                                    while (answer.equals("y")) {
                                        if ((param = findParam(methodToChangeParam)) == null) {
                                            break;
                                        }
                                        String oldParamName;
                                        if((oldParamName = param.getAttName()) == null) {
                                            break;
                                        }
                                        String newParamName;
                                        if((newParamName = getAttName("parameter")) == null) {
                                            break;
                                        }
                                        String newParamType;
                                        if ((newParamType = getAttType("parameter")) == null) {
                                            break;
                                        }
                                        param = new Parameter(newParamName, newParamType);
                                        setState();
                                        methodToChangeParam.changeParameter(oldParamName, param);

                                        terminal.writer().printf("Change another? [y/n]: ");
                                        answer = reader.readLine().toLowerCase().trim();
                                    }
                                }
                            }
                        }
                        // change relationship types
                        case "rel" -> {
                            // prompt user for source
                            terminal.writer().print("Enter source name: ");
                            String srcName = reader.readLine().trim();
                            // if source exists continue
                            if(model.findClass(srcName) != (null)){
                                // prompt user for destination
                                terminal.writer().print("Enter destination name: ");
                                String destName = reader.readLine().trim();
                                // if destination exists, continue
                                if(model.findClass(destName) != (null)){
                                    // if the relationship does not exist
                                    if(!model.isRelated(srcName, destName)){
                                        terminal.writer().println("Relationship does not exist");
                                        break;
                                    }
                                    // prompt user for old type
                                    terminal.writer().print("Enter old relationship type: ");
                                    String oldRelType = reader.readLine().trim();
                                    // check old type
                                    if(!Objects.equals(model.findRelType(srcName, destName), oldRelType)){
                                        terminal.writer().println("Relationship type does not exist for: "
                                                + srcName + " and " + destName);
                                    }
                                    // prompt for new type
                                    else {
                                        terminal.writer().print("Enter new relationship type: ");
                                        String newRelType = reader.readLine().trim();
                                        // check type validity
                                        if(!model.checkType(newRelType)){
                                            terminal.writer().println("Invalid relationship type");
                                        }
                                        // change the type
                                        else {
                                            setState();
                                            model.changeRelType(srcName, destName, newRelType);
                                            terminal.writer().print("Relationship type changed\n");
                                        }
                                    }
                                }
                            }
                        }
                        // If the user's command is not valid
                        default -> {
                            terminal.writer().println("Please enter a valid command");
                        }
                    }
                }

                //***************************************//
                //************* List cases **************//
                //***************************************//

                case "list", "l" -> {
                    switch (inputList[1]) {
                        // many classes
                        case "classes" -> {
                            listClasses();
                        }
                        // one class
                        case "class" -> {
                            // Get user to input desired class
                            terminal.writer().print("Enter class name: ");
                            String classToDisplayName = reader.readLine().trim();
                            // print class name
                            if (model.findClass(classToDisplayName) != null) {
                                terminal.writer().println("\n--------------------");
                                listClass(classToDisplayName);
                                terminal.writer().println("\n--------------------\n");
                            }
                            else{
                                terminal.writer().println("Class does not exist");
                            }
                        }
                        // list relationships
                        case "rel" -> {
                            // Ensure there are relationships to list. If not, break
                            if (model.getRelationshipList().size() == 0) {
                                terminal.writer().println("There are no relationships to list");
                                break;
                            }
                            // List relationships spaced out by new lines and arrows
                            // designating which class is the source and destination
                            terminal.writer().println("\n--------------------");
                            // if there is only one relationship, print it
                            if (model.getRelationshipList().size() >= 1) {
                                terminal.writer().print(model.getRelationshipList().get(0).getSource().getClassName());
                                terminal.writer().print(" [" + model.getRelationshipList().get(0).getRelType() +
                                        "] -> ");
                                terminal.writer().print(model.getRelationshipList().get(0).getDestination().
                                        getClassName());
                            }
                            // iterates through relationship list and prints the relationship
                            for (int i = 1; i < model.getRelationshipList().size(); ++i) {
                                terminal.writer().println();
                                terminal.writer().print("\n" + model.getRelationshipList().get(i).getSource()
                                        .getClassName());
                                terminal.writer().print(" [" + model.getRelationshipList().get(i).getRelType() +
                                        "] -> ");
                                terminal.writer().print(model.getRelationshipList().get(i).getDestination().
                                        getClassName());
                            }
                            terminal.writer().println("\n--------------------\n");
                        }
                        // If the user's command is not valid
                        default -> terminal.writer().println("Please enter a valid command");
                    }
                }
                // If the user's command is not valid
                default -> {
                    terminal.writer().println("Please enter a valid command");
                }
            }
            // catches invalid command
        } catch (Exception e) {
            terminal.writer().println("Please enter a valid command");
        }
    }


    /***************************
     *
     * Helper Methods
     *
     ***************************/

    //***************************************//
    //*************** Class *****************//
    //***************************************//

    /**
     * Prompts the user for the name of a class to perform an operation on
     *
     * @param operation the operation to perform on the class
     * @param attType the type of attribute to be defined
     * @return the user's given name for the class
     */
    public String getClassName(String operation, String attType) {
        // Get user input of Class name
        terminal.writer().print("Enter class name to " + operation + " " + attType + ": ");
        return reader.readLine().trim();
    }

    /**
     * Deletes the class with the matching name field if it exists, and returns the
     * classList
     *
     * @param classToDeleteName the name of the class to delete
     * @return the classList
     */
    public ArrayList<UMLClass> deleteClass(String classToDeleteName) {
        // Create new class object. If a class matches the user inputted name,
        // remove it from the ArrayList. Otherwise, inform user of failure.
        UMLClass UMLClassToDelete = null;
        // While loop to make sure the user can make a mistake when typing in
        // a class name to delete, and continue to delete a class afterwards
        while (UMLClassToDelete == null) {
            // Iterate through ArrayList of classes to see if class exists
            for (UMLClass UMLClassObj : this.model.getClassList()) {
                if (UMLClassObj.getClassName().equals(classToDeleteName)) {
                    UMLClassToDelete = UMLClassObj;
                }
            }
            // Remove whatever class classToDelete was assigned as from
            // the ArrayList
            if (UMLClassToDelete != null) {
                terminal.writer().print("Delete class \"" + classToDeleteName + "\"? (y/n): ");
                String theNextAnswer = reader.readLine().trim();
                // User confirms if they wish to delete. If no, break out of loop
                if (theNextAnswer.equalsIgnoreCase("y")) {
                    this.model.setRelationshipList(this.model.updateRelationshipList(classToDeleteName));
                    this.model.deleteClass(UMLClassToDelete);
                    terminal.writer().print("Class \"" + UMLClassToDelete.getClassName() + "\" has been deleted\n");
                    break;
                } else if (theNextAnswer.equalsIgnoreCase("n")) {
                    terminal.writer().print("Class \"" + UMLClassToDelete.getClassName() + "\" has NOT been deleted\n");
                    break;
                }
            }
            // If no class was found in the ArrayList matching the name of the
            // users input, classToDelete will still be null
            else {
                terminal.writer().println("Class \"" + classToDeleteName + "\" was not found");
            }
        }
        return this.model.getClassList();
    }

    /**
     * List all the contents of a specific class in a nice way
     *
     * @param className the name of the class to list
     */
    public void listClass(String className) {
        // copy class and attribute lists for more readable
        UMLClass copyClass = this.model.findClass(className);
        ArrayList<Field> copyFieldList = Objects.requireNonNull(copyClass).getFieldList();
        ArrayList<Method> copyMethList = Objects.requireNonNull(copyClass).getMethodList();
        // prints the name of a class
        terminal.writer().println("Class name: " + copyClass.getClassName());
        // prints all the fields in a class
        if (copyFieldList.size() == 0) {
            terminal.writer().print("This class has no fields\n");
        } else { terminal.writer().print("-----------------\n");
            for (int i = 0; i < copyFieldList.size(); ++i) {
                Field field = copyFieldList.get(i);
                if (i == 0) {
                    terminal.writer().printf("  -%s %s", field.getFieldType(), field.getAttName());
                } else {
                    terminal.writer().printf("\n  -%s %s", field.getFieldType(), field.getAttName());
                }
            }
        }
        // prints all the methods in class
        if (copyFieldList.size() == 0) {
            terminal.writer().print("This class has no methods\n");
        } else {
            terminal.writer().print("\n-----------------\n");
            for (int i = 0; i < copyMethList.size(); ++i) {
                Method method = copyMethList.get(i);
                if (i == 0) {
                    terminal.writer().printf("  +%s %s (", method.getReturnType(), method.getAttName());
                } else {
                    terminal.writer().print("\n  +" + method.getReturnType() + " " + copyMethList.get(i).getAttName() + " (");
                }
                // prints all the parameters of a method
                for (int j = 0; j < copyMethList.get(i).getParamList().size(); ++j) {
                    Parameter param = copyMethList.get(i).getParamList().get(j);
                    if (j == 0) {
                        terminal.writer().printf("%s %s", param.getFieldType(), param.getAttName());
                    } else {
                        terminal.writer().printf(", %s %s", param.getFieldType(), param.getAttName());
                    }
                }
                terminal.writer().print(")");
            }
            terminal.writer().print("\n-----------------\n");
        }

    }

    /**
     * List all the contents of all classes in the diagram in a nice way
     */
    public void listClasses() {
        // if there are classes to list, list them
        if (this.model.getClassList().size() != 0) {
            // Loops through classList and calls listClass on all elements
            terminal.writer().print("\n********************\n");
            if (this.model.getClassList().size() == 1) {
                listClass(this.model.getClassList().get(0).getClassName());
            } else {
                // Loops through classList and calls listClass on all elements
                for (UMLClass aUMLClass : this.model.getClassList()) {
                    terminal.writer().println();
                    listClass(aUMLClass.getClassName());
                    terminal.writer().print("\n");
                }
            }
            terminal.writer().println("\n********************\n");
        }
        // if there are no classes to list, prompt user that there are no classes
        else {
            terminal.writer().println("There are no classes to list");
        }
    }

    /**
     * Creates a memento object using the current state and puts it onto the state stack
     */
    private void setState() {
        Memento currState = new Memento(new UMLModel(this.model.getClassList(), this.model.getRelationshipList(), this.model.getCoordinateMap()));
        caretaker.pushToUndoStack(currState);
    }

    /**
     * Undoes the most recent action
     *
     * @return true if the undo succeeded, otherwise false
     */
    private boolean undo() {
        // if the stack is empty, return false, otherwise perform the undo and return
            if (this.caretaker.undoStackIsEmpty()) {
                return false;
            } else {
                // get the current state the model is in
                Memento currState = new Memento(new UMLModel(this.model.getClassList(), this.model.getRelationshipList(), this.model.getCoordinateMap()));
                // get the previous state on the state stack, and pass the helper the current state for the redo stack
                Memento prevState = caretaker.undoHelper(currState);
                // make this current model the previous model
                this.model = prevState.getState();
                return true;
        }
    }

    /**
     * Redoes the most recent action
     *
     * @return true if the redo succeeded, otherwise false
     */
    private boolean redo() {
        // if the stack is empty, return false, otherwise perform the redo and return
        if (this.caretaker.redoStackIsEmpty()) {
            return false;
        } else {
            // get the current state the model is in
            Memento currState = new Memento(new UMLModel(this.model.getClassList(), this.model.getRelationshipList(), this.model.getCoordinateMap()));
            // get the previous state on the state stack, and pass the helper the current state for the undo stack
            Memento prevState = caretaker.redoHelper(currState);
            // make this current model the previous model
            this.model = prevState.getState();
            return true;
        }
    }

    //***************************************//
    //****** Fields/Methods/Parameters ******//
    //***************************************//

    /**
     * Prompts the user for the name of an attribute with the given type
     *
     * @param attType the type of the attribute to be named
     * @return the user's given name for the attribute
     */
    public String getAttName(String attType) {
        terminal.writer().print("Enter " + attType + " name: ");
        return reader.readLine().trim();
    }

    /**
     * Prompts the user for the type of an attribute
     *
     * @param attType the type of attribute to be defined
     * @return the user's given type for the attribute
     */
    public String getAttType(String attType) {
        if (attType.equals("method")) {
            terminal.writer().print("Enter " + attType + " return type: ");
            String attToGet = reader.readLine().trim();
            if (this.model.isNotValidInput(attToGet)) {
                return null;
            }
            return attToGet;
        }
        terminal.writer().print("Enter " + attType + " type: ");
        String attToGet = reader.readLine().trim();
        if (this.model.isNotValidInput(attToGet)) {
            return null;
        }
        return attToGet;
    }

    /**
     * Prompts the user for the type of an attribute
     *
     * @param attType the type of attribute to be defined
     * @return the user's given type for the attribute
     */
    public String getAttReturnType(String attType) {
        if (attType.equals("method")) {
            attType += " return";
        }
        terminal.writer().print("Enter " + attType + " type: ");
        String attToGet = reader.readLine().trim();
        if (this.model.isNotValidInput(attToGet)) {
            return null;
        }
        return attToGet;
    }

    /**
     * Takes in an attribute object, its type and name
     * Checks if an attribute object exists
     *
     * @param obj the attribute object
     * @param type the type of the attribute
     * @param name the name of the attribute
     * @param <E> the class type(generic)
     * @return true if the object does not exist, false otherwise
     */
    public <E> boolean ifDoesntExist(E obj, String type, String name) {
        if (obj == null) {
            terminal.writer().printf("%s %s does not exist\n", type, name);
            return true;
        }
        return false;
    }

    /**
     * Takes in an attribute object, its type and name
     * Checks for duplicates
     *
     * @param obj the attribute object
     * @param type the attribute type
     * @param name the name
     * @param <E> the class type
     * @return true if dups exist, false otherwise
     */
    public <E> boolean ifExists(E obj, String type, String name) {
        if (obj != null) {
            terminal.writer().printf("%s %s already exists\n", type, name);
            return true;
        }
        return false;
    }

    /**
     * Takes in a class object, the attribute type and name
     * Adds a new attribute, either field, method or parameter, to the class given
     *
     * @param classToAddAtt the class object
     * @param attType the type of attribute(field, method, parameter)
     * @param attName the name of the attribute
     */
    public void addAttribute(UMLClass classToAddAtt, String attType, String attName) {
        // If attribute is a method, get its return type.
        // If attribute is a field, get its type.
        // Ensure type is valid
        String type = getAttType(attType);
        if (type == null) {
            return;
        }
        // If user wants to add a method, get its return type and
        // ensure it is valid. Then return updated class with added method.
        if (attType.equalsIgnoreCase("method")) {
            Method method = new Method(attName, type);
            classToAddAtt.addMethod(method);
        }
        // If user wants to add a field, get its type and
        // ensure it is valid. Then return updated class with added field.
        else if (attType.equalsIgnoreCase("field")) {
            Field field = new Field(attName, type);
            classToAddAtt.addField(field);
        }
    }

    /**
     * Takes in a class object and the attribute type
     * Removes an attribute and its parts from the class
     *
     * @param classWithAttToDel the class object
     * @param attType the attribute type
     */
    public void delAttribute(UMLClass classWithAttToDel, String attType) {
        // Get name of field or method user wants to delete
        terminal.writer().print("Enter " + attType + " to delete: ");
        String attToDel = reader.readLine().trim();
        // Ensure attribute exists
        Attribute deletedAtt = classWithAttToDel.findAtt(attToDel, attType);
        if (deletedAtt != null) {
            // Confirm  user wants to delete attribute
            terminal.writer().print("Delete " + attType + " \"" + attToDel + "\"? (y/n): ");
            String answer = reader.readLine().trim();
            // If the user wants to delete an attribute, proceed to do so
            if (answer.equalsIgnoreCase("y")) {
                classWithAttToDel.deleteAttribute(deletedAtt);
                terminal.writer().print(attType + " \"" + attToDel + "\" has been deleted \n");
            }
            // If user types n, confirm and return
            else {
                terminal.writer().println(attType + " \"" + attToDel + "\" has NOT been deleted");
            }
            // If attribute doesn't exist in class, inform user and return
        } else {
            terminal.writer().println("Attribute does not exist");
        }
    }

    /**
     * Takes in a class object and the attribute type
     * Finds the class associated with a certain attribute
     *
     * @param op the class object
     * @param type the type of attribute
     * @return the class found
     */
    public UMLClass findClass(String op, String type) {
        String className = getClassName(op, type);
        UMLClass umlClass = this.model.findClass(className);
        if (ifDoesntExist(umlClass, "Class", className)) { return null; }
        return umlClass;
    }

    /**
     * Takes in a class
     * Finds the method associated with the class given
     *
     * @param umlClass the class objcet
     * @return the method object
     */
    public Method findMethod(UMLClass umlClass) {
        String methodName = getAttName("method");
        Method method = umlClass.findMethod(methodName);
        if (ifDoesntExist(method, "Method", methodName)) { return null; }
        return method;
    }

    /**
     * Takes in a method object
     * Finds the parameter object associated with the method given
     *
     * @param method the method object
     * @return the parameter object
     */
    public Parameter findParam(Method method) {
        String paramName = getAttName("parameter");
        Parameter param = method.findParameter(paramName);
        if (ifDoesntExist(param, "Parameter", paramName)) { return null; }
        return param;
    }

    /**
     * Takes in a method object
     * Finds the parameter name of a parameter associated with the method object given
     *
     * @param method the method object
     * @return the parameter name
     */
    public String findParamName(Method method) {
        String paramName = getAttName("parameter");
        Parameter param = method.findParameter(paramName);
        if (ifExists(param, "Parameter", paramName)) { return null; }
        return paramName;
    }

    //***************************************//
    //*************** Flags *****************//
    //***************************************//

    /**
     * Takes in user input and operation
     * gives options to the user for a type of attribute to add
     *
     * @param userInputList user input
     * @param operation user command
     * @return true if flag is invalid, false otherwise
     */
    public boolean addAttFlag(ArrayList<String> userInputList, String operation) {
        terminal.writer().print("type \"-f\" to " + operation + " a field, or \"-m\" to " +
                operation + " a method: ");
        String attFlag = reader.readLine().trim();
        switch (attFlag) {
            case "-f", "-m" -> {
                userInputList.add(attFlag);
                return false;
            }
            default -> {
                terminal.writer().println("Invalid flag, no attribute added");
                return true;
            }
        }
    }

    /**
     * Takes in the user input and operation to give the user an option for a
     * relationship type flag to choose a type
     *
     * @param userInputList user input
     * @param operation the command
     * @return returns true if the correct flag is returned, false otherwise
     */
    public boolean addRelFlag(ArrayList<String> userInputList, String operation) {
        terminal.writer().print("type \"-a\" for aggregation, \n\"-c\" for composition, \n" +
                "\"-i\" for inheritance, \nor \"-r\" for realization to " + operation + " that relationship type: ");
        String relFlag = reader.readLine().trim();
        switch (relFlag) {
            case "-a", "-c", "-i", "-r" -> {
                userInputList.add(relFlag);
                return true;
            }
            default -> {
                terminal.writer().println("Invalid flag, no relationship added");
                return false;
            }
        }
    }

    /**
     * Takes a flag string from a command and returns the string
     * that the flag represents
     *
     * @param flag the flag of the command
     */
    public String flagToString(String flag) {
        return switch (flag) {
            case "-f" -> "field";
            case "-m" -> "method";
            case "-p" -> "parameter";
            case "-a" -> "aggregation";
            case "-c" -> "composition";
            case "-i" -> "inheritance";
            case "-r" -> "realization";
            default -> "";
        };
    }

    //***************************************//
    //**************** MSC. *****************//
    //***************************************//

    // clears the screen
    private void clearScreen() {
        // Clears Screen
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            terminal.writer().println("couldn't clear screen");
        }
    }

    private ArrayList<String> convertStringList2ArrayList(String[] list) {
        ArrayList<String> listToReturn = new ArrayList<>();
        for (int i = 0; i >= list.length; i++) {
            listToReturn.add(list[i]);
        }
        return listToReturn;
    }
}
