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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
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
    private static Set<Note> allNotes;
    //NOTE: Hmm, it seems like you are duplicating information about notes.
    //      This could make things difficult later.

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
    private AnchorPane playLinePane;
    
    /**
     * TODO
     */
    @FXML
    private Line movingLine;
    
    /**
     * TODO
     */
    @FXML
    private ToggleGroup instrumentToggle;
    
    private enum Instrument {
        PIANO, 
        HARPSICORD, 
        MARIMBA, 
        CHURCH_ORGAN, 
        ACCORDION, 
        GUITAR, 
        VIOLIN, 
        FRENCH_HORN;
        
        @Override
        public String toString() {
            switch(this) {
                case PIANO:         return "piano";
                case HARPSICORD:    return "harpsicord";
                case MARIMBA:       return "marimba";
                case CHURCH_ORGAN:  return "church-organ";
                case ACCORDION:     return "accordion";
                case GUITAR:        return "guitar";
                case VIOLIN:        return "violin";
                case FRENCH_HORN:   return "french-horn";
                default: throw new IllegalArgumentException();
            }
        }
    }
    
    
    public TuneComposer() {
        allNotes = new HashSet<Note>();
    }
    
    /**
     * Add the given note to the set of all notes, to be played later.
     * @param note note added to composition
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
        //NOTE: Nice use of higher-order programming!
        //NOTE: You could write this all on one line and it would still be 
        //      readable.
        
        PLAYER.play();      
        playLine.play(Note.getNotesEnd());
    }
    
    //TODO: Overloading doesn't seem right here. Could you name the 
    //      event handlers so that it's clear they are event handlers?
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
    public void initialize() {
        // Add grey lines to background
        for(int i = 1; i < 128; i++){
            Line row = new Line(0,10*i, 2000, 10*i);
            row.getStyleClass().add("row-divider");
            background.getChildren().add(row);
        }

        playLine = new PlayLine(movingLine);

        // Let mouse events go through to notePane
        playLinePane.setMouseTransparent(true);
        //NOTE: Good that you figured this out!
    }
    
    /**
     * TODO
     */
    private Instrument getInstrument() {
        RadioButton selectedButton = (RadioButton)instrumentToggle.getSelectedToggle();
        String instrument = selectedButton.getText();
        switch(instrument) {
            case "Piano":           return Instrument.PIANO;
            case "Harpsicord":      return Instrument.HARPSICORD;
            case "Marimba":         return Instrument.MARIMBA;
            case "Church Organ":    return Instrument.CHURCH_ORGAN;
            case "Accordion":       return Instrument.ACCORDION;
            case "Guitar":          return Instrument.GUITAR;
            case "Violin":          return Instrument.VIOLIN;
            case "French Horn":     return Instrument.FRENCH_HORN;
            default: 
                throw new IllegalArgumentException("Unrecognized Instrument");
        }
    }
    
    /**
     * Construct a note from a click. Called via FXML.
     * @param event a mouse click
     */
    public void handleClick(MouseEvent event) {
        // TODO: stopPlaying();
        if (! event.isControlDown()) {
            selectAll(false);
        }
        Instrument instrument = getInstrument();
        Note note = new Note(event.getX(), event.getY(), instrument.toString());
        allNotes.add(note);
        notePane.getChildren().add(note.getRectangle());
    }
    
    /**
     * TODO
     * @param event 
     */
    @FXML
    void handleDelete(ActionEvent event) {
        Collection toDelete = new ArrayList();
        allNotes.forEach((note) -> {
            if (note.getSelected()) {
                toDelete.add(note);
                notePane.getChildren().remove(note.getRectangle());
            }
        });
        allNotes.removeAll(toDelete);
    }
    
    /**
     * TODO
     * @param event 
     */
    @FXML
    void handleSelectAll(ActionEvent event) {
        selectAll(true);
    }
    
    private void selectAll(boolean selected) {
        allNotes.forEach((note) -> {
            note.setSelected(selected);
        });
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
