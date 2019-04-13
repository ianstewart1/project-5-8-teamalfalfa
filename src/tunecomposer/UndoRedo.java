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
 *
 * @author milloypr
 */
public class UndoRedo {
    private static Stack<Set<Playable>> undoStack;
    private static Stack<Set<Playable>> redoStack;
    
    public UndoRedo() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    
    public static Set<Playable> saveState(Set<Playable> composition) {
        Set<Playable> pushState = new HashSet();
        composition.forEach((playable) -> {
            pushState.add(playable.makeCopy());
        });
        return pushState;
    }
    
    public static Set<Playable> undo(Set<Playable> composition){
        redoStack.push(saveState(composition));
        return undoStack.pop();
    }
    
    public static Set<Playable> redo(Set<Playable> composition){
        undoStack.push(saveState(composition));
        return redoStack.pop();

    }
    
    public static boolean isUndoEmpty() {
        return undoStack.isEmpty();
    }
    
    public static boolean isRedoEmpty() {
        return redoStack.isEmpty();
    }
    
}
