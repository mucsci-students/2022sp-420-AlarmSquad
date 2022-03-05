package uml;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RelLine {

    // class box source class
    private ClassBox source;
    // class box destination class
    private ClassBox destination;

    // the line
    private Line line;

    // line constructor, needs a startX coord,
    // a startY coord, endX coord, endY coord,
    // and a color
    public RelLine(ClassBox source, ClassBox destination, Color color){
        this.source = source;
        this.destination = destination;
        // creates the new line that connects form a source class to a destination class
        this.line = new Line(source.getClassPane().getTranslateX(), source.getClassPane().getTranslateY(),
                destination.getClassPane().getTranslateX(), destination.getClassPane().getTranslateY());
        // line style
        line.setStrokeWidth(5);
        line.setStroke(color);

        // bind the line to the two class boxes
        line.startXProperty().bind(source.getClassPane().translateXProperty());
        line.startYProperty().bind(source.getClassPane().translateYProperty());
        line.endXProperty().bind(destination.getClassPane().translateXProperty());
        line.endYProperty().bind(destination.getClassPane().translateYProperty());
    }

    // get the class box source class
    public ClassBox getCBSrc() { return this.source; }

    // get the class box destination class
    public ClassBox getCBDest() { return this.destination; }

    // get the line from RelLine
    public Line getLine() { return this.line; }
}
