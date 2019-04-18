Project 6: Team Reflection
========================

*   Give a concise overview of your design. What strategy did you use to implement undo/redo? How did you divide the code into classes and methods? How does your design for Project 6 differ from your design to Project 5? How did you respond to feedback? (If I get it to you in time.)

We implemented our Undo/Redo functionality with a "Memento" design. Each time a change is made in the composition pane, the current state is "saved" by pushing it to an undoStack. If the user chooses to undo, the state is retrieved and the new current state is pushed to the redoStack. The user can continue to undo or redo as long as there are states to retrieve from the undoStack or redoStack. If the user makes a change in the composition pane, the redoStack is cleared. 

We chose this design in part because we thought that it worked particularly well with our current code. We realized that to save a state, we only needed to save the set of allPlayables. We wrote a new UndoRedo class, which holds the undoStack and redoStack and manages saving the current state or passing back a state from either stack. 

In terms of our existing code, we've made several improvements and have plans for further refactoring, all based on previous feedback. We rewrote our red playLine to use a TranslateTransition. In addition, we pulled out instrument information from the TuneComposer using Professor Davis' sample Project 4 code as a guide. Both of these make our code more readable and elegant. We are planning to tackle TuneComposer during this next project by extracting MenuHandler and CompositionHandler classes and condensing the remaining code. When we enter our next design cycle, we intend to prioritize our issues on GitHub and determine what we can do before or concurrently with our next project requirements. 

*   Explain why your way was the elegant way to do it. Address any improvements you made. *For full credit, apply concepts, guidelines, and/or principles you learned in class.*
*   Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it). *For full credit, apply concepts, guidelines, and/or principles you learned in class.*
*   Include an estimate of your velocity. How many story points did you estimate you would complete during this project? How many did you actually complete, how many person-hours did the team spend, and what is the ratio of points/person-hour? How does this compare to your velocity for the previous project assignment?
*   Include a short summary of your team retrospective. What went well that your team will keep doing during the next project assignment? What will you improve? How?


