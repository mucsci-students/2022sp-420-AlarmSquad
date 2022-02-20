package uml;
public class Method extends Attribute {

    private String returnType;
    private String paramName;

    public Method(String methodName, String returnType) {
        super(methodName);
        this.returnType = returnType;
    }

   public void setReturnType(String returnType){
        this.returnType = returnType;
   }

   public String getReturnType(){
        return this.returnType;
   }

   public void setParamName(String paramName){
        this.paramName = paramName;
    }

    public String getParamName(){
    return this.paramName;
    }
}

