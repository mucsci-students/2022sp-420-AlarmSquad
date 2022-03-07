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
    // The type of the relationship
    private String relType;


    /**
     * Default constructor
     * @param source      the source of the relationship
     * @param destination the destination of the relationship
     * @param relType     the type of the relationship
     * */

    public Relationship(UMLClass source, UMLClass destination, String relType) {
        this.source = source;
        this.destination = destination;
        this.relType = relType;
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

    /**
     * Gets the type of the relationship
     *
     * @return the relationship type
     */
    public String getRelType() { return this.relType; }

    /**
     * Sets the type of the relationship
     *
     * @return the new relationship type
     */
    public void setRelType(String newReltype) { this.relType = newReltype; }

}