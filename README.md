# README.md

#### Give a concise overview of your design. How did you divide the code into classes and methods, and why? How does your design for Project 4 differ from the design you inherited for Project 3? How did you respond to my feedback on that solution?

* We divided our code into five classes, adding SelectionArea and Instrument to the pre-existing ones from project 3. We 
used Instrument.java to handle our instrument enumeration, and SelectionArea.java was implemented to handle the selection when
a click on the composition pane was dragged. In terms of additions to the pre-existing classes, we added quite a few handlers 
for each of the types of mouse movements, trying to maximize our code reuse wherever we could. We took the feedback from 
project 3 and corrected/modified to account for them wherever we could when working on project 4.

#### Explain what, if anything, in your solution is inelegant and why you didn't make it elegant (for example, maybe you didn't have time or the knowledge to fix it).

* We were unable to refactor some of our more complicated methods to have them operate on the same level of abstraction as we 
ended up running out of time while working to finish up functionality and add last-minute javadoc comments. We were also unable
to transition the path for the PlayLine to the recommended implementation from your comments on project 3 due to the same
reason as above.

#### Finally, describe how your team collaborated on the project. What did you do together? What did you do separately? What did each team member contribute?

* Our team was able to work very productively on this project. We worked together in most cases, but tended to break into pairs 
of two to work on distinct functional requirements. Separately we did a little bit of debugging, some research, and quite
a bit of fixing merge conflicts with our most recent feature branches. It is hard to evaluate what each team member contributed
since we worked in pairs on most features, and switched pairs between different feature branches (Hopefully improved our
error spotting, as the article earlier this semester suggested it might).

#### In your reflection on collaboration, include a reflection on estimation: How did you use your estimates? For which requirement(s) did you make the most inaccurate estimate(s)? Why?

* We used our estimate to gauge how long we anticipated the project would take, and especially as a progress indicator 
as we worked throught the various project requirements. A lot of our estimates were not especially accurate, as we
ended up implementing things in a different order than we anticipated we would during the planning poker phase.
