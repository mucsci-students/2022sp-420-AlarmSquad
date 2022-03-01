package uml;
public class Field extends Attribute {

    private String fieldType;

    public Field(String fieldName, String fieldType) {
        super(fieldName);
        this.fieldType = fieldType;
    }

     /**
     * Sets the type of the field
     * 
     * @param fieldType the new type of the field
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Gets the type of the field
     * 
     * @return the type of the field
     */
    public String getFieldType() {
        return this.fieldType;
    }

}
