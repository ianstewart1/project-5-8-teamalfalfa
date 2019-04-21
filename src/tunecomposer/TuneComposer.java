/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.io.IOException;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This JavaFX app lets the user play scales.
 * @author Ian Stewart, Gavin James/Beckham, Nathaniel Larson, Paul Milloy
 */
public class TuneComposer extends Application {

     /**
     * A MidiPlayer for all notes to use.
     */
    public static final MidiPlayer PLAYER = 
        new MidiPlayer(Constants.RESOLUTION, Constants.BEATS_PER_MINUTE);

    /**
     * The set of all notes, to be played later.
     */
    private static Set<Playable> allPlayables;
    
    /**
     * A line moves from left to right across the main pane. It crosses each
     * note as that note is played.
     */
    private static PlayLine playLine;
    
    /**
     * Boolean flags to control flow when user clicks in composition panel.
     */
    private boolean clickInPane = true;
    private boolean changeDuration = false;
    private boolean isDragSelecting = false;

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
    private SelectionArea selection;

    /**
     * Rectangle used in click-and-drag note selection.
     */
    @FXML
    private Rectangle selectRect;
    
    /**
     * VBox used to hold the ToggleGroup of instruments.
     */
    @FXML
    private VBox instrumentPane;

    /**
     * A group of sidebar radio buttons for selecting an instrument.
     */
    @FXML
    private ToggleGroup instrumentToggle;
    
    /**
     * All of the menu buttons that need to be disabled on occasion.
     */
    @FXML
    private MenuItem playButton, stopButton, groupButton, ungroupButton, 
                     selectAllButton, deleteButton, undoButton, redoButton,
                     copyButton, cutButton, pasteButton;

    /**
     * Constructor initializes Note sets.
     */
    public TuneComposer() {
        allPlayables = new HashSet();
    }
    
    /**
     * Initializes FXML. Called automatically.
     * (1) adds 127 gray lines to background
     * (2) initializes the playLine(set to invisible)
     */
    public void initialize() {
        // Add gray lines to background
        for (int i = 1; i < 128; i++) {
            Line row = new Line(0, 10 * i, 2000, 10 * i);
            row.getStyleClass().add("row-divider");
            background.getChildren().add(row);
        }

        playLine = new PlayLine(movingLine);

        // Let mouse events go through to notePane
        playLinePane.setMouseTransparent(true);

        selection = new SelectionArea(selectRect);
        
        setupInstruments();
        updateMenuClick();
    }
    
    private void setupInstruments() {
        boolean first = true;
        for (Instrument inst : Instrument.values()) {
            RadioButton rb = new RadioButton();
            rb.setText(inst.getDisplayName());
            rb.getStyleClass().add(inst.getStyleClassName());
            rb.setUserData(inst);
            rb.setToggleGroup(instrumentToggle);
            rb.setMinWidth(instrumentPane.getPrefWidth());
            instrumentPane.getChildren().add(rb);
            if (first) {
                instrumentToggle.selectToggle(rb);
                first = false;
            }
        }
    }
    
    /**
     * Get the instrument currently selected in the sidebar.
     * @return the selected instrument
     */
    private Instrument getInstrument() {
        RadioButton selectedButton = (RadioButton)instrumentToggle.getSelectedToggle();
        return (Instrument) selectedButton.getUserData();
    }
    
    /**
     * This method is used to remove a gesture from the pane. 
     * @param gesture is a Gesture
     */
    protected void removeGesture(Gesture gesture){
        notePane.getChildren().remove(gesture.boundingRect);
    }


    /**
     * Add the given note to the set of all notes, to be played later.
     * @param note note added to composition
     */
    public static void addNote(Note note) {
        allPlayables.add(note);
    }
    
    /**
     * Find the last note so we know when to stop the player and red line.
     * @return lastNote x_coordinate of the last note in the pane, where 
     *                  the red line should stop.
     */
    public double findLastNote() {
        double lastNote = 0;       
        for(Playable note : allPlayables){
           double noteEnd = note.getX() + note.getWidth();
            if(noteEnd > lastNote){
                lastNote = noteEnd;
            } 
        }        
        return lastNote;
    }

