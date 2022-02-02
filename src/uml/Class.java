package uml;

import java.util.ArrayList;

import javax.print.attribute.Attribute;

public class Class {
    String name;
    ArrayList<Attribute> attributes;
    ArrayList<Relationship> incomingRelationships;
    ArrayList<Relationship> outgoingRelationships;

    public Class(String name) {
        this.name = name;
        // this.attributes = new ArrayList<Attribute>();
        // this.incomingRelationships = new ArrayList<Relationship>();
        // this.outgoingRelationships = new ArrayList<Relationship>();
    }

    public void setName() {

    }

    public String getName() {
        return "";
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void deleteAttribute(Attribute attribute) {
        this.attributes.remove(attribute);
    }

    public void addIncomingRelationship(Relationship relationship) {
        this.incomingRelationships.add(relationship);
    }

    public void deleteIncomingRelationship(Relationship relationship) {
        this.incomingRelationships.remove(relationship);
    }

    public void addOutgoingRelationship(Relationship relationship) {
        this.outgoingRelationships.add(relationship);
    }

    public void deleteOutgoingRelationship(Relationship relationship) {
        this.outgoingRelationships.remove(relationship);
    }
}
