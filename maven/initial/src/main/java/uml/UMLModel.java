package uml;

import java.util.ArrayList;

// The project model; holds information on the project data
public class UMLModel {
    // The arraylist of classes the diagram has
    private static ArrayList<UMLClass> UMLClassList = new ArrayList<>();
    // The arraylist of relationships the diagram has
    private static ArrayList<Relationship> relationshipList = new ArrayList<>();

    // getter for class list
    public static ArrayList<UMLClass> getClassList() { return UMLClassList; }
    // setter for class list
    public static void setClassList(ArrayList<UMLClass> UMLClassList) {
        UMLModel.UMLClassList = UMLClassList;
    }
    // getter for relationship list
    public static ArrayList<Relationship> getRelationshipList() {
        return relationshipList;
    }
    // setter for relationship list
    public static void setRelationshipList(ArrayList<Relationship> relationshipList) {
        UMLModel.relationshipList = relationshipList;
    }

    // add a class to the class list
    public static void addClass(UMLClass newUMLClass) {
        UMLClassList.add(newUMLClass);
    }
    // remove a class from the class list
    public static void deleteClass(UMLClass delUMLClass) {
        UMLClassList.remove(delUMLClass);
    }
    // clear the entire class list
    public static void clearClassList() {
        UMLClassList.clear();
    }

    // add a relationship to the rel list
    public static void addRel(Relationship newRel) {
        relationshipList.add(newRel);
    }
    // remove a relationship from the rel list
    public static void deleteRel(Relationship delRel) {
        relationshipList.remove(delRel);
    }
    // clear the entire rel list
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

    //***************************************//
    //************ Relationship *************//
    //***************************************//

