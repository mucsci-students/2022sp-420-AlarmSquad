package uml;

import java.util.ArrayList;

/**
 * A class for the UML class we will be working with in our diagram.
 * 
 * @authors
 */
public class Class {

    // The name of the class
    private String name;
    // The arraylist of attributes in the class
    private ArrayList<Attribute> attributeList;
    private ArrayList<Relationship> incomingRelationshipList, outgoingRelationshipList;

    /**
     * Default constructor
     * 
     * @param name the name of the class
     */
    public Class(String name) {
        this.name = name;
        this.attributeList = new ArrayList<Attribute>();
        this.incomingRelationshipList = new ArrayList<Relationship>();
        this.outgoingRelationshipList = new ArrayList<Relationship>();
    }

    /**
     * Sets the name of the class
     * 
     * @param name the new name for the class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the class
     * 
     * @return the name of the class
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the attribute list of the class
     * 
     * @return the list of attributes
     */
    public ArrayList<Attribute> getAttributes() {
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
     * Deletes the first occurance of the attribute from the attribute list
     * 
     * @param attribute the attribute to be deleted
     */
    public void deleteAttribute(Attribute attribute) {
        this.attributeList.remove(attribute);
    }

    /**
     * Gets the attribute list of the class
     * 
     * @return the list of attributes
     */
    public ArrayList<Relationship> getIncomingRelationships() {
        return this.incomingRelationshipList;
    }

    /**
     * Gets the attribute list of the class
     * 
     * @return the list of attributes
     */
    public ArrayList<Relationship> getOutgoingRelationships() {
        return this.outgoingRelationshipList;
    }

    /**
     * Lists the name of the class and its attributes in a nice way
     * 
     */
    public void listClass() {
        // prints the name of the class
        System.out.println("Class name: " + this.name);
        // prints all the attributes in a set
        System.out.print("[ ");
        if (attributeList.size() >= 1) {
            System.out.print(attributeList.get(0).getName());
        }
        for (int i = 1; i < attributeList.size(); ++i) {
            System.out.print(", " + attributeList.get(i).getName());
        }
        System.out.println(" ]");
    }
}