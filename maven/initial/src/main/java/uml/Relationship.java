package uml;

/**
 * A class for the relationship object between two UML Class objects
 *
 * @authors
 */
public class Relationship {

    // The source of the relationship
    private final Class source;
    // The destination of the relationship
    private final Class destination;


    /**
     * Default constructor
     *
     * @param source      the source of the relationship
     * @param destination the destination of the relationship
     */

    public Relationship(Class source, Class destination) {
        this.source = source;
        this.destination = destination;
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
     * Gets the destination of the relationship
     *
     * @return the destination of the relationship
     */
    public Class getDestination() {
        return this.destination;
    }


}