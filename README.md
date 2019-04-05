# README.md

#### Give a concise overview of your design for the new features. How did you divide the code into classes and methods? Make sure you address whether and how you refactored the code implementing earlier features.

We created a new interface Playable, which both Note and Gesture implement. Playable mainly consisted of methods from Note
that would also need to be performed on Gestures. Gesture contains a Set of Playable objects, which can be Notes or other 
Gestures. In TuneComposer, the Set of Notes was replaced with a Set of Playables. This allows it to contain both Notes and 
Gestures. All methods of TuneComposer that previously operated on Notes or took Notes as parameters were modified to take 
Playables instead.

#### Explain what, if anything, is particularly elegant about your solution and why it is elegant. For full credit, apply concepts, guidelines, and/or principles you learned in class.

We consider the creation of Playable elegant. Notes and Gestures share many of the same responsibilities, and we wanted to group them together into sets. If they both were the same type, then methods that ran on a Playable could be called on both
Notes and Gestures through dynamic method invocation without concern. This allowed us to expand upon our original code without changing the preexising structure of TuneComposer. We also managed to refactor several existing methods and classes, removing some fields and collections that we thought were unnecessary (startTime in Note, selectedNotes in TuneComposer) and removing redundancies. 

#### Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it). For full credit, apply concepts, guidelines, and/or principles you learned in class.

We realize that much of the code in the Note and Gesture classes is similar, or even identical, because some methods we included in the Playable interface did not requrie radically different implementations in the two implementing classes. This is inelegant because it makes our code longer and it reduces maintainability. If a method is changed in one class, it may need to be changed in the other class as well. 

Another small inelegance is that Note has a method getNodeList() that will always return a List of one element. This was done
because Set's removeAll method takes a List object as its parameter. This could be considered in violation of the Principle
of Least Astonishment, since a List is typically understood to contain more than one object.

In both of these cases, however, we decided that the usefulness of the Playable class in its current form outweighed these concerns.

#### Include an estimate of your velocity. How many story points did you estimate you would complete during this assignment? How many did you actually complete, how many person-hours did the team spend, and what is the ratio of points/person-hour? I want you to monitor your velocity to help you plan better ovehttps://classroom.github.com/g/3Hlr8BQmfr the final iterations. There are no "good" or "bad" numbers for velocity.

We did not compute an initial estimate of story points, but we did exceed our expectations by completing the assignment
with time to spare. We spent roughly 40 person-hours on the project, working quickly and solving major issues roughly
ever hour of work. To guesstimate a bit, 1 major story point was completed per 4 person-hours.

#### How did you colloborate? What role[s] did each team member play? What went well that your team will keep doing during the next project assignment? What will you improve? How?

We paired-programmed each time our group met, swapping partners during each session. Before writing any code, we held a design meeting where we decided on an list of the methods for the Playable interface, which we expanded as we uncovered additional Playable responsiblities. 
Each team member was a developer and designer. On this project, we were easily able to schedule times to meet as we went, but on future projects we may want to block off a regular coding session each week so that we have to coordinate less. 
Overall, paired-programming worked incredibly well to write quality code quickly. We also all realized the value in spending a signficant amount of time designing before coding--we knew better where we were going from the beginning. Nevertheless, there were some times when the two pair-programming teams did not commuicate effecitvely and ended up with small conflicts that we had to resolve after the fact. We will hold more frequent chek-ins between pairs about each pair's responsibilities,
in particular after a pair commits their code.
