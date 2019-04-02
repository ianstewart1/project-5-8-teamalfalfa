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
import javafx.scene.shape.Rectangle;

/**
 *
 * @author jamesbgd
 */
public class Gesture implements Playable{
    
    private boolean isSelected;
    protected Rectangle boundingRect;
    protected Set<Playable> elements;
    private double x_coord;
    private double y_coord;
    
    
    /**
     * Offsets for dragging Rectangle
     */
    private double xOffset;
    private double yOffset;
    private double widthOffset;

    public Gesture(Set<Playable> selected){
        isSelected = true;
        elements = new HashSet();
        selected.forEach((element) -> {
            elements.add(element);
        });
        boundingRect = calculateBounds();
        boundingRect.getStyleClass().add("selectedGesture");
        x_coord = boundingRect.getX();
        y_coord = boundingRect.getY();
        // CHECK IF ANYTHING EVEN IN THE GROUP BEFORE ALL THIS
    }
    
    /**
     * Tells whether the gesture is selected or not.
     * @return a boolean
     */
    @Override
    public boolean getSelected() {
        return isSelected;
    }
    
    /**
     * returns the elements grouped in the gesture
     * @return Set of playables that are in the gesture.
     */
    protected Set<Playable> getElements() {
        return elements;
    }

    /**
     * Updates a gesture's status to selected and updates the style.
     * This function also updates the style of the elements within gesture
     * to look selected
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

    @Override
    public void setMovingCoords(MouseEvent event) {
        xOffset = event.getX() - x_coord;
        yOffset = event.getY() - y_coord;
        // need to add this for all of the elements
    }

    @Override
    public void move(MouseEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopMoving(MouseEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Returns the Bounds object for the gesture's bounding rectangle in the
     * pane.
     * @return the Bounds
     */
    @Override
    public Bounds getBounds() {
        return boundingRect.getLayoutBounds();
    }
    
    /**
     * Returns the x coordinate of the upper left corner of the gesture
     * @return the x coordinate
     */
    @Override
    public double getX() {
        return x_coord;
    }
    
    /**
     * returns the width of the gesture's bounding rectangle
     * @return the width of the gesture as a double
     */
    @Override
    public double getWidth() {
        return boundingRect.getWidth();
    }
    
    /**
     * Adds all of the notes contained within the gesture and any nested
     * gestures to the midiplayer.
     */
    public Rectangle getBoundingRect() {
        return boundingRect;
    }
    
    @Override
    public void schedule() {
        elements.forEach((element) -> {
            element.schedule(); // Dynamic runtime invocation determines whether
                                // Note's schedule or Gesture's schedule is used
        
        });
    }
    
    /**
     * returns a list of all of the rectangles of each note in the gesture's
     * elements. this includes notes within nested gestures.
     * @return List of rectangles
     */
    @Override
    public List<Rectangle> getNodeList() {
        List<Rectangle> nodeList = new ArrayList();
        elements.forEach((element) -> {
            if (element instanceof Note) {
                nodeList.addAll(element.getNodeList());
            }
            else {
                element.getNodeList();
            }
        });
        return nodeList;
    }
    
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
    
    
    
}
