/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


/**
 * Note class creates a Rectangle representing the note to be played
 * @author Ian Hawkins, Madi Crowley, Ian Stewart, Melissa Kohl
 */
public class Note {
    
    public static double lastNote = 0;

    /**
     * Constants for playing note in MidiPlayer
     */
    private static final int VOLUME = 127;
    private static final int MAX_PITCH = 128;
    private static final int DEFAULT_DURATION = 100;
    /**
     * Constants for Rectangle in composition panel
     */
    private static final int RECTHEIGHT = 10;
    private static final int MARGIN = 5;
    
    /**
     * End time for MidiPlayer
     */
    private static double lastNoteX = 0;
    
    /**
     * Note fields for creating rectangle and playing note
     */

    private final Rectangle noteRect;
    private double x_coord;
    private double y_coord;
    private double rectWidth;
    private int startTime;
    private int pitch;
    private final Instrument instrument;
    
    /**
     * Offsets for dragging Rectangle
     */
    private double xOffset;
    private double yOffset;
    private double widthOffset;
    
    /**
     * Is this note currently selected
     */
    private boolean isSelected;

    /**
     * Creates new selected Rectangle
     * @param x
     * @param y
     * @param inst 
     */
    public Note(double x, double y, Instrument inst) {
        startTime = (int) x;
        pitch = MAX_PITCH - (int) y / RECTHEIGHT;
        x_coord = x;
        y_coord = y - ( y % RECTHEIGHT);
        instrument = inst;
        rectWidth = DEFAULT_DURATION;
        noteRect = new Rectangle(x_coord, y_coord, rectWidth, RECTHEIGHT);
        noteRect.getStyleClass().addAll("selected", instrument.toString());
        noteRect.setMouseTransparent(false);
        isSelected = true;
    }

    public void updateLastNote() {
        if (x_coord + rectWidth > lastNote) {
            lastNote = x_coord + rectWidth;
        }
    }
    
    public Rectangle getRectangle() {
        return noteRect;
    }
    
    public void schedule() {
        TuneComposer.PLAYER.addNote(pitch, VOLUME, startTime, (int)rectWidth, instrument.ordinal(), 0);
    }
    
    public boolean getSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
        if (selected) {
            noteRect.getStyleClass().clear();
            noteRect.getStyleClass().addAll("selected", instrument.toString());
        } else {
            noteRect.getStyleClass().clear();
            noteRect.getStyleClass().addAll("unselected", instrument.toString());
        }
    }
    
    public void setMovingCoords(MouseEvent event) {
        xOffset = event.getX() - x_coord;
        yOffset = event.getY() - y_coord;
    }
    
    public void moveNote(MouseEvent event) {
        noteRect.setX(event.getX() - xOffset);
        noteRect.setY(event.getY() - yOffset );
    }
    
    public void stopMoving(MouseEvent event) {
        double x = event.getX() - xOffset;
        double y = event.getY() - yOffset;
        startTime = (int) x;
        pitch = MAX_PITCH - (int) y / RECTHEIGHT;
        x_coord = x;
        y_coord = y - (y % RECTHEIGHT);
        
        noteRect.setX(x_coord);
        noteRect.setY(y_coord);
    }
    
    public boolean inLastFive(MouseEvent event) {
        return (event.getX() > x_coord + rectWidth - MARGIN);
    }
    
    public void setMovingDuration(MouseEvent event) {
        widthOffset = x_coord + rectWidth - event.getX();
    }
    
    public void moveDuration(MouseEvent event) {
        double tempWidth = event.getX() - x_coord + widthOffset;
        if (tempWidth < 5) tempWidth = 5;
        noteRect.setWidth(tempWidth);
    }
    
    public void stopDuration(MouseEvent event) {
        rectWidth = event.getX() - x_coord + widthOffset;
        if (rectWidth < MARGIN) rectWidth = MARGIN;
        
        noteRect.setWidth(rectWidth);
    }
    
}
