package testing;

import uml.Class;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for Class
 * 
 * @authors
 */
public class ClassTest {

    @Test
    public void testClassConstruction() {
        Class sharkClass = new Class("shark");
        assertEquals(true, sharkClass.getName().equals("shark"));
        assertEquals(true, sharkClass.getAttributes().isEmpty());
        assertEquals(true, sharkClass.getIncomingRelationships().isEmpty());
        assertEquals(true, sharkClass.getOutgoingRelationships().isEmpty());
    }
}
