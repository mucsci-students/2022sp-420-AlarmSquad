package uml;

/**
 * A class for the relationship object between two UML Class objects
 *
 * @authors
 */
public class Relationship {

    // The source of the relationship
    private final UMLClass source;
    // The destination of the relationship
    private final UMLClass destination;


    /**
     * Default constructor
     *
     * @param source      the source of the relationship
     * @param destination the destination of the relationship
     */

    public Relationship(UMLClass source, UMLClass destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Gets the source of the relationship
     *
     * @return the source of the relationship
     */
    public UMLClass getSource() {
        return this.source;
    }

    /**
     * Gets the destination of the relationship
     *
     * @return the destination of the relationship
     */
    public UMLClass getDestination() {
        return this.destination;
    }


}