    /**
     * Plays notes that have been added.
     * Called when the Play button is clicked.
     */
    public void startPlaying() {
        stopPlaying();
        Instrument.addAll(PLAYER);
        allPlayables.forEach((note) -> {
            note.schedule();
        });
        PLAYER.play();
        playLine.play(this.findLastNote());
    }

    /**
     * When user selects "Start" menu item, start playing composition
     * @param ignored not used
     */
    @FXML
    public void handleStartPlaying(ActionEvent ignored) {
        startPlaying();
        updateMenuClick();
    }
    
    /**
     * Stops playing composition.
     * Called when the Stop button is clicked. Does not remove notes from the
     * screen or from allPlayables.
     */
    public void stopPlaying() {
        PLAYER.stop();
        PLAYER.clear();
        playLine.stop();

    }
    
    /**
     * Toggles the disabling of MenuItems based on if their actions are 
     * feasible.
     */
    public void updateMenuClick() {
        boolean noNotes = allPlayables.isEmpty();
        Set<Playable> selected = selectedSet();
        int numSelected = selected.size();
        playButton.setDisable(noNotes);
        stopButton.setDisable(!playLine.isPlaying());
        groupButton.setDisable(numSelected < 2);
        ungroupButton.setDisable(!isGesture(selected));
        selectAllButton.setDisable(noNotes);
        deleteButton.setDisable(numSelected == 0);
        undoButton.setDisable(UndoRedo.isUndoEmpty());
        redoButton.setDisable(UndoRedo.isRedoEmpty());
        copyButton.setDisable(numSelected < 1);
        cutButton.setDisable(numSelected < 1);
        pasteButton.setDisable(true); //TODO: check if clipboard is playables
    }
    
    /**
     * When the user selects "Stop" menu item, stop playing composition
     * @param ignored not used
     */
    @FXML
    protected void handleStopPlaying(ActionEvent ignored) {
        stopPlaying();
        updateMenuClick();
    }

    /**
     * Returns a list of all of the selected Playables in the composition screen
     * @return a Set of Playables
     */
    protected Set<Playable> selectedSet() {
        Set<Playable> selected = new HashSet<>();
        allPlayables.forEach((element) -> {
            if (element.getSelected()){
                selected.add(element);
            }
        });
        return selected;
    }
    
    private boolean isGesture(Set<Playable> selected) {
        for (Playable element: selected) {
            if (element instanceof Gesture) return true;
        }
        return false;
    }
    
    /**
     * Groups all currently selected Playables into a Gesture. Removes the
     * selected playables from AllPlayables and adds the new gesture to 
     * allPlayables
     * @param ignored 
     */
    @FXML
    protected void handleGroup(ActionEvent ignored) {
        Set<Playable> selected = selectedSet();
        UndoRedo.pushUndo(allPlayables);

        Gesture gesture = new Gesture(selected);
        addHandlers(gesture);

        allPlayables.removeAll(selected);
        allPlayables.add(gesture);
        notePane.getChildren().add(gesture.getRectangle());
        
        updateMenuClick();
    }
    
    /**
     * Removes the selected gesture(s) from the pane and adds all of the
     * gesture's elements to the Set allPlayables.
     * This does not remove the gestures nested in the selected gestures.
     * @param ignored 
     */
    @FXML
    protected void handleUngroup(ActionEvent ignored) {
        UndoRedo.pushUndo(allPlayables);
        selectedSet().forEach((selectedPlayable) -> {
            if (selectedPlayable instanceof Gesture) {
                Gesture gesture = (Gesture) selectedPlayable;
                ungroupSingleGesture(gesture);
                removeGesture(gesture);
            }
        });
        updateMenuClick();
    }
    
    /**
     * Copy the selected playables to the system clipboard.
     * @param ignored
     */
    @FXML
    protected void handleCopy(ActionEvent ignored){
        System.out.println("Copy");
        //TODO
    }
    
    /**
     * Copy the selected playables to the system clipboard and 
     * remove those playables from the composition pane.
     * @param ignored 
     */
    @FXML
    protected void handleCut(ActionEvent ignored){
        System.out.println("Cut");
        //TODO
    }
    
