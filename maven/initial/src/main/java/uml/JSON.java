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

    // the default directory to save new files to
    private final String DEFAULT_DIR = System.getProperty("user.dir");
    // the model to use in the save
    private UMLModel model;
    // the view of the gui
    private GUIView view;
    // a copy of the classList from Driver
    private ArrayList<UMLClass> classList;
    // a copy of the relationshipList from Driver
    private ArrayList<Relationship> relationshipList;
    // the JSON object to be saved
    private JSONObject saveFile = new JSONObject();

    public JSON(UMLModel model) {
        this.model = model;
        this.classList = model.getClassList();
        this.relationshipList = model.getRelationshipList();
    }

    public JSON(UMLModel model, GUIView view) {
        this.view = view;
        this.model = model;
        this.classList = model.getClassList();
        this.relationshipList = model.getRelationshipList();
    }

    /**
     * Saves the current UML diagram to a file with a given name in the default directory
     * Overrides a file if it has the same name
     *
     * @param fileName the name of the file to be saved to
     */
    @SuppressWarnings("unchecked")
    public void saveCLI(String fileName) {
        // if the file name doesn't already end in .json, append it
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        // perform the save and give the data to the saveFile object
        saveFile = executeSave(true);
        // create a file with the default directory and given file name
        File fileToBeSaved = new File(DEFAULT_DIR, fileName);
        // save the file
        try (FileWriter file = new FileWriter(fileToBeSaved)) {
            file.write(saveFile.toString());
            file.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        // inform the user that the save worked
        System.out.println("Diagram has been saved to \"" + fileName + "\"");
    }

    /**
     * Saves the current UML diagram to a given file
     * Overrides a file if it is the same file
     *
     * @param file the file to be saved to
     */
    @SuppressWarnings("unchecked")
    public void saveGUI(File file) {
        // perform the save and give the data to the saveFile object
        saveFile = executeSave(false);
        // save the file to the given file
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(saveFile.toString());
            fileWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * The functionality of the save
     * Creates a JSONObject which holds all the model data
     *
     * @return a JSONObject to be used by other save methods
     */
    @SuppressWarnings("unchecked")
    private JSONObject executeSave(boolean isInCLI) {
        // a JSON array that contains a list of all the classes
        JSONArray saveClasses = new JSONArray();
        // a JSON array that contains a list of all the relationships
        JSONArray saveRelationships = new JSONArray();

        int numClasses = 0;
        // iterate through the classList
        for (UMLClass UMLClassObj : classList) {
            numClasses++;
            JSONObject classToBeSaved = new JSONObject();
            // add name to JSON
            classToBeSaved.put("name", UMLClassObj.getClassName());

            // make new JSONArray
            JSONArray fieldList = new JSONArray();
            JSONArray methList = new JSONArray();

            // iterate through the Class object's field and method lists
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
                methToBeSaved.put("return_type", methObj.getReturnType());

                // iterate through the method object's parameters
                JSONArray paramList = new JSONArray();
                for (Parameter paramObj : methObj.getParamList()) {
                    JSONObject paramToBeSaved = new JSONObject();
                    paramToBeSaved.put("name", paramObj.getAttName());
                    paramToBeSaved.put("type", paramObj.getFieldType());
                    // put each parameter in the JSONArray
                    paramList.add(paramToBeSaved);
                }
                methToBeSaved.put("params", paramList);
                // put each method in the JSONArray
                methList.add(methToBeSaved);
            }

            // put the field and method JSONArray into the JSON Class object
            classToBeSaved.put("fields", fieldList);
            classToBeSaved.put("methods", methList);

            // create a location object to store the x and y of the class box
            JSONObject location = new JSONObject();
            if (isInCLI) {
                int multiplier = ((numClasses - 1) / 8);
                int yOffset = 20 + (multiplier * 200) + 20;
                int xOffset = (-85) + (((numClasses - 1) % 8) * 115) + 100;
                location.put("x", (double) xOffset);
                location.put("y", (double) yOffset);
            } else {
                location.put("x", this.view.findClassBox(UMLClassObj.getClassName()).getX());
                location.put("y", this.view.findClassBox(UMLClassObj.getClassName()).getY());
            }

            // put the location object in the JSON Class object
            classToBeSaved.put("location", location);
            // put the JSONArray into the JSON object
            saveClasses.add(classToBeSaved);
        }
        // add the classList to the JSONObject
        saveFile.put("classes", saveClasses);

        // iterate through the relationship list
        for (Relationship relObj : relationshipList) {
            JSONObject relToBeSaved = new JSONObject();
            // add source to JSON object
            relToBeSaved.put("source", relObj.getSource().getClassName());
            // add destination to JSON object
            relToBeSaved.put("destination", relObj.getDestination().getClassName());
            // add relType to JSON object
            relToBeSaved.put("type", relObj.getRelType());
            // put the JSON object in the JSONArray
            saveRelationships.add(relToBeSaved);
        }
        // add the relationshipList to the JSONObject
        saveFile.put("relationships", saveRelationships);
        return saveFile;
    }

    /**
     * Loads the current UML diagram from a file with a given name
     * DOES NOT CHECK IF THE DIRECTORY IS EMPTY
     *
     * @param fileName the name of the file to be loaded from
     */
    @SuppressWarnings("unchecked")
    public UMLModel loadCLI(String fileName) {
        // if the file name doesn't already end in .json, append it
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        // checks to see if the file exists in the directory
        if (!doesFileExist(fileName)) {
            return null;
        }
        // gets the file in the correct directory
        File fileToBeLoaded = new File(DEFAULT_DIR + "/" + fileName);
        // perform the load with the new file and return the model
        return executeLoad(fileToBeLoaded, true);
    }

    /**
     * Loads the current UML diagram from a given file
     * DOES NOT CHECK IF THE DIRECTORY IS EMPTY
     *
     * @param file the file to be loaded from
     */
    @SuppressWarnings("unchecked")
    public UMLModel loadGUI(File file) {
        // perform the load with the given file
        return executeLoad(file, false);
    }

    /**
     * The functionality of the load
     * Accesses a file which holds all the model data, the adds that data to the model
     *
     * @param file the file to be loaded from
     */
    @SuppressWarnings("unchecked")
    private UMLModel executeLoad(File file, boolean isInCLI) {
        try {
            // wipe both lists
            this.model.clearClassList();
            this.model.clearRelationshipList();
            // makes the JSONParser
            Object obj = new JSONParser().parse(new FileReader(file));
            // casting obj to JSONObject
            JSONObject jo = (JSONObject) obj;

            // JSONArray for getting the saved classList
            JSONArray classArray = (JSONArray) jo.get("classes");

            // loops through the classes and attributes of the classes
            for (JSONObject current : (Iterable<JSONObject>) classArray) {
                // finds the class' name at the key "className"
                String className = (String) current.get("name");

                JSONArray fieldArray = (JSONArray) current.get("fields");
                JSONArray methArray = (JSONArray) current.get("methods");
                // iterator for iterating fieldList
                Iterator<JSONObject> fieldIter = fieldArray.iterator();
                // iterator for iterating fieldList
                Iterator<JSONObject> methIter = methArray.iterator();

                // make the new class
                UMLClass newUMLClass = new UMLClass(className);
                // loop through the fieldList
                while (fieldIter.hasNext()) {
                    JSONObject currentField = fieldIter.next();
                    String fieldName = (String) currentField.get("name");
                    String fieldType = (String) currentField.get("type");
                    // make new field
                    Field newField = new Field(fieldName, fieldType);
                    // add field to class
                    newUMLClass.addField(newField);
                }
                // loop through the methodList
                while (methIter.hasNext()) {
                    JSONObject currentMeth = methIter.next();
                    String methName = (String) currentMeth.get("name");
                    String methType = (String) currentMeth.get("return_type");
                    // make new method
                    Method newMeth = new Method(methName, methType);
                    // array of parameters in the method
                    JSONArray paramArray = (JSONArray) currentMeth.get("params");
                    // iterator for iterating parameters
                    Iterator<JSONObject> paramIter = paramArray.iterator();
                    // loop through the paramList
                    while (paramIter.hasNext()) {
                        JSONObject currentParam = paramIter.next();
                        String paramName = (String) currentParam.get("name");
                        String paramType = (String) currentParam.get("type");
                        // make new parameter
                        Parameter newParam = new Parameter(paramName, paramType);
                        newMeth.addParameter(newParam);
                    }
                    // add method to class
                    newUMLClass.addMethod(newMeth);
                }

                // get the JSON object containing the x and y values for the class box to be created
                JSONObject location = (JSONObject) current.get("location");
                // puts the x and y values for the class box in the coordinate map
                this.view.addToCoordinateMap((String) current.get("name"), (Double) location.get("x"), (Double) location.get("y"));

                // add the filled class to the classList
                this.model.addClass(newUMLClass);
            }

            // JSONArray for getting the saved relationshipList
            JSONArray relArray = (JSONArray) jo.get("relationships");
            // iterator for iterating for source
            Iterator<JSONObject> srcIter = relArray.iterator();
            // iterator for iterating for destination
            Iterator<JSONObject> destIter = relArray.iterator();
            // iterator for iterating for relType
            Iterator<JSONObject> typeIter = relArray.iterator();

            // iterate through the relationships
            while (srcIter.hasNext()) {
                // get the source name of the relationship
                String sourceName = (String) srcIter.next().get("source");
                // get the type name of the relationship (possible problem with multiple relationships)
                String relTypeName = (String) typeIter.next().get("type");
                // get the destination name of the relationship
                String destinationName = (String) destIter.next().get("destination");
                // make a new relationship with the correct parameters
                Relationship newRelationship = new Relationship(Objects.requireNonNull(this.model.findClass(sourceName)),
                        Objects.requireNonNull(this.model.findClass(destinationName)), relTypeName);
                // add the relationship to the relationship list
                this.model.addRel(newRelationship);
            }
        } catch (Exception exception) {
            // if the program catches an error related to an invalid file, inform the user
            if (isInCLI) {
                System.out.println("Invalid file");
            } else {
                this.view.popUpWindow("Error", "Invalid file");
            }
        }
        return this.model;
    }

    /**
     * Checks to see if the provided file is in the directory
     *
     * @param fileName the provided file name
     * @return true if found, false otherwise
     */
    public boolean doesFileExist(String fileName) {
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        // a file which acts as the save file directory
        File dir = new File(DEFAULT_DIR);
        for (String file : Objects.requireNonNull(dir.list())) {
            if (file.equals((fileName))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the save file directory is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean ifDirIsEmpty() {
        File dir = new File(DEFAULT_DIR);
        String[] fileList = dir.list();
        return Objects.requireNonNull(fileList).length == 1;
    }
}