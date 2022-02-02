package uml;

import java.util.*;

import javax.management.Attribute;

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
    private static ArrayList<Class> classes = new ArrayList<Class>();

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
                        // Get user defined name for class, then add to ArrayList classes
                        System.out.print("Enter class name: ");
                        String className = scan.next();
                        System.out.println("You added class \"" + className + "\"");
                        Class newClass = new Class(className);
                        classes.add(newClass);
                        break;
                    case "delete class":
                        break;
                    case "rename class":
                        break;
                    case "add attribute":
                        // Finds correct class to add attribute(Att)

                        System.out.print("First enter class name: ");
                        String classToAddAtt = scan.next();

                        for (int i = 0; i < classes.size(); ++i) {
                            if (classToAddAtt == classes.get(i).getName()) {
                                // Adds the Attribure to the desired class
                                System.out.print("Enter attribute name: ");
                                String attributeName = scan.next();
                                Attribute attribute = new Attribute(attributeName);
                                classes.get(i).addAttribute(attribute);
                                return;
                            } else
                                System.out
                                        .println(classToAddAtt + " class was not found, please try again or add class");
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
                        for (int i = 0; i < classes.size(); ++i) {
                            System.out.println(classes.get(i).getName());
                        }
                        break;
                    case "list class":
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
}