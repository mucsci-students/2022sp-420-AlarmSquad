package uml;
import java.util.ArrayList;

/**
 * A class for the UML Class object we will be working with in our diagram
 * 
 * @authors
 */
public class Class {

    // The name of the class
    private String className;
    // The arraylist of attributes in the class
    private ArrayList<Field> fieldList;
    private ArrayList<Method> methodList;


    /**
     * Default constructor
     * 
     * @param name the name of the class
     */
    public Class(String className) {
        this.className = className;
        this.fieldList = new ArrayList<Field>();
        this.methodList = new ArrayList<Method>();
    }

    /**
     * Sets the name of the class
     * 
     * @param className the new name for the class
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the name of the class
     * 
     * @return the name of the class
     */
    public String getClassName() {
        return this.className;
    }

    /**
     * Gets the field list of the class
     * 
     * @return the list of fields
     */
    public ArrayList<Field> getFieldList() {
        return this.fieldList;
    }

    /**
     * Adds a field to the end of the field list
     * 
     * @param field the attribute to be added
     */
    public void addField(Field field) {
        this.fieldList.add(field);
    }

    /**
     * Deletes the first occurance of the field from the field list
     * 
     * @param field the field to be deleted
     */
    public void deleteField(Field field) {
        this.fieldList.remove(field);
    }

    /**
     * Gets the method list of the class
     * 
     * @return the list of methods
     */
    public ArrayList<Method> getMethodList() {
        return this.methodList;
    }

    /**
     * Adds a Method to the end of the Method list
     * 
     * @param method the attribute to be added
     */
    public void addMethod(Method method) {
        this.methodList.add(method);
    }

    /**
     * Deletes the first occurance of the Method from the Method list
     * 
     * @param method the Method to be deleted
     */
    public void deleteMethod(Method method) {
        this.methodList.remove(method);
    }

    /**
     * Lists the name of the class and its attributes in a nice way
     * 
     */
    public void listClass() {
        // prints the name of the class
        System.out.println("Class name: " + this.className);
        // prints all the field in a set
        System.out.print("Fields");
        System.out.print("[ ");
        if (fieldList.size() >= 1) {
            System.out.print(fieldList.get(0).getAttName());
        }
        for (int i = 1; i < fieldList.size(); ++i) {
            System.out.print(", " + fieldList.get(i).getAttName());
        }
        System.out.println(" ]");
        // prints all the methods in a set
        System.out.print("Methods");
        System.out.print("[ ");
        if (methodList.size() >= 1) {
            System.out.print(methodList.get(0).getAttName());
        }
        for (int i = 1; i < methodList.size(); ++i) {
            System.out.print(", " + methodList.get(i).getAttName());
        }
        System.out.println(" ]");
    }

    /**
     * Iterates through fieldList, returns with .get()
     * 
     * @param fieldToFind
     * @return field if found, null if not found
     */
    public Field findField(String fieldToFind) {
        // iterates through the arraylist
        for (int i = 0; i < fieldList.size(); ++i) {
            // if the name matches, return class
            if (fieldToFind.equals(fieldList.get(i).getAttName())) {
                return fieldList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("Field \"" + fieldToFind + "\" not found in class " + getClassName());
        return null;
    }

     /**
     * Iterates through methodList, returns with .get()
     * 
     * @param methodToFind
     * @return method if found, null if not found
     */
    public Method findMethod(String methodToFind) {
        // iterates through the arraylist
        for (int i = 0; i < methodList.size(); ++i) {
            // if the name matches, return class
            if (methodToFind.equals(methodList.get(i).getAttName())) {
                return methodList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("Method \"" + methodToFind + "\" not found in class " + getClassName());
        return null;
    }
}