    /**
     * Iterate through arraylist and return the relationship if relationship with
     * matching source and destination names is found. Return null otherwise.
     *
     * @param source class name
     * @param dest   class name
     * @param relType type name
     * @return the relationship, null if not found
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
     * Iterates through the relationship list and finds the relationship
     * between two classes, if one is found, there is a relation
     *
     * @param source the source class name
     * @param dest the destination class name
     * @return true if two classes are related in src->dest order, false otherwise
     */
    public static boolean isRelated(String source, String dest){
        for(Relationship relationship : relationshipList){
            if(findRelationship(source, dest, findRelType(source,dest)) != null){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given relationship type is correct
     *
     * @param relType the relationship type
     * @return false if none are correct, true otherwise
     */
    public static boolean checkType(String relType){
        return switch (relType){
            case "aggregation", "composition", "inheritance", "realization" -> true;
            default -> false;
        };
    }

    /**
     * Changes the type of an existing relationship
     *
     * @param src the source name
     * @param dest the destination name
     * @param newRelType the new relationship type
     */
    public static void changeRelType(String src, String dest, String newRelType){
        // finds the relationship and changes the type
        for(Relationship r : relationshipList){
            if(r.getSource().getClassName().equals(src) &&
                    r.getDestination().getClassName().equals(dest)){
                r.setRelType(newRelType);
            }
        }
    }

    /**
     * If a class has been deleted, also delete any relationships associated
     * with that class
     *
     * @param className the name of the class associated with any relationship
     * @return updated relationship list
     */
    public static ArrayList<Relationship> updateRelationshipList(String className) {
        relationshipList.removeIf(rel -> rel.getSource().getClassName().equals(className) ||
                rel.getDestination().getClassName().equals(className));
        return relationshipList;
    }

    //***************************************//
    //******** User Input Correction ********//
    //***************************************//

    /**
     * Takes a string and checks if it is a valid identifier
     * (helper class of isNotValidInput)
     *
     * @param input the string to check
     * @return true if valid, otherwise false
     */
    public static boolean isValidIdentifier(String input) {
        if (input == null) {
            return false;
        }
        char[] c = input.toCharArray();
        if (Character.isJavaIdentifierStart(c[0])) {
            for (int i = 1; i < input.length(); i++) {
                if (!Character.isJavaIdentifierPart(c[i])) {
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Takes a string and checks if it is a valid name
     *
     * @param input the string to check
     * @return false if valid, otherwise true
     */
    public static boolean isNotValidInput(String input) {
        if (!isValidIdentifier(input)) {
            System.out.printf("Input %s is not a valid identifier\n", input);
            return true;
        }
        return false;
    }

    /**
     * Takes a string and checks if it is a valid field type
     *
     * @param input the string to check
     * @return false if valid, otherwise true
     */
    public static boolean isNotValidType(String input) {
        return switch (input) {
            case "string", "int", "double", "float", "char", "boolean", "short", "long" -> false;
            default -> true;
        };
    }

    /**
     * Takes a string and checks if it is a valid field type
     *
     * @param input the string to check
     * @return false if valid, otherwise true
     */
    public static boolean isNotValidReturnType(String input) {
        return (isNotValidType(input) && !input.equals("void"));
    }

    /**
     * Gets the text for the help menu in a CLI format
     *
     * @return the help menu
     */
    public static String getCLIHelpMenu() {
        return """
                   Commands		       Description
                --------------	    -----------------
                add/a class			Add a new class
                delete/d class		Delete an existing class
                rename/ class		Rename an existing class
                add/a att -f	    Add a new field to an existing class
                add/a att -m	    Add a new method to an existing class
                add/a att -p        Add a new parameter to an existing method
                delete/d att -f	    Delete a field from an existing class
                delete/d att -m	    Delete a method from an existing class
                delete/d att -p     Delete a parameter from an existing method
                change/c att -f	    Change a field in an existing class
                change/c att -m	    Change a method in an existing class
                change/c att -p     Change a parameter in an existing method
                add/a rel -a        Add an aggregation type relationship
                add/a rel -c        Add a composition type relationship
                add/a rel -i        Add an inheritance type relationship
                add/a rel -r        Add a realization type relationship
                delete/d rel		Delete an existing relationship
                change/c rel        Change an existing relationship type
                list/l class        List a specific class in the diagram
                list/l classes      List all the classes in the diagram
                list/l rel          List all the relationships in the diagram
                save			    Save the current UML diagram
                load			    Load a previously saved UML diagram
                clear			    Clear the command history
                help			    Display list of commands
                exit			    Exit the application""";
    }


    // TODO input delete parameter
    /**
     * Gets the text for the help menu in a GUI format
     *
     * @return the help menu
     */
    public static String getGUIHelpMenu() {
        return """
                 Commands\t\t\t\t\t\t\t\t\t   Description
                --------------\t\t\t\t\t\t\t\t\t-----------------
                Edit>Add>Add Class\t\t\t\t\t\t\tAdd a new class
                Edit>Delete>Delete Class\t\t\t\t\t\tDelete an existing class
                Edit>Rename>Rename Class\t\t\t\t\t\tRename an existing class
                Edit>Add>Add Attribute>Add Field\t\t\t\tAdd a new field to an existing class
                Edit>Add>Add Attribute>Add Method\t\t\t\tAdd a new method to an existing class
                Edit>Add>Add Parameter(s)\t\t\t\t\t\tAdd a new parameter to an existing method
                Edit>Delete>Delete Attribute>Delete Field\t\t\tDelete a field from an existing class
                Edit>Delete>Delete Attribute>Delete Method\t\tDelete a method from an existing class
                Edit>Rename>Rename Attribute>Rename Field\t\tRename a field from an existing class
                Edit>Rename>Rename Attribute>Rename Method\tRename a method from an existing class
                Edit>Change>Change Parameter(s)\t\t\t\tChange a parameter in an existing method
                Edit>Add>Add Relationship\t\t\t\t\tAdd a new relationship
                Edit>Delete>Delete Relationship\t\t\t\t\tDelete an existing relationship
                Edit>Change>Change Relationship Type\t\t\tChange a relationship type
                File>Save\t\t\t\t\t\t\t\t\t\tSave the current UML diagram
                File>Load\t\t\t\t\t\t\t\t\t\tLoad a previously saved UML diagram
                Help>Show Commands\t\t\t\t\t\t\tDisplay list of commands
                [X]\t\t\t\t\t\t\t\t\t\t\tExit the application""";
    }
}