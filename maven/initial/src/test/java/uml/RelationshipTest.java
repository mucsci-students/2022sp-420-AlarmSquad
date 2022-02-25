package uml;

import static org.junit.Assert.*;

import org.junit.Test;

import uml.managers.ClassManager;
import uml.managers.RelationshipManager;

/**
 * JUnit tests for Relationship
 * 
 * @authors
 */
public class RelationshipTest {
    private static ClassManager classManager = new ClassManager();
    private static RelationshipManager relationshipManager = new RelationshipManager();

    @Test
    public void testAddRel() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        relationshipManager.addRelationship(classManager, "Student", "Teacher", "Aggregation");

        assertEquals(true, relationshipManager.findRelationship(classManager,
                "Student", "Teacher", "Aggregation") != null);

        assertEquals(false, relationshipManager.findRelationship(classManager,
                "Student", "Student", "Aggregation") != null);

        assertEquals(false, relationshipManager.findRelationship(classManager,
                "Student", "Professor", "Aggregation") != null);
    }

    @Test
    public void testAddDuplicateRel() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        relationshipManager.addRelationship(classManager, "Student", "Teacher", "Aggregation");

        assertEquals("Relationship already exists between \"Student\" and \"Teacher\"\n",
                relationshipManager.addRelationship(classManager, "Student", "Teacher", "Aggregation"));
    }

    @Test
    public void testDelRel() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        relationshipManager.addRelationship(classManager, "Student", "Teacher", "Aggregation");
        relationshipManager.deleteRelationship(classManager, "Student", "Teacher");

        assertEquals(false, relationshipManager.findRelationship(classManager, "Student",
                "Teacher", "Aggregation") != null);
    }

    @Test
    public void testDelNonExistantRel() {
        classManager.addClass("Student");
        assertEquals("Relationship not found between \"Student\" and \"Student\"\n",
                relationshipManager.deleteRelationship(classManager, "Student", "Student"));
    }
}
