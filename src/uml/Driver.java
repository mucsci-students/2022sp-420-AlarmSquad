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
    // The arraylist of relationships the class has
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
                        // Get user defined name for class, then adds new class to ArrayList classes
                        System.out.print("Enter class name: ");
                        String className = scan.next();
                        System.out.println("You added class \"" + className + "\"");
                        Class newClass = new Class(className);
                        classList.add(newClass);
                        break;
                    case "delete class":
                        // Get user input of class name to be deleted
                        System.out.print("Enter class name to delete: ");
                        String classDeleteInput = scan.next();
                        // Copy classList into new ArrayList with deleted class
                        classList = deleteClass(classDeleteInput);
                        break;

                    case "rename class":

                        // Get user input of class name to be renamed
                        System.out.print("Enter class to rename: ");
                        String oldClassName = scan.next();

                        // If class exists, set class name to user inputed name
                        Class oldClass = findClass(oldClassName);
                        if (oldClass != null) {
                            System.out.print("Enter new name for class " + oldClass.getName() + ": ");
                            String newName = scan.next();
                            oldClass.setName(newName);
                            // Inform user of renamed class
                            System.out.println("The class \"" + oldClassName +
                                    "\" has been renamed to \"" + oldClass.getName() + "\"");
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
                        }
                        break;
                    case "delete attribute":
                        // Call find attribute method
                        Class classToDelAtt = findClass();
                        if (classToDelAtt != null) {
                            System.out.print("Enter attribute name to delete: ");
                            String attToDel = scan.next();
                            System.out.print("Are you sure you want to delete \"" + attToDel + "\"? (y/n) ");
                            String answer = scan.next();
                            if (answer.equals("y")) {
                                Attribute deletedAtt = classToDelAtt.findAtt(attToDel);
                                classToDelAtt.deleteAttribute(deletedAtt);
                                System.out.print("Attribute \"" + attToDel + "\" has been deleted");
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
                            Attribute newAtt = classWithAtt.findAtt(oldAttName);
                            if (newAtt != null) {
                                // Rename attribute with user's new name
                                System.out.print("Enter new name for " + oldAttName + ": ");
                                String newAttName = scan.next();
                                newAtt.setName(newAttName);
                                System.out.println(oldAttName + " in class " + classWithAttName +
                                        " renamed to " + newAtt.getName());
                            }
                        }
                        break;

                    case "add relationship":
                        System.out.println("Enter source class name: ");
                        String sourceName = scan.next();
                        // If source class is valid and exists get destination class
                        if (!findClass(sourceName).equals(null)) {
                            System.out.println("Enter destination: ");
                            String destinationName = scan.next();
                            // If destination class is valid and exists add
                            // relationship to relationship array list
                            if (!findClass(destinationName).equals(null)) {
                                Relationship newRelationship = new Relationship(findClass(sourceName),
                                        findClass(destinationName));
                                relationshipList.add(newRelationship);
                            }
                        }
                        break;
                    case "delete relationship":
                        findRelationship();
                        break;
                    case "rename relationship":
                        break;
                    case "save":
                        break;
                    case "load":
                        break;
                    case "list classes":
                        listClasses();
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

    /*********************************************************************************/
    /**
     * Programmer defined methods
     * /
     * /
     *********************************************************************************/

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
            if (classToFind.equals(classList.get(i).getName())) {
                return classList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("\"" + classToFind + "\" was not found, please enter an existing class");
        return null;
    }

    /**
     * List all classes and their accompanying attributes
     */
    public static void listClasses() {
        // Loops through classList and calls listClass on all elements
        for (int i = 0; i < classList.size(); ++i) {
            classList.get(i).listClass();
        }
    }

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

    public static ArrayList<Class> deleteClass(String classToDeleteName) {
        // Create new class object. If a class matches the user inputed name,
        // remove it from the ArrayList. Otherwise, inform user of failure.
        Class classToDelete = null;
        // Iterate through ArrayList of classes to see if class exists
        for (Class classObj : classList) {
            if (classObj.getName().equals(classToDeleteName))
                classToDelete = classObj;
        }
        // Remove whatever class classToDelete was assigned as from
        // the ArrayList
        if (classToDelete != null) {
            classList.remove(classToDelete);
        }
        // If no class was found in the ArrayList matching the name of the
        // users input, classToDelete will still be null
        else {
            System.out.println("Class \"" + classToDeleteName + "\" not found");
        }
        return classList;
    }
}
