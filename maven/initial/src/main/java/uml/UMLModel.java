package uml;

import java.util.ArrayList;

public class UMLModel {
    // The arraylist of classes the diagram has
    private static ArrayList<UMLClass> UMLClassList = new ArrayList<UMLClass>();
    // The arraylist of relationships the diagram has
    private static ArrayList<Relationship> relationshipList = new ArrayList<Relationship>();

    public static ArrayList<UMLClass> getClassList() {
        return UMLClassList;
    }

    public static void setClassList(ArrayList<UMLClass> UMLClassList) {
        UMLModel.UMLClassList = UMLClassList;
    }

    public static ArrayList<Relationship> getRelationshipList() {
        return relationshipList;
    }

    public static void setRelationshipList(ArrayList<Relationship> relationshipList) {
        UMLModel.relationshipList = relationshipList;
    }

    public static void addClass(UMLClass newUMLClass) {
        UMLClassList.add(newUMLClass);
    }

    public static void deleteClass(UMLClass delUMLClass) {
        UMLClassList.remove(delUMLClass);
    }

    public static void addRel(Relationship newRel) {
        relationshipList.add(newRel);
    }

    public static void deleteRel(Relationship delRel) {
        relationshipList.remove(delRel);
    }

    /**
     * Clears the classList
     */
    public static void clearClassList() {
        UMLClassList.clear();
    }

    /**
     * Clears the relationshipList
     */
    public static void clearRelationshipList() {
        relationshipList.clear();
    }

    /**
     * Iterate through arraylist and return true if class with
     * matching name is found. Return false otherwise.
     *
     * @return true if class exists in arraylist, false if not
     */
    public static UMLClass findClass(String nameOfClass) {
        for (UMLClass aUMLClass : UMLClassList) {
            // if the name matches, return class
            if (nameOfClass.equals(aUMLClass.getClassName())) {
                return aUMLClass;
            }
        }
        return null;
    }

    /**
     * Iterate through arraylist and return the relationship if relationship with
     * matching source and destination names is found. Return false otherwise.
     *
     * @param source class name
     * @param dest   class name
     * @param relType type name
     * @return true if class exists in arraylist, false if not
     */
    public static Relationship findRelationship(String source, String dest, String relType) {
        for (Relationship relationship : relationshipList) {
            // If the name matches, return class
            if (relationship.getSource().getClassName().equals(source) &&
                    relationship.getDestination().getClassName().equals(dest) &&
                    relationship.getRelType().equals(relType)) {

                return relationship;
            }
        }
        return null;
    }

    /**
     * Iterates through relationship list and finds the type of a
     * relationship given its source and destination
     *
     * @param source class name
     * @param dest class name
     * @return string of relationship type
     */
    public static String findRelType(String source, String dest) {
        for(Relationship relationship : relationshipList){
            // if name matches, return relationship type
            if(relationship.getSource().getClassName().equals(source) &&
                    relationship.getDestination().getClassName().equals(dest)){

                return relationship.getRelType();
            }
        }
        return null;
    }

    /**
     * If a class has been deleted, also delete any relationships associated
     * with that class
     *
     * @param className
     * @return updated relationship list
     */
    public static ArrayList<Relationship> updateRelationshipList(String className) {
        relationshipList.removeIf(rel -> rel.getSource().getClassName().equals(className) ||
                rel.getDestination().getClassName().equals(className));
        return relationshipList;
    }
}