Project 5: Grouping Notes into Gestures
=======================================

In this project, you are to enhance the preceding project by adding the ability to group sounds into "gestures" that can be treated as one sound. Grouped sounds can later be ungrouped.

[Watch a demonstration video narrated by Dale Skrien.](https://drive.google.com/open?id=19hF1NiQstTya9rIEyHfRvFLbbdiKnJE9)

Functional requirements
-----------------------

*   Add two new menu items to the Edit menu called **Group** and **Ungroup**.
*   When the **Group** menu item is chosen, all selected note bars are grouped into one _gesture_. A gesture behaves as if it is just one note bar with a complex sound to it. Once note bars have been grouped into a gesture, selecting/deleting/dragging any one of the note bars in the gesture causes the same thing to happen to all the others in the gesture.
*   If nothing is selected when the **Group** menu item is chosen, nothing happens.
*   Gestures are indicated visually by a thin black dotted line around the group of note bars. A selected gesture is indicated using a style similar to that of a selected note bar.
*   Gestures can include note bars or other gestures (that is, gestures can be nested).
*   When the **Ungroup** menu item is chosen, all selected gestures are ungrouped. Note that nested inner gestures are not ungrouped; you have to ungroup the nested gestures separately after ungrouping the outer gestures.
*   If no groups are selected when **Ungroup** is chosen, nothing happens.
*   After selecting the **Group** menu item, the new gesture becomes the only selected item. After choosing **Ungroup**, all the inner notebars and gestures of the ungrouped gesture are selected.

"Stretch goal"
--------------

*   When the right edge of a gesture is grabbed and stretched left or right, the duration and offset of everything in the gesture should stretch proportionally.

Tools and process
-----------------

*   All team members should create or join their [Project 5-8 team on Github Classroom](https://classroom.github.com/g/3Hlr8BQm). You will begin from the strongest Project 4 solution. 
*   Use [GitHub issues](https://guides.github.com/features/issues/) to manage tasks and track bugs within your project team. I will look more kindly on bugs and incomplete features if I see them listed as issues here. Starting with Project 5, I will comment on the quality of your code using issues rather than inline TODOs.
*   Consider using [Slack](http://slack.com) for team communication. You can join [WhitmanCS](https://join.slack.com/t/whitmancs/shared_invite/enQtNTg2OTg4NzYyNjkyLThhODBkMzc3Zjg5ZTJkMDFkZTU1NDNhZmYxZTkxYjQ5ODBhN2Q0MzA0ZjRmMzdjNTMzZTQ5YTI1OTBjYjE4ZjM) and create a channel for your team. There is also a channel for discussing JavaFX.
*   If you are having difficulty with the Netbeans/Git integration, consider using the command-line tool as a supplement (I can help), or try Elegit, a visual Git interface that is also installed on the Linux workstations. Alternatively, your team may choose to adopt a different IDE such as Eclipse, IntelliJ IDEA (JetBrains), or Visual Studio Code, all of which are installed on the lab computers.
*   Play [Planning Poker](https://docs.google.com/a/whitman.edu/document/d/1Qa-E-uucvmyyI0y3PFxdg2YnvjeMVpfQ-SnJ0m_LDiA/edit?usp=sharing) to estimate how much effort each task will take. Before playing, agree on a class design for gestures and discuss what refactoring tasks you want to undertake. (See Advice below.)
*   Keep track of how many person-hours you spend on the project so that you can compute a velocity (points/person-hour).
*   Try to allow some time for a brief team retrospective. What do you want to keep doing (sustain)? What do you want to improve? How?

Advice
------
*   Plan to spend some time on design. This is an appropriate time to apply what you've learned about inheritance and interfaces. You may need to refactor existing code in order to implement new features.
*   With citation, you may improve the elegance of your solution by borrowing code and inspiration from my [Project 4 sample solution](https://github.com/WhitmanSWDesignSpring2018/project4-sample-solution) or from team members' solutions.
*   Your FXML file is probably getting pretty big by now. If you haven't done so already, learn about the `<fx:include>` tag and use it as appropriate. Not only does this let you have smaller, more organized FXML files, it also lets you create smaller, more organized controllers. For example, the composition pane and the instrument pane might each have their own controllers, separate from the main application controller that handles the menu items.
*   We use FXML and CSS to help with separation of responsibilities. But the Model-View-Controller (MVC) pattern goes beyond just using FXML and CSS. To learn more about MVC, watch [this video](https://www.youtube.com/watch?v=6EcjhVwH0Dw) from 18:36 - 35:00. In a nutshell, the Model should be independent from the user interface. For example, it should be possible to use the same Model classes with a graphical user interface or with a command-line interface. The View should get all the data it displays from a Controller, who gets it from the Model. The Model and the View should not talk to each other directly, but only indirectly through a Controller or via an Observer/Observable relationship. Controllers can talk to each other as much as they want.
*   In Project 3, many groups had three kinds of children in the composition pane: horizontal lines, the moving red line when playing, and note bars. This means you have to keep a separate list of the note bars so you can select them and play them. This is duplication of knowledge. Here's another way: As some of you have discovered, a `StackPane` contains a stack of Panes. By default a Pane is visually transparent. Panes can also be configured so they are transparent to mouse clicks. Therefore, you can create one Pane with all the lines, one Pane with all the note rectangles, and even a third Pane in front in which the red line moves. Then all the children of the composition pane are note rectangles, and there is no need to keep a separate list.

What to Submit
--------------

### Code

Push your code to your team's GitHub repository. When you are done, **tag your work as `project-5-release`**.

### Documentation

**This is new:** Put all documentation for Project 5, including the UML and reflection, in the ```project-5``` directory. I will also put the grading rubric there.

#### UML

Provide a UML diagram shoowing all the core classes that you created or modified in this project and the associations between them. I recommend [draw.io](http://draw.io), a [free](https://support.draw.io/pages/viewpage.action?pageId=11829278) drawing tool that integrates with GitHub. Some others also recommend [LucidChart](https://www.lucidchart.com/documents#docs?folder_id=home&browser=icon&sort=saved-desc), which has a [GitHub integration](https://www.lucidchart.com/pages/integrations/github).

#### Team reflection

Write your team reflection in ```project-5/README.md``` using a [MarkDown](https://daringfireball.net/projects/markdown/). Address the following points:

*   Give a concise overview of your design for the new features. How did you divide the code into classes and methods? Make sure you address whether and how you refactored the code implementing earlier features.
*   Explain what, if anything, is particularly elegant about your solution and why it is elegant. *For full credit, apply concepts, guidelines, and/or principles you learned in class.*
*   Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it). *For full credit, apply concepts, guidelines, and/or principles you learned in class.*
*   Include an estimate of your velocity. How many story points did you estimate you would complete during this assignment? How many did you actually complete, how many person-hours did the team spend, and what is the ratio of points/person-hour? _I want you to monitor your velocity to help you plan better ovehttps://classroom.github.com/g/3Hlr8BQmfr the final iterations. There are no "good" or "bad" numbers for velocity._
*   How did you colloborate? What role[s] did each team member play? What went well that your team will keep doing during the next project assignment? What will you improve? How?

### Code reviews

For Projects 5-8, team members will take turns walking me through the code and explaining their contributions. Each student should expect to make at least two such presentations. I will provide information about scheduling closer to the deadline.

Acknowledgments
---------------

This assignment is adapted from one by Dale Skrien.
