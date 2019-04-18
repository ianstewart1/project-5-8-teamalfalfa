Project 6: Team Reflection
========================

*   Give a concise overview of your design. What strategy did you use to implement undo/redo? How did you divide the code into classes and methods? How does your design for Project 6 differ from your design to Project 5? How did you respond to feedback? (If I get it to you in time.)

We implemented our Undo/Redo functionality with a "Memento" design. Each time a change is made in the composition pane, the current state is "saved" by pushing it to an undoStack. If the user chooses to undo, the state is retrieved and the new current state is pushed to the redoStack. The user can continue to undo or redo as long as there are states to retrieve from the undoStack or redoStack. If the user makes a change in the composition pane, the redoStack is cleared. 

We chose this design in part because we thought that it worked particularly well with our current code. We realized that to save a state, we only needed to save the set of allPlayables. We wrote a new UndoRedo class, which holds the undoStack and redoStack and manages saving the current state or passing back a state from either stack. 

In terms of our existing code, we've made several improvements and have plans for further refactoring, all based on previous feedback. We rewrote our red playLine to use a TranslateTransition. In addition, we pulled out instrument information from the TuneComposer using Professor Davis' sample Project 4 code as a guide. Both of these make our code more readable and elegant. We are planning to tackle TuneComposer during this next project by extracting MenuHandler and CompositionHandler classes and condensing the remaining code. When we enter our next design cycle, we intend to prioritize our issues on GitHub and determine what we can do before or concurrently with our next project requirements. 

INELEGANCE
======
One inelegant aspect of our solution is that menu items are all updated in a single place in TuneComposer which increases the responsibilities of TuneComposer in violation of the principle of single responsibility. To abide by that principle, these menu updates should be handled in a separate class that displays them to the composition pane. We chose to keep this because moving them to a separate class would involve learning to have multiple FXML files that communicate on the same objects, which felt like to large a project to take on when we were also still figuring out how and when the menu items should be grayed out. Now that we have solved this first part of the problem, and since all the updates are already grouped together, we are set up well to implement the more elegant solution in the future. Another inelegance was that our handlers for mouse events on notes/gestures are still in TuneComposer. Again, this is a violation of the principle of single responsibility, as it would make more sense for the notes/gestures themselves to apply these handlers. This would allow for less coupling between TuneComposer and notes/gestures, where TuneComposer creates the composition pane area and notes/gestures handle how they can be interacted with in that area. We did not implement this because we found it was more efficient to add handlers within TuneComposer as handlers are only added to the set of notes/gestures that are interactable in the pane. Handlers applied to notes/gestures not in the pane would remain even if those notes/gestures were removed as they would be stored in the Undo/Redo stacks, creating the potential for access to handlers that are supposed to be inaccessible.

VELOCITY
======
Using an interval of 1 day, we completed 0.6 story points per day of work. We estimated we would complete 7 story points this project. We ended up completing 8, since we completed the additional story of putting all Instrument related information inside of the Instrument class. We spent around 32 person-hours on this project, meaning we had a ratio of 0.25 story points per person-hour. This is equal to our rough estimate of velocity from last project, however, since we also completed a strech goal from last project, our velocity for this project should be considered to be somewhat slower than before. Given that we still were able to complete the functional requirements for this project, we consider this velocity to be a comfortable rate of progress even if it leaves some room for improvement. 

ELEGANCE
======
We think our way of implementing undo/redo was elegant because it was the
simplest way to do it and still include all of the necessary features. We were
able to create UndoRedo.java as a very cohesive class. Its one purpose is to
handle state changes. It might seem like undoing and redoing are two seperate
jobs, but having separate classes for each would violate the Expert Pattern
(and be annoying to deal with). The way we implemented it, one class conatins
all of the necessary data for changing the state through undo. It also makes
sense to group these actions into one class becaue redoing is something that
can only be done after undo, so it is part of an undo chain and not a seperate
thing. UndoRedo.java has a consistent interface. All of the methods are given
intentional-meaningful names and there are corresponding methods for undoing
and redoing when it makes sense. We did not right a pushRedo method to mirror
pushUndo, because no one should be able to push to the redo stack without
having to go through the redo method. Basically out of necessity, our UndoRedo
class conforms to the code to interfaces roll. Rather than working on just
notes or gestures, it works for playables. However, in retrospect, we think it
may have been better to implement UndoRedo to work for any set, not just a set
of playables.

TEAMWORK
======
Our team worked well on this project. We decided to have a planning session at the beginning of our process since that was successful last time. That worked well again this time. Next time, we might need to spend a good portion of time simply discussing how to dismantle TuneComposer. It is likely that small things could break, and approaching this process as methodically as possible will be important. To that end, when we hit roadbloacks, which happened a couple of times during this assignment, we may try to step back and discuss possible solutions before experimenting too much. When dealing with how to assign new handlers to notes and gestures from undo and redo, it took a little thinking to get the solution, which ended up being fairly simple. 

We think that planning ahead for when we will work is good. This Friday, we may try to plan out several work sessions so that we have to discuss less. 


