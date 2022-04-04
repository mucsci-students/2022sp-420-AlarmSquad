package uml;

//import uml.managers.ClassManager;

/**
 * JUnit tests for Attribute
 * 
 * @authors Ryan Ganzke
 */
public class AttributeTest {
    //private static ClassManager classManager = new ClassManager();

    //TODO
    // MAKE THIS FOR FIELDS AND METHODS

//    @Test
//    public void testAddAttr() {
//        classManager.addClass("Student");
//        classManager.addAttribute("Student", "ID");
//        assertEquals(true, classManager.findClass("Student").getAttributeList().stream().anyMatch(o -> o.getAttName().equals("ID")));
//    }
//
//    @Test
//    public void testAddInvalidAtt() {
//        classManager.addClass("Student");
//        assertEquals("\"3Name\" is not a valid identifier\n", classManager.addClass("3Name"));
//        assertEquals("\"&Name\" is not a valid identifier\n", classManager.addClass("&Name"));
//    }
//
//    @Test
//    public void testDeleteAttr() {
//        classManager.addClass("Student");
//        classManager.addAttribute("Student", "FirstName");
//        classManager.deleteAttribute("Student", "FirstName");
//        assertEquals(false, classManager.findClass("Student").getAttributeList().stream().anyMatch(o -> o.getAttName().equals("FirstName")));
//    }
//
//    @Test
//    public void testAddDuplicateAttr() {
//        classManager.addClass("Student");
//        classManager.addAttribute("Student", "FirstName");
//        assertEquals("Attribute \"FirstName\" already exists in class \"Student\"\n", classManager.addAttribute("Student", "FirstName"));
//    }
//
//    @Test
//    public void testRenameAttr() {
//        classManager.addClass("Student");
//        classManager.addAttribute("Student", "FirstName");
//        classManager.addAttribute("Student", "MidName");
//        classManager.renameAttribute("Student", "MidName", "LastName");
//        assertEquals(true, classManager.findClass("Student").getAttributeList().stream().anyMatch(o -> o.getAttName().equals("FirstName")));
//        assertEquals(false, classManager.findClass("Student").getAttributeList().stream().anyMatch(o -> o.getAttName().equals("MidName")));
//        assertEquals(true, classManager.findClass("Student").getAttributeList().stream().anyMatch(o -> o.getAttName().equals("LastName")));
//    }
//
//    @Test
//    public void testRenameInvalidAtt() {
//        classManager.addClass("Student");
//        classManager.addAttribute("Student", "Grade");
//        assertEquals("\"7thGrade\" is not a valid identifier\n", classManager.addClass("7thGrade"));
//        assertEquals("\"?thGrade\" is not a valid identifier\n", classManager.addClass("?thGrade"));
//    }
//
//    @Test
//    public void testRenameDuplicateAttr() {
//        classManager.addClass("Student");
//        classManager.addAttribute("Student", "FirstName");
//        classManager.addAttribute("Student", "MidName");
//        assertEquals("Attribute \"FirstName\" already exists in class \"Student\"\n", classManager.renameAttribute("Student", "MidName", "FirstName"));
//    }
}
