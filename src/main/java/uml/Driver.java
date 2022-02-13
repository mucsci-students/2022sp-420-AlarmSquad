package uml;

import java.io.*;
import java.util.*;

/**
 * Gets user input, then uses switch statements to process commands
 * 
 * @authors
 */
public class Driver {

    // Default prompt for user
    private static String prompt = "> ";
    // Creates a new scanner
    private static Scanner scan = new Scanner(System.in);
    // The arraylist of Classes in the diagram
    private static ArrayList<Class> classList = new ArrayList<Class>();
    // The arraylist of relationships the Class has
    private static ArrayList<Relationship> relationshipList = new ArrayList<Relationship>();

    public static void main(String[] args) {
        clearScreen();

        String intro = "Welcome to ALARM Squad's UML editor!";
        intro += "\n\nType 'help' to list all commands and their accompanying descriptions.\n";
        System.out.println(intro);

        System.out.print(prompt);

        // Enters the command loop
        while (true) {

            // Takes in the next line of user input
            String input = scan.nextLine().trim();

            // If the user enters a blank line, prompt again
            if (input.equals("")) {
                System.out.print(prompt);
            } else {
                // If the user enters a command
                switch (input) {

                    case "exit":
                        return;

                    case "clear":
                        clearScreen();
                        System.out.print(prompt);
                        break;

                    case "add class":
                    case "a class":
                        // Get user defined name for Class, then adds new Class to the classList
                        System.out.print("Enter class name: ");
                        String className = scan.next().trim();

                        if (!ifValidInput(className)) {
                            break;
                        }

                        if (classList.stream().anyMatch(o -> o.getClassName().equals(className))) {
                            System.out.printf("Class %s already exists\n", className);
                            break;
                        }

                        System.out.println("Added class \"" + className + "\"");
                        Class newClass = new Class(className);
                        classList.add(newClass);
                        break;

                    case "delete class":
                    case "d class":
                        // Get user input of Class name to be deleted
                        System.out.print("Enter class name to delete: ");
                        String classDeleteInput = scan.next().trim();
                        Class classToDel = findClass(classDeleteInput);
                        if (classToDel != null) {
                            // Copy classList into new ArrayList with deleted Class
                            classList = deleteClass(classDeleteInput);

                        }
                        break;

                    case "rename class":
                    case "r class":
                        // Get user input of Class name to be renamed
                        System.out.print("Enter class to rename: ");
                        String oldClassName = scan.next().trim();

                        // If Class exists, set Class name to user inputed name
                        Class oldClass = findClass(oldClassName);

                        if (oldClass != null) {
                            System.out.print("Enter new name for class " + oldClass.getClassName() + ": ");
                            String newName = scan.next().trim();
                            if (!ifValidInput(newName)) {
                                break;
                            }
                            oldClass.setClassName(newName);
                            // Inform user of renamed Class
                            System.out.println("The class \"" + oldClassName +
                                    "\" has been renamed to \"" + oldClass.getClassName() + "\"");
                        }
                        break;

                    case "add att":
                    case "a att":
                        // Call find class method
                        Class classToAddAtt = findClass();
                        if (classToAddAtt != null) {
                            // Adds the Attribute to the desired class
                            System.out.print("Enter attribute name: ");
                            String attributeName = scan.next().trim();
                            if (!ifValidInput(attributeName)) {
                                break;
                            }
                            if (classToAddAtt.getAttributeList().stream()
                                    .anyMatch(attObj -> attObj.getAttName().equals(attributeName))) {
                                System.out.println("Attribute " + attributeName +
                                        " already exists in class " + classToAddAtt.getClassName());
                                break;
                            }
                            Attribute attribute = new Attribute(attributeName);
                            classToAddAtt.addAttribute(attribute);
                            System.out.print("Added attribute \"" + attributeName + "\" to class \""
                                    + classToAddAtt.getClassName() + "\"\n");
                        }
                        break;

                    case "delete att":
                    case "d att":

                        // Call find class method to find the class the attribute is
                        Class classToDelAtt = findClass();
                        Boolean attBool = false;
                        // Prompt user to delete an attribute
                        if (classToDelAtt != null) {
                            // while bool is false continue loop until the user does not want to delete an
                            // attribute
                            // or until an attribute is deleted
                            // (this may be changed so the user can type exit to exit the program as well)
                            while (attBool.equals(false)) {
                                System.out.print("Enter attribute name to delete: ");
                                String attToDel = scan.next().trim();
                                Attribute deletedAtt = classToDelAtt.findAttribute(attToDel);
                                if (deletedAtt != null) {
                                    System.out.print("Delete attribute \"" + attToDel + "\"? (y/n): ");
                                    String answer = scan.next().trim();
                                    // If the user wants to delete an attribute, proceed to do so
                                    if (answer.toLowerCase().equals("y")) {
                                        classToDelAtt.deleteAttribute(deletedAtt);
                                        System.out.print("Attribute \"" + attToDel + "\" has been deleted \n");
                                        attBool = true;
                                    }
                                    // if user types n, break out of loop, then break out of case
                                    // and bring back prompt for new command
                                    else {
                                        System.out.println("Attribute " + attToDel + " has NOT been deleted");
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }

                        break;

                    case "rename att":
                    case "r att":
                        // Show user classes and attributes before asking for input
                        listClasses();
                        System.out.print("Enter class that contains attribute: ");
                        String classWithAttName = scan.next().trim();

                        // Ensure class exists
                        Class classWithAtt = findClass(classWithAttName);
                        if (classWithAtt != null) {
                            // List attributes in class before asking for input
                            findClass(classWithAttName).listClass();
                            System.out.print("Enter attribute to be renamed: ");
                            String oldAttName = scan.next().trim();

                            // Ensure attribute exists
                            Attribute newAtt = classWithAtt.findAttribute(oldAttName);
                            if (newAtt != null) {
                                // Rename attribute with user's new name
                                System.out.print("Enter new name for " + oldAttName + ": ");
                                String newAttName = scan.next().trim();
                                if (!ifValidInput(newAttName)) {
                                    break;
                                }
                                newAtt.setAttName(newAttName);
                                System.out.println(oldAttName + " in class " + classWithAttName +
                                        " renamed to " + newAtt.getAttName());
                            }
                        }
                        break;

                    case "add rel":
                    case "a rel":
                        System.out.print("Enter source class name: ");
                        String sourceName = scan.next().trim();
                        // If source class is valid and exists get destination class
                        if (findClass(sourceName) != (null)) {
                            System.out.print("Enter destination: ");
                            String destinationName = scan.next().trim();
                            // If destination class is valid and exists add
                            // relationship to relationship array list
                            if (findClass(destinationName) != (null)) {
                                if (relationshipList.stream()
                                        .anyMatch(srcObj -> srcObj.getSource().getClassName().equals(sourceName) &&
                                                relationshipList.stream().anyMatch(destObj -> destObj.getDestination()
                                                        .getClassName().equals(destinationName)))) {

                                    System.out.println("Relationship already exists between " +
                                            sourceName + " and " + destinationName);
                                    break;
                                }
                                Relationship newRelationship = new Relationship(findClass(sourceName),
                                        findClass(destinationName));
                                relationshipList.add(newRelationship);
                                System.out.println("Relationship added between " + sourceName
                                        + " and " + destinationName);
                            }
                        }
                        break;

                    case "delete rel":
                    case "d rel":

                        // Find the relationship
                        Relationship r = findRelationship();
                        if (r != null) {
                            System.out.print("Delete relationship between \"" +
                                    r.getSource().getClassName() + "\" and \"" + r.getDestination().getClassName()
                                    + "\"? (y/n): ");
                            String rAnswer = scan.next().trim();
                            // If the user wants to delete the relationship, proceed to do so
                            if (rAnswer.toLowerCase().equals("y")) {
                                relationshipList.remove(r);
                                System.out.println("Relationship has been deleted");
                            }
                            // If not, prompt user and move on
                            else if (rAnswer.toLowerCase().equals("n")) {
                                System.out.println("Relationship has NOT been deleted");
                            }
                        }
                        break;

                    case "save":
                        System.out.print("Save file name: ");
                        String saveFileName = scan.next();
                        JSON.save(saveFileName);
                        System.out.println("Diagram has been saved to \"" + saveFileName + ".json\"");
                        break;
                    case "load":
                        if (JSON.ifDirIsEmpty()) {
                            System.out.println("No save files found");
                            System.out.print(prompt);
                            break;
                        } else {
                            System.out.print("Load file name: ");
                            String loadFileName = scan.next();
                            JSON.load(loadFileName);
                            break;
                        }
                    case "list classes":
                    case "l classes":
                        // if there are classes to list, list them
                        if (classList.size() != 0) {
                            // Loops through classList and calls listClass on all elements
                            System.out.println("\n--------------------");
                            if (classList.size() >= 1) {
                                classList.get(0).listClass();
                            }
                            for (int i = 1; i < classList.size(); ++i) {
                                System.out.println();
                                classList.get(i).listClass();
                            }
                            System.out.println("--------------------\n");
                        }
                        // if there are no classes to list, prompt user that there are no classes
                        else {
                            System.out.println("There are no classes to list");
                        }
                        System.out.print(prompt);
                        break;

                    case "list class":
                    case "l class":
                        // Get user to input desired class
                        Class classToList = findClass();
                        // print class name
                        if (classToList != null) {
                            System.out.println("\n--------------------");
                            classToList.listClass();
                            System.out.println("--------------------\n");
                        }
                        break;

                    case "list rel":
                    case "l rel":
                        if (relationshipList.size() == 0) {
                            System.out.println("There are no relationships to list");
                            System.out.print(prompt);
                            break;
                        }

                        // List relationships spaced out by new lines and arrows
                        // designating which class is the source and destination
                        System.out.println("\n--------------------");
                        if (relationshipList.size() >= 1) {
                            System.out.print(relationshipList.get(0).getSource().getClassName());
                            System.out.print(" -> ");
                            System.out.print(relationshipList.get(0).getDestination().getClassName());
                        }

                        for (int i = 1; i < relationshipList.size(); ++i) {
                            System.out.println();
                            System.out.print("\n" + relationshipList.get(i).getSource().getClassName());
                            System.out.print(" -> ");
                            System.out.print(relationshipList.get(i).getDestination().getClassName());
                        }
                        System.out.println("\n--------------------\n");

                        System.out.print(prompt);
                        break;

                    case "help":
                        displayHelp();
                        System.out.print(prompt);
                        break;

                    // If the user's command is not valid
                    default:
                        System.out.println("Please enter a valid command");
                        System.out.print(prompt);
                }
            }
        }
    }

    /********************************************************************
     * 
     * Programmer defined Helper Methods
     * 
     ********************************************************************/

    private static void clearScreen() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    /**
     * Prompts the user for the name of a class, returns it if it's in the
     * classList. Otherwise, prompts them that it does not exist and returns null
     * 
     * @return the Class with the matching name field, otherwise null
     */
    private static Class findClass() {
        // prompts user for name and scans the name
        System.out.print("Enter class name: ");
        String classToFind = scan.next().trim();
        // iterates through the arraylist
        for (int i = 0; i < classList.size(); ++i) {
            // if the name matches, return class
            if (classToFind.equals(classList.get(i).getClassName())) {
                return classList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("Class \"" + classToFind + "\" was not found");
        return null;
    }

    /**
     * Searches for a given class name in the classList and returns the class,
     * otherwise, returns null
     * 
     * @param classToFind the given class name
     * @return the class with the given class name
     */
    public static Class findClass(String classToFind) {
        // iterates through the arraylist
        for (int i = 0; i < classList.size(); ++i) {
            // if the name matches, return class
            if (classToFind.equals(classList.get(i).getClassName())) {
                return classList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("Class \"" + classToFind + "\" was not found");
        return null;
    }

    /**
     * List all classes and their accompanying attributes
     * 
     */
    public static void listClasses() {
        // Loops through classList and calls listClass on all elements
        for (int i = 0; i < classList.size(); ++i) {
            classList.get(i).listClass();
        }
    }

    /**
     * Prompts the user for the name of a relationship, returns it if it's in the
     * relationshipList. Otherwise, prompts them that it does not exist and returns
     * null
     * 
     * @return the relationship if it was found, otherwise returns null
     */
    private static Relationship findRelationship() {
        System.out.print("Enter relationship source name: ");
        String sourceToFind = scan.next().trim();
        Class src = findClass(sourceToFind);

        if (src != null) {
            // If source name was found, proceed to find dest name
            System.out.print("Enter relationship destination name: ");
            String destToFind = scan.next().trim();
            Class dest = findClass(destToFind);

            if (dest != null) {
                // If dest name was found, proceed to find relationship
                for (int i = 0; i < relationshipList.size(); ++i) {
                    // If the name matches, return class
                    if (relationshipList.get(i).getSource().getClassName().equals(sourceToFind) &&
                            relationshipList.get(i).getDestination().getClassName().equals(destToFind)) {

                        return relationshipList.get(i);
                    }
                }
                // If relationship is not found, for loop will end and will proceed to here
                System.out.println("Relationship not found between " + sourceToFind + " and " + destToFind);
            }
        }
        // If user's source or destintion input did not match any relationship's
        // source and destination fields, output error
        return null;
    }

    /**
     * Deletes the class with the matching name field if it exists, and returns the
     * classList.
     * 
     * @param classToDeleteName the name of the class to delete
     * @return the classList
     */
    private static ArrayList<Class> deleteClass(String classToDeleteName) {
        // Create new class object. If a class matches the user inputed name,
        // remove it from the ArrayList. Otherwise, inform user of failure.
        Class classToDelete = null;

        // While loop to make sure the user can make a mistake when typing in
        // a class name to delete, and continue to delete a class afterwards
        while (classToDelete == null) {
            // Iterate through ArrayList of classes to see if class exists
            for (Class classObj : classList) {
                if (classObj.getClassName().equals(classToDeleteName)) {
                    classToDelete = classObj;
                }
            }
            // Remove whatever class classToDelete was assigned as from
            // the ArrayList
            if (classToDelete != null) {
                System.out.print("Delete class \"" + classToDeleteName + "\"? (y/n): ");
                String theNextAnswer = scan.next().trim();

                // User confirms if they wish to delete. If no, break out of loop
                if (theNextAnswer.toLowerCase().equals("y")) {
                    classList.remove(classToDelete);
                    System.out.print("Class \"" + classToDelete.getClassName() + "\" has been deleted\n");
                    break;
                } else if (theNextAnswer.toLowerCase().equals("n")) {
                    System.out.print("Class \"" + classToDelete.getClassName() + "\" has NOT been deleted\n");
                    break;
                }
            }
            // If no class was found in the ArrayList matching the name of the
            // users input, classToDelete will still be null
            else {
                System.out.println("Class \"" + classToDeleteName + "\" was not found");

                // System.out.print("Do you still want to delete a class? (y/n): ");
                // String theAnswer = scan.next();
                // // If the user does not want to delete a class, return class list
                // if (theAnswer.toLowerCase().equals("n")) {
                // return classList;
                // }
                // // Otherwise, prompt user to enter a class to delete again
                // System.out.print("Enter class name to delete: ");
                // classToDeleteName = scan.next();
            }
        }
        return classList;
    }

    /**
     * Gets the classList
     * 
     * @return the list of classes in the diagram
     */
    public static ArrayList<Class> getClassList() {
        return classList;
    }

    /**
     * Gets the relationshipList
     * 
     * @return the list of relationships in the diagram
     */
    public static ArrayList<Relationship> getRelationshipList() {
        return relationshipList;
    }

    /**
     * Clears the classList
     * 
     */
    public static void clearClassList() {
        classList.clear();
    }

    /**
     * Clears the relationshipList
     * 
     */
    public static void clearRelationshipList() {
        relationshipList.clear();
    }

    /**
     * Adds the given Class object to the classList
     * 
     * @param newClass the class to be added
     */
    public static void addToClassList(Class newClass) {
        classList.add(newClass);
    }

    /**
     * Adds the given Relationship object to the relationshipList
     * 
     * @param newRelationship the relationship to be added
     */
    public static void addToRelationshipList(Relationship newRelationship) {
        relationshipList.add(newRelationship);
    }

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

    public static boolean ifValidInput(String input) {
        if (!isValidIdentifier(input)) {
            System.out.printf("Input %s is not a valid identifier\n", input);
            return false;
        }
        return true;
    }

    /**
     * Display list of commands and their accompanying descriptions
     * 
     */
    public static void displayHelp() {
        String helpMessage = "\n   Commands\t\t   Description\n--------------\t\t-----------------\n";
        helpMessage += "a class\t\t\tAdd a new class\n";
        helpMessage += "d class\t\t\tDelete an existing class\n";
        helpMessage += "r class\t\t\tRename an existing class\n";
        helpMessage += "a att\t\t\tAdd a new attribute to an existing class\n";
        helpMessage += "d att\t\t\tDelete an attribute from an existing class\n";
        helpMessage += "r att\t\t\tRename and attribute from an existing class\n";
        helpMessage += "a rel\t\t\tAdd a new relationship\n";
        helpMessage += "d rel\t\t\tDelete an existing relationship\n";
        helpMessage += "save\t\t\tSave the current UML diagram\n";
        helpMessage += "load\t\t\tLoad a previously saved UML diagram\n";
        helpMessage += "clear\t\t\tClear the command history\n";
        helpMessage += "help\t\t\tDisplay list of commands\n";
        helpMessage += "exit\t\t\tExit the application\n";

        System.out.println(helpMessage);
    }

    /**
     * Pass in name of class being deleted. If any relationship with
     * 
     * @param className
     * @return updated relationship list
     */
    public static ArrayList<Relationship> updateRelationshipList(String className) {

        for (Relationship rel : relationshipList) {
            if (rel.getSource().getClassName() == className ||
                    rel.getDestination().getClassName() == className) {
                relationshipList.remove(rel);
            }

        }

        return relationshipList;
    }
}
