package uml;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ClassBox {

    private String classBoxName;
    private double boxWidth = 100;
    private double boxHeight = 20;
    private StackPane classPane;

    public ClassBox(String className) {
        classBoxName = className;
        classPane = new StackPane();
        Rectangle titleBox = new Rectangle((boxWidth), (boxHeight));
        titleBox.setFill(Color.WHITESMOKE);
        titleBox.setStroke(Color.BLACK);
        Text classTitle = new Text(className);
        classTitle.setFont(Font.font(classTitle.getFont().getName(), FontWeight.BOLD, 12));
        classPane.getChildren().addAll(titleBox, classTitle);
    }

    public StackPane getClassPane() { return this.classPane; }

    public void setClassPane(StackPane classPane) {
        this.classPane = classPane;
    }

    public String getClassBoxName() {
        return this.classBoxName;
    }

    public void setClassBoxName(String classBoxName) {
        this.classBoxName = classBoxName;
    }

    public double getBoxWidth() {
        return this.boxWidth;
    }

    public void setBoxWidth(double boxWidth) {
        this.boxWidth = boxWidth;
    }

    public double getBoxHeight() {
        return this.boxHeight;
    }

    public void setBoxHeight(double boxHeight) {
        this.boxHeight = boxHeight;
    }
}
