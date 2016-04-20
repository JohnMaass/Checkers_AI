# Checkers_AI

This program contains a checkers AI I created for my Heuristic Programming course.  It uses a min-max evaluator function
in order to determine the best play along with an early move database derived from tournement games hosted by the American 
Checkers Federation.  The version that was used for credit also used Chinooks 6-piece end game database, but I removed it
in this version for simplicity since accesses required the use of sockets and was set up to run on linux machines.  The debug 
code to access the database can be found on my github page.

Grades for the project were assigned based on how well our AI  placed in a tournament with other students AI.  Overall, this 
program tied for second with 2 other groups.  

The function used to evaluate any given state takes into account the positions of the total pieces, men positions, king 
positions, total jumps and number of trapped pieces.  Like everyone else's AI in the class, the end game was problematic.  
One problem that seemed to be universal was that the AI would get stuck in a loop repeating the same moves when there were
only 3 or 4 piece on the board.  

# Running the Program

To run this program import the project from the pom.xml into netbeans 8.1 and run com.checkers.game.gui.Checkers_Gui.  
A new game can be created by selecting the new game menu option in the File menu.  You can choose from human, random(chooses 
moves randomly) or AI players and which color goes first.  The moves for each color are select in two different jLists.  When 
a move is selected it is highlighted on the board.  Any move can be undone using the undue menu option under the Edit menu.
