/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
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
    
    private static double lastNoteX = 0;
    
    //NOTE: You haven't documented these private fields, but
    //      they are pretty self-explanatory.
    //TODO: That said, it seems a bit inelegant for a Note
    //      to maintain a reference to a TuneComposer. 
    //      If a TuneComposer object is calling the methods where
    //      this field is used, could it pass itself as a parameter?
    private TuneComposer composer;
    private double x;
    private double y;       // Rounded to the grey line above
    private int startTime;
    private int pitch;

    //TODO: Document constructors as well as other methods
    public Note(TuneComposer composer, double x, double y) {
        this.composer = composer;

        startTime = (int)x;
        //TODO: Where does 128 come from?
        //TODO: (Otherwise you are doing relatively well at 
        //      avoiding magic numbers.)
        pitch = 128-(int)y/RECTHEIGHT;
        this.x = x;
        this.y = y - (y%RECTHEIGHT);
        updateLastNote();
        TuneComposer.addNote(this);
    }

    //TODO: Document even private methods!
    private void updateLastNote() {
        if (x > lastNoteX) {
            lastNoteX = x;
        }
    }

    public static double getNotesEnd() {
        return lastNoteX + 100;
        //TODO: Where does 100 come from?
    }
    
    public void draw() {
        Rectangle noteRect = new Rectangle(x, y, RECTWIDTH, RECTHEIGHT);
        noteRect.getStyleClass().add("note-rect");
        //TODO: Delete debugging code before merging to master branch.
        System.out.println("Rectangle: " + x + ", " + y + ", " + RECTWIDTH + ", " + RECTHEIGHT);
        composer.addNoteRect(noteRect);
    }
    
    public void schedule() {
        TuneComposer.PLAYER.addNote(pitch, VOLUME, startTime, DURATION, 0, 0);
    }
    
    public String toString() {
        return "Start Time: " + startTime + ", Pitch: " + pitch;
    }
    
    
}
