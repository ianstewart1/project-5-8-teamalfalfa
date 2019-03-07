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
import javax.sound.midi.ShortMessage;

/**
 * This JavaFX app lets the user play scales.
 * @author Ian[0], Ian[1], Angie, Melissa
 * @since January 26, 2017
 */
public class TuneComposer extends Application {

     /**
     * A MidiPlayer for all notes to use.
     */
    public static final MidiPlayer PLAYER = new MidiPlayer(100,60);

    /**
     * A list of instrument values to associate with MidiPlayer channels
     */
    private final int[] timbreList = new int[] {0, 6, 12, 19, 21, 24, 40, 60};

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
     * The line wrapped by PlayLine.
     */
    @FXML
    private Line movingLine;

    /**
     * An area for click-and-drag note selection.
     */
    @FXML
    private Rectangle selectionRectangle;

    /**
     * A group of sidebar radio buttons for selecting an instrument.
     */
    @FXML
    private ToggleGroup instrumentToggle;

    private enum Instrument {
//        PIANO (0),
//        HARPSICHORD (6),
//        MARIMBA (12),
//        CHURCH_ORGAN (19),
//        ACCORDION (21),
//        GUITAR (24),
//        VIOLIN (40),
//        FRENCH_HORN (60);
        PIANO,
        HARPSICHORD,
        MARIMBA,
        CHURCH_ORGAN,
        ACCORDION,
        GUITAR,
        VIOLIN,
        FRENCH_HORN;

//        private int timbre;
//
//        Instrument(int value) {
//           timbre = value;
//        }
//
//        public int getTimbre(){
//            return timbre;
//        }

        @Override
        public String toString() {
            switch(this) {
                case PIANO:         return "piano";
                case HARPSICHORD:    return "harpsichord";
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
     * Get the instrument currently selected in the sidebar.
     * @return the selected instrument
     */
    private Instrument getInstrument() {
        RadioButton selectedButton = (RadioButton)instrumentToggle.getSelectedToggle();
        String instrument = selectedButton.getText();
        switch(instrument) {
            case "Piano":           return Instrument.PIANO;
            case "Harpsichord":     return Instrument.HARPSICHORD;
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
        System.out.println("Cluck");
        Note note = new Note(event.getX(), event.getY());
        allNotes.add(note);
        notePane.getChildren().add(note.draw());
    }

    public void startDrag(MouseEvent event) {

        // TODO We might choose not to use this method and only use
        // `continueDrag` to detect the start of a drag.

        // TODO If the ctrl key isn't pressed, deselect all notes.
        //      If the ctrl key is pressed, add to the existing selection.

        // Put first rectangle corner at cursor
        selectionRectangle.setX(event.getX());
        selectionRectangle.setY(event.getY());

        // Make rectangle visible
        selectionRectangle.setVisible(true);

        // TODO These lines are for testing
        System.out.println("Start drag");
        selectionRectangle.setWidth(100);
        selectionRectangle.setHeight(70);
    }

    public void continueDrag(MouseEvent event) {
        System.out.println("Drug");
        // TODO Select all notes within the rectangle and deselect others.
        // We need a container of sorta-selected notes that fall within the
        // rectangle but aren't fully selected yet.

        // TODO If we've started dragging, update the width and height of the
        // rectangle.

        // TODO It might make sense to have a SelectionRectangle class with its own
        // properties, so that we can keep track of how far the mouse has been
        // dragged.
        // Alternatively, we can get the coordinates of the rectangle's first
        // corner and compare those values to the click coordinates to
        // calculate the rectangle's new width and height.
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

        for(int i=0; i<8; i++){
            PLAYER.addMidiEvent(ShortMessage.PROGRAM_CHANGE + i, timbreList[i], 0, 0, 0);
        }

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
