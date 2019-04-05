# README.md

#### Give a concise overview of your design for the new features. How did you divide the code into classes and methods? 
Make sure you address whether and how you refactored the code implementing earlier features.

* We created a new interface Playable, which both Note and Gesture implement. PLayable mainly consisted of methods from Note
that would also need to be performed on Gestures. Gesture contains a Set of Playable objects, which can be Notes or other 
Gestures. In TuneComposer, the Set of Notes was replaced with a Set of Playables. This allows it to contain both Notes and 
Gestures. All methods of TuneComposer that previously operated on Notes or took Notes as parameters were modified to take 
Playables instead.

#### Explain what, if anything, is particularly elegant about your solution and why it is elegant. For full credit, 
apply concepts, guidelines, and/or principles you learned in class.

* We consider the creation of Playable very elegant. We wanted Notes and Gestures to share an is-a relationship so that we could
have polymorphism between them. If they both were the same type, then methods that ran on a Playable could be called on both
Notes and Gestures through dynamic method invocation without concern. This allowed us to refactor our original code without
changing many of the preexising TuneComposer methods. 

#### Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe 
you didn't have time or the knowledge to fix it). For full credit, apply concepts, guidelines, and/or principles you 
learned in class.

* Some code was almost completely copied between methods in Note and Gesture that were very similar for both. We did this because
some methods that both required and that we deemed necessary to include in the Playable did not requrie radically different
implementations. This has the disadvantage that if these similar methods are changed in one class, they will need to be
changed in the other class as well.
Another small inelegance is that Note has a method getNodeList() that will always return a List of one element. This was done
because Set's removeAll method required a List object to operate on. This could be considered in violation of the Principle
of Least Astonishment, since a List is typically understood to contain more than one object.
We feel however that these inelegances have been far outweighed by the usefulness of the Playable class.

#### Include an estimate of your velocity. How many story points did you estimate you would complete during this 
assignment? How many did you actually complete, how many person-hours did the team spend, and what is the ratio of 
points/person-hour? I want you to monitor your velocity to help you plan better 
ovehttps://classroom.github.com/g/3Hlr8BQmfr the final iterations. There are no "good" or "bad" numbers for velocity.

* We did not do an initial estimate of story points, but we did exceed our expectations by completing the assignment
with time to spare. We soent roughly 40 person-hours on the project, working quickly and solving major issues roughlu
ever hour of work. To guesstimate a bit, 1 major story point was completed per 4 person-hours.

#### How did you colloborate? What role[s] did each team member play? What went well that your team will keep doing 
during the next project assignment? What will you improve? How?

* We performed paired-programming in duos that we swapped around depending on what the goal of the work was. 
Before writing any code, we held a design meeting where we decided on the Playable interface and the methods it
would include. The methods included was subject to change, but the overall design is what we finally implemented.
Each team member was a developer, and responsibilities of planning and design were also shared between members.
Overall, paired-programming worked incredibly well to write quality code quickly. Also, starting purely with design
made the rest of the project go much mroe smoothly. There were some times where the scope of the work of each pair
was not communicated between pairs. We will hold more frequent chek-ins between pairs about each pair's responsibilities,
in particular after a pair commits their code.