    /**
     * Pastes the system clipboard (if it is a set of playables) to the pane.
     * @param ignored 
     */
    @FXML
    protected void handlePaste(ActionEvent ignored){
        //TODO
    }
    
    /**
     * Undo the last user action on the set of Playables, allPlayables.
     * @param ignored 
     */
    @FXML
    protected void handleUndo(ActionEvent ignored) {
        allPlayables = UndoRedo.undo(allPlayables);
        updateCompositionPane(allPlayables);
        updateMenuClick();
    }
    
    /**
     * Redo the last user action on the set of Playables, allPlayables.
     * @param ignored 
     */
    @FXML
    protected void handleRedo(ActionEvent ignored) {
        allPlayables = UndoRedo.redo(allPlayables);
        updateCompositionPane(allPlayables);
        updateMenuClick();
    }
    
    /**
     * Clear the composition pane and draw all of the rectangles in
     * the input set.
     * @param set Set to be added to the composition pane.
     */
    protected void updateCompositionPane(Set<Playable> set) {
        notePane.getChildren().clear();
        for (Playable element: set) {
            if (element instanceof Gesture) {
                notePane.getChildren().add(element.getRectangle());
            }
            addHandlers(element);
            notePane.getChildren().addAll(element.getNodeList());
        }
    }
    
    /**
     * Adds all of the elements in a gesture to the Set of allPLayables and
     * removes the gesture
     * @param gesture is the gesture that is going to be ungrouped
     */
    protected void ungroupSingleGesture(Gesture gesture){
        allPlayables.remove(gesture);
        gesture.getElements().forEach((element) -> {
            allPlayables.add((Playable)element);
        });
    }
    
    /**
     * Construct a note from a click. Called via FXML.
     * @param event a mouse click
     */
    public void handleClick(MouseEvent event) {
        
        if (playLine.isPlaying()) {
            stopPlaying();
        }
        else if (isDragSelecting){
            isDragSelecting = false;
            selection.endRectangle();
        }
        else if (clickInPane) {
            UndoRedo.pushUndo(allPlayables);
            
            if (! event.isControlDown()) {
                selectAll(false);
            }
            
            Instrument instrument = getInstrument();
            Note note = new Note(event.getX(), event.getY(), instrument);
            
            allPlayables.add(note);
            notePane.getChildren().add(note.getRectangle());
            
            addHandlers(note);
        }
        clickInPane = true;  
        
        updateMenuClick();
    }
    
    /**
     * Add handlers to the rectangles associated with Gestures and Notes.
     * @param play Playable to have handlers added to.
     */
    private void addHandlers(Playable play) {
        play.getRectangle().setOnMousePressed((MouseEvent pressedEvent) -> {
            handlePlayableClick(pressedEvent, play);
            handlePlayablePress(pressedEvent, play);
        });

        play.getRectangle().setOnMouseDragged((MouseEvent dragEvent) -> {
            handlePlayableDrag(dragEvent);
        });

        play.getRectangle().setOnMouseReleased((MouseEvent releaseEvent) -> {
            handlePlayableStopDragging(releaseEvent);
        });
    }
    
    /**
     * When user presses on a note, set the notes to be selected or 
     * unselected accordingly.
     * @param event mouse click
     * @param playable Playable that was clicked
     */
    private void handlePlayableClick(MouseEvent event, Playable playable) {     
        UndoRedo.pushUndo(allPlayables);
        
        clickInPane = false;
        boolean control = event.isControlDown();
        boolean selected = playable.getSelected();
        if (! control && ! selected) {
            selectAll(false);
            playable.setSelected(true);
        } else if ( control && ! selected) {
            playable.setSelected(true);
        } else if (control && selected) {
            playable.setSelected(false);
        }
        updateMenuClick();
    }
    
