/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
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
    
    private boolean isSelected;

    /**
     * TODO
     * @param x
     * @param y
     * @param selected 
     */
    public Note(double x, double y, String inst) {
        startTime = (int)x;
        pitch = MAX_PITCH-(int)y/RECTHEIGHT;
        x_coord = x;
        y_coord = y - (y%RECTHEIGHT);
        noteRect = new Rectangle(x_coord, y_coord, RECTWIDTH, RECTHEIGHT);
        noteRect.getStyleClass().addAll("selected", inst);
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
        TuneComposer.PLAYER.addNote(pitch, VOLUME, startTime, DURATION, 0, 0);
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
            noteRect.getStyleClass().add("selected");
        } else {
            noteRect.getStyleClass().clear();
            noteRect.getStyleClass().add("unselected");
        }
    }
    
    public void toggleSelected() {
        isSelected = !isSelected;
    }
}