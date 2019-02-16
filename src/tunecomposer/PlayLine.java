/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Creates and manages the moving red line that moves as the composition plays
 * @author larsonnd
 */
public class PlayLine {
    
//    private boolean visible;
    private final Rectangle movingLine;
    private final Timeline timeline;
//    private double runLength;
    public Stage stage;
    public Group group;
    
    
    public PlayLine(Stage stage, Group group) {
        
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        
        movingLine = new Rectangle(0,0,1,100);
        movingLine.setFill(Color.RED);

    }
    
    public void play() {
//        this.visible = true;
        
        KeyValue xValue = new KeyValue(movingLine.xProperty(), stage.getWidth()); //figure out when this works
        KeyFrame kf = new KeyFrame(Duration.millis(1500), xValue);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
    public void stop() {
        timeline.stop();
        
    }
    
    public void pause() {
        timeline.pause();
    }
    
    
}
