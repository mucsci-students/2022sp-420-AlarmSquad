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
     * Iterate through arraylist and return the class if a class with
     * matching name is found. Return null otherwise.
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
     * matching source and destination names is found. Return null otherwise.
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

    /**
     * Gets the text for the help menu in a CLI format
     *
     * @return the help menu
     */
    public static String getCLIHelpMenu() {
        String helpMenu = """
                   Commands		   Description
                --------------	-----------------
                a class			Add a new class
                d class			Delete an existing class
                r class			Rename an existing class
                a att -f	    Add a new field to an existing class
                a att -m	    Add a new method to an existing class
                d att -f	    Delete a field from an existing class
                d att -m	    Delete a method from an existing class
                r att -f	    Rename a field from an existing class
                r att -m	    Rename a method from an existing class
                a rel			Add a new relationship
                d rel			Delete an existing relationship
                save			Save the current UML diagram
                load			Load a previously saved UML diagram
                clear			Clear the command history
                help			Display list of commands
                exit			Exit the application""";
        return helpMenu;
    }

    /**
     * Gets the text for the help menu in a GUI format
     *
     * @return the help menu
     */
    public static String getGUIHelpMenu() {
        String helpMenu = """
                 Commands\t   Description
                --------------\t-----------------
                a class\t\tAdd a new class
                d class\t\tDelete an existing class
                r class\t\tRename an existing class
                a att -f\t\tAdd a new field to an existing class
                a att -m\t\tAdd a new method to an existing class
                d att -f\t\tDelete a field from an existing class
                d att -m\t\tDelete a method from an existing class
                r att -f\t\tRename a field from an existing class
                r att -m\t\tRename a method from an existing class
                a rel\t\t\tAdd a new relationship
                d rel\t\t\tDelete an existing relationship
                save\t\t\tSave the current UML diagram
                load\t\t\tLoad a previously saved UML diagram
                clear\t\t\tClear the command history
                help\t\t\tDisplay list of commands
                exit\t\t\tExit the application""";
        return helpMenu;
    }
}