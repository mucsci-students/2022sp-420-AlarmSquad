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
    private static ArrayList<Class> classes;

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
                    case "add class":
                        System.out.print("Enter class name: ");
                        String className = scan.next();
                        System.out.println("You added class \"" + className + "\"");
                        Class newClass = new Class(className);
                        break;
                    case "delete class":
                        break;
                    case "rename class":
                        break;
                    case "add attribute":
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
                        break;
                    case "list class":
                        break;
                    case "list relationships":
                        break;
                    case "help":
                        break;
                    case "exit":
                        return;
                    // If the command is not valid
                    default:
                        System.out.println("Please enter a valid command");
                        System.out.print(prompt);
                }
            }
        }
    }
}