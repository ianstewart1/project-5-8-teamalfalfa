/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Ian Hawkins, Madi Crowley
 */
public class Note {

    /**
     * Play notes at maximum volume.
     */
    private static final int VOLUME = 127;
    private static final int DURATION = 1;
    private static final int RECTWIDTH = 100;
    private static final int RECTHEIGHT = 10;
    
    
    private TuneComposer composer;
    private double x;
    private double y;       // Rounded to the grey line above
    private int startTime;
    private int pitch;
    
    public Note(TuneComposer composer, double x, double y) {
        this.composer = composer;

        //TODO fix startTime and pitch
        startTime = (int)x/RECTWIDTH;
        pitch = 128-(int)y/RECTHEIGHT;
        this.x = x;
        this.y = y - (y%RECTHEIGHT);
        TuneComposer.addNote(this);
    }
    
    public void draw() {
        // TODO Figure out how to draw rectangles in a group in a stackpane
        Rectangle noteRect = new Rectangle(x, y, RECTWIDTH, RECTHEIGHT);
        System.out.println("Rectangle: " + x + ", " + y + ", " + RECTWIDTH + ", " + RECTHEIGHT);
        composer.addNoteRect(noteRect);
    }
    
    public void schedule() {
        TuneComposer.PLAYER.addNote(pitch, VOLUME, startTime, DURATION, 0, 0);
    }
    
    public String toString() {
        // TODO
        return "Start Time: " + startTime + ", Pitch: " + pitch;
    }
    
    
}
