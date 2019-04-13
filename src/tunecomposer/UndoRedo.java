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
    Stack undoStack = new Stack();
    Stack redoStack = new Stack();
    
    public void saveState(Set composition) {
        
        undoStack.push(composition);
    }
    
    public Set undo(){
        
        return null;
    }
    
}
