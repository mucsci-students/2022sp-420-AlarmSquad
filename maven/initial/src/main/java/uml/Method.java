package uml;

import java.util.ArrayList;

public class Method extends Attribute {

    private String returnType;
    private ArrayList<Parameter> paramList;

    public Method(String methodName, String returnType) {
        super(methodName);
        this.returnType = returnType;
        paramList = new ArrayList<Parameter>();
    }

    public String getReturnType(){
        return this.returnType;
    }
    public void setReturnType(String returnType){
        this.returnType = returnType;
    }

    /**
     * Iterates through paramList, returns with .get()
     *
     * @param param parameter to add to paramList
     */
    public void addParameter(Parameter param) {
        paramList.add(param);
    }

    public void deleteParameter(Parameter param) {
        paramList.remove(param);
    }

    /**
     * Iterates through paramList, returns with .get()
     *
     * @param paramToFind
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
     * @param paramToFind
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

    public ArrayList<Parameter> returnList () {
        return paramList;
    }
}

