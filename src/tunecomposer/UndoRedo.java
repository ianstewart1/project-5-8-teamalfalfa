
package tunecomposer;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Manages undo and redo functionality. Saves each composition each time a 
 * change is made and allows for retrieval. 
 * @author milloypr
 */
public class UndoRedo {
    private static Stack<Set<Playable>> undoStack = new Stack<>();
    private static Stack<Set<Playable>> redoStack = new Stack<>();

    /**
     * Returns a deep copy of the set of Playable objects.  
     * @param composition a set of Playable objects
     * @return copy, an identical set of Playable objects
     */
    public static Set<Playable> copyComposition(Set<Playable> composition) {
        Set<Playable> copy = new HashSet();
        composition.forEach((playable) -> {
            copy.add(playable.makeCopy());
        });
        return copy;
    }
    
    /**
     * pushUndo pushes the new composition to the undoStack.
     * @param composition a set of Playable objects.
     */
    public static void pushUndo(Set<Playable> composition) {
        undoStack.push(copyComposition(composition));
        redoStack.clear();
    }
    
    /**
     * Adds the current state to the redoStack and returns the top of the 
     * undoStack. Called by handleUndo.
     * @param composition a set of Playable objects
     * @return a set of Playable objects, the top of the undoStack
     */
    public static Set<Playable> undo(Set<Playable> composition){
        redoStack.push(copyComposition(composition));
        return undoStack.pop();
    }
    
    /**
     * Adds the current state to the undoStack and returns the top of the
     * redoStack. Called by handleRedo.
     * Precondition: redoStack must not be empty //is this guaranteed??
     * @param composition a set of Playable objects
     * @return a set of Playable objects, the top of the redoStack
     */
    public static Set<Playable> redo(Set<Playable> composition){
        undoStack.push(copyComposition(composition));
        return redoStack.pop();

    }
    
    /**
     * Empties both the undo and redo stack
     * 
     */
    public void emptyStacks(){
        undoStack.clear();
        redoStack.clear();
    }
    
    /**
     * Is the undoStack empty? Important for disabling undo button.
     * @return boolean
     */
    public static boolean isUndoEmpty() {
        return undoStack.isEmpty();
    }
    
    /**
     * Is the redoStack empty? Important for disabling redo button.
     * @return boolean
     */
    public static boolean isRedoEmpty() {
        return redoStack.isEmpty();
    }
    
}
