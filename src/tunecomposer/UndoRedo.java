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
    Stack<Set<Playable>> undoStack = new Stack<>();
    Stack<Set<Playable>> redoStack = new Stack<>();
    
    public Set<Playable> saveState(Set<Playable> composition) {
        Set<Playable> pushState = new HashSet();
        composition.forEach((playable) -> {
            pushState.add(playable.makeCopy());
        });
        return pushState;
    }
    
    public Set<Playable> undo(Set<Playable> composition){
        redoStack.push(saveState(composition));
        return undoStack.pop();
    }
    
    public Set<Playable> redo(Set<Playable> composition){
        undoStack.push(saveState(composition));
        return redoStack.pop();

    }
    
}
