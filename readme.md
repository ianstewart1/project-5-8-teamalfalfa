Project 4: Instruments and Editing
==================================

In Project 3, you let the user add note bars to the composition panel. Unfortunately, once a note is added, it cannot be changed. Moreover, all notes are played by the same, default instrument.

In this project, you will let the user add notes to be played by any of a collection of 8 instruments and let the user edit existing notes. More precisely, you are to:

*   allow the user to choose among different instruments using a set of radio buttons in an "instrument pane."
*   allow notes to be edited through selecting, dragging, stretching, and deleting groups of note bars in the composition pane.

[Watch a demonstration video narrated by Dale Skrien.](https://drive.google.com/open?id=1QqiYis6bWWO8ODuCTwamEdMsMuGapG_b)

Functional requirements: The instrument pane
--------------------------------------------

*   The instrument pane should appear to the left of the composition pane. It should remain on screen even when scrolling to the right.
*   In the instrument pane, create a set of radio buttons for each of the following 8 instruments: Piano, Harpsicord, Marimba, Church organ, Accordion, Guitar, Violin, French horn. You are welcome to add more buttons and more instruments if you wish. Each button should display the name of the instrument and have a different colored background.
*   When the program starts, the first radio button in the panel in the instrument panel should be automatically selected.
*   The buttons in the instrument panel should be radio buttons, so when the user clicks on one of the buttons, the new button becomes selected and the old selected button becomes unselected.
*   When the user clicks in the composition panel to create a new note bar, it should have the currently-selected instrument's background color and should sound like it is coming from that instrument when played.
*   Once a note is created, you cannot change what instrument it is associated with.

Functional requirements: Selecting and editing notes
----------------------------------------------------

*   A selected note bar must be visually distinguished from unselected note bars. For example, it could have a thick bright red border. You are also welcome to come up with some other form of highlighting.
*   Clicking and control-clicking in the composition panel:
    *   When the user clicks in the composition panel but not in a note bar, a new note bar is created. The new note bar is selected, and other already-selected note bars become unselected.
    *   When the user holds down the control key while clicking in the composition panel but not in a note bar, a new note bar should be created and become selected while other already-selected note bars stay selected.

*   Clicking and control-clicking in note bars:
      *   When the user clicks in an unselected note bar, the note bar becomes selected and any already-selected note bars become unselected.
      *   When the user clicks an already-selected note bar, nothing happens.
      *   When the user holds down the control key while clicking an unselected note bar, the note bar is selected and any already-selected note bars remain selected.
      *   When the user holds down the control key while clicking an already-selected note bar, the note bar is unselected and the other already-selected note bars remain selected.

*   Dragging and control-dragging in the composition panel:
    *   When the user presses the mouse button over the composition panel but not in a note bar and then drags, a "selection" rectangle appears and one corner of the selection rectangle drags with the mouse. As note bars intersect the selection rectangle, they become selected. As note bars move outside the selection rectangle, they become unselected. All previously selected note bars outside the rectangle become unselected.
    *   If the user presses the control key and the mouse button over the composition panel but not in a note bar, the same thing happens as in the case where the control key is not pressed, except that already-selected note bars stay selected regardless of the position of the selection rectangle.
*   Dragging in note bars:
      *   The control key plays no role in this case.
      *   If the user presses the mouse on the right edge of an existing note bar (within the last 5 pixels horizontally) and drags, then the right edge of the bar moves horizontally (but not vertically) with the mouse. In this way, the duration of a note can be changed. Assume all notes have a minimum duration of 5 ticks and don't let the bars get any smaller than that.
      *   If the note bar whose right edge is being dragged was unselected, then it becomes the only selected note bar.
      *   If the note bar whose right edge is being dragged was already selected, then all selected note bars' right edges are dragged simultaneously the same distance horizontally.
      *   When the user presses the mouse button anywhere in a selected note bar other than on the right edge, the note bar and all other selected note bars are dragged in the panel.
      *   When the user presses the mouse button in an unselected note bar other than on the right edge, the note bar is selected and dragged by itself and other already-selected note bars are unselected.
      *   When notes that have been dragged are released, they should snap vertically into the nearest slots so that notes are always (except when being dragged) sitting between two horizontal black lines.

*   If the program is playing the notes when the mouse is pressed, the program should stop playing and the red vertical line should disappear. That is, pressing the mouse in the composition pane is another way to stop the playing of the notes.
*   Selection does not affect how notes are played.
*   The menu bar should be enhanced with an "Edit" menu with two menu items: "Select All" which, when chosen, selects all the note bars in the window, and "Delete", which, when chosen, deletes all selected note bars in the composition panel. Add appropriate keyboard shortcuts for all menu items.

Implementation requirements
---------------------------

*   Begin by discussing your team members' design strategies for Project 3. Choose an implementation to build on.
*   All team members should [create or join their Project 4 team on Github Classroom](https://classroom.github.com/g/Wwv3fg2t). You will get an empty repository.
*   The team member whose work is being built upon should follow [these instructions to duplicate a repository](https://help.github.com/articles/duplicating-a-repository/). For the old repository, use the Project 3 repository you want to build on. For the new repository, use the GitHub Classroom repository you just obtained.
*   Keep the starting point for your application (`public static void main(String[] args)`) in the `TuneComposer` class.
*   Continue to strive for elegance.

Advice
------

To tell the player which instrument to use to play which notes, you need to associate a channel to each instrument you will be using. To do so, add "Program Change" Midi events to the player, using the following code:

```
player.addMidiEvent(ShortMessage.PROGRAM\_CHANGE + c, i, 0, s, t);
```

where _c_ is the channel (0-15), _i_ is the instrument (0-127) to be associated with the channel, _s_ is the start tick indicating when the change is to take effect, and _t_ is the track. A list of the instrument values (to use for _i_) can be found at the following web site: [https://www.midi.org/specifications/item/gm-level-1-sound-set](https://www.midi.org/specifications/item/gm-level-1-sound-set). However, you need to subtract 1 from the values given on that web site. For example, if you want to use the Marimba, you need to use 12 for _i_, not 13.

A Program Change event affects all notes in the given channel _c_ whose start tick value is after the Program Change's start tick.

What to Submit
--------------

Submit your work by pushing your code to your team's GitHub repository. When you are done, tag your work as `project-4-release`.

**New:** For this assignment and all future project assignments, you should create **UML diagrams** of all the core classes that you created or modified in this project and the associations between them. You may use a computer-based drawing tool such as [http://draw.io](draw.io), but it may be easier to draw on a whiteboard and take a photo. Clearly label the image files and place them in a directory named `uml`.

Include a team reflection as a [MarkDown](https://daringfireball.net/projects/markdown/) file. Address the following points:

*   Give a concise overview of your design. How did you divide the code into classes and methods, and why? How does your design for Project 4 differ from the design you inherited for Project 3? How did you respond to my feedback on that solution?
*   Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it).
*   Finally, describe how your team collaborated on the project. What did you do together? What did you do separately? What did each team member contribute?
*   In your reflection on collaboration, include a reflection on estimation: How did you use your estimates? For which requirement(s) did you make the most inaccurate estimate(s)? Why?

Finally, you will be prompted through CATME to evaluate your work and your teammates' work on Project 4.

Acknowledgments
---------------

This assignment is adapted from one by Dale Skrien.
