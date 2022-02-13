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
            
            Driver.runCLI();

            assertEquals("Student", Driver.findClass("Student").getClassName());
            assertEquals(null, Driver.findClass("Teacher"));

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testAddDuplicateClass() {
        String data = "a class\nStudent\na class\nTeacher\na class\nStudent\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);

            Driver.runCLI();

            assertEquals("Student", Driver.findClass("Student").getClassName());
            assertEquals("Teacher", Driver.findClass("Teacher").getClassName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testDeleteClass() {
        String data = "a class\nStudent\na class\nTeacher\nd class\nStudent\ny\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);

            Driver.runCLI();

            assertEquals("Teacher", Driver.findClass("Teacher").getClassName());
            assertEquals(null, Driver.findClass("Student"));

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testDeleteNonExistantClass() {
        String data = "a class\nStudent\na class\nTeacher\nd class\nRoom\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);

            Driver.runCLI();

            assertEquals("Student", Driver.findClass("Student").getClassName());
            assertEquals("Teacher", Driver.findClass("Teacher").getClassName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testRenameClass() {
        String data = "a class\nStudent\na class\nTeacher\nr class\nTeacher\nProfessor\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);

            Driver.runCLI();

            assertEquals("Student", Driver.findClass("Student").getClassName());
            assertEquals(null, Driver.findClass("Teacher"));
            assertEquals("Professor", Driver.findClass("Professor").getClassName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testRenameDuplicateClass() {
        String data = "a class\nStudent\na class\nTeacher\nr class\nTeacher\nStudent\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);

            Driver.runCLI();

            assertEquals("Student", Driver.findClass("Student").getClassName());
            assertEquals("Teacher", Driver.findClass("Teacher").getClassName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }
}