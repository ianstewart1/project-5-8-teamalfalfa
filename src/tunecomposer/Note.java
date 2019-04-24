
package tunecomposer;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Note class creates a Rectangle representing the note to be played
 * @author Ian Hawkins, Madi Crowley, Ian Stewart, Melissa Kohl
 */
public class Note implements Playable {
  
    /**
     * Note fields for creating rectangle and playing note
     */
    private final Rectangle noteRect;
    private double x_coord;
    private double y_coord;
    private double rectWidth;
    private int pitch;
    private final Instrument instrument;
    
    /**
     * Offsets for dragging Rectangle
     */
    private double xOffset;
    private double yOffset;
    private double widthOffset;
    
    /**
     * Is this note currently selected
     */
    private boolean isSelected;

    /**
     * Creates new selected Rectangle at given coordinates with a default width
     * of 100 pixels and creates a note of the given instrument at the 
     * calculated start time and pitch.
     * @param x x-coordinate of new rectangle and starting time for note
     * @param y y-coordinate of new rectangle and pitch of note
     * @param inst instrument that the note should be played
     */
    public Note(double x, double y, Instrument inst) {
        pitch = Constants.MAX_PITCH - (int) y / Constants.RECTHEIGHT;
        
        x_coord = x;
        y_coord = y - ( y % Constants.RECTHEIGHT);
        
        instrument = inst;
        rectWidth = Constants.DEFAULT_DURATION;
        
        noteRect = new Rectangle(x_coord, y_coord, rectWidth, Constants.RECTHEIGHT);
        noteRect.getStyleClass().addAll("selected", instrument.getStyleClassName());
        noteRect.setMouseTransparent(false);
        
        isSelected = true;
    }
    
    /**
     * Creates a copy of a given Note by initializing the fields of this Note
     * to those of the given one.
     * @param note The Note to be copied into this Note
     */
    private Note(Note note) {
        x_coord = note.getX();
        y_coord = note.getY();
        rectWidth = note.getWidth();
        noteRect = new Rectangle(x_coord, y_coord, rectWidth, Constants.RECTHEIGHT);
        pitch = note.getPitch();
        instrument = note.getInstrument();
        setSelected(note.getSelected());
    }
    
    /**
     * Uses the copy constructor to make and return a deep copy of a note.
     * @return a copy of this
     */
    @Override
    public Playable makeCopy() {
        return new Note(this);
    }
    
    /**
     * Get this Note's Rectangle object.
     * @return this Note's Rectangle
     */
    @Override
    public Rectangle getRectangle() {
        return noteRect;
    }
    
    /**
     * Get a list containing the currently selected Note rectangles.
     * @return List of Note rectangles that are selected
     */
    @Override
    public List<Rectangle> getNodeList() {
        List<Rectangle> listRect = new ArrayList<Rectangle>();
        listRect.add(this.getRectangle());
        return listRect;
    }
    
    /**
     * Get this Note's Bounds object of the rectangle in the pane.
     * @return this Note's Bounds
     */
    @Override
    public Bounds getBounds() {
        return this.getRectangle().getLayoutBounds();
    }
    
    /**
     * Get this Note's x coordinate.
     * @return x_coord double this Note's x coordinate
     */
    @Override
    public double getX() {
        return x_coord;
    }
    
    /**
     * Get this Note's y coordinate.
     * @return y_coord double this Note's y coordinate
     */
    public double getY() {
        return y_coord;
    }
    
    /**
     * Get this Note's width.
     * @return rectWidth double this Note's width
     */
    @Override
    public double getWidth(){
        return rectWidth;
    }
    
    /**
     * Checks if this note is selected.
     * @return true if note is selected, false otherwise
     */
    @Override
    public boolean getSelected() {
        return isSelected;
    }
    
    
    /**
     * Get this Note's pitch
     * @return pitch The pitch of the Note
     */
    public int getPitch(){
        return pitch;
    }
    
    
    /**
     * Get this Note's instrument.
     * @return instrument The instrument of the note
     */
    public Instrument getInstrument(){
        return instrument;
    }
    
    @Override
    public Element generateXML(Document document) {
        Element note = document.createElement("note");
        
        note.setAttribute("pitch", Integer.toString(getPitch()));
        note.setAttribute("delay", Integer.toString((int) getX()));
        note.setAttribute("duration", Integer.toString((int) getWidth()));
        note.setAttribute("channel", 
                          Integer.toString(getInstrument().getChannel()));
        note.setAttribute("track", "0");
        
        return note;
    }
    
