package uml;

/**
 *
 */
public class Memento {

    // the state of the model this memento is storing
    private UMLModel state;

    /**
     * Constructor
     *
     * @param state the state of the model this memento is storing
     */
    public Memento(UMLModel state){
        this.state = state;
    }

    /**
     * Gets the state of the model this memento is storing
     *
     * @return the state of the memento
     */
    public UMLModel getState(){
        return state;
    }
}
