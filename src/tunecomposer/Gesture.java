/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.Set;
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
        //Set<Playable> selected = TuneComposer.getSelected();
        //selected.forEach((element) -> {
            //elements.add(element);
        //});
        //Calculate boundingRect and set elements = to currently selected
    }
    
    @Override
    public boolean getSelected() {
        return isSelected;
    }

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
    
    
    
}
