/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.Set;
import java.util.Stack;

/**
 *
 * @author milloypr
 */
public class UndoRedo {
    Stack<Set<Playable>> undoStack = new Stack<>();
    Stack<Set<Playable>> redoStack = new Stack<>();
    
    public void saveState(Set composition) {
        
        composition.forEach((element) -> {
            
        });
        undoStack.push(composition);
    }
    
    public static Set<Playable> undo(){
        
        return null;
    }
    
    public static Set<Playable> redo(){
        
        return null;
    }
    
}
