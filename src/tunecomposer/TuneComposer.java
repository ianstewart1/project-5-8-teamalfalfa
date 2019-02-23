/*
 * CS 300-A, 2017S
 *
 * TODO
 * Set rectangle style in CSS
 * Stop playLine at the end of the last note
 * Set time scale to 100 ticks per beat and 1 beat per second
 */
package tunecomposer;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This JavaFX app lets the user play scales.
 * @author Janet Davis 
 * @author SOLUTION - PROJECT 1
 * @since January 26, 2017
 */
public class TuneComposer extends Application {
    
     /**
     * A MidiPlayer for all notes to use.
     */
    public static final MidiPlayer PLAYER = new MidiPlayer(1,60);
    
//    private Stage primaryStage;
//    private Timeline timeline;
    private static Set<Note> allNotes = new HashSet<Note>();

    private Timeline timeline;
    private Rectangle playLine = new Rectangle(0, 0,1,1280);
    
    /**
     * Constructs a new ScalePlayer application.
     */
    public TuneComposer() {
        //PLAYER = new MidiPlayer(1,60);
        playLine.setFill(Color.RED);
        playLine.setVisible(false);
    }
    
    public static void addNote(Note note) {
        allNotes.add(note);
    }
    
    /**
     * Plays notes that have been added.
     * Called when the Play button is clicked.
     */
    public void startPlaying() {
        //TODO Start red line movement
        PLAYER.stop();
        PLAYER.clear();
        allNotes.forEach((note) -> {
            note.schedule();
        });
        
        PLAYER.play();      
        playLineMove(2000); //TODO, pass correct x coordinate
    }
    
    public void startPlaying(ActionEvent ignored) {
        startPlaying();
    }

    /**
     * Stops playing.
     * Called when the Stop button is clicked.
     */
    public void stopPlaying() {
        PLAYER.stop();
        timeline.stop();
        playLine.setVisible(false);
        
    }

    /**
     * When the user clicks the "Stop playing" button, stop playing the scale.
     * @param event the button click event
     */
    @FXML 
    protected void stopPlaying(ActionEvent event) {
        stopPlaying();
    }
    
    /**
     * When the user clicks the "Exit" menu item, exit the program.
     * @param event the menu selection event
     */
    @FXML
    protected void handleExitMenuItemAction(ActionEvent event) {
        System.exit(0);
    }
    
    /**
     * The background of the application
     */
    @FXML
    private Group background;
    
    /**
     * The pane in which notes are constructed
     */
    @FXML
    private Pane notePane;

    
    /**
     * The pane in which the play line is constructed and plays
     */
    @FXML
    private BorderPane playLinePane;
    
    /**
     * Initializes FXML: 
     * (1) adds 127 gray lines to background
     * (2) adds the playLine (set to invisible) 
     */
    public void initialize(){
        for(int i = 1; i < 128; i++){
            Line row = new Line(0,10*i, 2000, 10*i);
            row.setStroke(Color.LIGHTGREY);
            background.getChildren().add(row);
        }

        playLinePane.setMouseTransparent(true);
        playLinePane.getChildren().add(playLine);
    }
    
    public void addNoteRect(Rectangle noteRect) {
        notePane.getChildren().add(noteRect);
    }

    public void handleClick(MouseEvent event) {
        Note note = new Note(this, event.getX(), event.getY());
        allNotes.add(note);
        note.draw();
    }
    
    /**
     * Make a red line track across the composition at constant speed
     * @param endXCoordinate the x coordinate of the final note of the composition
     */
    public void playLineMove(double endXCoordinate){
        playLine.setX(0); //place playLine back at the beginning 
        playLine.setVisible(true);
        
        timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue keyValueX = new KeyValue(playLine.xProperty(), endXCoordinate);
        
        //duration calculated for constant speed of 100 pixels per second
        Duration duration = Duration.millis(endXCoordinate*10); 
        
        //when finsihed, playLine will disappear
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                playLine.setVisible(false);
            }
        };
 
        KeyFrame keyFrame = new KeyFrame(duration, onFinished, keyValueX);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * Construct the scene and start the application.
     * @param primaryStage the stage for the main window
     * @throws java.io.IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {                
        Parent root = FXMLLoader.load(getClass().getResource("TuneComposer.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });
        primaryStage.show();
    }


    /**
     * Launch the application.
     * @param args the command line arguments are ignored
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
