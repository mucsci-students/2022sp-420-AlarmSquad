package uml.managers;

import uml.Relationship;

import java.util.ArrayList;
import java.util.Scanner;

public class RelationshipManager {

    public static ArrayList<Relationship> relationshipList;
    public static Scanner scan;

    public RelationshipManager() {
        relationshipList = new ArrayList<Relationship>();
        scan = new Scanner(System.in);
    }

    public ArrayList<Relationship> getRelationshipList() {
        return relationshipList;
    }

    public void addRelationship(ClassManager classManager, String sourceName, String destinationName) {
        if (classManager.findClass(destinationName) != (null)) {
            Relationship newRelationship = new Relationship(classManager.findClass(sourceName),
                    classManager.findClass(destinationName));
            relationshipList.add(newRelationship);
        }
    }

    public void deleteRelationship() {
        relationshipList.remove(findRelationship());
    }

    // /********************************************************************
    //  * 
    //  * Programmer defined Helper Methods
    //  * 
    //  ********************************************************************/

    /**
    //  * Prompts the user for the name of a relationship, returns it if it's in the
    //  * relationshipList. Otherwise, prompts them that it does not exist and returns
    //  * null
    //  * 
    //  * @return the relationship if it was found, otherwise returns null
    //  */
    
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
}
