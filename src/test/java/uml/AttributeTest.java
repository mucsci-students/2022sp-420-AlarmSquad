package uml;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;

/**
 * JUnit tests for Attribute
 * 
 * @authors Ryan Ganzke
 */
public class AttributeTest {

    @Test
    public void testAddAttr() {
        String data = "a class\nStudent\na att\nStudent\nName\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals("Name", Driver.findClass("Student").findAttribute("Name").getAttName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testAddDuplicateAttr() {
        String data = "a class\nStudent\na att\nStudent\nName\na att\nStudent\nName\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals("Name", Driver.findClass("Student").findAttribute("Name").getAttName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testDeleteAttr() {
        String data = "a class\nStudent\na att\nStudent\nName\na att\nStudent\nID\nd att\nStudent\nName\ny\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals(null, Driver.findClass("Student").findAttribute("Name"));
            assertEquals("ID", Driver.findClass("Student").findAttribute("ID").getAttName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testRenameAttr() {
        String data = "a class\nStudent\na att\nStudent\nFirstName\nr att\nStudent\nFirstName\nLastName\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals(null, Driver.findClass("Student").findAttribute("FirstName"));
            assertEquals("LastName", Driver.findClass("Student").findAttribute("LastName").getAttName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testRenameNonExistantAttr() {
        String data = "a class\nStudent\na att\nStudent\nFirstName\nr att\nStudent\nLastName\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals("FirstName", Driver.findClass("Student").findAttribute("FirstName").getAttName());
            assertEquals(null, Driver.findClass("Student").findAttribute("LastName"));

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    public void testRenameDuplicateAttr() {
        String data = "a class\nStudent\na att\nStudent\nFirstName\na att\nStudent\nMidName\nr att\nStudent\nFirstName\nMidName\nexit";
        InputStream stdin = System.in;

        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Scanner scan = new Scanner(System.in);
            
            Driver.runCLI();

            assertEquals("FirstName", Driver.findClass("Student").findAttribute("FirstName").getAttName());
            assertEquals("MidName", Driver.findClass("Student").findAttribute("MidName").getAttName());

            scan.close();
        } finally {
            System.setIn(stdin);
        }
    }
}
