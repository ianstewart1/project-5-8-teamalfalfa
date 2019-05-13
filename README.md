README: Project 8
=================================

* Give a concise overview of your design. How did you implement the new features? How does your design for Project 8 differ from your design for Project 7? How did you respond to feedback? (If I get it to you in time.)

In Project 8 we added custom improvements to the GUI and functionality and fixed several issues with the previous release of our code. Our code now allows the user to switch the instrument of any set of playables and adjust the volume using a slider, which changes the opacity of the notes. We improved the GUI by adding the option of a "dark mode," making prettier gestures, and creating a custom icon for the application.

As part of this project, we also fixed open issues. The composition pane now updates after notes are added or moved so that it can extend as far as needed. There was also a bug relating to "save" that we fixed, and an issue with selection when the user copied and pasted parts of the composition. 

* Explain why your way was the elegant way to do it. Address any improvements you made. For full credit, apply concepts, guidelines, and/or principles you learned in class.

Several elements of our new design are elegant. We implemented a version of the observer pattern by adding a listener to the volume slider that causes the note volumes to be updated. The way in which our parser was made allowed us to easily add new attributes to notes so that they can be saved and loaded with different volumes. In order to change notes, a user can simply select the notes, click a new instrument, and apply the change by clicking the switch instrument menu item. 

The dark mode takes advantage of high-level coding and existing libraries. Instead of changing the individual nodes in the pane, as we originally anticipated, we simply added a style sheet to the scene that adjusted all of the children nodes to a darker theme. Using a CheckMenuItem to select this option is intuitive and usable because the view mode toggles between exactly two options.

* Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it). For full credit, apply concepts, guidelines, and/or principles you learned in class.

The instrument changing feature is not the most elegant in that the user must know that the instrument that will be applied will be the one selected on the left side-bar. We were hesitant to add menu items that would be essentially duplicating the functionality of the instrument side-bar; however, this could potentially be an infraction on the Principle of Least Astonishment.

Another inelegant feature was the volume slider, and specifically the fact that it doesn't reflect the currently selected Note's volume value. This was because we weren't sure how to constantly check for what was selected on the composition pane, and were also unsure how we wanted to update the slider value when multiple Notes were selected. The slider was implemented using a listener and observable, but we couldn't figure out a way to do the same in reverse to update where the "thumb" was on the slider.

* Include an estimate of your velocity. How many story points did you estimate you would complete during this project? How many did you actually complete, how many person-hours did the team spend, and what is the ratio of points/person-hour? How does this compare to your velocity for the previous project assignment?

For everything we planned to implement for this project, we estimated that we would complete 6 story points. Unfortunately, we found some old bugs. Fortunately, we had time to resolve these issues. Including the bugs we resolved and the new features we implemented, we completed a total of 11 story points for this project. We spent about 34 person hours on this project which gives us a velocity of approximately 0.324 storypoints/personHours. This velocity is slightly slower than last project 7's. We think this is because we had more trouble focusing on work when we met for this project and because we had to spend more time actually planning what features we were going to implement which we did not have to do in previous projects.

* Include a short summary of your team retrospective. What went well that your team will keep doing during the next project assignment? What will you improve? How?

I wish that we could work together sometime in the future, but unfortunately this is the last project. During our retrospecitve we talked about what we enjoyed. We plan to keep allotting a good portion of time for design at the beginning of any other projects we get to work on in our careers. Both Ian and Paul give thumbs up, and Paul even said that the process was "great." 
