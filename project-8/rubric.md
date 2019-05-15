# GRADE: 78/80 

## Original composition - 5/6 points

| Points Earned | Points Possible | Requirement | Comments
|--------------:|----------------:|-------------|---------
|1|1| A composition is presented.
|0|1| The composition is original.
|4|4| The composition is artistic and/or demonstrates the features of your software.

## Demo - 6/6 points

| Points Earned | Points Possible | Requirement | Comments
|--------------:|----------------:|-------------|---------
|4|4| Each new feature was justified and demonstrated.
|1|1| The composition was played.
|1|1| All team members were included.

## User manual - 6/6 points

| Points Earned | Points Possible | Requirement | Comments
|--------------:|----------------:|-------------|---------
|2|2| There is a file named ```manual.md``` in the top-level directory.
|2|2| It describes each new feature added in Project 8. | _This documentation was immensely helpful. Thanks for taking it seriously._
|2|2| It explains how to use each new feature. 

## Feature selection - 12/12 points

One difficult enhancement may be worth several easier enhancements.

_Commentary:
* I agree that changing a note's instrument was a clearly missing feature. Similarly for changing volume and infinite scroll.
* While your visual display of volume is imperfect - it is not ideal to alter the opacity of a note's outline along with the fill - you were thoughtful about how changing a note's instrument and volume would interact with the existing controls. You also did not take the easy way out in implementing the ledger lines.
* I would consider "Dark Mode" and the custom icon to be minor enhancements by comparison to these others. (By the way, I don't see your custom icon on my Mac.)
* There wasn't one big feature here, but you tackled a coherent group of features with some challenges among them.
_

## Execution -  28/28 points

### Implementation of new features - 20/20 points

* All new features behave as described in the user manual.  If the description in the user manual is incomplete, then the behavior follows standard conventions and is otherwise unsurprising.
* There are no uncaught exceptions from new features.
* There is no debugging output from new features.

### No regressions - 8/8 points

All prior requirements are met, unless they have been superseded by new requirements or are documented by the team as known bugs for this iteration.


_I appreciate that you put some effort into paying down your technical debt._

## Release - 2/2 points

| Pts Earned | Pts Possible | Requirement | Comments
|-----------:|-------------:|-------------|---------
| 1 | 1 | The release is tagged as ```project-8-release```.
| 1 | 1 | Documentation is in the ```project-8``` directory.

## Reflection and elegance - 19/20 points

| Pts Earned | Pts Possible | Requirement | Comments
|-----------:|-------------:|-------------|---------
| 4 | 4 | UML diagram is accurate and complete.
| 3 | 4 | New classes/methods are reasonably self-explanatory.
| 2 | 2 | Design overview addresses changes from Project 7 in general.
| 2 | 2 | Design overview addresses the implementation of each new feature.
| 6 | 6 | Assessment of what is elegant and what is not thoughtfully addresses object-oriented design principles.
| 1 | 1 | Velocity is presented. 
| 1 | 1 | Team retrospective is presented.

_Comments:
* It looks like you made excellent use of feature branches and pull requests.
* I agree with the choices you assess as elegant.
* I see the pane for the note names is created in FXML. It makes sense to add the labels programmatically, since they are quite repetitive like the ledger lines.  Applying the prototype pattern would let you style the labels in FXML as much as possible.
* I was disappointed to see the volume slider created in Java and not FXML. I would have liked to see an explanation of this choice (which is not inherently self-documenting).
* I'd be happy to talk about how to apply the Observer pattern to make the volume slider reflect the Note's volume (if you are still interested). 
* Thanks for your reflections!
_
