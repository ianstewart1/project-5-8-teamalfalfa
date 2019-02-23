/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

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
    
    
    private double x;
    private double y;
    private int startTime;
    private int pitch;
    
    public Note(double x, double y) {
        this.x = x;
        this.y = y;
        //TODO fix startTime and pitch
        startTime = (int)x/100;
        pitch = 128-(int)y/10;
        TuneComposer.addNote(this);
    }
    
    public void draw() {
        // TODO Figure out how to draw rectangles in a group in a stackpane
    }
    
    public void schedule() {
        TuneComposer.PLAYER.addNote(pitch, VOLUME, startTime, DURATION, 0, 0);
    }
    
    public String toString() {
        // TODO
        return "Start Time: " + startTime + ", Pitch: " + pitch;
    }
    
    
}
