package uml;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RelLine {

    // class box source class
    private ClassBox source;
    // class box destination class
    private ClassBox destination;
    // relationship type
    private String relType;
    // the line
    private Line line;

    // line constructor, needs a startX coord,
    // a startY coord, endX coord, endY coord,
    // and a color
    public RelLine(ClassBox source, ClassBox destination, String relType){
        this.source = source;
        this.destination = destination;
        this.relType = relType;
        // creates the new line that connects form a source class to a destination class
        this.line = new Line(source.getClassPane().getTranslateX(), source.getClassPane().getTranslateY(),
                destination.getClassPane().getTranslateX(), destination.getClassPane().getTranslateY());
        // line style
        line.setStrokeWidth(9.5);
        line.setStroke(Color.BLACK);

        // set middle of rectangle bindings
        DoubleBinding startXBind = source.getClassPane().
                translateXProperty().add(source.getClassPane().widthProperty().divide(2));
        DoubleBinding startYBind = source.getClassPane().
                translateYProperty().add(source.getClassPane().heightProperty().divide(2));
        DoubleBinding endXBind = destination.getClassPane().
                translateXProperty().add(destination.getClassPane().widthProperty().divide(2));
        DoubleBinding endYBind = destination.getClassPane().
                translateYProperty().add(destination.getClassPane().heightProperty().subtract(16));
        // bind the line to the two class box rectangles
        line.startXProperty().bind(startXBind);
        line.startYProperty().bind(startYBind);
        line.endXProperty().bind(endXBind);
        line.endYProperty().bind(endYBind);
    }

    /**
     * get the class box source class
     *
     * @return the source class box object
     */
    public ClassBox getCBSrc() { return this.source; }

    /**
     * get the class box destination class
     *
     * @return the destination class box object
     */
    public ClassBox getCBDest() { return this.destination; }

    /**
     * get the line from RelLine
     *
     * @return the relationship line
     */
    public Line getLine() { return this.line; }

    /**
     * get the relationship type
     *
     * @return the relationship type
     */
    public String getRelType() { return this.relType; }


}
