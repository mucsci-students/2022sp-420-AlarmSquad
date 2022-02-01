public class Class {
    String name;
    ArrayList<Attribute> attributes;
    ArrayList<Relationship> incomingRelationships, outgoingRelationships;

    public Class(String name) {
        this.name = name;
        this.attributes = new ArrayList<Attribute>();
        this.incomingRelationships = new ArrayList<Relationship>();
        this.outgoingRelationships = new ArrayList<Relationship>();
    }

    void setName(String name) {
        this.name = name
    }
}
