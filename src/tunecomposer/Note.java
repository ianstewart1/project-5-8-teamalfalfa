/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


//TODO: Write Javadoc for classes
/**
 *
 * @author Ian Hawkins, Madi Crowley
 */
public class Note {

    /**
     * Play notes at maximum volume.
     */
    private static final int VOLUME = 127;
    private static final int RECTHEIGHT = 10;
    private static final int MAX_PITCH = 128;
    private static final int MARGIN = 5;
    private static final int DEFAULT_DURATION = 100;
    
    private static double lastNoteX = 0;
    
    private final Rectangle noteRect;
    private double x_coord;
    private double y_coord;       // Rounded to the grey line above
    private double rectWidth;
    private int startTime;
    private int pitch;
    private final Instrument instrument;
    private double xOffset;
    private double yOffset;
    private double widthOffset;
    
    private boolean isSelected;

    /**
     * TODO
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
        updateLastNote();
    }

    private void updateLastNote() {
        if (x_coord + rectWidth > lastNoteX) {
            lastNoteX = x_coord + rectWidth;
        }
    }

    public static double getNotesEnd() {
        return lastNoteX;
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
        
        updateLastNote();
    }
    
}
