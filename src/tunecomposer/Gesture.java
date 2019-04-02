/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
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

    public Gesture(){
        isSelected = true;
        Set<Playable> selected = TuneComposer.getSelected();
        selected.forEach((element) -> {
            elements.add(element);
        });
        //Calculate boundingRect and set elements = to currently selected
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
    public List getNodeList() {
        List nodeList = new ArrayList();
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
    
    
    
}
