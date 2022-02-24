package uml.managers;

import uml.CLIController;
import uml.UMLClass;

import java.util.ArrayList;

public class ClassManager {

    private final ArrayList<UMLClass> UMLClassList;

    public ClassManager() {
        UMLClassList = new ArrayList<UMLClass>();
    }

    public ArrayList<UMLClass> getClassList() {
        return UMLClassList;
    }

    public String addClass(String className) {
        if (CLIController.isNotValidInput(className)) {
            return "\"" + className + "\" is not a valid identifier\n";
        }

        if (UMLClassList.stream().anyMatch(o -> o.getClassName().equals(className))) {
            return "Class \"" + className + "\" already exists\n";
        }

        UMLClass newUMLClass = new UMLClass(className);
        UMLClassList.add(newUMLClass);
        return "Added class \"" + className + "\"\n";
    }

    public String deleteClass(String classDeleteInput) {
        UMLClass UMLClassToDel = findClass(classDeleteInput);
        if (UMLClassToDel != null) {
            UMLClassList.remove(UMLClassToDel);
            return "Class \"" + UMLClassToDel.getClassName() + "\" has been deleted\n";
        }
        return "Class \"" + classDeleteInput + "\" was not found\n";
    }

    public String renameClass(String oldClassName, String newName) {
        if (CLIController.isNotValidInput(newName)) {
            return "\"" + newName + "\" is not a valid identifier\n";
        }
        if(findClass(newName) != null) {
            return "Class \"" + newName + "\" already exists\n";
        }
        UMLClass oldUMLClass = findClass(oldClassName);
        oldUMLClass.setClassName(newName);
        // Inform user of renamed Class
        return "The class \"" + oldClassName +
            "\" has been renamed to \"" + oldUMLClass.getClassName() + "\"\n";
    }

    public int numOfClasses(String searchName) {
        int count = 0;
        for (UMLClass UMLClassObj : UMLClassList) {
            if (UMLClassObj.getClassName().equals(searchName)) {
                ++count;
            }
        }
        return count;
    }

    public UMLClass findClass(String classToFind) {
        // iterates through the arraylist
        for (UMLClass aUMLClass : UMLClassList) {
            // if the name matches, return class
            if (classToFind.equals(aUMLClass.getClassName())) {
                return aUMLClass;
            }
        }
        // otherwise, tell the user it does not exist and return null
        System.out.println("Class \"" + classToFind + "\" was not found");
        return null;
    }
}
