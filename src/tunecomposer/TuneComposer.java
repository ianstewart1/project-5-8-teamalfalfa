/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

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
    
    private Set<Note> allNotes = new HashSet<Note>();
    
    /**
     * Constructs a new ScalePlayer application.
     */
    public TuneComposer() {
        //PLAYER = new MidiPlayer(1,60);
    }
    
    /**
     * Plays notes that have been added.
     * Called when the Play button is clicked.
     */
    
    public void startPlaying() {
        //TODO Start red line movement
        PLAYER.stop();
        PLAYER.clear();
        for (Note note : allNotes) {
            note.schedule();
        }
        PLAYER.play();      
    }
    
    public void startPlaying(ActionEvent ignored) {
        startPlaying();
    }

    /**
     * Stops playing.
     * Called when the Stop button is clicked.
     */
    public void stopPlaying() {
        //TODO Stop line movement
        PLAYER.stop();
    }
    
    public void stopPlaying(ActionEvent ignored) {
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
    
    @FXML
    private Group background;
     
    public void initialize(){
        for(int i = 0; i < 128; i++){
            Line row = new Line(0,10*i, 2000, 10*i);
            row.setStroke(Color.LIGHTGREY);
            background.getChildren().add(row);
        }
    }
    
    @FXML
    private Group notePane;
    
    @FXML
    private Group playLinePane; //I think that we can now refer to these in other functions.
    
    
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
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Note note = new Note(event.getSceneX(), event.getSceneY());
                allNotes.add(note);
                note.draw();
            }
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
