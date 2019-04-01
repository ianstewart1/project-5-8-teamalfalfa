/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

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

    public Gesture(){
        isSelected = true;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void move(MouseEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopMoving(MouseEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Bounds getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double getX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double getWidth() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void schedule() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List getNode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
