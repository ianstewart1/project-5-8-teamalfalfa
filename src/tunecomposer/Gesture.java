/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author jamesbgd
 */
public class Gesture implements Playable{
    
    /**
     * Gesture rectangle values
     */
    protected Rectangle boundingRect;
    private double x_coord;
    private double y_coord;
    private double width;
    
    /**
     * Selection boolean toggle
     */
    private boolean isSelected;
    
    /**
     * Offsets for dragging Rectangle
     */
    private double xOffset;
    private double yOffset;
    private double widthOffset;
    
    /**
     * Set of elements within Gesture
     */
    protected Set<Playable> elements;

    /**
     * Create a Gesture containing Notes or other Gestures.
     * @param selected Set of currently selected elements
     */
    public Gesture(Set<Playable> selected){
        isSelected = true;
        elements = new HashSet();
        selected.forEach((element) -> {
            elements.add(element);
        });
        boundingRect = calculateBounds();
        boundingRect.getStyleClass().add("selectedGesture");
        width = boundingRect.getWidth();
        x_coord = boundingRect.getX();
        y_coord = boundingRect.getY();
    }
    
    /**
     * Get selection boolean.
     * @return boolean if selected
     */
    @Override
    public boolean getSelected() {
        return isSelected;
    }
    
    /**
     * Get a set of elements from a Gesture.
     * @return Set of playables that are in the gesture.
     */
    protected Set<Playable> getElements() {
        return elements;
    }
    
    /**
     * Get a list of rectangles within the Gesture.
     * elements. this includes notes within nested gestures.
     * @return List of rectangles within the Gesture
     */
    @Override
    public List<Rectangle> getNodeList() {
        List<Rectangle> nodeList = new ArrayList();
        elements.forEach((element) -> {
                nodeList.addAll(element.getNodeList());
        });
        return nodeList;
    }
    
    /**
     * Get the bounds for the Gesture rectangle.
     * @return the Bounds
     */
    @Override
    public Bounds getBounds() {
        return boundingRect.getLayoutBounds();
    }
    
    /**
     * Get the x coordinate.
     * @return the x coordinate
     */
    @Override
    public double getX() {
        return x_coord;
    }
    
    /**
     * Get the width of the Gesture rectangle.
     * @return the width of the gesture as a double
     */
    @Override
    public double getWidth() {
        return width;
    }
    
    /**
     * Get the Gesture rectangle.
     * @return Gesture rectangle
     */
    public Rectangle getBoundingRect() {
        return boundingRect;
    }

