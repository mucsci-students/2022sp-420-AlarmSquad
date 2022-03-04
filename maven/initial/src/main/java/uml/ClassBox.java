package uml;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ClassBox {

    private String classBoxName;
    static private double boxWidth = 100;
    static private double boxHeight = 20;
    private static StackPane classPane;


    public static StackPane getClassPane() {
        return classPane;
    }

    public static void setClassPane(StackPane classPane) {
        ClassBox.classPane = classPane;
    }

    public String getClassBoxName() {
        return classBoxName;
    }

    public void setClassBoxName(String classBoxName) {
        this.classBoxName = classBoxName;
    }

    public static double getBoxWidth() {
        return boxWidth;
    }

    public static void setBoxWidth(double boxWidth) {
        ClassBox.boxWidth = boxWidth;
    }

    public static double getBoxHeight() {
        return boxHeight;
    }

    public static void setBoxHeight(double boxHeight) {
        ClassBox.boxHeight = boxHeight;
    }

    public ClassBox(String className) {
        this.classBoxName = className;

        Rectangle titleBox = new Rectangle((boxWidth), (boxHeight));
        titleBox.setFill(Color.WHITESMOKE);
        titleBox.setStroke(Color.BLACK);
        Text classTitle = new Text(className);
        classTitle.setFont(Font.font(classTitle.getFont().getName(), FontWeight.BOLD, 12));
        classPane.getChildren().addAll(titleBox, classTitle);
        classPane.setLayoutX(GUIController.getXGridPosition());
        classPane.setLayoutY(GUIController.getYGridPosition());
    }

}
