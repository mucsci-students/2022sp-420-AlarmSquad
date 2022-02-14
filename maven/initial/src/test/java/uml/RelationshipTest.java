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
        relationshipManager.addRelationship(classManager, "Student", "Teacher");
        assertEquals(true, relationshipManager.findRelationship(classManager, "Student", "Teacher") != null);
        assertEquals(false, relationshipManager.findRelationship(classManager, "Student", "Student") != null);
        assertEquals(false, relationshipManager.findRelationship(classManager, "Student", "Professor") != null);
    }

    @Test
    public void testAddDuplicateRel() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        relationshipManager.addRelationship(classManager, "Student", "Teacher");
        assertEquals("Relationship already exists between \"Student\" and \"Teacher\"\n", relationshipManager.addRelationship(classManager, "Student", "Teacher"));
    }

    @Test
    public void testDelRel() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        relationshipManager.addRelationship(classManager, "Student", "Teacher");
        relationshipManager.deleteRelationship(classManager, "Student", "Teacher");
        assertEquals(false, relationshipManager.findRelationship(classManager, "Student", "Teacher") != null);
    }

    @Test
    public void testDelNonExistantRel() {
        classManager.addClass("Student");
        assertEquals("Relationship not found between \"Student\" and \"Student\"\n", relationshipManager.deleteRelationship(classManager, "Student", "Student"));
    }
}
