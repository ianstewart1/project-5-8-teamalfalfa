/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;


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
    private static final int DURATION = 100;
    private static final int RECTWIDTH = 100;
    private static final int RECTHEIGHT = 10;
    private static final int MAX_PITCH = 128;
    
    private static double lastNoteX = 0;
    
    private Rectangle noteRect;
    private double x_coord;
    private double y_coord;       // Rounded to the grey line above
    private int startTime;
    private int pitch;
    private final Instrument instrument;
    private double xOffset;
    private double yOffset;
    
    private boolean isSelected;

    /**
     * TODO
     * @param x
     * @param y
     * @param selected 
     */
    public Note(double x, double y, Instrument inst) {
        startTime = (int) x;
        pitch = MAX_PITCH - (int) y / RECTHEIGHT;
        x_coord = x;
        y_coord = y - ( y % RECTHEIGHT);
        instrument = inst;
        noteRect = new Rectangle(x_coord, y_coord, RECTWIDTH, RECTHEIGHT);
        noteRect.getStyleClass().addAll("selected", instrument.toString());
        noteRect.setMouseTransparent(false);
        isSelected = true;
        updateLastNote();
    }

    //TODO: Document even private methods!
    private void updateLastNote() {
        if (x_coord > lastNoteX) {
            lastNoteX = x_coord;
        }
    }

    public static double getNotesEnd() {
        return lastNoteX + 100;
        //TODO: Where does 100 come from?
    }
    
    public Rectangle getRectangle() {
        return noteRect;
    }
    
    public void schedule() {
        TuneComposer.PLAYER.addNote(pitch, VOLUME, startTime, DURATION, instrument.ordinal(), 0);
    }
    
    public String toString() {
        return "Start Time: " + startTime + ", Pitch: " + pitch;
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
    
    public void toggleSelected() {
        isSelected = !isSelected;
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
    
}
