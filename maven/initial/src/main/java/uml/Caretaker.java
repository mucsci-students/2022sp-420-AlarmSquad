package uml;

import java.util.Stack;

/**
 *
 */
public class Caretaker {

    // the stacks of states the UMLModel has taken
    private Stack<Memento> undoStack;
    private Stack<Memento> redoStack;

    /**
     * Constructor
     * Gets the initial state of the project and adds it to the stack
     */
    public Caretaker() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Push the given state onto the stack
     *
     * @param state the state to push
     */
    public void pushToUndoStack(Memento state){
        undoStack.push(state);
        redoStack.clear();
    }

    /**
     * Checks if the undo stack is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean undoStackIsEmpty() {
        return undoStack.isEmpty();
    }

    /**
     * Checks if the redo stack is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean redoStackIsEmpty() {
        return redoStack.isEmpty();
    }

    /**
     * Performs the undo action on the undo stack and adds the state to the redo stack
     *
     * @param currState the state to add to the redo stack
     * @return the state to roll back to
     */
    public Memento undoHelper(Memento currState) {
        redoStack.push(currState);
        return undoStack.pop();
    }

    /**
     * Performs the redo action on the redo stack and adds the state to the undo stack
     *
     * @param currState the state to add to the undo stack
     * @return the state to roll back to
     */
    public Memento redoHelper(Memento currState) {
        undoStack.push(currState);
        return redoStack.pop();
    }
}
