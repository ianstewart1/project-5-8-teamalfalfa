/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
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
import org.xml.sax.SAXException;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.geometry.Pos;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

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
     * Copy of the stage created in the start method.
     */
    private static Stage windowStage;
    
    /**
     * A pop-up window to prompt the user for files.
     */
    private static CompositionFileChooser fileChooser;
    
    /**
     * File currently being saved to by the TuneComposer.
     */
    private static File currentFile = null;
    
    /**
     * Boolean flags to control flow when user clicks in composition panel.
     */
    private boolean clickInPane = true;
    private boolean changeDuration = false;
    private boolean isDragSelecting = false;
    
    /**
     * Boolean flag to check if changes have been made since last save.
     */
    private static boolean ifChanged = false;
    
    /**
     * Boolean flag to check if an element has been cut or copied.
     */
    private static boolean ifCutCopy = false;
    
    
    /**
     * The scene of the application.
     */
    @FXML
    private Scene scene;
    
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
     * ScrollBar for the instrumentPane.
     */
    @FXML
    private ScrollPane instrumentScroll;  
    
    /**
     * VBox used to hold the note names of each line.
     */
    @FXML
    private VBox noteNamePane;
    
    /**
     * ScrollBar for the noteNamePane.
     */
    @FXML
    private ScrollPane noteNameScroll;

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
                     copyButton, cutButton, pasteButton, saveButton, 
                     changeInstrument;
    
    /**
     * The toggle dark mode menu item. 
     */
    @FXML
    private CheckMenuItem toggleMode;
    
    /**
     * Slider for adjusting the volume of selected Notes.
     */
    private Slider volumeSlider = new Slider();

    /**
     * Constructor initializes Note sets.
     */
    public TuneComposer() {
        allPlayables = new HashSet<>();
    }
    
    /**
     * Initializes FXML. Called automatically.
     * (1) adds 127 gray lines to background
     * (2) initializes the playLine(set to invisible)
     */
    public void initialize() {
        updateGrayLines();

        playLine = new PlayLine(movingLine);

        // Let mouse events go through to notePane
        playLinePane.setMouseTransparent(true);

        selection = new SelectionArea(selectRect);
        
        fileChooser = new CompositionFileChooser(windowStage);
        
        setupInstruments();
        setupVolumeSlider();
        setupNoteNames();
        updateMenuClick();
        
    }
    
    /**
     * Update gray lines so that the pane can extend infinitely.
     */
    public void updateGrayLines() {
        for (int i = 1; i < Constants.NUM_PITCHES; i++) {
            Line row = new Line(0, 10 * i, findLastNote() + 2000, 10 * i);
            row.getStyleClass().add("row-divider");
            background.getChildren().add(row);
        }
    }
    
    /**
     * Interacts with radiobuttons to get the selected instrument.
     */
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
     * Draws the note value that corresponds to each row in the composition.
     */
    private void setupNoteNames() {
        Label noteLabel;
        
        for (int i = 0; i < Constants.NUM_PITCHES; i++) {
            int labelNumber = i % Constants.NOTE_NAMES.size();
            noteLabel = new Label(Constants.NOTE_NAMES.get(labelNumber));
            noteLabel.setAlignment(Pos.CENTER_RIGHT);
            noteLabel.setFont(new Font("Arial", 8));
            noteLabel.setMinHeight(10.0);
            noteLabel.setMinWidth(30.0);
            noteNamePane.getChildren().add(noteLabel);
        }      
        
        noteNameScroll.vvalueProperty().bindBidirectional(
                instrumentScroll.vvalueProperty());
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
     * Creates the slider for note volume, sets the upper and lower bounds, and
     * adds a listener that sets the volume of selected notes.
     */
    private void setupVolumeSlider() {
        Label label = new Label("Volume");
        
        volumeSlider.setMin(Constants.MIN_VOLUME);
        volumeSlider.setMax(Constants.MAX_VOLUME);
        volumeSlider.setValue(Constants.MAX_VOLUME);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(Constants.MAX_VOLUME / 2);
        
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    selectedSet().forEach((element) -> {
                        element.setVolume(new_val.intValue());
                    });
            }   
        });
        volumeSlider.setOnMousePressed((MouseEvent ignored) -> {
            ifChanged = true;
            UndoRedo.pushUndo(allPlayables);
        });
        
        instrumentPane.getChildren().addAll(label, volumeSlider);
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
        for (Playable note : allPlayables){
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
        updateMenuClick();
    }

    /**
     * When user selects "Play" menu item, start playing composition
     * @param ignored not used
     */
    @FXML
    public void handleStartPlaying(ActionEvent ignored) {
        startPlaying();
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
        pasteButton.setDisable(!ifCutCopy);
        saveButton.setDisable(!ifChanged);
        changeInstrument.setDisable(numSelected < 1);
        updateGrayLines();
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
    
    /**
     * Check if there is a Gesture in a set of playables.
     * @param selected Set of Playables to be searched
     * @return boolean if a gesture is present
     */
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

        ifChanged = true;
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
        ifChanged = true;
        updateMenuClick();
    }
    
    /**
     * Copy the selected playables to the system clipboard.
     * @param ignored
     */
    @FXML
    protected void handleCopy(ActionEvent ignored){
        Set<Playable> selected = selectedSet();
        Document doc = CompositionParser.compositionToXML(selected);
        String str = CompositionParser.printToString(doc);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(str), null);
        ifCutCopy = true;
        updateMenuClick();
    }
    
    /**
     * Copy the selected playables to the system clipboard and 
     * remove those playables from the composition pane.
     * @param ignored 
     */
    @FXML
    protected void handleCut(ActionEvent ignored){
        UndoRedo.pushUndo(allPlayables); //undoable
        handleCopy(ignored);
        handleDelete(ignored);
        ifCutCopy = true;
        updateMenuClick();
    }
    
    /**
     * Pastes the system clipboard (if it is a set of playables) to the pane.
     * @param ignored 
     */
    @FXML
    protected void handlePaste(ActionEvent ignored) throws UnsupportedFlavorException, IOException, SAXException{
        UndoRedo.pushUndo(allPlayables); //undoable
        selectAll(false);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        InputSource xml = CompositionParser.clipboardToInputSource(clipboard);
        Set<Playable> composition = CompositionParser.xmlToComposition(xml);
        updateCompositionPane(allPlayables);
        allPlayables.addAll(composition);
        addToCompositionPane(composition);
        updateMenuClick();
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
     * Change the instrument associated with a selection of notes to
     * whatever is selected in the side bar.
     * @param ignored unused
     */
    @FXML
    protected void handleChangeInstrument(ActionEvent ignored) {
        ifChanged = true;
        UndoRedo.pushUndo(allPlayables);
        Instrument instrument = getInstrument();
        selectedSet().forEach((element) -> {
                element.setInstrument(instrument);
            }        
        );
        
        
    }
    
    /**
     * Clear the composition pane and draw all of the rectangles in
     * the input set.
     * @param set Set to be added to the composition pane.
     */
    protected void updateCompositionPane(Set<Playable> set) {
        notePane.getChildren().clear();
        addToCompositionPane(set);
    }
    
    /**
     * Clear the composition pane and draw all of the rectangles in
     * the input set.
     * @param set Set to be added to the composition pane.
     */
    protected void addToCompositionPane(Set<Playable> set) {
        for (Playable element: set) {            
            notePane.getChildren().addAll(element.getNodeList());
            addHandlers(element);
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
            ifChanged = true;
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
        ifChanged = true;
        
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
        
        Collection<Playable> toDelete = new ArrayList<>();
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
     * Saves composition to File. Prompts if file has not yet been saved.
     */
    private void promptSave() {
        if (currentFile == null) {
            currentFile = fileChooser.saveFile();
        } 
        if (currentFile != null) {
            Document xml = CompositionParser.compositionToXML(allPlayables);
            CompositionParser.printToFile(xml, currentFile);
            ifChanged = false;
        }
        updateMenuClick();
    }
    
    /**
     * Control for user click on the 'About' MenuItem.
     * @param ignored ignored
     */
    @FXML
    protected void handleAbout(ActionEvent ignored) {
        CompositionAlert.aboutAlert();
    }
    
    /**
     * Control for user click on the 'New' MenuItem.
     * @param ignored ignored
     */
    @FXML
    protected void handleNew(ActionEvent ignored) {
        if (ifChanged) {
            CompositionAlert.Result value= CompositionAlert.saveAlert();
            if (value == CompositionAlert.Result.YES) {
                promptSave();
            } 
            else if (value == CompositionAlert.Result.CANCEL) {
                return;
            }
        }
        notePane.getChildren().clear();
        allPlayables.clear();
        UndoRedo.clearStacks();
        currentFile = null;
        ifChanged = false;
        updateMenuClick();
    }
    
    /**
     * Control for user click on the 'Open' MenuItem.
     * @param ignored ignored
     */
    @FXML
    protected void handleOpen(ActionEvent ignored) throws SAXException, IOException {
        if (ifChanged) {
            CompositionAlert.Result value= CompositionAlert.saveAlert();
            if (value == CompositionAlert.Result.YES) {
                promptSave();
            } 
            else if (value == CompositionAlert.Result.CANCEL) {
                return;
            }
        }
        File file = fileChooser.openFile();
        if (file != null) {
            InputSource xml = CompositionParser.fileToInputSource(file);
            allPlayables = CompositionParser.xmlToComposition(xml);
            updateCompositionPane(allPlayables);
            UndoRedo.clearStacks();
            currentFile = file;
            ifChanged = false;
        }
        updateMenuClick();
    }
    
    /**
     * Control for user click on the 'Save' MenuItem.
     * @param ignored ignored
     */
    @FXML
    protected void handleSave(ActionEvent ignored) {
        promptSave();
    }
    
    /**
     * Control for user click on the 'Save As' MenuItem.
     * @param ignored ignored
     */
    @FXML
    protected void handleSaveAs(ActionEvent ignored) {
        currentFile = fileChooser.saveFile();
        if (currentFile != null) {
           Document xml = CompositionParser.compositionToXML(allPlayables);
           CompositionParser.printToFile(xml, currentFile);
           ifChanged = false;
        }
        updateMenuClick();
    }
    
    /**
     * When the user selects the "Exit" menu item, exit the program.
     * @param event the menu selection event
     */
    @FXML
    protected void handleExitMenuItemAction(ActionEvent event) {
        if (ifChanged) {
            CompositionAlert.Result value= CompositionAlert.saveAlert();
            if (value == CompositionAlert.Result.YES) {
                promptSave();
            } 
            else if (value == CompositionAlert.Result.CANCEL) {
                return;
            }
        }
        System.exit(0);
    }
    
    @FXML
    protected void handleToggleMode(ActionEvent event) {
        if (toggleMode.selectedProperty().getValue()) {
            scene.getStylesheets()
                    .add(getClass().getResource("darkmode.css").toString());
        } 
        else {
            scene.getStylesheets()
                    .remove(getClass().getResource("darkmode.css").toString());
        }
    }

    /**
     * Construct the scene and start the application.
     * @param primaryStage the stage for the main window
     * @throws java.io.IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage stage = FXMLLoader.load(getClass().getResource("TuneComposer.fxml"));
        
        windowStage = primaryStage;

        stage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });
        
        stage.show();
    }

    /**
     * Launch the application.
     * @param args the command line arguments are ignored
     */
    public static void main(String[] args) {
        launch(args);
    }

}
