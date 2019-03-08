/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import javafx.scene.shape.Rectangle;

/**
 * A unique rectangle drawn when selecting notes in an area.
 * @author Ian Hawkins, Angie Mead
 */
public class SelectionArea {
    
    /**
     * Fields for SelectionArea Rectangle
     */
    private final Rectangle rect;
    private double originX;
    private double originY;
    
    /**
     * Construct SelectionArea object
     * @param newRect Rectangle being passed in
     */
    public SelectionArea(Rectangle newRect) {
        rect = newRect;
    }
    
    /**
     * Begin to draw selection box rectangle
     * @param initX starting X coordinate
     * @param initY starting Y coordinate
     */
    public void startRectangle(double initX, double initY) {
        rect.setX(initX);
        rect.setY(initY);
        originX = initX;
        originY = initY;
        rect.setVisible(true);
    }
    
    /**
     * Updates selection rectangle as its being dragged
     * @param newX updated rectangle width
     * @param newY updated rectangle height
     */
    public void update(double newX, double newY) {
        if (newX < originX){
            rect.setX(newX);
            rect.setWidth(originX - newX);
        } else{
            rect.setWidth(newX - originX);
        }
        if (newY < originY){
            rect.setY(newY);
            rect.setHeight(originY - newY);
        } else{
            rect.setHeight(newY - originY);
        }
    }
    
    /**
     * Reset selection rectangle after done dragging
     */
    public void endRectangle(){
        rect.setVisible(false);
        rect.setX(0);
        rect.setY(0);
        rect.setWidth(0);
        rect.setHeight(0);
    }
    
    /**
     * Get rectangle object
     * @return rectangle used by SelectionArea
     */
    public Rectangle getRectangle() {
        return rect;
    }
}
