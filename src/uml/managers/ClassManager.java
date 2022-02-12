package uml.managers;

import uml.Class;
import uml.Attribute;
import java.util.ArrayList;
import java.util.Scanner;

public class ClassManager {
    private static ArrayList<Class> classList;
    private Scanner scan;

    public ClassManager() {
        classList = new ArrayList<Class>();
        scan = new Scanner(System.in);
    }

    public ArrayList<Class> getClassList() {
        return classList;
    }

    public void addClass(String className) {
        if(classList.stream().anyMatch(o -> o.getClassName().equals(className)))
        {
            System.out.printf("Class %s already exists/n", className);
            return;  
        }

        System.out.println("You added class \"" + className + "\"");
        Class newClass = new Class(className);
        classList.add(newClass);
    }

    public void deleteClass(String className) {
        // Copy classList into new ArrayList with deleted Class
        classList = deleteSpecifiedClass(className);
    }

    public void renameClass(String oldClassName, String newName) {
        // If Class exists, set Class name to user inputed name
        Class oldClass = findClass(oldClassName);
        oldClass.setClassName(newName);
        // Inform user of renamed Class
        System.out.println("The class \"" + oldClassName +
                "\" has been renamed to \"" + oldClass.getClassName() + "\"");
    }

    public void addAttribute(String classToAddAttName, String attributeName) {
        // Call find class method
        Class classToAddAtt = findClass(classToAddAttName);
        Attribute attribute = new Attribute(attributeName);
        classToAddAtt.addAttribute(attribute);
        System.out.print("You added attribute \"" + attributeName + "\" to class \""
                + classToAddAtt.getClassName() + "\"\n");
    }

    public boolean deleteAttribute(String classToDelAttName, String attToDel) {
        Class classToDelAtt = findClass(classToDelAttName);
        Attribute deletedAtt = classToDelAtt.findAttribute(attToDel);
        if (deletedAtt != null) {
            System.out.print("Are you sure you want to delete \"" + attToDel + "\"? (y/n) ");
            String answer = scan.next();
            // If the user wants to delete an attribute, proceed to do so
            if (answer.equals("y")) {
                classToDelAtt.deleteAttribute(deletedAtt);
                System.out.print("Attribute \"" + attToDel + "\" has been deleted \n");
                return true;
            }
            // if user types n, break out of loop and bring back prompt for new command
            else {
                return false;
            }
        }

        return false;
    }

    public void renameAttribute(String classWithAttName, String oldAttName, String newAttName) {
        Class classWithAtt = findClass(classWithAttName);
        Attribute newAtt = classWithAtt.findAttribute(oldAttName);
        newAtt.setAttName(newAttName);
        System.out.println(oldAttName + " in class " + classWithAtt.getClassName() +
                " renamed to " + newAtt.getAttName());
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
    public Class findClass() {
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
    public Class findClass(String classToFind) {
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
     * Deletes the class with the matching name field if it exists, and returns the
     * classList.
     * 
     * @param classToDeleteName the name of the class to delete
     * @return the classList
     */
    private ArrayList<Class> deleteSpecifiedClass(String classToDeleteName) {
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
     * List all classes and their accompanying attributes
     * 
     */
    public static void listClasses() {
        // Loops through classList and calls listClass on all elements
        for (int i = 0; i < classList.size(); ++i) {
            classList.get(i).listClass();
        }
    }
}
