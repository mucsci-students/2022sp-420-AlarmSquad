package uml;

import java.util.ArrayList;

public class UMLModel {
    // The arraylist of classes the diagram has
    private static ArrayList<Class> classList = new ArrayList<Class>();
    // The arraylist of relationships the diagram has
    private static ArrayList<Relationship> relationshipList = new ArrayList<Relationship>();

    public UMLModel() {
    }

    public static ArrayList<Class> getClassList() {
        return classList;
    }

    public static void setClassList(ArrayList<Class> classList) {
        UMLModel.classList = classList;
    }

    public static ArrayList<Relationship> getRelationshipList() {
        return relationshipList;
    }

    public static void setRelationshipList(ArrayList<Relationship> relationshipList) {
        UMLModel.relationshipList = relationshipList;
    }

    public static void addClass(Class newClass) {
        classList.add(newClass);
    }

    public static void deleteClass(Class delClass) {
        classList.remove(delClass);
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
        classList.clear();
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
     * @param nameOfClass
     * @return true if class exists in arraylist, false if not
     */
    public boolean classExists(String nameOfClass) {
        for (Class aClass : classList) {
            // if the name matches, return class
            if (nameOfClass.equals(aClass.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterate through arraylist and return true if class with
     * matching name is found. Return false otherwise.
     *
     * @param source class name
     * @param dest class name
     * @return true if class exists in arraylist, false if not
     */
    public boolean relExists(String source, String dest) {
        for (Relationship relationship : relationshipList) {
            // If the name matches, return class
            if (relationship.getSource().getClassName().equals(source) &&
                    relationship.getDestination().getClassName().equals(dest)) {

                return true;
            }
        }
        return false;
    }
}

