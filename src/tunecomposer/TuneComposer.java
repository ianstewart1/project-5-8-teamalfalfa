/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.io.IOException;
import java.util.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This JavaFX app lets the user play scales.
 * @author Madi, Ian, Haley, Nathaniel
 * @since January 26, 2017
 */
public class TuneComposer extends Application {
    
     /**
     * A MidiPlayer for all notes to use.
     */
    public static final MidiPlayer PLAYER = new MidiPlayer(100,60);
    
    /**
     * The set of all notes, to be played later.
     */
    private static Set<Note> allNotes = new HashSet<Note>();

    /**
     * A line moves from left to right across the main pane. It crosses each
     * note as that note is played.
     */
    private static PlayLine playLine;

    /**
     * Controls the movement of playLine.
     */
    private Timeline timeline;
    
    /**
     * The background of the application.
     */
    @FXML
    private Group background;
    
    /**
     * The pane in which notes are constructed.
     */
    @FXML
    private Pane notePane;
    
    /**
     * The pane in which the play line is constructed and plays.
     */
    @FXML
    private BorderPane playLinePane;
    
    /**
     * Add the given note to the set of all notes, to be played later.
     */
    public static void addNote(Note note) {
        allNotes.add(note);
    }
    
    /**
     * Plays notes that have been added.
     * Called when the Play button is clicked.
     */
    public void startPlaying() {
        PLAYER.stop();
        PLAYER.clear();
        allNotes.forEach((note) -> {
            note.schedule();
        });
        
        PLAYER.play();      
        playLine.play(Note.getNotesEnd());
    }
    
    /**
     * Overload version of startPlaying() which ignores an ActionEvent.
     * @param ignored not used
     */
    public void startPlaying(ActionEvent ignored) {
        startPlaying();
    }

    /**
     * Stops playing.
     * Called when the Stop button is clicked. Does not remove notes from the
     * screen or from allNotes.
     */
    public void stopPlaying() {
        PLAYER.stop();
        playLine.stop();
        
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
     * Initializes FXML. Called automatically.
     * (1) adds 127 grey lines to background
     * (2) initializes the playLine(set to invisible)
     */
    public void initialize(){
        // Add grey lines to background
        for(int i = 1; i < 128; i++){
            Line row = new Line(0,10*i, 2000, 10*i);
            row.getStyleClass().add("row-divider");
            background.getChildren().add(row);
        }

        playLine = new PlayLine(playLinePane);

        // Let mouse events go through to notePane
        playLinePane.setMouseTransparent(true);
    }
    
    /**
     * Draw a rectangle representing a note.
     * @param noteRect a Rectangle to be drawn
     */
    public void addNoteRect(Rectangle noteRect) {
        notePane.getChildren().add(noteRect);
    }

    /**
     * Construct a note from a click. Called via FXML.
     * @param event a mouse click
     */
    public void handleClick(MouseEvent event) {
        Note note = new Note(this, event.getX(), event.getY());
        allNotes.add(note);
        note.draw();
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
        scene.getStylesheets().add("tunecomposer.css");
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
