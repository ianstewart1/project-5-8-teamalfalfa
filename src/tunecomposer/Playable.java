/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author milloypr
 */
public interface Playable {
    
    boolean getSelected();
    void setSelected(boolean selected);
    
    void setMovingCoords(MouseEvent event);
    void move(MouseEvent event);
    void stopMoving(MouseEvent event);
    
}
