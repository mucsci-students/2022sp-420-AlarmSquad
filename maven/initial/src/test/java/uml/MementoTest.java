package uml;

import junit.framework.TestCase;

public class MementoTest extends TestCase {

    UMLModel model = new UMLModel();
    UMLClass student = new UMLClass("student");
    Caretaker caretaker = new Caretaker();

    public void testMemento(){
        Memento memento = new Memento(model);
        caretaker.pushToUndoStack(memento);
        memento.getState().addClass(student);
        assertEquals("student", memento.getState().getClassList().get(0).getClassName());
        caretaker.undoHelper(memento);
        assertEquals(true, caretaker.undoStackIsEmpty());
    }
    
    public void testGetState() {
        model.addClass(student);
        Memento undo = new Memento(model);
        assertEquals("student", undo.getState().getClassList().get(0).getClassName());
    }
}
