/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Creates and manages the moving red line that moves as the composition plays
 * @author Nathaniel Larson
 * @author Ian Stewart, Melissa Kohl
 */
public class PlayLine {
    
    /**
     * Fields for the PlayLine class
     */
    private final Line movingLine;
    private TranslateTransition path;
    
    /**
     * Check if TuneComposer is currently playing
     */
    private boolean playing;
    
    /**
     * Initializes a new PlayLine object in the newPane
     * @param line a layout pane, now implemented with AnchorPane
     */
    public PlayLine(Line line) {
        
        // initialize the red line
        movingLine = line;
        movingLine.setVisible(false);
        
        // initialize TranslateTransition
        path = new TranslateTransition(Duration.seconds(0), movingLine);
        path.setInterpolator(Interpolator.LINEAR);
        path.setOnFinished((ActionEvent e) -> { resetPlayLine(); });
    }
    
    private void resetPlayLine() {
        movingLine.setVisible(false);
        movingLine.setTranslateX(-1);
        playing = false;
    }
    
    /**
     * Make a red line track across the composition at constant speed
     * @param endXCoordinate the x coordinate of the final note of 
     *                       the composition
     */
    public void play(double endXCoordinate) {
        movingLine.setEndX(0); // place playLine back at the beginning 
        movingLine.setStartX(0);
        movingLine.setVisible(true);
        
        path.setToX(endXCoordinate);
        // duration calculated for constant speed of 100 pixels per second
        Duration duration = Duration.seconds(endXCoordinate / 
                                             Constants.DEFAULT_DURATION); 
        path.setDuration(duration);
        System.out.println(duration);
        path.play();
 
        playing = true;
    }
    
    /**
     * Gets the current state of the PlayLine
     * @return Boolean indicating PlayLine status
     */
    public boolean isPlaying() {
        return playing;
    }
    
    /**
     * Stop the red line and make it disappear
     */
    public void stop() {
        path.stop();
        resetPlayLine();
    } 
    
}
