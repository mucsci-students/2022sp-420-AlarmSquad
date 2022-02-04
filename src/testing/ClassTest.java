package testing;

import uml.Class;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for Class
 * 
 * @authors Ryan Ganzke
 */
public class ClassTest {

    @Test
    public void testClassConstruction() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        assertEquals(true, classFiles[0].getName().equals("Students"));
        assertEquals(true, classFiles[0].getAttributes().isEmpty());
        assertEquals(true, classFiles[0].getIncomingRelationships().isEmpty());
        assertEquals(true, classFiles[0].getOutgoingRelationships().isEmpty());
    }

    @Test
    public void testAddClass() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        assertEquals(true, classFiles.listClass("Students"));
        assertEquals(false, classFiles.listClass("Rooms"));
    }

    @Test
    public void testAddDuplicatClass() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        assertEquals("Class Students already exists", classFiles.addClass("Students"));
        classFiles.addClass("Rooms");
        assertEquals("Class Rooms already exists", classFiles.addClass("Students"));
    }

    @Test
    public void testDeleteClass() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        assertEquals(true, classFiles.listClass("Students"));
        classFiles.deleteClass("Students");
        assertEquals(false, classFiles.listClass("Students"));
        assertEquals(true, classFiles.listClass("Teachers"));
    }

    @Test
    public void testDeleteMultipleClasses() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        classFiles.addClass("Rooms");
        assertEquals(true, classFiles.listClass("Students"));
        classFiles.deleteClass("Students", "Rooms");
        assertEquals(false, classFiles.listClass("Students"));
        assertEquals(true, classFiles.listClass("Teachers"));
        assertEquals(false, classFiles.listClass("Rooms"));
    }

    @Test
    public void testDeleteNonexistantClass() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        assertEquals("Rooms class does not exist", classFiles.deleteClass("Rooms"));
        classFiles.deleteClass("Teachers");
        assertEquals(false, classFiles.listClass("Teachers"));
    }

    @Test
    public void testRenameClass() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        assertEquals(true, classFiles.listClass("Students"));
        classFiles.renameClass("Students", "Rooms");
        assertEquals(false, classFiles.listClass("Students"));
        assertEquals(true, classFiles.listClass("Rooms"));
    }

    @Test
    public void testRenameDuplicateClass() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        assertEquals(true, classFiles.listClass("Students"));
        assertEquals("A class named Teachers already exists", classFiles.renameClass("Students", "Teachers"));
        assertEquals(true, classFiles.listClass("Students"));
        assertEquals(true, classFiles.listClass("Teachers"));
    }

    @Test
    public void testClassMaster() {
        ClassManager classFiles = new ClassManager();
        classFiles.addClass("Students");
        classFiles.addClass("Teachers");
        classFiles.addClass("Rooms");
        classFiles.addClass("Courses");
        classFiles.addClass("Tests");
        assertEquals(true, classFiles.listClass("Students"));
        assertEquals(true, classFiles.listClass("Teachers"));
        assertEquals(false, classFiles.listClass("Exams"));
        classFiles.renameClass("Tests", "Exams");
        classFiles.renameClass("Teachers", "Professors");
    }
}
