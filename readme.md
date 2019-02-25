
Design Overview 
=====

Our design attempted to clarify each part of the application by separating independent parts into different panes and classes. In addition to the TuneComposer controller file and the FXML and CSS, we created separate Note and PlayLine classes to handle the data and drawing of the notes and moving red line. In the GUI, we placed the background lines in one pane, created notes in a second pane, and put the moving line in a third pane. The three panes are layered in a single StackPane. 

This design is significantly different than the design for Project 2. Our TuneComposer file now does less than it used to: more of the program is distributed into separate classes. The FXML is now more complex, with layers of elements and an initialization function to inject the horizontal background lines. In general, the program is 

Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it).

Is it Elegant?
=====

During our first meeting, we intentionally planned how our program would fit together. By adhering fairly closely to this plan, we believe that most aspects of our design are elegant. We split our program up into readable and extensible classes. We don't rely on extremely long methods that are difficult to maintain. From our testing, it appears that our code is usable and robust. 

What is inelegant about our design? As beginners using FXML, perhaps there are ways to better inject new GUI elements into the TuneComposer.fxml file that we do not know about. We also intermittently treat TuneComposer as a static class, using static methods and fields sometimes and not others. Of course, we can't make it static because it needs to be instantiated, even if only once. This also means that we're less object oriented, but we think that makes sense here.

Finally, describe how your team collaborated on the project. What did you do together? What did you do separately? What did each team member contribute? Optionally, include a brief team retrospective: What is one thing you did well as a team? What is one thing you could have improved?

Teamwork
======

For the most part, our entire team met at the same time. We split into teams of two at the beginning of the process in order to develop different parts of the project (the Note class and the background). Throughout the process, we either pair programmed or worked individually next to another team member.

When Ian was asked about our teamwork on this assignment, he simply gave the thumbs up. In general, we were satisfied with each other. Aside from minor scheduling difficulties–which was inevitable with four people–we didn't have many issues. As more improvements to our process, only one one of our members was an expert in Git. For those of us who were Git novices, we probably could have practiced better committing/branch/merging techniques, but we always did have backup if we needed it. 


Future Feature ideas
=====
During our design process, we thought of possible improvements. Here are a few of our feature ideas:
* Make the notes draggable, which changes their duration
* Make notes snap to a grid of vertical lines
* Make window infinitely long
