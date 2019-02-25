/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
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
    
    private final Rectangle movingLine;
    private Timeline timeline;
    
    /**
     * Initializes a new PlayLine object in the newPane
     * @param pane a layout pane, currently implemented with BorderPane
     */
    public PlayLine(Pane pane) {
        
        //initialize Timeline
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        
        //initialize the red line
        movingLine = new Rectangle(0,0,1,1280);
        movingLine.getStyleClass().add("play-line");
        movingLine.setVisible(false);
        
        //add the red line to correct pane of FXML
        pane.getChildren().add(movingLine);

    }
    
    /**
     * Make a red line track across the composition at constant speed
     * @param endXCoordinate the x coordinate of the final note of the composition
     */
    public void play(double endXCoordinate) {
        timeline.getKeyFrames().clear();
        movingLine.setX(0); //place playLine back at the beginning 
        movingLine.setVisible(true);
        
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue keyValueX = new KeyValue(movingLine.xProperty(), endXCoordinate);
        
        //duration calculated for constant speed of 100 pixels per second
        Duration duration = Duration.millis(endXCoordinate*10); 
        
        //when finsihed, playLine will disappear
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                movingLine.setVisible(false);
            }
        };
 
        KeyFrame keyFrame = new KeyFrame(duration, onFinished, keyValueX);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
    
    /**
     * Stop the red line and make it disappear
     */
    public void stop() {
        timeline.stop();
        movingLine.setVisible(false);
        
    } 
    
}
