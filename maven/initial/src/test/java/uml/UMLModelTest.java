package uml;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class UMLModelTest {

    private UMLClass student = new UMLClass("student");
    private UMLClass teacher = new UMLClass("teacher");
    private Relationship stuTea = new Relationship(student, teacher, "aggregation");
    private Relationship teaStu = new Relationship(teacher, student, "realization");

    @Test
    public void getClassList() {
        UMLModel model = new UMLModel();
        model.addClass(student);
        model.addClass(teacher);
        assertEquals("student, teacher", model.getClassList().get(0).getClassName() + ", "
                + model.getClassList().get(1).getClassName());
    }

    @Test
    public void setClassList() {
        UMLModel model = new UMLModel();
        ArrayList<UMLClass> umlList = new ArrayList<>();
        umlList.add(student);
        umlList.add(teacher);
        model.setClassList(umlList);
        assertEquals("student, teacher", model.getClassList().get(0).getClassName() + ", "
                + model.getClassList().get(1).getClassName());
    }

    @Test
    public void getRelationshipList() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        assertEquals("student, teacher", model.getRelationshipList().get(0).getSource().getClassName()
                + ", " + model.getRelationshipList().get(0).getDestination().getClassName());
    }

    @Test
    public void setRelationshipList() {
        UMLModel model = new UMLModel();
        ArrayList<Relationship> relList = new ArrayList<>();
        relList.add(stuTea);
        model.setRelationshipList(relList);
        assertEquals("student, teacher", model.getRelationshipList().get(0).getSource().getClassName()
                + ", " + model.getRelationshipList().get(0).getDestination().getClassName());
    }

    @Test
    public void getCoordinateMap() {
    }

    @Test
    public void setCoordinateMap() {
    }

    @Test
    public void addClass() {
        UMLModel model = new UMLModel();
        model.addClass(student);
        model.addClass(teacher);
        assertEquals("student, teacher", model.getClassList().get(0).getClassName() + ", "
                + model.getClassList().get(1).getClassName());
    }

    @Test
    public void deleteClass() {
        UMLModel model = new UMLModel();
        model.addClass(student);
        model.addClass(teacher);
        model.deleteClass(student);
        assertEquals("teacher", model.getClassList().get(0).getClassName());
    }

    @Test
    public void clearClassList() {
        UMLModel model = new UMLModel();
        model.addClass(student);
        model.addClass(teacher);
        model.clearClassList();
        assertEquals("[]", "" + model.getClassList());
    }

    @Test
    public void addRel() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        assertEquals("student, teacher", model.getRelationshipList().get(0).getSource().getClassName()
                + ", " + model.getRelationshipList().get(0).getDestination().getClassName());
    }

    @Test
    public void deleteRel() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        model.addRel(teaStu);
        model.deleteRel(stuTea);
        assertEquals("teacher, student", model.getRelationshipList().get(0).getSource().getClassName()
                + ", " + model.getRelationshipList().get(0).getDestination().getClassName());
    }

    @Test
    public void clearRelationshipList() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        model.addRel(teaStu);
        model.clearRelationshipList();
        assertEquals("[]", "" + model.getRelationshipList());
    }

    @Test
    public void findClass() {
        UMLModel model = new UMLModel();
        model.addClass(student);
        model.addClass(teacher);
        assertEquals("student", model.findClass("student").getClassName());
        assertEquals("teacher", model.findClass("teacher").getClassName());
    }

    @Test
    public void findRelationship() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        model.addRel(teaStu);
        assertEquals("student", model.findRelationship("student", "teacher", "aggregation")
                .getSource().getClassName());
        assertEquals("teacher", model.findRelationship("teacher", "student", "realization")
                .getSource().getClassName());
    }

    @Test
    public void findRelType() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        model.addRel(teaStu);
        assertEquals("aggregation", model.findRelType("student", "teacher"));
        assertEquals("realization", model.findRelType("teacher", "student"));
    }

    @Test
    public void isRelated() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        assertEquals(true, model.isRelated("student", "teacher"));
        assertEquals(false, model.isRelated("parent", "teacher"));
    }

    @Test
    public void checkType() {
        UMLModel model = new UMLModel();
        assertEquals(true, model.checkType("aggregation"));
        assertEquals(false, model.checkType("generalization"));
    }

    @Test
    public void changeRelType() {
        UMLModel model = new UMLModel();
        model.addRel(stuTea);
        model.changeRelType("student", "teacher", "composition");
        assertEquals("composition", model.findRelationship("student", "teacher", "composition")
                .getRelType());
    }

    @Test
    public void updateRelationshipList() {
        UMLModel model = new UMLModel();
        model.addClass(student);
        model.addClass(teacher);
        model.addRel(stuTea);
        model.deleteClass(student);
        model.updateRelationshipList("student");
        assertEquals("[]", "" + model.getRelationshipList());
    }

    @Test
    public void isValidIdentifier() {
        UMLModel model = new UMLModel();
        assertEquals(true, model.isValidIdentifier("string"));
        assertEquals(false, model.isValidIdentifier("123String"));
        assertEquals(true, model.isValidIdentifier("string123"));
        assertEquals(false, model.isValidIdentifier("&string"));
    }

    @Test
    public void isNotValidInput() {
        UMLModel model = new UMLModel();
        assertEquals(true, model.isValidIdentifier("string"));
        assertEquals(false, model.isValidIdentifier("123String"));
        assertEquals(true, model.isValidIdentifier("string123"));
        assertEquals(false, model.isValidIdentifier("&string"));
    }

    @Test
    public void getCLIHelpMenu() {
        UMLModel model = new UMLModel();
        assertEquals("""
                   Commands		       Description
                --------------	    -----------------
                add/a class			Add a new class
                delete/d class		Delete an existing class
                rename/ class		Rename an existing class
                add/a att -f	    Add a new field to an existing class
                add/a att -m	    Add a new method to an existing class
                add/a att -p        Add a new parameter to an existing method
                delete/d att -f	    Delete a field from an existing class
                delete/d att -m	    Delete a method from an existing class
                delete/d att -p     Delete a parameter from an existing method
                change/c att -f	    Change a field in an existing class
                change/c att -m	    Change a method in an existing class
                change/c att -p     Change a parameter in an existing method
                add/a rel -a        Add an aggregation type relationship
                add/a rel -c        Add a composition type relationship
                add/a rel -i        Add an inheritance type relationship
                add/a rel -r        Add a realization type relationship
                delete/d rel		Delete an existing relationship
                change/c rel        Change an existing relationship type
                list/l class        List a specific class in the diagram
                list/l classes      List all the classes in the diagram
                list/l rel          List all the relationships in the diagram
                undo                erases most recent edit to diagram 
                redo                reverts an undo
                save			    Save the current UML diagram
                load			    Load a previously saved UML diagram
                clear			    Clear the command history
                help			    Display list of commands
                exit			    Exit the application""", model.getCLIHelpMenu());
    }

    @Test
    public void getGUIHelpMenu() {
        UMLModel model = new UMLModel();
        assertEquals("""
                 Commands\t\t\t\t\t\t\t\t\t   Description
                --------------\t\t\t\t\t\t\t\t\t-----------------
                Edit>Add>Add Class\t\t\t\t\t\t\tAdd a new class
                Edit>Delete>Delete Class\t\t\t\t\t\tDelete an existing class
                Edit>Rename>Rename Class\t\t\t\t\t\tRename an existing class
                Edit>Add>Add Attribute>Add Field\t\t\t\tAdd a new field to an existing class
                Edit>Add>Add Attribute>Add Method\t\t\t\tAdd a new method to an existing class
                Edit>Add>Add Parameter(s)\t\t\t\t\t\tAdd a new parameter to an existing method
                Edit>Delete>Delete Attribute>Delete Field\t\t\tDelete a field from an existing class
                Edit>Delete>Delete Attribute>Delete Method\t\tDelete a method from an existing class
                Edit>Rename>Rename Attribute>Rename Field\t\tRename a field from an existing class
                Edit>Rename>Rename Attribute>Rename Method\tRename a method from an existing class
                Edit>Change>Change Parameter(s)\t\t\t\tChange a parameter in an existing method
                Edit>Add>Add Relationship\t\t\t\t\t\tAdd a new relationship
                Edit>Delete>Delete Relationship\t\t\t\t\tDelete an existing relationship
                Edit>Change>Change Relationship Type\t\t\tChange a relationship type
                Edit>Undo\t\t\t\t\t\t\t\t\tUndo a previous action
                Edit>Redo\t\t\t\t\t\t\t\t\tRedo a previous action
                File>Save\t\t\t\t\t\t\t\t\t\tSave the current UML diagram
                File>Load\t\t\t\t\t\t\t\t\t\tLoad a previously saved UML diagram
                Help>Show Commands\t\t\t\t\t\t\tDisplay list of commands
                [X]\t\t\t\t\t\t\t\t\t\t\tExit the application""", model.getGUIHelpMenu());
    }
}
