package uml;

import java.util.ArrayList;

/**
 * A class for the UML Class object we will be working with in our diagram
 *
 * @authors
 */
public class Class {

    // The name of the class
    private String className;
    // The arraylist of attributes in the class
    private final ArrayList<Attribute> attributeList;


    /**
     * Default constructor
     *
     * @param className the name of the class
     */
    public Class(String className) {
        this.className = className;
        this.attributeList = new ArrayList<Attribute>();
    }

    /**
     * Gets the name of the class
     *
     * @return the name of the class
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * Sets the name of the class
     *
     * @param className the new name for the class
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the attribute list of the class
     *
     * @return the list of attributes
     */
    public ArrayList<Attribute> getAttributeList() {
        return this.attributeList;
    }

    /**
     * Adds an attribute to the end of the attribute list
     *
     * @param attribute the attribute to be added
     */
    public void addAttribute(Attribute attribute) {
        this.attributeList.add(attribute);
    }

    /**
     * Deletes the first occurrence of the attribute from the attribute list
     *
     * @param attribute the attribute to be deleted
     */
    public void deleteAttribute(Attribute attribute) {
        this.attributeList.remove(attribute);
    }

    /**
     * Lists the name of the class and its attributes in a nice way
     */
    public void listClass() {
        // prints the name of the class
        System.out.println("Class name: " + this.className);
        // prints all the attributes in a set
        System.out.print("[ ");
        if (attributeList.size() >= 1) {
            System.out.print(attributeList.get(0).getAttName());
        }
        for (int i = 1; i < attributeList.size(); ++i) {
            System.out.print(", " + attributeList.get(i).getAttName());
        }
        System.out.println(" ]");
    }

    /**
     * Iterates through AttributeList, returns with .get()
     *
     * @param attToFind
     * @return attribute if found, null if not found
     */
    public Attribute findAttribute(String attToFind) {
        // iterates through the arraylist
        for (Attribute attribute : attributeList) {
            // if the name matches, return class
            if (attToFind.equals(attribute.getAttName())) {
                return attribute;
            }
        }
        // otherwise, tell the user it does not exist and return null
        System.out.println("Attribute \"" + attToFind + "\" not found in class " + getClassName());
        return null;
    }
}
