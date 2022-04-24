package uml;

import junit.framework.TestCase;

public class MementoTest extends TestCase {

    UMLModel model = new UMLModel();
    UMLClass student = new UMLClass("student");

    public void testGetState() {
        model.addClass(student);
        Memento undo = new Memento(model);
        assertEquals("student", undo.getState().getClassList().get(0).getClassName());
    }
}