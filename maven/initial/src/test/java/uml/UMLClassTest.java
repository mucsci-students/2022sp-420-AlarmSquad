package uml;

import junit.framework.TestCase;

public class UMLClassTest extends TestCase {

    UMLClass student = new UMLClass("student");
    Field id = new Field("id", "int");
    Field name = new Field("name", "String");
    Field hairColor = new Field("hairColor", "String");
    Method setName = new Method("setName", "void");
    Method getName = new Method("getName", "String");
    Method moveDesk = new Method("moveDesk", "void");

    public void testGetClassName() {
        assertEquals("student", student.getClassName());
    }

    public void testSetClassName() {
        student.setClassName("teacher");
        assertEquals("teacher", student.getClassName());
    }

    public void testGetAttList() {
        student.addField(id);
        student.addField(name);
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("id, name", student.getAttList("field").get(0).getAttName() + ", " +
                student.getAttList("field").get(1).getAttName());
        assertEquals("setName, getName", student.getAttList("method").get(0).getAttName() + ", " +
                student.getAttList("method").get(1).getAttName());
        assertEquals(null, student.getAttList("parameter"));
    }

    public void testFindAtt() {
        student.addField(id);
        student.addField(name);
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("name", student.findAtt("name", "field").getAttName());
        assertEquals(null, student.findAtt("hairColor", "field"));
        assertEquals("getName", student.findAtt("getName", "method").getAttName());
        assertEquals(null, student.findAtt("moveDesk", "method"));
        assertEquals(null, student.findAtt("newName", "parameter"));

    }

    public void testDeleteAttribute() {
        student.addField(id);
        student.addField(name);
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("name", student.findAtt("name", "field").getAttName());
        student.deleteAttribute(name);
        assertEquals(null, student.findAtt("name", "field"));
        assertEquals("getName", student.findAtt("getName", "method").getAttName());
        student.deleteAttribute(getName);
        assertEquals(null, student.findAtt("getName", "method"));
    }

    public void testGetFieldList() {
        student.addField(id);
        student.addField(name);
        assertEquals("id, name", student.getFieldList().get(0).getAttName() + ", " +
                student.getFieldList().get(1).getAttName());
    }

    public void testAddField() {
        student.addField(id);
        assertEquals("id", student.getFieldList().get(0).getAttName());
    }

    public void testDeleteField() {
        student.addField(id);
        student.addField(name);
        assertEquals("name", student.findField("name").getAttName());
        student.deleteField(name);
        assertEquals(null, student.findField("name"));
    }

    public void testGetMethodList() {
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("setName, getName", student.getMethodList().get(0).getAttName() + ", " +
                student.getMethodList().get(1).getAttName());
    }

    public void testAddMethod() {
        student.addMethod(setName);
        assertEquals("setName", student.getMethodList().get(0).getAttName());
    }

    public void testDeleteMethod() {
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("getName", student.findMethod("getName").getAttName());
        student.deleteMethod(getName);
        assertEquals(null, student.findMethod("getName"));
    }

    public void testFindField() {
        student.addField(id);
        student.addField(name);
        assertEquals("name", student.findField("name").getAttName());
        assertEquals(null, student.findField("hairColor"));
    }

    public void testFindMethod() {
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("getName", student.findMethod("getName").getAttName());
        assertEquals(null, student.findMethod("moveDesk"));
    }

    public void testFindMethodNoPrint() {
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("getName", student.findMethodNoPrint("getName").getAttName());
        assertEquals(null, student.findMethodNoPrint("moveDesk"));
    }

    public void testChangeField() {
        student.addField(id);
        student.addField(name);
        assertEquals("name", student.findField("name").getAttName());
        assertEquals(null, student.findField("hairColor"));
        student.changeField("name", hairColor);
        assertEquals(null, student.findField("name"));
        assertEquals("hairColor", student.findField("hairColor").getAttName());
        assertEquals(null, student.changeField("name", name));
    }

    public void testChangeMethod() {
        student.addMethod(setName);
        student.addMethod(getName);
        assertEquals("getName", student.findMethodNoPrint("getName").getAttName());
        assertEquals(null, student.findMethodNoPrint("moveDesk"));
        student.changeMethod("getName", moveDesk);
        assertEquals(null, student.findMethodNoPrint("getName"));
        assertEquals("moveDesk", student.findMethodNoPrint("moveDesk").getAttName());
        assertEquals(null, student.changeMethod("getName", getName));
    }
}
