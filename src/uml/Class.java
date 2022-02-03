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

    /**
     * Default constructor
     * 
     * @param name the name of the class
     */
    public Class(String name) {
        this.name = name;
        this.attributes = new ArrayList<Attribute>();
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
}