package uml.managers;

import uml.Class;
import uml.Attribute;
import uml.Driver;

import java.util.ArrayList;

public class ClassManager {
    
    private ArrayList<Class> classList;

    public ClassManager() {
        classList = new ArrayList<Class>();
    }

    public ArrayList<Class> getClassList() {
        return classList;
    }

    public String addClass(String className) {
        if (!Driver.ifValidInput(className)) {
            return "\"" + className + "\" is not a valid identifier\n";
        }

        if (classList.stream().anyMatch(o -> o.getClassName().equals(className))) {
            return "Class \"" + className + "\" already exists\n";
        }

        Class newClass = new Class(className);
        classList.add(newClass);
        return "Added class \"" + className + "\"\n";
    }

    public String deleteClass(String classDeleteInput) {
        Class classToDel = findClass(classDeleteInput);
        if (classToDel != null) {
            classList.remove(classToDel);
            return "Class \"" + classToDel.getClassName() + "\" has been deleted\n";
        }
        return "Class \"" + classDeleteInput + "\" was not found\n";
    }

    public String renameClass(String oldClassName, String newName) {
        if (!Driver.ifValidInput(newName)) {
            return "\"" + newName + "\" is not a valid identifier\n";
        }
        if(findClass(newName) != null) {
            return "Class \"" + newName + "\" already exists\n";
        }
        Class oldClass = findClass(oldClassName);
        oldClass.setClassName(newName);
        // Inform user of renamed Class
        return "The class \"" + oldClassName +
            "\" has been renamed to \"" + oldClass.getClassName() + "\"\n";
    }

    public String addAttribute(String classToAddAttName, String attributeName) {
        if (!Driver.ifValidInput(attributeName)) {
            return "\"" + attributeName + "\" is not a valid identifier\n";
        }
        Class classToAddAtt = findClass(classToAddAttName);
        if (classToAddAtt.getAttributeList().stream()
                .anyMatch(attObj -> attObj.getAttName().equals(attributeName))) {
            return "Attribute \"" + attributeName +
                    "\" already exists in class \"" + classToAddAtt.getClassName() + "\"\n";
        }
        Attribute attribute = new Attribute(attributeName);
        classToAddAtt.addAttribute(attribute);
        return "Added attribute \"" + attributeName + "\" to class \""
                + classToAddAttName + "\"\n";
    }

    public void deleteAttribute(String classToDelAttName, String deletedAttName) {
        Class classToDelAtt = findClass(classToDelAttName);
        Attribute deletedAtt = classToDelAtt.findAttribute(deletedAttName);
        classToDelAtt.deleteAttribute(deletedAtt);
        System.out.print("Attribute \"" + deletedAttName + "\" has been deleted \n");
    }

    public String renameAttribute(String classWithAttName, String oldAttName, String newAttName) {
        if (!Driver.ifValidInput(newAttName)) {
            return "\"" + oldAttName + "\" is not a valid identifier\n";
        }
        Class classWithAtt = findClass(classWithAttName);
        Attribute newAtt = classWithAtt.findAttribute(oldAttName);
        if(classWithAtt.findAttribute(newAttName) != null) {
            return "Attribute \"" + newAttName +
                    "\" already exists in class \"" + classWithAttName + "\"\n";
        }
        newAtt.setAttName(newAttName);
        return "\"" + oldAttName + "\" in class \"" + classWithAttName +
                "\" renamed to \"" + newAtt.getAttName() + "\"";
    }

    public int numOfClasses(String searchName) {
        int count = 0;
        for(Class classObj : classList)
        {
            if(classObj.getClassName().equals(searchName)) {
                ++count;
            }
        }
        return count;
    }

    public Class findClass(String classToFind) {
        // iterates through the arraylist
        for (int i = 0; i < classList.size(); ++i) {
            // if the name matches, return class
            if (classToFind.equals(classList.get(i).getClassName())) {
                return classList.get(i);
            }
        }
        // otherwise tell the user it does not exist and return null
        System.out.println("Class \"" + classToFind + "\" was not found");
        return null;
    }
}
