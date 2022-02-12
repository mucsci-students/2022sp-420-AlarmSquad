package uml.managers;

import uml.Class;
import java.util.ArrayList;

public class InterfaceManager {
    public void listClasses(ClassManager classManager, String prompt) {
        // if there are classes to list, list them
        ArrayList<Class> classList = classManager.getClassList();
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
    }

    public void listClass(ClassManager classManager) {
        // Get user to input desired class
        Class classToList = classManager.findClass();
        // print class name
        if (classToList != null) {
            classToList.listClass();
        }
    }

    public void listRelationships(RelationshipManager relationshipManager) {
        if (relationshipManager.getRelationshipList().size() >= 1) {
            System.out.print(relationshipManager.getRelationshipList().get(0).getID());
        }
        for (int i = 1; i < relationshipManager.getRelationshipList().size(); ++i) {
            System.out.print(", " + relationshipManager.getRelationshipList().get(i).getID());
        }
    }

    public void help(String prompt) {
        displayHelp();
        System.out.print(prompt);
    }

    /********************************************************************
     * 
     * Programmer defined Helper Methods
     * 
     ********************************************************************/

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
