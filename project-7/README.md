Team Reflection: Project 7 (Copy, Cut, Paste, and Save)
=================================

*   Give a concise overview of your design. What strategy did you use to implement copy, cut, paste, and save? How did you divide the code into classes and methods? How does your design for Project 6 differ from your design to Project 5? How did you respond to feedback? (If I get it to you in time.)

In this version of the code we implemented copy, cut, paste, and save functionality. We also built in behavior so that a user is prompted to save before exiting or opening a new document. After some discussion, we decided to implement copy/cut/paste and save using the same XML parsing system (rather than developing our own clipboard that could store Playables). We added three new classes: CompositionParser (to manage writing compositions as XML documents, and parsing XML into compositions), CompositionAlert (for the About page and Save prompt), and CompositionFileChooser (which uses the JavaFX FileChooser to save and select XML files).  

We also addressed several issues that showed up with Undo-Redo, including an issue where clicking undo would eliminate the inner gesture in a nested gesture. These were simple fixes that required minimal adjustments to the code (moving lines earlier/later within methods).

*   Explain why your way was the elegant way to do it. Address any improvements you made. *For full credit, apply concepts, guidelines, and/or principles you learned in class.*

Our solution is elegant in several ways. Each of the new responsibilities was separated into what we see as appropriate classes (Expert Pattern). The Composition Parser is related to everything about transfering information to XML (we are wondering whether notes and gestures should be able to write themselves with a toXML method included in the Playable interface, which would reduce coupling/conditionals and allow for dynamic method invocation). The CompositionAlert is flexible enough to be extended to other forms of alerts as needed. Finally, the CompositionFileChooser effectively uses an existing class, and deals with unusual input for saving (robust), ensuring that every file saves with an .xml tag. 

We also considered taking a sledgehammer to our TuneComposer class. We discussed the possibility of having a separate controller for menu items and the composition pane. In discussions amongst ourselves and Professor Davis, we realized that this would require a lot of additional communication between classes and leave the allPlayables set relatively exposed, so we decided with our more secure approach.

*   Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it). *For full credit, apply concepts, guidelines, and/or principles you learned in class.*

We struggled to find an elegant solution to returning a value from our save prompt, as there were three possible values that could be returned. After consulting Professor Davis we settled on an enumerator to make clear the meaning of the value returned (instead of simply 0, 1 or 2), but still feel that there could be a more elegant solution. In discussing solutions with Professor Davis the Command-Query and DRY principles came up in opposition to each other among solutions. We decided to go with the solution that catered more heavily to the Command Query principle, but violated the DRY principle. Another inelegant aspect is that the red X button on the TuneComposer does not prompt to save when clicked as the Exit menu item does. We were unable to figure out how to interrupt a lambda function in the case of cancel (as the handler for the X is assigned in start()), as we had done with the handler for the exit menu option.

*   Include an estimate of your velocity. How many story points did you estimate you would complete during this project? How many did you actually complete, how many person-hours did the team spend, and what is the ratio of points/person-hour? How does this compare to your velocity for the previous project assignment?
*   Include a short summary of your team retrospective. What went well that your team will keep doing during the next project assignment? What will you improve? How?

### Code reviews

For Projects 5-8, team members will take turns walking me through the code and explaining their contributions. Each student should expect to make at least two such presentations. I will provide information about scheduling closer to the deadline.

We are pleased with how this project went. We were very efficient with splitting up the work to subgroups (after discussing design). We appreciated the extra time at the end of the project, when we had implemented all of the features, to thoroughly go over our design choices and refactor some of the code. Unfortunately, everyone's schedule was more busy than previous weeks, so not all group members were present during certain consequential decisions and implementations. As always, camaraderie and enthusiasm are at an all time high!  

Next project, we plan on maintaining our dedication to thoroughly discussing design with the entire group as we have for all of the projects. We also want to keep the good division of labor that was relatively new to our workflow with Project 7.

Next time, we want to spend more time planning at the begining to make sure that the things we choose to do are reasonable goals. We also hope that our schedules line up more nicely in the coming week.

Acknowledgments
---------------

This assignment is adapted from one by Dale Skrien.




