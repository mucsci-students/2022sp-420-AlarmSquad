package uml.managers;

import uml.Relationship;
import uml.UMLClass;

import java.util.ArrayList;

public class RelationshipManager {
    private static ArrayList<Relationship> relationshipList;

    public RelationshipManager() {
        relationshipList = new ArrayList<Relationship>();
    }

    public String addRelationship(ClassManager classManager, String sourceName, String destinationName, String relType) {
        if (classManager.findClass(destinationName) != (null)) {
            if (relationshipList.stream()
                    .anyMatch(srcObj -> srcObj.getSource().getClassName().equals(sourceName) &&
                            relationshipList.stream().anyMatch(destObj -> destObj.getDestination()
                                    .getClassName().equals(destinationName)))) {

                return "Relationship already exists between \"" +
                        sourceName + "\" and \"" + destinationName + "\"\n";
            }
            Relationship newRelationship = new Relationship(classManager.findClass(sourceName),
                    classManager.findClass(destinationName), relType);
            relationshipList.add(newRelationship);
            return "Relationship added between \"" + sourceName
                    + "\" and \"" + destinationName + "\"\n";
        }
        return "Relationship already exists between " +
                sourceName + " and " + destinationName + "\"\n";
    }

    public String deleteRelationship(ClassManager classManager, String sourceToFind, String destToFind) {
        UMLClass dest = classManager.findClass(destToFind);

        if (dest != null) {
            // If dest name was found, proceed to find relationship
            for (int i = 0; i < relationshipList.size(); ++i) {
                // If the name matches, return class
                if (relationshipList.get(i).getSource().getClassName().equals(sourceToFind) &&
                        relationshipList.get(i).getDestination().getClassName().equals(destToFind)) {

                    relationshipList.remove(relationshipList.get(i));
                    return "Relationship has been deleted\n";
                }
            }
        }

        // If relationship is not found, for loop will end and will proceed to here
        return "Relationship not found between \"" + sourceToFind + "\" and \"" + destToFind + "\"\n";
    }

    public Relationship findRelationship(ClassManager classManager, String sourceToFind, String destToFind, String relType) {
        UMLClass src = classManager.findClass(sourceToFind);

        if (src != null) {
            // If source name was found, proceed to find dest name
            UMLClass dest = classManager.findClass(destToFind);

            if (dest != null) {
                // If dest name was found, proceed to find relationship
                for (Relationship relationship : relationshipList) {
                    // If the name matches, return class
                    if (relationship.getSource().getClassName().equals(sourceToFind) &&
                            relationship.getDestination().getClassName().equals(destToFind) &&
                            relationship.getRelType().equals(relType)) {

                        return relationship;
                    }
                }
                // If relationship is not found, for loop will end and will proceed to here
                System.out.println("Relationship not found between " + sourceToFind + " and " + destToFind);
            }
        }
        // If user's source or destination input did not match any relationship's
        // source and destination fields, output error
        return null;
    }
}
