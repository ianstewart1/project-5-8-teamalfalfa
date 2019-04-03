/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    
    Bounds getBounds();
    double getX();
    double getWidth();
    
    void schedule();
    List getNodeList();
    void removeFromPane(Pane notepane);
    
}
