package uml;

import static org.junit.Assert.*;

import org.junit.Test;

import uml.managers.ClassManager;

/**
 * JUnit tests for Class
 * 
 * @authors Ryan Ganzke
 */
public class ClassTest {
    private static ClassManager classManager = new ClassManager();

    @Test
    public void testClassConstruction() {
        Class studentClass = new Class("Student");
        assertEquals(true, studentClass.getClassName().equals("Student"));
        assertEquals(true, studentClass.getAttributeList().isEmpty());
    }

    @Test
    public void testAddClass() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Student")));
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Teacher")));
    }

    @Test
    public void testAddInvalid() {
        classManager.addClass("Student");
        assertEquals("\"5Teacher\" is not a valid identifier\n", classManager.addClass("5Teacher"));
        assertEquals("\"@Child\" is not a valid identifier\n", classManager.addClass("@Child"));
    }


    @Test
    public void testAddDuplicateClass() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        assertEquals("Class \"Student\" already exists\n", classManager.addClass("Student"));
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Student")));
        assertEquals(1, classManager.numOfClasses("Student"));
    }

    @Test
    public void testDeleteClass() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        classManager.deleteClass("Student");
        assertEquals(false, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Student")));
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Teacher")));
    }

    @Test
    public void testDeleteNonExistantClass() {
        classManager.addClass("Student");
        assertEquals("Class \"Professor\" was not found\n", classManager.deleteClass("Professor"));
    }

    @Test
    public void testRenameClass() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        classManager.renameClass("Teacher", "Professor");
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Student")));
        assertEquals(false, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Teacher")));
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Professor")));
    }

    @Test
    public void testRenameInvalid() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        assertEquals("\"5Teacher\" is not a valid identifier\n", classManager.renameClass("Teacher", "5Teacher"));
        assertEquals("\"(Teacher)\" is not a valid identifier\n", classManager.renameClass("Teacher", "(Teacher)"));
    }

    @Test
    public void testRenameDuplicateClass() {
        classManager.addClass("Student");
        classManager.addClass("Teacher");
        assertEquals("Class \"Student\" already exists\n", classManager.renameClass("Teacher", "Student"));
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Student")));
        assertEquals(true, classManager.getClassList().stream().anyMatch(o -> o.getClassName().equals("Teacher")));
    }
}