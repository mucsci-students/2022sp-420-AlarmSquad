package uml;

import java.util.*;

import uml.managers.ClassManager;
import uml.managers.InterfaceManager;
import uml.managers.RelationshipManager;

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

    private static ClassManager classManager = new ClassManager();
    private static RelationshipManager relationshipManager = new RelationshipManager();
    private static InterfaceManager interfaceManager = new InterfaceManager();

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

                        classManager.addClass(className);
                        break;

                    case "delete class":
                        // Get user input of Class name to be deleted
                        System.out.print("Enter class name to delete: ");

                        String classDeleteInput = scan.next();
                        // Copy classList into new ArrayList with deleted Class
                        //classList = deleteClass(classDeleteInput);

                        classManager.deleteClass(classDeleteInput);
                        break;

                    case "rename class":
                        // Get user input of Class name to be renamed
                        System.out.print("Enter class to rename: ");
                        String oldClassName = scan.next();

                        // If Class exists, set Class name to user inputed name
                        Class oldClass = classManager.findClass(oldClassName);

                        if (oldClass != null) {
                             System.out.print("Enter new name for class " + oldClass.getClassName() + ": ");
                             String newName = scan.next();
                             classManager.renameClass(oldClassName, newName);
                        }

                        break;
                    case "add attribute":
                        // Call find class method
                        Class classToAddAtt = classManager.findClass();
                        if (classToAddAtt != null) {
                            // Adds the Attribute to the desired class
                            System.out.print("Enter attribute name: ");
                            String attributeName = scan.next();
                            classManager.addAttribute(classToAddAtt.getClassName(), attributeName);
                        }
                        break;
                    case "delete attribute":
                        // Call find class method to find the class the attribute is
                        Class classToDelAtt = classManager.findClass();
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
                                attBool = classManager.deleteAttribute(classToDelAtt.getClassName(), attToDel);
                            }
                        }
                        break;
                    case "rename attribute":
                        // Show user classes and attributes before asking for input
                        ClassManager.listClasses();
                        System.out.print("Enter the class that contains the attribute: ");
                        String classWithAttName = scan.next();

                        // Ensure class exists
                        Class classWithAtt = classManager.findClass(classWithAttName);
                        if (classWithAtt != null) {
                            // List attributes in class before asking for input
                            classManager.findClass(classWithAttName).listClass();
                            System.out.print("Enter attribute to be renamed: ");
                            String oldAttName = scan.next();

                            // Ensure attribute exists
                            Attribute newAtt = classWithAtt.findAttribute(oldAttName);
                            if (newAtt != null) {
                                // Rename attribute with user's new name
                                System.out.print("Enter new name for " + oldAttName + ": ");
                                String newAttName = scan.next();
                                classManager.renameAttribute(classWithAttName, oldAttName, newAttName);
                            }
                        }
                        break;
                    case "add relationship":
                        System.out.println("Enter source class name: ");
                        String sourceName = scan.next();
                        // If source class is valid and exists get destination class
                        if (classManager.findClass(sourceName) != (null)) {
                            System.out.println("Enter destination: ");
                            String destinationName = scan.next();
                            // If destination class is valid and exists add
                            // relationship to relationship array list
                            relationshipManager.addRelationship(classManager, sourceName, destinationName);
                        }
                        break;
                    case "delete relationship":
                        relationshipManager.deleteRelationship();
                        break;
                    case "save":
                        JSON.save();
                        break;
                    case "load":
                        break;
                    case "list classes":
                        // if there are classes to list, list them
                        interfaceManager.listClasses(classManager, prompt);
                        break;
                    case "list class":
                        // Get user to input desired class
                        interfaceManager.listClass(classManager);
                        break;
                    case "list relationships":
                        interfaceManager.listRelationships(relationshipManager);
                        break;

                    case "help":
                        interfaceManager.help(prompt);
                        break;

                    // If the command is not valid
                    default:
                        System.out.println("Please enter a valid command");
                        System.out.print(prompt);
                }
            }
        }
    }
}
