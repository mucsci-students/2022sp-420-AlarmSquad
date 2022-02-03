package uml;

/**
 * A class for the relationships we will be working with in our UML classes.
 * 
 * @authors
 */
public class Relationship {

    // The identification of the relationship
    private String id;
    // The source of the relationship
    private Class source;
    // The destination of the relationship
    private Class destination;

    /**
     * Default constructor
     * 
     * @param source      the source of the relationship
     * @param destination the destination of the relationship
     */
    
    public Relationship(Class source, Class destination) {
        this.id = source.getName() + destination.getName();
        this.source = source;
        this.destination = destination;
    }

    /**
     * Sets the id of the relationship
     * 
     * @param id the new id of the relationship
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Gets the id of the relationship
     * 
     * @return the id of the relationship
     */
    public String getID() {
        return this.id;
    }

    /**
     * Sets the source of the relationship
     * 
     * @param source the new source of the relationship
     */
    public void setSource(Class source) {
        this.source = source;
    }

    /**
     * Gets the source of the relationship
     * 
     * @return the source of the relationship
     */
    public Class getSource() {
        return this.source;
    }

    /**
     * Sets the destination of the relationship
     * 
     * @param destination the new destination of the relationship
     */
    public void setDestination(Class destination) {
        this.destination = destination;
    }

    /**
     * Gets the destination of the relationship
     * 
     * @return the destination of the relationship
     */
    public Class getDestination() {
        return this.destination;
    }
}