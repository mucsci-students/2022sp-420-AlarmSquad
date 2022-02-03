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
    private ArrayList<Attribute> attributes;
    // The arraylist of relationships the class has
    private ArrayList<Relationship> relationships;

    /**
     * Default constructor
     * 
     * @param name the name of the class
     */
    public Class(String name) {
        this.name = name;
        this.attributes = new ArrayList<Attribute>();
        this.relationships = new ArrayList<Relationship>();
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
     * 
     * @return
     */
    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }


    /**
     * Adds an attribute to the end of the attribute list
     * 
     * @param attribute the attribute to be added
     */
    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    /**
     * Deletes the first occurance of the attribute from the attribute list
     * 
     * @param attribute the attribute to be deleted
     */
    public void deleteAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
    }

    /**
     * Adds a relationship to the end of the relationship list
     * 
     * @param relationship the relationship to be added
     */
    public void addIncomingRelationship(Relationship relationship) {
        this.relationships.add(relationship);
    }

    /**
     * Deletes the first occurance of the relationship from the relationship list
     * 
     * @param relationship the relationship to be deleted
     */
    public void deleteIncomingRelationship(Relationship relationship) {
        this.relationships.remove(relationship);
    }

    /**
     * 
     * 
     * @param att
     * @return
     */
    //public Boolean attributeExists(Attribute att) {

    //}
}