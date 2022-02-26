package uml;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class JSON {

    // directory of the save files
    private final static String FILE_DIR = "savefiles";
    // a copy of the classList from Driver
    private static final ArrayList<UMLClass> UML_CLASS_LIST = UMLModel.getClassList();
    // a copy of the relationshipList from Driver
    private static final ArrayList<Relationship> relationshipList = UMLModel.getRelationshipList();
    // the JSON object to be saved
    private static final JSONObject saveFile = new JSONObject();

    /**
     * Saves the current UML diagram to a file with a given name
     * Overrides a file if it has the same name
     *
     * @param fileName the name of the file to be saved to
     */
    @SuppressWarnings("unchecked")
    public static void save(String fileName) {

        // a JSON array that contains a list of all the classes
        JSONArray saveClasses = new JSONArray();
        // a JSON array that contains a list of all the relationships
        JSONArray saveRelationships = new JSONArray();

        // iterate through the classList
        for (UMLClass UMLClassObj : UML_CLASS_LIST) {
            JSONObject classToBeSaved = new JSONObject();
            // add name to JSON
            classToBeSaved.put("className", UMLClassObj.getClassName());
            // make new JSONArray
            JSONArray fieldList = new JSONArray();
            JSONArray methList = new JSONArray();
            // iterate through the Class object's field and method lists
            //TODO
            // MAKE THIS SAVE FIELDS AND METHODS

            for (Field fieldObj : UMLClassObj.getFieldList()) {
               JSONObject fieldToBeSaved = new JSONObject();
               fieldToBeSaved.put("name", fieldObj.getAttName());
               fieldToBeSaved.put("type", fieldObj.getFieldType());
                // put each field in the JSONArray
                fieldList.add(fieldToBeSaved);
            }

            for (Method methObj : UMLClassObj.getMethodList()) {
                JSONObject methToBeSaved = new JSONObject();
                methToBeSaved.put("name", methObj.getAttName());
                methToBeSaved.put("returnType", methObj.getReturnType());
                // put each method in the JSONArray
                methList.add(methToBeSaved);
            }
            // put the field and method JSONArray into the JSON Class object
            classToBeSaved.put("fieldList", fieldList);
            classToBeSaved.put("methodList", methList);
            // put the JSONArray into the JSON object
            saveClasses.add(classToBeSaved);
        }
        // add the classList to the JSONObject
        saveFile.put("classList", saveClasses);

        // iterate through the relationship list
        for (Relationship relObj : relationshipList) {
            JSONObject relToBeSaved = new JSONObject();
            // add source to JSON object
            relToBeSaved.put("source", relObj.getSource().getClassName());
            // add destination to JSON object
            relToBeSaved.put("destination", relObj.getDestination().getClassName());
            // add type to JSON object
            relToBeSaved.put("type", relObj.getRelType());
            // put the JSON object in the JSONArray
            saveRelationships.add(relToBeSaved);
        }
        // add the relationshipList to the JSONObject
        saveFile.put("relationshipList", saveRelationships);


        // a file with the correct directory and file name
        File fileToBeSaved = new File(FILE_DIR, fileName + ".json");
        // save the file
        try (FileWriter file = new FileWriter(fileToBeSaved)) {
            file.write(saveFile.toString());
            file.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Loads the current UML diagram from a file with a given name
     * DOES NOT CHECK IF THE DIRECTORY IS EMPTY
     *
     * @param fileName the name of the file to be loaded from
     */
    @SuppressWarnings("unchecked")
    public static void load(String fileName) {

        try {
            // a file which acts as the save file directory
            File dir = new File(FILE_DIR);
            // a list of the names of the files in the directory
            String[] fileList = dir.list();
            for (String file : Objects.requireNonNull(dir.list())) {
                System.out.println(file);
            }
            boolean hasFoundFile = false;

            for (String file : Objects.requireNonNull(dir.list())) {
                if (file.equals((fileName + ".json"))) {
                    hasFoundFile = true;
                    break;
                }
            }
            if (!hasFoundFile) {
                System.out.println("File does not exist");
                return;
            }


//            // loops through the list of file names
//            for (int i = 0; i < Objects.requireNonNull(fileList).length; ++i) {
//                // if the list only has one file (and placeholder.txt) and it is the correct name
//                if (fileList.length == 2 && fileList[i].equals(fileName + ".json")) {
//                    hasFoundFile = true;
//                    // otherwise, keep looping
//                } else {
//                    // if the end of the list has been reached and the file has not been found
//                    if (i == (fileList.length - 1) && !hasFoundFile) {
//                        // tell the user the file does not exist and exit
//                        System.out.println("File does not exist");
//                        return;
//                        // if the file has been found
//                    } else if (fileList[i].equals(fileName + ".json")) {
//                        hasFoundFile = true;
//                    }
//                }
//            }

            // wipe both lists
            UMLModel.clearClassList();
            UMLModel.clearRelationshipList();

            // gets the file in the correct directory
            File fileToBeLoaded = new File(FILE_DIR + "/" + fileName + ".json");
            // makes the JSONParser
            Object obj = new JSONParser().parse(new FileReader(fileToBeLoaded));
            // casting obj to JSONObject
            JSONObject jo = (JSONObject) obj;

            // JSONArray for getting the saved classList
            JSONArray classArray = (JSONArray) jo.get("classList");
            // iterator for iterating classList
            Iterator<JSONObject> classIter = classArray.iterator();


            // loops through the classes and attributes of the classes
            while (classIter.hasNext()) {
                // finds the class' name at the key "className"
                String className = (String) classIter.next().get("className");

                JSONArray fieldArray = (JSONArray) jo.get("fieldList");

                JSONArray methArray = (JSONArray) jo.get("methodList");
                // iterator for iterating fieldList
                Iterator<JSONObject> fieldIter = fieldArray.iterator();
                // iterator for iterating fieldList
                Iterator<JSONObject> methIter = methArray.iterator();


//                // finds the list of fields at the key "fieldList"
//                String fieldList = (String) fieldIter.next().get("fieldList");
//                // finds the list of attributes at the key "methodList"
//                ArrayList<String> methList = (ArrayList<String>) methIter.next().get("methodList");

                // make the new class
                UMLClass newUMLClass = new UMLClass(className);
                // loop through the fieldList
               while (fieldIter.hasNext()) {
                   String fieldName = (String) fieldIter.next().get("name");
                   String fieldType = (String) fieldIter.next().get("type");
                   // make new field
                   Field newField = new Field(fieldName, fieldType);
                   // add field to class
                   newUMLClass.addField(newField);
               }
                // loop through the methodList
                while (methIter.hasNext()) {
                    String methName = (String) methIter.next().get("name");
                    String methType = (String) methIter.next().get("returnType");
                    // make new method
                    Method newMeth = new Method(methName, methType);
                    // add method to class
                    newUMLClass.addMethod(newMeth);
                }

                // add the filled class to the classList
                UMLModel.addClass(newUMLClass);
            }

            // JSONArray for getting the saved relationshipList
            JSONArray relArray = (JSONArray) jo.get("relationshipList");
            // iterator for iterating for source
            Iterator<JSONObject> srcIter = relArray.iterator();
            // iterator for iterating for destination
            Iterator<JSONObject> destIter = relArray.iterator();

            // iterate through the relationships
            while (srcIter.hasNext()) {
                // get the source name of the relationship
                String sourceName = (String) srcIter.next().get("source");
                // get the destination name of the relationship
                String destinationName = (String) destIter.next().get("destination");

                String relType = UMLModel.findRelType(sourceName, destinationName);
                // make a new relationship with the correct parameters
                Relationship newRelationship = new Relationship(Objects.requireNonNull(UMLModel.findClass(sourceName)),
                        Objects.requireNonNull(UMLModel.findClass(destinationName)), relType);
                        //UMLModel.findRelationship(sourceName, destinationName, relType));
                // add the relationship to the relationship list
                UMLModel.addRel(newRelationship);
            }

            System.out.println("Diagram has been loaded from \"" + fileName + ".json\"");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Checks to see if the save file directory is empty
     *
     * @return true if empty, false otherwise
     */
    public static boolean ifDirIsEmpty() {
        File dir = new File(FILE_DIR);
        String[] fileList = dir.list();
        return Objects.requireNonNull(fileList).length == 1;
    }
}