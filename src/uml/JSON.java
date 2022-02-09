package uml;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSON {

    // a copy of the classList from Driver
    private static ArrayList<Class> classList = Driver.getClassList();
    // a copy of the relationshipList from Driver
    private static ArrayList<Relationship> relationshipList = Driver.getRelationshipList();
    // the JSON object to be saved
    private static JSONObject saveFile = new JSONObject();

    @SuppressWarnings("unchecked")
    public static void save() {
        // a JSON array that contains a list of all the classes
        JSONArray saveClasses = new JSONArray();
        // a JSON array that contains a list of all the relationships
        JSONArray saveRelationships = new JSONArray();

        // iterate through the classList
        for (Class classObj : classList) {
            JSONObject classToBeSaved = new JSONObject();
            // add name to JSON
            classToBeSaved.put("className", classObj.getClassName());
            // make new JSONArray
            JSONArray attList = new JSONArray();
            // iterate through the Class object's attributeList
            for (Attribute attObj : classObj.getAttributeList()) {
                // put each attribute in the JSONArray
                attList.add(attObj.getAttName());
            }
            // put the attributeList JSONArray into the JSON Class object
            classToBeSaved.put("attList", attList);
            // put the JSONArray into the JSON object
            saveClasses.add(classToBeSaved);
        }
        saveFile.put("classList", saveClasses);

        // iterate through the relationship list
        for (Relationship relObj : relationshipList) {
            JSONObject relToBeSaved = new JSONObject();
            // add source to JSON object
            relToBeSaved.put("source", relObj.getSource().getClassName());
            // add destination to JSON object
            relToBeSaved.put("destination", relObj.getDestination().getClassName());
            // put the JSON object in the JSONArray
            saveRelationships.add(relToBeSaved);
        }
        saveFile.put("relationshipList", saveRelationships);

        // save the file
        try (FileWriter file = new FileWriter("placeholder.json")) {
            file.write(saveFile.toString());
            file.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void load() {

        // wipe both lists
        Driver.clearClassList();
        Driver.clearRelationshipList();

        try {
            // makes the JSONParser
            Object obj = new JSONParser().parse(new FileReader("placeholder.json"));
            // casting obj to JSONObject
            JSONObject jo = (JSONObject) obj;

            // JSONArray for getting the saved classList
            JSONArray classArray = (JSONArray) jo.get("classList");
            // iterator for iterating classList
            Iterator<JSONObject> classIter = classArray.iterator();
            // iterator for iterating attList
            Iterator<JSONObject> attIter = classArray.iterator();

            // loops through the classes and attributes of the classes
            while (classIter.hasNext()) {
                // finds the class' name at the key "className"
                String className = (String) classIter.next().get("className");
                // finds the list of attributes at the key "attList"
                ArrayList<String> attList = (ArrayList<String>) attIter.next().get("attList");
                // make the new class
                Class newClass = new Class(className);
                // loop through the attList
                for (String attName : attList) {
                    // make a new Attribute and add it to the class
                    Attribute newAtt = new Attribute(attName);
                    newClass.addAttribute(newAtt);
                }
                // add the filled class to the classList
                Driver.addToClassList(newClass);
            }

            // JSONArray for getting the saved relationshipList
            JSONArray relArray = (JSONArray) jo.get("relationshipList");
            // iterator for iterating for source
            Iterator<JSONObject> srcIter = relArray.iterator();
            // iterator for iterating for destination
            Iterator<JSONObject> destIter = relArray.iterator();

            while (srcIter.hasNext()) {
                String sourceName = (String) srcIter.next().get("source");
                String destinationName = (String) destIter.next().get("destination");
                Relationship newRelationship = new Relationship(Driver.findClass(sourceName),
                        Driver.findClass(destinationName));
                Driver.addToRelationshipList(newRelationship);
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