    /**
     * When user presses on a note, set offsets in each Note in case the user
     * drags the mouse.
     * @param event mouse click
     * @param note note Rectangle that was clicked
     */
    private void handlePlayablePress(MouseEvent event, Playable playable) {
        changeDuration = playable.inLastFive(event);
        selectedSet().forEach((element) -> {
            if (changeDuration) {
                element.setMovingDuration(event);
            } else {
                element.setMovingCoords(event);
            }
        });
    }
    
    /**
     * When the user drags the mouse on a note Rectangle, move all selected
     * notes
     * @param event mouse drag
     */
    private void handlePlayableDrag(MouseEvent event) {
        selectedSet().forEach((element) -> {
            if (changeDuration) {
                element.moveDuration(event);
            } else {
                element.move(event);
            }
        });
    }
    
    /**
     * When the user stops dragging the mouse, stop moving the selected notes
     * @param event mouse click
     */
    private void handlePlayableStopDragging(MouseEvent event) {
        clickInPane = false;
        selectedSet().forEach((element) -> {
            if (changeDuration) {
                element.stopDuration(event);
            } else {
                element.stopMoving(event);
            }
        });
        changeDuration = false;
        
        updateMenuClick();
    }

    /**
     * Automatically-called when user drags mouse. Stops playing if composition
     * is playing, and starts dragging selection rectangle if mouse click is
     * not on a note Rectangle.
     * @param event mouse click
     */
    public void startDrag(MouseEvent event) {
        if (playLine.isPlaying()) {
            stopPlaying();
        } else if (clickInPane) {
            handleSelectionStartDrag(event);
        }
    }

    /**
     * Automatically-called when user drags mouse. Stops playing if composition
     * is playing, and continues to drag selection rectangle if initial mouse 
     * click was not on a note Rectangle.
     * @param event mouse click
     */
    public void continueDrag(MouseEvent event) {
        if (playLine.isPlaying()) {
            stopPlaying();
        } else if (clickInPane) {
            handleSelectionContinueDrag(event);
        }
    }
    
    /**
     * Move lower-right corner of selection rectangle with the dragging mouse
     * @param event mouse drag
     */
    private void handleSelectionStartDrag(MouseEvent event) {
        UndoRedo.pushUndo(allPlayables);
        
        isDragSelecting = true;
        
        selection.startRectangle(event.getX(), event.getY());

        if(!event.isControlDown()){
            selectAll(false);
        }
    }
    
    /**
     * Continue to update notes throughout drag. Called from FXML
     * @param event Current value of MouseEvent
     */
    private void handleSelectionContinueDrag(MouseEvent event) {
        selection.update(event.getX(), event.getY());

        allPlayables.forEach((note) -> {
            double horizontal = selectRect.getX() + selectRect.getWidth();
            double vertical = selectRect.getY() + selectRect.getHeight();

            // Thanks to Paul for suggesting the `intersects` method.
            if(selection.getRectangle().intersects(note.getBounds())) {
                note.setSelected(true);
            } else {
                if (note.getSelected()) {
                    note.setSelected(false);
                }
            }
        });
        updateMenuClick();
    }

    /**
     * Delete all selected notes. Called from FXML
     * @param event unused
     */
    @FXML
    void handleDelete(ActionEvent event) {
        UndoRedo.pushUndo(allPlayables);
        
        Collection toDelete = new ArrayList<Playable>();
        allPlayables.forEach((element) -> {
            if (element.getSelected()) {
                toDelete.add(element);
                element.removeFromPane(notePane);
            }
        });
        allPlayables.removeAll(toDelete);
        
        updateMenuClick();
    }
    
    /**
     * Select all notes. Called from FXML
     * @param event unused
     */
    @FXML
    void handleSelectAll(ActionEvent event) {
        UndoRedo.pushUndo(allPlayables);
        selectAll(true);
        updateMenuClick();
    }
    
    /**
     * Sets selection values for all of the notes
     * @param selected true to select all
     */
    private void selectAll(boolean selected) {
        allPlayables.forEach((element) -> {
            element.setSelected(selected);
        });
    }
    
    /**
     * When the user selects the "Exit" menu item, exit the program.
     * @param event the menu selection event
     */
    @FXML
    protected void handleExitMenuItemAction(ActionEvent event) {
        System.exit(0);
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
