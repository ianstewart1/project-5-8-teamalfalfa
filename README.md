Project 7: Cut, Copy, Paste; Save
=================================

In this project, you are to enhance the preceding project by adding two new capabilities: the ability to cut, copy, and paste elements of a composition, and the ability to save to a file the musical compositions you create and later reload them into your application.

Functional requirements
-----------------------

*   Add **Cut**, **Copy**, and **Paste** menu items to the **Edit** menu with the standard keyboard shortcuts.
    *   When **Cut** or **Copy** is chosen, all currently selected note rectangles and gesture rectangles are copied into the system clipboard. If **Cut** was chosen, the original selected rectangles are also deleted.
    *   When **Paste** is chosen, the rectangles in the system clipboard are added to the composition panel. The pasted rectangles become the only selected rectangles. You should be able to paste the contents of the clipboard multiple times, adding new copies of the clipboard's rectangles to the composition each time.
    *   Gestures can be cut/copied/pasted just like individual notes.
    *   **Cut** and **Paste** should be undoable/redoable actions (**Copy** is not undoable). The clipboard contents do not need to be restored to its previous value when undoing a **Cut** operation.
    *   If you cut and then immediately paste, you should end up with the same thing as before you cut, except that there are now two new actions to be undone/redone.

*   Add five new menu items to the **File** menu. These menu items are **About...**, **New**, **Open...**, **Save**, and **Save as...**. These menu items should behave as they behave with other applications, except that we will allow only one window (stage) at a time to appear.
    *   The **About...** menu item should be at the top of the **File** menu. When the **About...** menu item is selected, a dialog box appears saying something about the application. You can include whatever you want in this dialog. Just be sure to include all your team's names as authors of the software.
    *   There should be a separator between the **About...** menu item and the rest of the **File** menu items and there should be a separator between the **Exit** menu item (placed at the bottom of the menu) and the rest of the menu items.
    *   When the **Save as...** menu item is chosen, a dialog appears in which the user is asked for the name of the file into which the sounds in the composition panel are to be saved. If the user enters a legal name and presses the **OK** button in the dialog, then your application should create a new text file by that name and write to that file all the information concerning the current contents of the composition panel so that those contents can later be reloaded. I don't care where (that is, in which directory) the file is saved. If the user presses the **Cancel** button in the dialog, then the dialog closes and no saving occurs.
    *   When the **Save** menu item is chosen for a composition that was not loaded from a file nor ever saved to a file, it behaves like the **Save as...** menu item. If the current composition was loaded from a file or previously saved to a file, then the composition is saved to that file. No dialog appears in that case.
    *   If the **New** menu item is chosen and the current composition has already been saved to a file, then the composition panel is cleared as if the application had just started up. If the current composition has been changed since it was last saved to a file, a dialog appears asking whether you want to save the composition before closing it. There should be 3 options: **Yes**, **No**, **Cancel**. If the user chooses **No** from the dialog, the current composition is disposed of and the composition panel is cleared.  If the user chooses **Cancel**, the dialog just disappears. If the user chooses **Yes**, the behavior is the same as if the **Save** menu item was chosen.
    *   If the **Open...** menu item is chosen, it behaves like the **New** menu item with regard to saving the current composition. Then, if the user hasn't canceled, an dialog appears in which the user can select a file to open. If the user chooses a valid file, it is loaded into the composition pane. If the user cancels, the dialog disappears. If the user chooses an invalid file, an error message is displayed and the open dialog stays visible.
    *   If the **Exit** menu item is chosen and there have been changes made to the composition since it was last saved, then a dialog appears asking the user whether she wants to save the composition before exiting. If the user chooses **No** from the dialog, then the program exits.  If the user chooses **Cancel**, then the exiting is canceled and the application stays running. If the user chooses **Yes**, then the application behaves as if the **Save** menu item was chosen, after which the application exits.
    *   Starting a new composition or opening a file should cause all undo/redo state to be cleared. You cannot undo the saving or loading of a file or a new composition.
    *   Each of the five new menu items should be disabled if their action is not appropriate to the current application state. In particular, the **Save** menu item should be disabled if the composition has not been edited since the previous save.

Advice
------
*   Every team is in a really different place right now. I would be happy to talk with any team about the choices you are making. The sooner, the better.
*   JavaFX has a Clipboard class that makes cut/copy/paste using the system clipboard very straightforward. You can figure out how to use it by reading the online documentation.
*   You are welcome to store the data any way you wish on the clipboard. One way is to store the data as a String. That is, the `cut` and `copy` actions just take the selected rectangles, convert the rectangles into a string, and store that string in the system clipboard. The `paste` action then gets the contents of the clipboard as a string, parses the string to convert it back into rectangles, and adds the rectangles to the composition.
*   The JavaFX **FileChooser** class should be useful for creating the open and save dialogs.
*   To load into your composition panel sounds that have been previously saved to a file, your code needs to parse the text file and reconstruct the notes. I strongly recommend you use XML. One reason to use XML is that Java 8 includes a XML parser (in the **javax.xml.parsers** package) and so you can avoid writing a parser yourself.
*   Here is a recommended XML format: The text file starts with a string of the form `<composition>` (which is called a _tag_ in XML) and ends with a `</composition>` tag. In between are a sequence of 0 or more note or gesture tags. For example, an XML file for a composition consisting of a note and a gesture containing two notes and also containing a gesture containing one note might look as follows:
    
    ```
    <composition> 
        <note pitch="60" delay="150" duration="100" channel="0" volume="100" track="0" /> 
        <gesture duration="131" delay="75" pitch="80" volume="60"> 
            <note pitch="70" delay="140" duration="100" channel="0" volume="90" track="0" /> 
            <note pitch="73" delay="170" duration="100" channel="2" volume="70" track="0" /> 
            <gesture duration="131" delay="130" pitch="40" volume="60"> 
                <note pitch="40" delay="250" duration="100" channel="1" volume="40" track="0" /> 
            </gesture>
        </gesture> 
    </composition>
    ```
    
    In this example, the note tags have pitch, delay, duration, channel, volume, and track attributes. The gesture tags have some of these attributes but also surround an arbitrary number of notes or other gestures.
    

Tools and process
-----------------

*   Continue to work with the same team on the same repository.
*   Before you commit any changes to master for this project, [create a tag/release](https://help.github.com/articles/creating-releases/) named **project-6-release**.
*   Consider using [GitHub issues](https://guides.github.com/features/issues/) to manage tasks and track bugs within your project team. I will look more kindly on bugs and incomplete features if I see them listed as issues here.
*   Consider using [Slack](http://slack.com) for team communication. You can join [WhitmanCS](https://whitmancs.slack.com/shared_invite/MTU5OTQ5MTAwNTMyLTE0OTA1NjUwNTYtYzUyOWM1OTBiMQ) and create a channel for your team. There is also a channel for discussing JavaFX.
*   Play [Planning Poker](https://docs.google.com/a/whitman.edu/document/d/1Qa-E-uucvmyyI0y3PFxdg2YnvjeMVpfQ-SnJ0m_LDiA/edit?usp=sharing) to estimate how much effort each task will take. Before playing, discuss any additional refactoring tasks you may want to undertake.
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



