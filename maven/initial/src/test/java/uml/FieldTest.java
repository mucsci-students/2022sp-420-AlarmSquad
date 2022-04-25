package uml;

import junit.framework.TestCase;

public class FieldTest extends TestCase {

    Field id = new Field("id", "int");
    Field name = new Field("name", "String");

    public void testSetFieldType() {
        assertEquals("int", id.getFieldType());
        id.setFieldType("String");
        id.setAttName("ID");
        assertEquals("String", id.getFieldType());
        assertEquals("ID", id.getAttName());
    }

    public void testGetFieldType() {
        assertEquals("int", id.getFieldType());
        assertEquals("String", name.getFieldType());
    }
}