    /**
     * Set the note to be selected or unselected and updates the style class
     * of the Rectangle.
     * @param selected boolean to set selected to
     */
    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        if (selected) {
            noteRect.getStyleClass().clear();
            noteRect.getStyleClass().addAll("selected", 
                                            instrument.getStyleClassName());
        } else {
            noteRect.getStyleClass().clear();
            noteRect.getStyleClass().addAll("unselected", 
                                            instrument.getStyleClassName());
        }
    }
    
    /**
     * When the user presses the mouse to start dragging, calculate the offset
     * between the upper-left corner of the Rectangle and where the mouse is
     * in the Rectangle.
     * @param event mouse click
     */
    @Override
    public void setMovingCoords(MouseEvent event) {
        xOffset = event.getX() - x_coord;
        yOffset = event.getY() - y_coord;
    }
    
    /**
     * When the user clicks near the right end of the Rectangle, calculate 
     * the offset between the right edge of the Rectangle and where the mouse 
     * is in the Rectangle.
     * @param event mouse click
     */
    @Override
    public void setMovingDuration(MouseEvent event) {
        widthOffset = x_coord + rectWidth - event.getX();
    }
    
    /**
     * Set the new size and position of the Note based on the Gesture it is
     * in and the proportional change specified.
     * @param gestureX x coordinate of the parent Gesture
     * @param proportion magnitude of change specified for the Note position
     */
    @Override
    public void setProportions(double gestureX, double proportion) {
        double offset = (x_coord - gestureX) * proportion;
        double newX = gestureX + offset;
        double newWidth = rectWidth * proportion;
        x_coord = newX;
        rectWidth = newWidth;
        noteRect.setX(newX);
        noteRect.setWidth(newWidth);
    }
    
    /**
     * Add a note to the MidiPlayer.
     */
    @Override
    public void schedule() {
        TuneComposer.PLAYER.addNote(pitch, Constants.VOLUME, (int)x_coord, 
                                    (int)rectWidth, instrument.ordinal(), 
                                    Constants.TRACK);
    }
    
    /**
     * Move both the x and y coordinates of the Note.
     * @param event mouse drag
     */
    @Override
    public void move(MouseEvent event) {
        moveX(event);
        moveY(event);
    }

    /**
     * Move the x coordinate of the Note based on the mouse click.
     * @param event mouse click
     */
    @Override
    public void moveX(MouseEvent event) {
        double moveX = event.getX() - xOffset;
        
        if(moveX > 0 && (moveX + rectWidth) < Constants.WIDTH){
            noteRect.setX(moveX);
        }
    }

    /**
     * Move the y coordinate of the Note based on the mouse click.
     * @param event mouse click
     */
    @Override
    public void moveY(MouseEvent event) {
        double moveY = event.getY() - yOffset;
        
        if(moveY > 0 && (moveY + Constants.LINE_SPACING) < Constants.HEIGHT){
            noteRect.setY(moveY);
        }
    }
    
    /**
     * When the user stops dragging the mouse, set Note fields to the
     * Rectangle's current location.
     * @param event mouse click
     */
    @Override
    public void stopMoving(MouseEvent event) {
        double x = noteRect.getX();
        double y = noteRect.getY();

        pitch = Constants.MAX_PITCH - (int) y / Constants.RECTHEIGHT;
        
        x_coord = x;
        y_coord = y - (y % Constants.RECTHEIGHT);
        
        noteRect.setY(y_coord);
    }
    
    /**
     * Check whether the user has clicked within the last 5 pixels
     * of the Rectangle.
     * @param event mouse click
     * @return true if mouse is within the last 5 pixels of the Rectangle
     */
    @Override
    public boolean inLastFive(MouseEvent event) {
        return (event.getX() > x_coord + rectWidth - Constants.MARGIN);
    }
    
    /**
     * While the user is dragging the mouse, change the width of the Rectangle.
     * @param event mouse drag
     */
    @Override
    public void moveDuration(MouseEvent event) {
        double tempWidth = event.getX() - x_coord + widthOffset;
        if (tempWidth < 5) tempWidth = 5;
        noteRect.setWidth(tempWidth);
    }
    
    /**
     * When the user stops dragging the mouse, set Rectangle width
     * to final width.
     * @param event mouse click
     */
    @Override
    public void stopDuration(MouseEvent event) {
        rectWidth = event.getX() - x_coord + widthOffset;
        if (rectWidth < Constants.MARGIN) rectWidth = Constants.MARGIN;
        
        noteRect.setWidth(rectWidth);
    }
    
    /**
     * Remove a Note rectangle from the composition pane.
     * @param pane pane to be removed from.
     */
    @Override
    public void removeFromPane(Pane pane) {
        pane.getChildren().remove(noteRect);

    }
    
    
}