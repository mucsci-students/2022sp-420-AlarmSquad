package uml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * The controller for the CLI version of the UML Diagram
 *
 * @authors AlarmSquad
 */
@SuppressWarnings("DanglingJavadoc")
public class CLIController {

    // Creates a new scanner
    private static final Scanner scan = new Scanner(System.in);

    // Interprets user input
    public static void main(String[] args) {

        clearScreen();

        // intro to CLI for user
        String intro = "Welcome to ALARM Squad's UML editor!";
        intro += "\n\nType 'help' to list all commands and their accompanying descriptions.\n";
        System.out.println(intro);

        // Default prompt for user
        String prompt = "> ";
        System.out.print(prompt);

        // Enters the command loop
        while (true) {

            // Takes in the next line of user input
            String input = scan.nextLine().trim();

            /*
            input should be in form [command] <type> <flag>
             */

            //TODO
            // SEE IF TABS/NEWLINES MESS THINGS UP INSIDE
            ArrayList<String> inputList = new ArrayList<>(Arrays.asList(input.split(" ")));

            try {
                // Check first word user inputted and act accordingly
                switch (inputList.get(0)) {

                    // If the user enters a blank line, prompt again
                    case "" -> System.out.print(prompt);

                    // Quit program
                    case "exit" -> {
                        return;
                    }

                    // Clear screen
                    case "clear" -> {
                        clearScreen();
                        System.out.print(prompt);
                    }

                    case "save" -> {
                        System.out.print("Save file name: ");
                        String saveFileName = scan.next();
                        JSON.save(saveFileName);
                        System.out.println("Diagram has been saved to \"" + saveFileName + ".json\"");
                    }

                    case "load" -> {
                        if (JSON.ifDirIsEmpty()) {
                            System.out.println("No save files found");
                            System.out.print(prompt);
                        } else {
                            System.out.print("Load file name: ");
                            String loadFileName = scan.next();
                            JSON.load(loadFileName);
                        }
                    }

                    case "help" -> {
                        displayHelp();
                        System.out.print(prompt);
                    }

                    case "add", "a" -> {
                        switch (inputList.get(1)) {
                            case "class" -> {
                                // Get user defined name for Class, then adds new Class to the classList
                                String className = getClassName("");

                                // Edge cases for class name, checks validity
                                if (isNotValidInput(className)) {
                                    break;
                                }

                                // Checks for duplicates
                                if (UMLModel.getClassList().stream().anyMatch(o -> o.getClassName().equals(className))) {
                                    System.out.printf("Class %s already exists\n", className);
                                } else {
                                    // Adds class to class list then prompts confirmation to use
                                    UMLClass newUMLClass = new UMLClass(className);
                                    UMLModel.getClassList().add(newUMLClass);
                                    System.out.println("Added class \"" + className + "\"");
                                }
                            }
                            case "rel" -> {
                                System.out.print("Enter source class name: ");
                                String sourceName = scan.next().trim();
                                // If source class is valid and exists get destination class
                                if (UMLModel.findClass(sourceName) != (null)) {
                                    System.out.print("Enter destination: ");
                                    String destinationName = scan.next().trim();
                                    // If destination class is valid and exists add
                                    // relationship to relationship array list
                                    if (UMLModel.findClass(destinationName) != (null)) {
                                        if (UMLModel.findRelationship(sourceName, destinationName) != null) {
                                            System.out.println("Relationship already exists between " +
                                                    sourceName + " and " + destinationName);
                                            break;
                                        }
                                        Relationship newRelationship = new Relationship(UMLModel.findClass(sourceName),
                                                UMLModel.findClass(destinationName));
                                        UMLModel.addRel(newRelationship);
                                        System.out.println("Relationship added between " + sourceName
                                                + " and " + destinationName);
                                    }
                                }
                            }
                            case "att" -> {
                                // If user inputted "a att" with no flag, ask for one
                                if (inputList.size() == 2) {
                                    System.out.print("type \"-f\" to add a field, or \"-m\" to input a method: ");
                                    String attAddFlag = scan.next().trim();

                                    if (attAddFlag.equals("-f") || attAddFlag.equals("-m"))
                                        inputList.add(attAddFlag);
                                    else {
                                        System.out.println("Invalid flag, no attribute added");
                                    }
                                }
                                switch (inputList.get(2)) {

                                    case "-f", "-m" -> {
                                        String flagName = flagToString(inputList.get(2));
                                        if (!flagName.equals("")) {
                                            // Get name of class user wants to add attribute to and
                                            // ensure the class exists
                                            String classToAddAttName = getClassName(flagName);
                                            UMLClass classToAddAtt = UMLModel.findClass(classToAddAttName);
                                            if (classToAddAtt != null) {
                                                addAttribute(classToAddAtt, flagName);
                                            } else {
                                                System.out.println("Class " + classToAddAttName + " does not exist");
                                            }
                                        }
                                    }

                                    default -> {
                                        System.out.println("Invalid flag");
                                        System.out.print(prompt);
                                    }
                                }
                            }
                        }
                    }

                    // delete class of the user's choice from the class list
                    case "delete", "d" -> {
                        switch (inputList.get(1)) {
                            case "class" -> {
                                // Get user input of Class name to be deleted
                                System.out.print("Enter class name to delete: ");
                                String classDeleteInput = scan.next().trim();
                                UMLClass UMLClassToDel = UMLModel.findClass(classDeleteInput);
                                if (UMLClassToDel != null) {
                                    // Copy classList into new ArrayList with deleted Class
                                    UMLModel.setClassList(deleteClass(classDeleteInput));
                                }
                            }

                            case "rel" -> {
                                System.out.print("Enter relationship source name: ");
                                String sourceToFind = scan.next().trim();
                                UMLClass src = UMLModel.findClass(sourceToFind);

                                if (src != null) {
                                    // If source name was found, proceed to find dest name
                                    System.out.print("Enter relationship destination name: ");
                                    String destToFind = scan.next().trim();
                                    UMLClass dest = UMLModel.findClass(destToFind);
                                    if (dest != null) {

                                        // Find the relationship
                                        Relationship r = UMLModel.findRelationship(sourceToFind, destToFind);
                                        if (r != null) {
                                            System.out.print("Delete relationship between \"" +
                                                    r.getSource().getClassName() + "\" and \"" + r.getDestination().getClassName()
                                                    + "\"? (y/n): ");
                                            String rAnswer = scan.next().trim();
                                            // If the user wants to delete the relationship, proceed to do so
                                            if (rAnswer.equalsIgnoreCase("y")) {
                                                UMLModel.deleteRel(r);
                                                System.out.println("Relationship has been deleted");
                                            }
                                            // If not, prompt user and move on
                                            else if (rAnswer.equalsIgnoreCase("n")) {
                                                System.out.println("Relationship has NOT been deleted");
                                            }
                                        }
                                    }
                                }
                            }

                            // delete field
                            case "att" -> {
                                switch (inputList.get(2)) {
                                    case "-f" -> {
                                        // Get user input of Class name to delete field from
                                        System.out.print("Enter class name to delete field: ");
                                        String classToDelFieldName = scan.next().trim();
                                        // Call find class method to find the class the field is in
                                        UMLClass UMLClassToDelField = UMLModel.findClass(classToDelFieldName);
                                        boolean fieldBool = false;
                                        // Prompt user to delete an attribute
                                        if (UMLClassToDelField != null) {
                                            // while bool is false continue loop until the user does not want to delete an
                                            // attribute
                                            // or until an attribute is deleted
                                            // (this may be changed so the user can type exit to exit the program as well)
                                            while (!fieldBool) {
                                                System.out.print("Enter field name to delete: ");
                                                String fieldToDel = scan.next().trim();
                                                Field deletedField = UMLClassToDelField.findField(fieldToDel);
                                                if (deletedField != null) {
                                                    System.out.print("Delete field \"" + fieldToDel + "\"? (y/n): ");
                                                    String answer = scan.next().trim();
                                                    // If the user wants to delete an attribute, proceed to do so
                                                    if (answer.equalsIgnoreCase("y")) {
                                                        UMLClassToDelField.deleteField(deletedField);
                                                        System.out.print("Field \"" + fieldToDel + "\" has been deleted \n");
                                                        fieldBool = true;
                                                    }
                                                    // if user types n, break out of loop and case
                                                    // and bring back prompt for new command
                                                    else {
                                                        System.out.println("Field " + fieldToDel + " has NOT been deleted");
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    case "-m" -> {
                                        // Get user input of Class name to delete method from
                                        System.out.print("Enter class name to delete method: ");
                                        String classToDelMethName = scan.next().trim();
                                        // Call find class method to find the class the method is in
                                        UMLClass UMLClassToDelMethod = UMLModel.findClass(classToDelMethName);
                                        boolean methodBool = false;
                                        // Prompt user to delete a method
                                        if (UMLClassToDelMethod != null) {
                                            // while bool is false continue loop until the user does not want to delete a
                                            // method
                                            // or until a method is deleted
                                            // (this may be changed so the user can type exit to exit the program as well)
                                            while (!methodBool) {
                                                System.out.print("Enter method name to delete: ");
                                                String methodToDel = scan.next().trim();
                                                Method deletedMethod = UMLClassToDelMethod.findMethod(methodToDel);
                                                if (deletedMethod != null) {
                                                    System.out.print("Delete method \"" + methodToDel + "\"? (y/n): ");
                                                    String answer = scan.next().trim();
                                                    // If the user wants to delete a method, proceed to do so
                                                    if (answer.equalsIgnoreCase("y")) {
                                                        UMLClassToDelMethod.deleteMethod(deletedMethod);
                                                        System.out.print("Method \"" + methodToDel + "\" has been deleted \n");
                                                        methodBool = true;
                                                    }
                                                    // if user types n, break out of loop, then break out of case
                                                    // and bring back prompt for new command
                                                    else {
                                                        System.out.println("Method " + methodToDel + " has NOT been deleted");
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // rename class will rename a class of the user's choice
                    case "rename", "r" -> {
                        switch (inputList.get(1)) {
                            case "class" -> {
                                // Get user input of Class name to be renamed
                                System.out.print("Enter class to rename: ");
                                String oldClassName = scan.next().trim();

                                // If Class exists, set Class name to user inputted name
                                UMLClass oldUMLClass = UMLModel.findClass(oldClassName);

                                // Prompt user to rename a class name
                                if (oldUMLClass != null) {
                                    System.out.print("Enter new name for class " + oldUMLClass.getClassName() + ": ");
                                    String newName = scan.next().trim();
                                    if (isNotValidInput(newName)) {
                                        System.out.println("\"" + newName + "\" is not a valid identifier\n");
                                        break;
                                    }
                                    if (UMLModel.findClass(newName) != null) {
                                        System.out.println("Class \"" + newName + "\" already exists\n");
                                        break;
                                    }
                                    oldUMLClass.setClassName(newName);
                                    // Inform user of renamed Class
                                    System.out.println("The class \"" + oldClassName +
                                            "\" has been renamed to \"" + oldUMLClass.getClassName() + "\"");
                                }
                            }

                            case "att" -> {
                                switch (inputList.get(2)) {
                                    case "-f" -> {
                                        // Show user classes and attributes before asking for input
                                        listClasses();
                                        System.out.print("Enter class that contains field: ");
                                        String classWithFieldName = scan.next().trim();

                                        // Ensure class exists
                                        UMLClass UMLClassWithField = UMLModel.findClass(classWithFieldName);
                                        if (UMLClassWithField != null) {
                                            // List attributes in class before asking for input
                                            listClass(classWithFieldName);
                                            System.out.print("Enter field to be renamed: ");
                                            String oldFieldName = scan.next().trim();

                                            // Ensure attribute exists
                                            Attribute newField = UMLClassWithField.findField(oldFieldName);
                                            if (newField != null) {
                                                // Rename attribute with user's new name
                                                System.out.print("Enter new name for " + oldFieldName + ": ");
                                                String newFieldName = scan.next().trim();
                                                if (isNotValidInput(newFieldName)) {
                                                    System.out.println("\"" + newFieldName + "\" is not a valid identifier\n");
                                                    break;
                                                }
                                                if (UMLClassWithField.findField(newFieldName) != null) {
                                                    System.out.println("Field \"" + newFieldName +
                                                            "\" already exists in class \"" + classWithFieldName + "\"\n");
                                                    break;
                                                }
                                                newField.setAttName(newFieldName);
                                                System.out.println(oldFieldName + " in class " + classWithFieldName +
                                                        " renamed to " + newField.getAttName());
                                            }
                                        }
                                    }
                                    case "-m" -> {
                                        // Show user classes and attributes before asking for input
                                        listClasses();
                                        System.out.print("Enter class that contains method: ");
                                        String classWithMethodName = scan.next().trim();

                                        // Ensure class exists
                                        UMLClass UMLClassWithMethod = UMLModel.findClass(classWithMethodName);
                                        if (UMLClassWithMethod != null) {
                                            // List attributes in class before asking for input
                                            listClass(classWithMethodName);
                                            System.out.print("Enter method to be renamed: ");
                                            String oldMethodName = scan.next().trim();

                                            // Ensure attribute exists
                                            Attribute newField = UMLClassWithMethod.findField(oldMethodName);
                                            if (newField != null) {
                                                // Rename attribute with user's new name
                                                System.out.print("Enter new name for " + oldMethodName + ": ");
                                                String newMethodName = scan.next().trim();
                                                if (isNotValidInput(newMethodName)) {
                                                    System.out.println("\"" + newMethodName + "\" is not a valid identifier\n");
                                                    break;
                                                }
                                                if (UMLClassWithMethod.findField(newMethodName) != null) {
                                                    System.out.println("Method \"" + newMethodName +
                                                            "\" already exists in class \"" + classWithMethodName + "\"\n");
                                                    break;
                                                }
                                                newField.setAttName(newMethodName);
                                                System.out.println(oldMethodName + " in class " + classWithMethodName +
                                                        " renamed to " + newField.getAttName());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                    case "list", "l" -> {
                        switch (inputList.get(1)) {
                            case "classes" -> {
                                listClasses();
                                System.out.print(prompt);
                            }
                            case "class" -> {
                                // Get user to input desired class
                                System.out.print("Enter class name: ");
                                String classToDisplayName = scan.next().trim();

                                // print class name
                                if (UMLModel.findClass(classToDisplayName) != null) {
                                    System.out.println("\n--------------------");
                                    listClass(classToDisplayName);
                                    System.out.println("--------------------\n");
                                }
                            }
                            case "rel" -> {
                                // Ensure there are relationships to list. If not, break
                                if (UMLModel.getRelationshipList().size() == 0) {
                                    System.out.println("There are no relationships to list");
                                    System.out.print(prompt);
                                    break;
                                }

                                // List relationships spaced out by new lines and arrows
                                // designating which class is the source and destination
                                System.out.println("\n--------------------");
                                if (UMLModel.getRelationshipList().size() >= 1) {
                                    System.out.print(UMLModel.getRelationshipList().get(0).getSource().getClassName());
                                    System.out.print(" -> ");
                                    System.out.print(UMLModel.getRelationshipList().get(0).getDestination().getClassName());
                                }
                                for (int i = 1; i < UMLModel.getRelationshipList().size(); ++i) {
                                    System.out.println();
                                    System.out.print("\n" + UMLModel.getRelationshipList().get(i).getSource().getClassName());
                                    System.out.print(" -> ");
                                    System.out.print(UMLModel.getRelationshipList().get(i).getDestination().getClassName());
                                }
                                System.out.println("\n--------------------\n");
                                System.out.print(prompt);
                            }
                        }
                    }

                    // If the user's command is not valid
                    default -> {
                        System.out.println("Please enter a valid command");
                        System.out.print(prompt);
                    }
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid command");
            }
        }
    }


/******************************************************************
 *
 * Programmer defined Helper Methods
 *
 ******************************************************************/

    /**
     * Execute OS clear function to clear terminal
     *
     */
    private static void clearScreen() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            System.out.println("couldn't clear screen");
        }

    }

    /**
     * Prompts the user for the name of a class to be added
     *
     * @param classNameToAdd the name of the class to add
     * @return the user's given name for the class
     */
    public static String getClassName(String classNameToAdd) {
        // Get user input of Class name
        System.out.print("Enter class name to add " + classNameToAdd + ": ");
        return scan.next().trim();
    }

    /**
     * Prompts the user for the type of an attribute
     *
     * @param attType the type of attribute to be defined
     * @return the user's given type for the attribute
     */
    public static String getAttType(String attType) {
        if (attType.equals("method"))
            attType += " return";
        System.out.print("Enter " + attType + " type: ");
        return scan.next().trim();
    }

    /**
     * Prompts the user for the name of an attribute with the given type
     *
     * @param attType the type of the attribute to be named
     * @return the user's given name for the attribute
     */
    public static String getAttName(String attType) {
        System.out.print("Enter " + attType + " name: ");
        return scan.next().trim();
    }

    public static void addAttribute(UMLClass classToAddAtt, String attType) {
        // Get name of attribute user wants to add and
        // ensure it is valid and not a duplicate
        String attName = getAttName(attType);
        if (isNotValidInput(attName)) {
            System.out.println("\"" + attName + "\" is not a valid identifier\n");
        }
        if (classToAddAtt.getAttList(attType).stream()
                .anyMatch(attObj -> attObj.getAttName().equals(attName))) {
            System.out.println(attType + " " + attName +
                    " already exists in class " + classToAddAtt.getClassName());
        }
        // If attribute is a method, get its return type.
        // If attribute is a field, get its type.
        // Ensure type is valid
        String type = getAttType(attType);
        if (isNotValidType(type)) {
            System.out.println("\"" + type + "\" is not a valid return type\n");
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

    public static void addAtt(String flag) {
        String attr = switch (flag) {
            case "-f" -> "field";
            case "-m" -> "method";
            default -> "";
        };
        if (!attr.equals("")) {
            // Get name of class user wants to add attribute to and
            // ensure the class exists
            String classToAddAttName = getClassName(attr);
            UMLClass classToAddAtt = UMLModel.findClass(classToAddAttName);
            if (classToAddAtt != null) {
                addAttribute(classToAddAtt, attr);
            } else {
                System.out.println("Class " + classToAddAttName + " does not exist");
            }
        }
    }

    /**
     * List all the contents of a specific class in a nice way
     *
     * @param className the name of the class to list
     */
    public static void listClass(String className) {
        // copy class and attribute lists for more readable
        // code and operations later
        UMLClass copyClass = UMLModel.findClass(className);
        ArrayList<Field> copyFieldList = Objects.requireNonNull(copyClass).getFieldList();
        ArrayList<Method> copyMethList = Objects.requireNonNull(copyClass).getMethodList();
        // prints the name of the class
        System.out.println("Class name: " + copyClass.getClassName());
        // prints all the field in a set
        System.out.print("Fields");
        System.out.print("[ ");
        if (copyClass.getFieldList().size() >= 1) {
            System.out.print(copyClass.getFieldList().get(0).getAttName());
        }
        for (int i = 1; i < copyFieldList.size(); ++i) {
            System.out.print(", " + copyFieldList.get(i).getAttName());
        }
        System.out.println(" ]");
        // prints all the methods in a set
        System.out.print("Methods");
        System.out.print("[ ");
        if (copyMethList.size() >= 1) {
            System.out.print(copyMethList.get(0).getAttName());
        }
        for (int i = 1; i < copyMethList.size(); ++i) {
            System.out.print(", " + copyMethList.get(i).getAttName());
        }
        System.out.println(" ]");
    }

    /**
     * List all the contents of all classes in the diagram in a nice way
     *
     */
    public static void listClasses() {
        // if there are classes to list, list them
        if (UMLModel.getClassList().size() != 0) {
            // Loops through classList and calls listClass on all elements
            System.out.println("\n--------------------");
            if (UMLModel.getClassList().size() == 1) {
                listClass(UMLModel.getClassList().get(0).getClassName());
            } else {
                // Loops through classList and calls listClass on all elements
                for (UMLClass aUMLClass : UMLModel.getClassList()) {
                    System.out.println();
                    listClass(aUMLClass.getClassName());
                }
            }
            System.out.println("--------------------\n");
        }
        // if there are no classes to list, prompt user that there are no classes
        else {
            System.out.println("There are no classes to list");
        }
    }

    /**
     * Deletes the class with the matching name field if it exists, and returns the
     * classList
     *
     * @param classToDeleteName the name of the class to delete
     * @return the classList
     */
    public static ArrayList<UMLClass> deleteClass(String classToDeleteName) {
        // Create new class object. If a class matches the user inputted name,
        // remove it from the ArrayList. Otherwise, inform user of failure.
        UMLClass UMLClassToDelete = null;
        // While loop to make sure the user can make a mistake when typing in
        // a class name to delete, and continue to delete a class afterwards
        while (UMLClassToDelete == null) {
            // Iterate through ArrayList of classes to see if class exists
            for (UMLClass UMLClassObj : UMLModel.getClassList()) {
                if (UMLClassObj.getClassName().equals(classToDeleteName)) {
                    UMLClassToDelete = UMLClassObj;
                }
            }
            // Remove whatever class classToDelete was assigned as from
            // the ArrayList
            if (UMLClassToDelete != null) {
                System.out.print("Delete class \"" + classToDeleteName + "\"? (y/n): ");
                String theNextAnswer = scan.next().trim();
                // User confirms if they wish to delete. If no, break out of loop
                if (theNextAnswer.equalsIgnoreCase("y")) {
                    UMLModel.setRelationshipList(UMLModel.updateRelationshipList(classToDeleteName));
                    UMLModel.deleteClass(UMLClassToDelete);
                    System.out.print("Class \"" + UMLClassToDelete.getClassName() + "\" has been deleted\n");
                    break;
                } else if (theNextAnswer.equalsIgnoreCase("n")) {
                    System.out.print("Class \"" + UMLClassToDelete.getClassName() + "\" has NOT been deleted\n");
                    break;
                }
            }
            // If no class was found in the ArrayList matching the name of the
            // users input, classToDelete will still be null
            else {
                System.out.println("Class \"" + classToDeleteName + "\" was not found");
            }
        }
        return UMLModel.getClassList();
    }

    /**
     * Takes a string and checks if it is a valid identifier
     * (helper class of isNotValidInput)
     *
     * @param input the string to check
     * @return true if valid, otherwise false
     */
    public static boolean isValidIdentifier(String input) {
        if (input == null) {
            return false;
        }
        char[] c = input.toCharArray();
        if (Character.isJavaIdentifierStart(c[0])) {
            for (int i = 1; i < input.length(); i++) {
                if (!Character.isJavaIdentifierPart(c[i])) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Takes a string and checks if it is a valid name
     *
     * @param input the string to check
     * @return false if valid, otherwise true
     */
    public static boolean isNotValidInput(String input) {
        if (!isValidIdentifier(input)) {
            System.out.printf("Input %s is not a valid identifier\n", input);
            return true;
        }
        return false;
    }

    /**
     * Takes a string and checks if it is a valid field type
     *
     * @param input the string to check
     * @return false if valid, otherwise true
     */
    public static boolean isNotValidType(String input) {
        return switch (input) {
            case "string", "int", "double", "float", "char", "boolean", "short", "long", "void" -> false;
            default -> true;
        };
    }

    /**
     * Display list of commands and their accompanying descriptions
     *
     */
    public static void displayHelp() {
        String helpMenu = """
                   Commands		   Description
                --------------	-----------------
                a class			Add a new class
                d class			Delete an existing class
                r class			Rename an existing class
                a att -f	    Add a new field to an existing class
                a att -m	    Add a new method to an existing class
                d att -f	    Delete a field from an existing class
                d att -m	    Delete a method from an existing class
                r att -f	    Rename a field from an existing class
                r att -m	    Rename a method from an existing class
                a rel			Add a new relationship
                d rel			Delete an existing relationship
                save			Save the current UML diagram
                load			Load a previously saved UML diagram
                clear			Clear the command history
                help			Display list of commands
                exit			Exit the application""";
        System.out.println(helpMenu);
    }
}