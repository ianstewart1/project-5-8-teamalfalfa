# GRADE: 61/64

_Well done!_

## Functional requirements: 36/36 points total

### Main application - 6/6 points
| Earned | Possible | Requirement
| ------:|---------:|------------
|      1 |        1 | The application window should fit on the screen.
|      1 |        1 | Menu items should be organized into two menus: **File** and **Action**.
|      1 |        1 | Under **File**, there should be an **Exit** menu item which quits the program
|      1 |        1 | Under **Action**, there should be menu items **Play** and **Stop**.
|      1 |        1 | The menu bar should be outside the composition pane scrollbars so it is always visible.
|      1 |        1 | All menu items should have keyboard shortcuts.

### Composition pane - 6/6 points
| Earned | Possible | Requirement
| ------:|---------:|------------
|      1 |        1 |  It should be 1280 pixels high and 2000 pixels wide.
|      1 |        1 |  It should have horizontal and vertical scroll bars.
|      4 |        4 |  It should display 127 thin gray horizontal lines spaced 10 pixels apart vertically.

### Creating notes - 8/8 points
| Earned | Possible | Requirement
| ------:|---------:|------------
|      2 |        2 | Each time the user clicks in the composition panel, a new rectangle should appear.
|      1 |        1 | It should be positioned horizontally so that the left edge lines up with the point that was clicked.
|      1 |        1 | It should have a width of 100 pixels.
|      3 |        3 | It should be positioned vertically to sit in between two of the horizontal lines.
|      1 |        1 | It should be blue with a thin black outline.

### Playing sounds - 8/8 points
| Earned | Possible | Requirement
| ------:|---------:|------------
|      1 |        1 | The horizontal position of each rectangle indicates when its sound should be played. 
|      1 |        1 | The vertical position of each rectangle indicates pitch. 
|      1 |        1 | A higher position means a higher pitch.
|      1 |        1 | The width of the rectangle indicates duration. 1 pixel = 1 tick.
|      1 |        1 | Assume a fixed time scale of 100 ticks per beat with 1 beat per second.
|      1 |        1 | The **Play** menu item starts playing.
|      1 |        1 | If the user chooses the **Play** menu item while sounds are being played, it should start over.
|      1 |        1 | The **Stop** menu item stops playing.

### Animation - 8/8 points
| Earned | Possible | Requirement
| ------:|---------:|------------
|      2 |        2 | As the composition plays, a vertical red line should move across the composition panel from left to right.
|      2 |        2 | It should just hit the left edge of a rectangle when the sound associated with that rectangle begins playing and should hit the right edge of that rectangle just as the sound stops.
|      2 |        2 | When it reaches the last right edge of the last rectangle, it should disappear.
|      2 |        2 | The animation should start (or start over) when the **Play** menu item is selected.
|      1 |        1 | The animation should stop when the **Stop** menu item is selected.
|      1 |        1 | The line should not be visible when there are no sounds playing.

## Elegance; implementation and submission requirements - 17/20 points
| Earned | Possible | Requirement
| ------:|---------:|------------
|      2 |        2 | Thoughtful division of code into classes and methods.
|      3 |        4 | Self-explanatory code: names, javadoc, inline comments. _You are thoughtful about naming and inline comments, but the Note class is entirely devoid of Javadoc._
|      2 |        2 | Reasonable choices of type modifiers in Java code. _I'm giving you a pass on your use of static fields and methods in ```TuneComposer``` because you recognized this as inelegant. If you build on this code in Project 4, it's an issue you should resolve._
|      1 |        2 | No unused fields or methods. No debugging code. _See annotations._
|      1 |        2 | Appropriate use of basic Java language constructs and JavaFX components; reasonably concise code. _Compile with ```javac -Xlint:unchecked``` and resolve these warnings._
|      2 |        2 | Whenever possible, JavaFX components are created in FXML. _You should create the red line in FXML. I'm giving you a pass on this because of the effort you made to divide your code into appropriate classes. You will want to make a new FXML file for the red line pane, and make PlayLine the corresponding controller._
|      2 |        2 | Whenever possible, JavaFX components are styled using CSS.
|      2 |        2 | Minimal, well-formatted FXML and CSS code.
|      1 |        1 | Keep the starting point for your application in the given TuneComposer class.
|      1 |        1 | Merge all production code into the master branch. Tag final version as 'project3-release'.

## Reflection - 8/8 points
| Earned | Possible | Requirement
| ------:|---------:|------------
|      2 |        2 | Design overview
|      4 |        4 | What's elegant or not?
|      2 |        2 | How did your team collaborate?
