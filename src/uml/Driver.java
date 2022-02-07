package uml;

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

        System.out.print(prompt);

        // Enters the command loop
        while (true) {

            // Takes in the next line of user input
            String input = scan.nextLine();

            // If the user enters a blank line, prompt again
            if (input.equals("")) {
                System.out.print(prompt);
            } else {
                // If the user enters a command
                switch (input) {
                    case "exit":
                        return;

                    case "add class":
                        // Get user defined name for Class, then adds new Class to the classList
                        System.out.print("Enter class name: ");
                        String className = scan.next();

                        if(classList.stream().anyMatch(o -> o.getName().equals(className)))
                        {
                            System.out.printf("Class %s already exists/n", className);
                            break;  
                        }

                        System.out.println("You added class \"" + className + "\"");
                        Class newClass = new Class(className);
                        classList.add(newClass);
                        break;

                    case "delete class":
                        // Get user input of Class name to be deleted
                        System.out.print("Enter class name to delete: ");
                        String classDeleteInput = scan.next();
                        // Copy classList into new ArrayList with deleted Class
                        classList = deleteClass(classDeleteInput);
                        break;

                    case "rename class":
                        // Get user input of Class name to be renamed
                        System.out.print("Enter class to rename: ");
                        String oldClassName = scan.next();

                        // If Class exists, set Class name to user inputed name
                        Class oldClass = findClass(oldClassName);

                        if (oldClass != null) {
                            System.out.print("Enter new name for class " + oldClass.getClassName() + ": ");
                            String newName = scan.next();
                            oldClass.setClassName(newName);
                            // Inform user of renamed Class
                            System.out.println("The class \"" + oldClassName +
                                    "\" has been renamed to \"" + oldClass.getClassName() + "\"");
                        }
                        break;

                    case "add attribute":
                        // Call find class method
                        Class classToAddAtt = findClass();
                        if (classToAddAtt != null) {
                            // Adds the Attribute to the desired class
                            System.out.print("Enter attribute name: ");
                            String attributeName = scan.next();
                            Attribute attribute = new Attribute(attributeName);
                            classToAddAtt.addAttribute(attribute);
                            System.out.print("You added attribute \"" + attributeName + "\" to class \""
                                    + classToAddAtt.getClassName() + "\"\n");
                        }
                        break;
                    case "delete attribute":
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
                                String attToDel = scan.next();
                                Attribute deletedAtt = classToDelAtt.findAttribute(attToDel);
                                if (deletedAtt != null) {
                                    System.out.print("Are you sure you want to delete \"" + attToDel + "\"? (y/n) ");
                                    String answer = scan.next();
                                    // If the user wants to delete an attribute, proceed to do so
                                    if (answer.equals("y")) {
                                        classToDelAtt.deleteAttribute(deletedAtt);
                                        System.out.print("Attribute \"" + attToDel + "\" has been deleted \n");
                                        attBool = true;
                                    }
                                    // if user types n, break out of loop and bring back prompt for new command
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case "rename attribute":
                        // Show user classes and attributes before asking for input
                        listClasses();
                        System.out.print("Enter the class that contains the attribute: ");
                        String classWithAttName = scan.next();

                        // Ensure class exists
                        Class classWithAtt = findClass(classWithAttName);
                        if (classWithAtt != null) {
                            // List attributes in class before asking for input
                            findClass(classWithAttName).listClass();
                            System.out.print("Enter attribute to be renamed: ");
                            String oldAttName = scan.next();

                            // Ensure attribute exists
                            Attribute newAtt = classWithAtt.findAttribute(oldAttName);
                            if (newAtt != null) {
                                // Rename attribute with user's new name
                                System.out.print("Enter new name for " + oldAttName + ": ");
                                String newAttName = scan.next();
                                newAtt.setAttName(newAttName);
                                System.out.println(oldAttName + " in class " + classWithAttName +
                                        " renamed to " + newAtt.getAttName());
                            }
                        }
                        break;
                    case "add relationship":
                        System.out.println("Enter source class name: ");
                        String sourceName = scan.next();
                        // If source class is valid and exists get destination class
                        if (findClass(sourceName) != (null)) {
                            System.out.println("Enter destination: ");
                            String destinationName = scan.next();
                            // If destination class is valid and exists add
                            // relationship to relationship array list
                            if (findClass(destinationName) != (null)) {
                                Relationship newRelationship = new Relationship(findClass(sourceName),
                                        findClass(destinationName));
                                relationshipList.add(newRelationship);
                            }
                        }
                        break;
                    case "delete relationship":
                        relationshipList.remove(findRelationship());
                        break;
                    case "save":
                        JSON.save();
                        break;
                    case "load":
                        break;
                    case "list classes":
                        // if there are classes to list, list them
                        if (classList.size() != 0) {
                            // Loops through classList and calls listClass on all elements
                            for (int i = 0; i < classList.size(); ++i) {
                                classList.get(i).listClass();
                            }
                        }
                        // if there are no classes to list, prompt user that there are no classes
                        else {
                            System.out.print("There are currently no classes to list.\n");
                        }
                        System.out.print(prompt);
                        break;
                    case "list class":
                        // Get user to input desired class
                        Class classToList = findClass();
                        // print class name
                        if (classToList != null) {
                            classToList.listClass();
                        }
                        break;
                    case "list relationships":
                        if (relationshipList.size() >= 1) {
                            System.out.print(relationshipList.get(0).getID());
                        }
                        for (int i = 1; i < relationshipList.size(); ++i) {
                            System.out.print(", " + relationshipList.get(i).getID());
                        }
                        break;

                    case "help":
                        displayHelp();
                        System.out.print(prompt);
                        break;

                    // If the command is not valid
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

    /**
     * Prompts the user for the name of a class, returns it if it's in the
     * classList. Otherwise, prompts them that it does not exist and returns null
     * 
     * @return the Class with the matching name field, otherwise null
     */
    private static Class findClass() {
        // prompts user for name and scans the name
        System.out.print("Enter class name: ");
        String classToFind = scan.next();
        // iterates through the arraylist
        for (int i = 0; i < classList.size(); ++i) {
            // if the name matches, return class
            if (classToFind.equals(classList.get(i).getClassName())) {
                return classList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("\"" + classToFind + "\" was not found, please enter an existing class");
        return null;
    }

    /**
     * Searches for a given class name in the classList and returns the class,
     * otherwise, returns null
     * 
     * @param classToFind the given class name
     * @return the class with the given class name
     */
    private static Class findClass(String classToFind) {
        // iterates through the arraylist
        for (int i = 0; i < classList.size(); ++i) {
            // if the name matches, return class
            if (classToFind.equals(classList.get(i).getClassName())) {
                return classList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("\"" + classToFind + "\" was not found, please enter an existing class");
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
        System.out.print("Enter Relationship ID: ");
        String relationToFind = scan.next();

        for (int i = 0; i < relationshipList.size(); ++i) {
            // if the name matches, return class
            if (relationToFind.equals(relationshipList.get(i).getID())) {
                return relationshipList.get(i);
            }
        }
        System.out.println("\"" + relationToFind + "\" was not found, please enter an existing class");
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
                System.out.print("Are you sure? (y/n): ");
                String theNextAnswer = scan.next();
                if (theNextAnswer.equals("y")) {
                    classList.remove(classToDelete);
                    System.out.print("Class \"" + classToDelete.getClassName() + "\" has been deleted\n");
                    break;
                } else if (theNextAnswer.equals("n")) {
                    System.out.print("Class \"" + classToDelete.getClassName() + "\" has NOT been deleted\n");
                    break;
                }
            }
            // If no class was found in the ArrayList matching the name of the
            // users input, classToDelete will still be null
            else {
                System.out.println("Class \"" + classToDeleteName + "\" not found");
                System.out.print("Do you want to delete a class? (y/n): ");
                String theAnswer = scan.next();
                // If the user does not want to delete a class, return class list
                if (theAnswer.equals("n")) {
                    return classList;
                }
                // Otherwise, prompt user to enter a class to delete again
                System.out.print("Enter class name to delete: ");
                classToDeleteName = scan.next();
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
     * Display list of commands and their accompanying descriptions
     * 
     */
    public static void displayHelp() {
        String helpMessage = "\n   Commands\t\t   Description\n--------------\t\t-----------------\n";
        helpMessage += "add class\t\tAdd a new class\n";
        helpMessage += "delete class\t\tDelete an existing class\n";
        helpMessage += "rename class\t\tRename an existing class\n";
        helpMessage += "add attribute\t\tAdd a new attribute to an existing class\n";
        helpMessage += "delete attribute\tDelete an attribute from an existing class\n";
        helpMessage += "rename attribute\tRename and attribute from an existing class\n";
        helpMessage += "add relationship\tAdd a new relationship\n";
        helpMessage += "delete relationship\tDelete an existing relationship\n";
        helpMessage += "rename relationship\tRename an existing relationship\n";
        helpMessage += "save\t\t\tSave the current UML diagram\n";
        helpMessage += "load\t\t\tLoad a previously saved UML diagram\n";
        helpMessage += "help\t\t\tDisplay list of commands\n";
        helpMessage += "exit\t\t\tExit the application\n";

        System.out.println(helpMessage);
    }
}
