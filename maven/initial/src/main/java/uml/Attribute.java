package uml;

/**
 * A class for the attribute object within a UML Class object
 * 
 * @authors
 */
public class Attribute {

    // The name of the attribute
    private String attName;

    /**
     * Default constructor
     * 
     * @param attName the name of the attribute
     */
    public Attribute(String attName) {
        this.attName = attName;
    }

    /**
     * Sets the name of the attribute
     * 
     * @param attName the new name of the attribute
     */
    public void setAttName(String attName) {
        this.attName = attName;
    }

    /**
     * Gets the name of the attribute
     * 
     * @return the name of the attribute
     */
    public String getAttName() {
        return this.attName;
    }
}
