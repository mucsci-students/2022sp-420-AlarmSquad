package uml;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

/**
 * JUnit tests for Relationship
 * 
 * @authors
 */
public class RelationshipTest {

    @Test
    public void testAddRel() {
        String data = "a class\nStudent\na class\nTeacher\na rel\nStudent\nTeacher\nexit\nStudent\nTeacher";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals("StudentTeacher", Driver.findRelationship().getID());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testAddDuplicateRel() {
        String data = "a class\nStudent\na class\nTeacher\na rel\nStudent\nTeacher\na rel\nStudent\nTeacher\nexit\nStudent\nTeacher";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals("StudentTeacher", Driver.findRelationship().getID());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testDelRel() {
        String data = "a class\nStudent\na class\nTeacher\na rel\nStudent\nTeacher\nd rel\nStudent\nTeacher\ny\nexit\nStudent\nTeacher";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals(null, Driver.findRelationship());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testDelNonExistantRel() {
        String data = "a class\nStudent\na class\nTeacher\na rel\nStudent\nTeacher\nd rel\nTeacher\nStudent\nexit\nStudent\nTeacher";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals("StudentTeacher", Driver.findRelationship().getID());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }
}
