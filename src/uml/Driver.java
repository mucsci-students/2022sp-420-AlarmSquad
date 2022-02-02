package uml;

import java.util.*;

/**
 * Gets user input, then uses switch statements to process commands.
 * 
 * @authors
 */
public class Driver {

    // Default prompt for user
    private static String prompt = "> ";
    // Creates new scanner
    private static Scanner scan = new Scanner(System.in);
    // The arraylist of classes in the diagram
    private static ArrayList<Class> classList = new ArrayList<Class>();

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
                        // Get user defined name for class, then adds new class to ArrayList classes
                        System.out.print("Enter class name: ");
                        String className = scan.next();
                        System.out.println("You added class \"" + className + "\"");
                        Class newClass = new Class(className);
                        classList.add(newClass);
                        break;
                    case "delete class":
                        break;
                    case "rename class":
                        break;
                    case "add attribute":
                        // call find class method
                        Class classToAddAtt = findClass();
                        if (classToAddAtt != null) {
                            // Adds the Attribute to the desired class
                            System.out.print("Enter attribute name: ");
                            String attributeName = scan.next();
                            Attribute attribute = new Attribute(attributeName);
                            classToAddAtt.addAttribute(attribute);
                        }
                        break;
                    case "delete attribute":
                        break;
                    case "rename attribute":
                        break;
                    case "add relationship":
                        break;
                    case "delete relationship":
                        break;
                    case "rename relationship":
                        break;
                    case "save":
                        break;
                    case "load":
                        break;
                    case "list classes":
                        // outer loop iterating through classes
                        for (int i = 0; i < classList.size(); ++i) {
                            System.out.println(classList.get(i).getName());
                        }
                        break;
                    case "list class":
                        // call find class method
                        Class classToList = findClass();
                        //display attributes
                        System.out.println(classToList); 
                        //display relationships
                        break;
                    case "list relationships":
                        break;
                    case "help":
                        break;
                    // If the command is not valid
                    default:
                        System.out.println("Please enter a valid command");
                        System.out.print(prompt);
                }
            }
        }
    }

    /**
     * Prompts the user for the name of a class, searches for it in the classList,
     * returns the class
     * otherwise, prompts them if it does not exist and returns null
     * 
     * @return the class with the matching name field, otherwise null
     */
    private static Class findClass() {
        // prompts user for name and scans the name
        System.out.print("Enter class name: ");
        String classToFind = scan.next();
        // iterates through the arraylist
        for (int i = 0; i < classList.size(); ++i) {
            // if the name matches, return class
            if (classToFind.equals(classList.get(i).getName())) {
                return classList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("\"" + classToFind + "\" was not found, please enter an existing class");
        return null;
    }

    /**
     * the user for the name of a class, searches for it in the classList,
     * returns the class
     * 
     * @param classToFind
     * @return
     */
    private static Class findClass(String classToFind) {
        // iterates through the arraylist
        for (int i = 0; i < classList.size(); ++i) {
            // if the name matches, return class
            if (classToFind.equals(classList.get(i).getName())) {
                return classList.get(i);
            }
        }
        return null;
    }
}