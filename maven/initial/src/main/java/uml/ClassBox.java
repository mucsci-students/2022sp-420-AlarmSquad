package uml;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import java.util.ArrayList;

public class ClassBox {

    private final Rectangle titleBox;
    private String classBoxName;
    private double boxWidth = 100;
    private double boxHeight = 20;
    private StackPane classPane;
    private TextFlow flow;
    private TextFlow fieldFlow;
    private TextFlow methFlow;
    private Text classTitle;
    private ArrayList<Text> fieldTextList;
    private ArrayList<Text> methTextList;
    private double X;
    private double Y;

    public ClassBox(String className) {
        classBoxName = className;
        classPane = new StackPane();
        titleBox = new Rectangle((boxWidth), (boxHeight));
        titleBox.setFill(Color.WHITESMOKE);
        titleBox.setStroke(Color.BLACK);
        classTitle = new Text(className);
        classTitle.setFont(Font.font(classTitle.getFont().getName(), FontWeight.BOLD, 12));
        flow = new TextFlow();
        flow.setTextAlignment(TextAlignment.CENTER);
        flow.getChildren().add(classTitle);
        classPane.getChildren().add(0, titleBox);
        classPane.getChildren().add(1, flow);
    }

    // Getters and Setters
    public double getX() {return X;}

    public void setX(double X) {this.X = X;}

    public double getY() {return Y;}

    public void setY(double Y) {this.Y = Y;}

    public StackPane getClassPane() {
        return this.classPane;
    }

    public void setClassPane(StackPane classPane) {
        this.classPane = classPane;
    }

    public TextFlow getFlow(){
        return this.flow;
    }

    public void setFlow(TextFlow flow) {
        this.flow = flow;
    }

    public String getClassBoxName() {
        return this.classBoxName;
    }

    public void setClassBoxName(String classBoxName) {
        this.classBoxName = classBoxName;
    }

    public Text getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(Text classTitle) {
        this.classTitle = classTitle;
    }

    public double getBoxWidth() {
        return this.boxWidth;
    }

    public void setBoxWidth(double boxWidth) {
        this.titleBox.setHeight(boxWidth);
        this.boxWidth = boxWidth;
    }

    public double getBoxHeight() {
        return this.boxHeight;
    }

    public void setBoxHeight(double boxHeight) {
        this.titleBox.setHeight(boxHeight);
        this.boxHeight = boxHeight;
    }

    public ArrayList<Text> getFieldTextList() {
        return fieldTextList;
    }

    public void setFieldTextList(ArrayList<Text> fieldTextList) {
        this.fieldTextList = fieldTextList;
    }

    public ArrayList<Text> getMethTextList() {
        return methTextList;
    }

    public void setMethTextList(ArrayList<Text> methTextList) {
        this.methTextList = methTextList;
    }

    /**
     * Takes in a generic object, 'E att', with the number of fields and methods
     * currently in the class. Iterates through the fieldTextList and methTextList
     * in the classBox object, and outputs the fields and methods accordingly
     *
     * @param att generic attribute to be added, either of type method or field
     * @param numOfFields number of fields currently in the class
     * @param numOfMeths number of methods currently in the class
     */
    public <E> void addText(E att, int numOfFields, int numOfMeths, boolean dontAddPadding) {
        // Clear the flow each time a method or field is added,
        // thus clearing the classbox of all objects.
        flow.getChildren().clear();
        // Remake the flow with just the name of the class added
        flow.getChildren().add(classTitle);
        // Must initialize attName outside if/else statement or intellij cries
        String attName = "";
        // To format output correctly, determine if att belongs to
        // the class Field or Method
        if (att instanceof Field) {
            // If att is a Field, add a newline to the front of it's name to put it
            // on a new line in the classbox, and add "- " to the front of the name for formatting
            attName = ("\n- " + ((Field) att).getAttName() + " : " + ((Field) att).getFieldType());
            // If this is the first field ever being added to the class associated with this
            // classbox, then initialize the fieldTextList, and increase the size of the
            // classbox for formatting. Attributes are added to their classes BEFORE the drawing of the box,
            // so the number of fields in a class will only == 1 inside this method when this is the first
            // field being added to a class.
            if (numOfFields == 1) {
                fieldTextList = new ArrayList<>();
                if (!dontAddPadding) {
                    setBoxHeight(boxHeight + 15);
                }
                // If methods will also need to be placed in the classbox, extra padding
                // is needed to accommodate the additional separator line between the field list and
                // the method list.
                if (numOfMeths != 0)
                    setBoxHeight(boxHeight + 10);
            }
            // Add the field to the fieldTextList
            Text text = new Text(attName);
            fieldTextList.add(text);
        }

        // If the object to be added is a method, add a newline to the front of it's name to put it
        // on a new line in the classbox, and add "+ " to front of the name, and "()" to the back
        // for formatting
        else {
            attName = ("\n+ " + ((Method) att).getAttName() + "(");
            String paramText = "";
            ArrayList<Parameter> paramList = ((Method) att).getParamList();
            if (paramList.size() >= 1) {
                paramText += paramList.get(0).getAttName() + " : " + paramList.get(0).getFieldType();
                for (int i = 1; i < paramList.size(); ++i) {
                    paramText += ", " + paramList.get(i).getAttName() + " : " + paramList.get(i).getFieldType();
                }
            }
            attName += paramText + ")";
            if(!((Method) att).getReturnType().equals("void")){
                 attName += " : " + ((Method) att).getReturnType();
            }
            if (dontAddPadding) {
                String name = ((Method) att).getAttName();
                methTextList.removeIf(textObj -> textObj.getText().startsWith("\n+ " + name));
            }
                // If this is the first method ever being added to the class associated with this
                // classbox, then initialize the methTextList, and increase the size of the
                // classbox for formatting. Attributes are added to their classes BEFORE the drawing of the box,
                // so the number of methods in a class will only == 1 inside this method when this is the first
                // method being added to a class.
                if (numOfMeths == 1) {
                    methTextList = new ArrayList<>();
                    if (!dontAddPadding) {
                        setBoxHeight(boxHeight + 15);
                    }
                    // If fields will also need to be placed in the classbox, extra padding
                    // is needed to accommodate the additional separator line between the field list and
                    // the method list.
                    if (!dontAddPadding && numOfFields != 0)
                        setBoxHeight(boxHeight + 10);
                }
                // Add the method to the methTextList
                Text text = new Text(attName);
                methTextList.add(text);
            }
        // Draw a separating line between the title of the class, and the attribute list(s)
        // that are about to be added
        drawSeparator();
        // If fields need to be added...
        if (numOfFields != 0) {
            // Iterate through the fieldTextList, and add each Text object to the flow, which in turn
            // displays the fields on the box
            for (Text fieldText : fieldTextList) {
                flow.getChildren().add(fieldText);
            }
            // If methods also need to be added, draw another separator line
            if (numOfMeths != 0) {
                drawSeparator();
            }
        }
        // If methods need to be added...
        if (numOfMeths != 0) {
            // Iterate through the methTextList, and add each Text object to the flow, which in turn
            // displays the methods on the box
            for (Text methText : methTextList) {
                flow.getChildren().add(methText);
            }
        }
    }

    /**
     * draw a horizontal line across the classbox
     */
    public void drawSeparator () {
        flow.getChildren().add(new Text(System.lineSeparator()));
        Line line = new Line(0, 0, titleBox.getWidth(), 0);
        flow.getChildren().add(line);
    }
}
