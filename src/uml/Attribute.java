package uml;

/**
 * A class for the attributes we will be working with in our UML classes.
 * 
 * @authors
 */
public class Attribute {

    // The name of the attribute
    private String name;

    /**
     * Default constructor
     * 
     * @param name the name of the attribute
     */
    public Attribute(String name) {
        this.name = name;
    }

    /**
     * Sets the name of the attribute
     * 
     * @param name the new name of the attribute
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the attribute
     * 
     * @return the name of the attribute
     */
    public String getName() {
        return this.name;
    }
}
