package uml;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

/**
 * JUnit tests for Class
 * 
 * @authors Ryan Ganzke
 */
public class ClassTest {

    @Test
    public void testClassConstruction() {
        Class studentClass = new Class("Student");
        assertEquals(true, studentClass.getClassName().equals("Student"));
        assertEquals(true, studentClass.getAttributeList().isEmpty());
    }

    @Test
    public void testAddClass() {
        String data = "a class\nStudent\nexit";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);

            Driver mainDriver = new Driver();
            mainDriver.main(new String[0]);

            assertEquals("Student", mainDriver.findClass("Student").getClassName());
            assertEquals(null, mainDriver.findClass("Teacher"));
            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    // @Test
    // public void testAddDuplicatClass() {
    //     ArrayList<Class> classList = new ArrayList<Class>();
    //     Class studentClass = new Class("Student");
    //     Class teacherClass = new Class("Teacher");
    //     classList.add(studentClass);
    //     classList.add(teacherClass);
    //     assertEquals("", classList.add(studentClass));
    //     classFiles.addClass("Rooms");
    //     assertEquals("Class Rooms already exists", classFiles.addClass("Students"));
    // }

    // @Test
    // public void testDeleteClass() {
    //     ClassManager classFiles = new ClassManager();
    //     classFiles.addClass("Students");
    //     classFiles.addClass("Teachers");
    //     assertEquals(true, classFiles.listClass("Students"));
    //     classFiles.deleteClass("Students");
    //     assertEquals(false, classFiles.listClass("Students"));
    //     assertEquals(true, classFiles.listClass("Teachers"));
    // }

    // @Test
    // public void testDeleteMultipleClasses() {
    //     ClassManager classFiles = new ClassManager();
    //     classFiles.addClass("Students");
    //     classFiles.addClass("Teachers");
    //     classFiles.addClass("Rooms");
    //     assertEquals(true, classFiles.listClass("Students"));
    //     classFiles.deleteClass("Students", "Rooms");
    //     assertEquals(false, classFiles.listClass("Students"));
    //     assertEquals(true, classFiles.listClass("Teachers"));
    //     assertEquals(false, classFiles.listClass("Rooms"));
    // }

    // @Test
    // public void testDeleteNonexistantClass() {
    //     ClassManager classFiles = new ClassManager();
    //     classFiles.addClass("Students");
    //     classFiles.addClass("Teachers");
    //     assertEquals("Rooms class does not exist", classFiles.deleteClass("Rooms"));
    //     classFiles.deleteClass("Teachers");
    //     assertEquals(false, classFiles.listClass("Teachers"));
    // }

    // @Test
    // public void testRenameClass() {
    //     ClassManager classFiles = new ClassManager();
    //     classFiles.addClass("Students");
    //     classFiles.addClass("Teachers");
    //     assertEquals(true, classFiles.listClass("Students"));
    //     classFiles.renameClass("Students", "Rooms");
    //     assertEquals(false, classFiles.listClass("Students"));
    //     assertEquals(true, classFiles.listClass("Rooms"));
    // }

    // @Test
    // public void testRenameDuplicateClass() {
    //     ClassManager classFiles = new ClassManager();
    //     classFiles.addClass("Students");
    //     classFiles.addClass("Teachers");
    //     assertEquals(true, classFiles.listClass("Students"));
    //     assertEquals("A class named Teachers already exists", classFiles.renameClass("Students", "Teachers"));
    //     assertEquals(true, classFiles.listClass("Students"));
    //     assertEquals(true, classFiles.listClass("Teachers"));
    // }

    // @Test
    // public void testClassMaster() {
    //     ClassManager classFiles = new ClassManager();
    //     classFiles.addClass("Students");
    //     classFiles.addClass("Teachers");
    //     classFiles.addClass("Rooms");
    //     classFiles.addClass("Courses");
    //     classFiles.addClass("Tests");
    //     assertEquals(true, classFiles.listClass("Students"));
    //     assertEquals(true, classFiles.listClass("Teachers"));
    //     assertEquals(false, classFiles.listClass("Exams"));
    //     classFiles.renameClass("Tests", "Exams");
    //     classFiles.renameClass("Teachers", "Professors");
    // }
}