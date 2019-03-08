/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import javafx.scene.shape.Rectangle;

/**
 * A unique rectangle drawn when selecting notes in an area.
 * @author Ian H, Angie
 * TODO Make the rectangle disappear
 */
public class SelectionArea {

    Rectangle rect;
    double originX;
    double originY;

    public SelectionArea(Rectangle newRect) {
        rect = newRect;
    }

    public void startRectangle(double initX, double initY) {
        rect.setX(initX);
        rect.setY(initY);
        originX = initX;
        originY = initY;
        rect.setVisible(true);
    }
    
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
    
    public void endRectangle(){
        rect.setVisible(false);
        rect.setX(0);
        rect.setY(0);
        rect.setWidth(0);
        rect.setHeight(0);
    }
}
