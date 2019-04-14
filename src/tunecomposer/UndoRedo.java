/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private static Stack<Set<Playable>> undoStack;
    private static Stack<Set<Playable>> redoStack;
    
    public UndoRedo() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    
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
     * Each time an action is made, pushUndo pushes the new composition to the
     * undoStack.
     * @param composition a set of Playable objects.
     */
    public static void pushUndo(Set<Playable> composition) {
        undoStack.push(copyComposition(composition));
        redoStack.clear();
    }
    
    public static Set<Playable> undo(Set<Playable> composition){
        redoStack.push(copyComposition(composition));
        return undoStack.pop();
    }
    
    public static Set<Playable> redo(Set<Playable> composition){
        undoStack.push(copyComposition(composition));
        return redoStack.pop();

    }
    
    public static boolean isUndoEmpty() {
        return undoStack.isEmpty();
    }
    
    public static boolean isRedoEmpty() {
        return redoStack.isEmpty();
    }
    
}
