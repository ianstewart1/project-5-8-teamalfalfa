Project 6: Undo and Redo
========================

In this project, you are to enhance the preceding project by adding two new menu items to the Edit menu: **Undo** and **Redo**.

[Watch a demonstration video narrated by Dale Skrien.](https://drive.google.com/open?id=1evCJFFYPngs4aHPshNC3luDiy5OpSqFL)

Functional requirements
-----------------------

*   Add two new menu items to the top of the **Edit** menu: **Undo** and **Redo**. Put a separator between them and the rest of the **Edit** menu items.
*   When the **Undo** menu item is chosen, the last action you did in the composition pane should be undone. For example, if you deleted one or more note bars, then undoing that action should cause the note bars to be restored to the panel in their previous position. If you moved one or more note bars, then undoing should move them back to their previous locations. If a new note bar was added to the pane, then after choosing b>Edit, it should be gone.
*   The actions that should be undo-able and redo-able are:
    *   adding an note bar;
    *   selecting or unselecting one or more note bars;
    *   deleting the selected note bars;
    *   editing the selected note bars by dragging or stretching;
    *   grouping or ungrouping note bars.
*   Make sure that every undo or redo action changes the state of the composition panel. It is not user-friendly to have nothing visible happen when the **Undo** or **Redo** menu item is chosen. That is, each undo or redo action should have a visible effect on the composition pane. For example, the action of playing the sounds and the action of selecting a new instrument in the control panel on the left are not undo-able and redo-able, since they do not correspond to changes to the composition pane.
*   If note bars are deleted, edited, or selected as a group, for example, by using the **Select All** menu item, then an undo operation should affect all of the note bars.
*   The user should be allowed to undo actions repeatedly, all the way back to the original blank composition pane when the application was first started.
*   When the **Redo** menu item is chosen, the last Undo action should be undone and the state restored to what it was before the **Undo** action was performed. Redoing is only possible if the composition panel has not been changed (by performing a new undo-able action) since the last **Undo** action.
*   If a sequence of **Undo** actions was performed, then the user should be allowed to **Redo** the whole sequence.
*   The **Undo** menu item should have a keyboard equivalent of Ctrl/Cmd-Z. The **Redo** menu item should have a keyboard equivalent Ctrl/Cmd-Shift-Z.
*   Disable each of the menu items when it is inappropriate to select it. For example, when you first start up the program, the **Undo** menu item should be disabled since there is nothing to undo.
*   More generally, disable any menu item in the application when it is appropriate to do so, not just **Undo** and **Redo**. And from now on, whenever you add a new menu item, be sure to disable that menu item when it cannot be applied.

Advice
------

*   You probably did not accomplish all the refactorings you wanted to for Project 5. As you have time, continue to improve the elegance of your code.

Tools and process
-----------------

*   Continue to work with the same team on the same repository.
*   Before you commit any changes to master for this project, make sure you [create a tag/release](https://help.github.com/articles/creating-releases/) named **`project-5-release`**.
*   Use [GitHub issues](https://guides.github.com/features/issues/) to manage tasks and track bugs within your project team. I will look more kindly on bugs and incomplete features if I see them listed as issues here. Starting with Project 5, I will comment on the quality of your code using issues rather than inline TODOs.
*   Consider using [Slack](http://slack.com) for team communication. You can join [WhitmanCS](https://join.slack.com/t/whitmancs/shared_invite/enQtNTg2OTg4NzYyNjkyLThhODBkMzc3Zjg5ZTJkMDFkZTU1NDNhZmYxZTkxYjQ5ODBhN2Q0MzA0ZjRmMzdjNTMzZTQ5YTI1OTBjYjE4ZjM) and create a channel for your team. There is also a channel for discussing JavaFX.
*   If you are having difficulty with the Netbeans/Git integration, consider using the command-line tool as a supplement (I can help), or try Elegit, a visual Git interface that is also installed on the Linux workstations. Alternatively, your team may choose to adopt a different IDE such as Eclipse, IntelliJ IDEA (JetBrains), or Visual Studio Code, all of which are installed on the lab computers.
*   Play [Planning Poker](https://docs.google.com/a/whitman.edu/document/d/1Qa-E-uucvmyyI0y3PFxdg2YnvjeMVpfQ-SnJ0m_LDiA/edit?usp=sharing) to estimate how much effort each task will take. Before playing, agree on a class design for gestures and discuss what refactoring tasks you want to undertake. (See Advice below.)
*   Keep track of how many person-hours you spend on the project so that you can compute a velocity (points/person-hour).
*   Try to allow some time for a brief team retrospective. What do you want to keep doing (sustain)? What do you want to improve? How?

What to Submit
--------------

### Code

Push your code to your team's GitHub repository. When you are done, **tag your work as **`project-6-release`**.**

### Documentation

Put all documentation for Project 6, including the UML and reflection, in a directory named ```project-6```. I will also put the grading rubric there.

### UML

Provide a UML diagram shoowing all the core classes that you created or modified in this project and the associations between them. I recommend [draw.io](http://draw.io), a [free](https://support.draw.io/pages/viewpage.action?pageId=11829278) drawing tool that integrates with GitHub. Some others also recommend [LucidChart](https://www.lucidchart.com/documents#docs?folder_id=home&browser=icon&sort=saved-desc), which has a [GitHub integration](https://www.lucidchart.com/pages/integrations/github).

### Team reflection

Include a team reflection as a [MarkDown](https://daringfireball.net/projects/markdown/) file. Address the following points:

*   Give a concise overview of your design. What strategy did you use to implement undo/redo? How did you divide the code into classes and methods? How does your design for Project 6 differ from your design to Project 5? How did you respond to feedback? (If I get it to you in time.)
*   Explain why your way was the elegant way to do it. Address any improvements you made. *For full credit, apply concepts, guidelines, and/or principles you learned in class.*
*   Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it). *For full credit, apply concepts, guidelines, and/or principles you learned in class.*
*   Include an estimate of your velocity. How many story points did you estimate you would complete during this project? How many did you actually complete, how many person-hours did the team spend, and what is the ratio of points/person-hour? How does this compare to your velocity for the previous project assignment?
*   Include a short summary of your team retrospective. What went well that your team will keep doing during the next project assignment? What will you improve? How?


### Code reviews

For Projects 5-8, team members will take turns walking me through the code and explaining their contributions. Each student should expect to make at least two such presentations. I will provide information about scheduling closer to the deadline.

Acknowledgments
---------------

This assignment is adapted from one by Dale Skrien.
