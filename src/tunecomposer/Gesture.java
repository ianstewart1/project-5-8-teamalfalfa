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

    public Gesture(){
        isSelected = true;
        elements = new HashSet();
        Set<Playable> selected = TuneComposer.getSelected();
        selected.forEach((element) -> {
            elements.add(element);
        });
        boundingRect = calculateBounds();
        boundingRect.getStyleClass().add("selectedGesture");
        x_coord = boundingRect.getX();
        y_coord = boundingRect.getY();
        // CHECK IF ANYTHING EVEN IN THE GROUP BEFORE ALL THIS
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
    
    public Rectangle getBoundingRect() {
        return boundingRect;
    }
    
    @Override
    public void schedule() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
