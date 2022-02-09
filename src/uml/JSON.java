package uml;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
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

    public void load() {

        relationshipList.clear();
        classList.clear();

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("placeholder.json"));
            JSONObject readFile = (JSONObject) obj;

            JSONArray readRelationships = (JSONArray) readFile.get("relationshipList");
            Iterator<Relationship> iter = readRelationships.iterator();
            while (iter.hasNext()) {

            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (ParseException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
