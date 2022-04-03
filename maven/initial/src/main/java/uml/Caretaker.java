package uml;

import java.util.Stack;

/**
 *
 */
public class Caretaker {

    // the stack of states the UMLModel has taken
    private Stack<Memento> mementoStack;

    /**
     * Constructor
     * Gets the initial state of the project and adds it to the stack
     */
    public Caretaker() {
        this.mementoStack = new Stack<>();
    }

    /**
     * Push the given state onto the stack
     *
     * @param state the state to push
     */
    public void push(Memento state){ mementoStack.push(state); }

    /**
     * Pop the most recent state off the stack and return it
     *
     * @return the most recent state removed from the stack
     */
    public Memento pop() { return mementoStack.pop(); }

    /**
     * Peek at the most recent state on the stack
     *
     * @return the most recent state on the stack
     */
    public Memento peek() { return mementoStack.peek(); }

    /**
     * Get the size of the memento stack
     *
     * @return the size of the memento stack
     */
    public int getSize() { return mementoStack.size(); }

    /**
     * Checks if the stack is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean stackIsEmpty() {
        return mementoStack.isEmpty();
    }

    /**
     * Performs the undo action on the undo stack and adds the state to the redo stack
     *
     * @param modelToAddToRedo the state to add to the redo stack
     * @return the state to roll back to
     */
    public Memento undoHelper(UMLModel modelToAddToRedo) {
        Memento prevState = mementoStack.pop();
        return prevState;
    }
}
