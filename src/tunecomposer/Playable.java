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
import javafx.scene.shape.Rectangle;

/**
 * Interface for Gesture and Note to make polymorphism possible.
 * @author milloypr
 */
public interface Playable {
    
    // "Getter" methods for Note and Gesture
    boolean getSelected();
    Bounds getBounds();
    double getX();
    double getWidth();
    List<Rectangle> getNodeList();
    
    // "Setter" methods for Note and Gesture
    void setSelected(boolean selected);
    void setMovingCoords(MouseEvent event);
    void setMovingDuration(MouseEvent event);
    
    // Move Notes and Gestures
    void move(MouseEvent event);
    void moveX(MouseEvent event);
    void moveY(MouseEvent event);
    void stopMoving(MouseEvent event);
    void moveDuration(MouseEvent event);
    
    // Schedule Notes and notes within Gestures
    void schedule();
    
    // Remove rectangles from the pane
    void removeFromPane(Pane notepane);
    
    // Stretch goal implementation methods
    boolean inLastFive(MouseEvent event);
    void stopDuration(MouseEvent event);
    void setProportions(double gestureX, double proportion);
    
}
