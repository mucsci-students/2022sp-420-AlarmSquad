package uml;

import java.util.ArrayList;

public class Method extends Attribute {

    private String returnType;
    private ArrayList<Parameter> paramList;

    /**
     * Method constructor
     *
     * @param methodName the name of the method
     * @param returnType the return type of the method
     */
    public Method(String methodName, String returnType) {
        super(methodName);
        this.returnType = returnType;
        paramList = new ArrayList<>();
    }

    // getter for return type
    public String getReturnType(){
        return this.returnType;
    }

    // getter for param list
    public ArrayList<Parameter> getParamList() {
        return paramList;
    }
    // adds a parameter to the param list
    public void addParameter(Parameter param) {
        paramList.add(param);
    }
    // removes a parameter from the param list
    public void deleteParameter(Parameter param) {
        paramList.remove(param);
    }

    /**
     * Iterates through paramList, returns with .get()
     *
     * @param paramToFind the name of the parameter
     * @return param if found, null if not found
     */
    public Parameter findParameter(String paramToFind) {
        // iterates through the arraylist
        for (Parameter parameter : paramList) {
            // if the name matches, return class
            if (paramToFind.equals(parameter.getAttName())) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * Iterates through paramList, returns with .get()
     *
     * @param paramToFind the name of the parameter
     * @return param if found, null if not found
     */
    public Parameter changeParameter(String paramToFind, Parameter paramToChange) {
        // iterates through the arraylist
        for (int i = 0; i < paramList.size(); ++i) {
            // if the name matches, return class
            if (paramToFind.equals(paramList.get(i).getAttName())) {
                paramList.set(i, paramToChange);
                return paramToChange;
            }
        }
        return null;
    }
}