    /**
     * Updates a gesture's style to reflect selected status.
     * This function also updates the style of the elements within gesture
     * to look selected.
     * @param selected a boolean representing if the gesture is selected or not
     */
    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        if(selected){
            boundingRect.getStyleClass().clear();
            boundingRect.getStyleClass().add("selectedGesture");
        }
        else{
            boundingRect.getStyleClass().clear();
            boundingRect.getStyleClass().add("unselectedGesture");
        }
        elements.forEach((element) -> {
            element.setSelected(selected);
        });
    }

    /**
     * Set the coordinates of the Gesture rectangle while moving.
     * @param event mouse click
     */
    @Override
    public void setMovingCoords(MouseEvent event) {
        xOffset = event.getX() - x_coord;
        yOffset = event.getY() - y_coord;        
        elements.forEach((element) -> {
            element.setMovingCoords(event);
        });
    }
    
    /**
     * Set the proportion of change for Gesture's within elements.
     * @param gestureX x coordinate of the parent Gesture
     * @param proportion magnitude of change specified for the Gesture position
     */
    @Override
    public void setProportions(double gestureX, double proportion) {
        double offset = (x_coord - gestureX) * proportion;
        double newX = gestureX + offset;
        double newWidth = width * proportion;
        x_coord = newX;
        width = newWidth;
        boundingRect.setX(newX);
        boundingRect.setWidth(newWidth);
        elements.forEach((element) -> {
            element.setProportions(gestureX, proportion);
        });
    }
    
    /**
     * Sets the mouse click offset during a duration change selection.
     * @param event mouse click
     */
    @Override
    public void setMovingDuration(MouseEvent event) {
        widthOffset = x_coord + width - event.getX();
    }

    /**
     * Move a Gesture's x and y coordinates.
     * @param event mouse click
     */
    @Override
    public void move(MouseEvent event) {
        moveX(event);
        moveY(event);
    }

    /**
     * Move x coordinate based on mouse position.
     * @param event mouse click
     */
    @Override
    public void moveX(MouseEvent event) {
        double moveX = event.getX() - xOffset;
        
        if(moveX > 0 && (moveX + width) < Constants.WIDTH){
            boundingRect.setX(moveX);
            
            elements.forEach((element) -> {
                element.moveX(event);
            });
        }
    }

    /**
     * Move y coordinate based on mouse position.
     * @param event mouse click
     */
    @Override
    public void moveY(MouseEvent event) {
        double moveY = event.getY() - yOffset;
        
        if(moveY > 0 && (moveY + boundingRect.getHeight()) < Constants.HEIGHT) {
            boundingRect.setY(moveY);
            
            elements.forEach((element) -> {
                element.moveY(event);
            });
        }
    }

    /**
     * Set coordinates at the end of a mouse drag.
     * @param event mouse click
     */
    @Override
    public void stopMoving(MouseEvent event) {
        double x = boundingRect.getX();
        double y = boundingRect.getY();
        
        x_coord = x;
        y_coord = y - (y % Constants.LINE_SPACING);
        
        boundingRect.setY(y_coord);
        
        elements.forEach((element) -> {
            element.stopMoving(event);
        });
    }

    /**
     * Adds all of the notes contained within the gesture and any nested
     * gestures to the midiplayer PLAYER.
     */
    @Override
    public void schedule() {
        elements.forEach((element) -> {
            element.schedule(); 
        });
    }
    
    /**
     * Calculate the bounds for the Gesture rectangle. Based on elements in the
     * Gesture's set.
     * @return Rectangle representing Gesture bounds
     */
    private Rectangle calculateBounds() {
        List<Rectangle> nodes = getNodeList();
        double minX = Constants.WIDTH;
        double minY = Constants.HEIGHT;
        double maxX = 0;
        double maxY = 0;
        for (Rectangle r : nodes) {
            if (r.getX() < minX) minX = r.getX();
            if (r.getY() < minY) minY = r.getY();
            if (r.getX() + r.getWidth() > maxX) maxX = r.getX() + r.getWidth();
            if (r.getY() + r.getHeight() > maxY) maxY = r.getY() + r.getHeight();
        }
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }
    
    /**
     * Remove Gesture and elements rectangles from the composition pane.
     * @param pane Pane to be removed from
     */
    @Override
    public void removeFromPane(Pane pane) {
        pane.getChildren().remove(boundingRect);
        elements.forEach((element) -> {
            element.removeFromPane(pane);
        });
    }
    
    /**
     * Checks if the mouse click should be a duration change. True if within
     * Constants.MARGIN.
     * @param event mouse click
     * @return if within the margin
     */
    @Override
    public boolean inLastFive(MouseEvent event) {
        return (event.getX() > x_coord + width - Constants.MARGIN);
    }
    
    /**
     * Change the Gesture rectangle duration, and calls for elements to be
     * adjusted proportionately.
     * @param event mouse drag
     */
    @Override
    public void moveDuration(MouseEvent event) {
        double tempWidth = event.getX() - x_coord + widthOffset;
        if (tempWidth < 5) tempWidth = 5;
        double proportion = tempWidth / width;
        boundingRect.setWidth(tempWidth);
        elements.forEach((element) -> {
            element.setProportions(x_coord, proportion);
        });
    }
    
    /**
     * When the user stops dragging the mouse, set Rectangle width
     * to final width.
     * @param event mouse click
     */
    @Override
    public void stopDuration(MouseEvent event) {
        double newWidth = event.getX() - x_coord + widthOffset;
        if (newWidth < Constants.MARGIN) newWidth = Constants.MARGIN;
        
        boundingRect.setWidth(newWidth);
    }
}
