Date & Description\
Jan 9 2023: I cloned the repository onto my local desktop and set it up using intelliJ
and I think I got the Git and Maven working, but I am not certain.

Jan 9 2023: I have confirmed that Git is working, and to answer the questions of 
whether the MVP's are needed I have created a document on google docs.

Jan 10 2023: I have started to work on step 2 and have confirmed that the dice roll
is currently working. I also updated the README file to better fit my goals for step 2 with
the backlog as well as the definition of done. I finished the feature to roll 8 dice
at the same time and had it print out the results.

Jan 11 2023:
* I fixed my code to make it a method in the pk package rather than in the PiratenKarpen file.
* Changed the backlog to better match the current step requirements.
* Started working on the other features.
* Finished the base setup to play a singular game and the score per player.
* Finished simulating 42 games, which completes MVP's 3,4,5.
* Able to print out the percent of wins for each player, finishing step 2
* Cleanup the program by commenting and removing unnecessary debug prints

Jan 13 2023:
* Created a logger to replace print statements while debugging, but confused on how step 3 requires me to make a trace mode.
* Learned how to input command line arguments using maven, and updated the README with a new command.

Jan 17 2023:
* I learned that I did the game and understood step 2 wrong. Today I will try to finish the completed business logic for step 2.
* I fixed the game, so that the game only ends if a player reaches 6000 or more points. 
* Each turn, a player has a 50% chance to choose to reroll any dice at all
* They will also choose with 50% chance to reroll any dice that is not skulls should they choose to reroll
* Completed logger tutorial and successfully tested it in my code.
* Added the feature where the user can choose to display the logging information or not
* Added more logging statements to my code

Jan 18 2023:
* Started on feature 9 where each player if they choose to reroll, must reroll at least 2 die.
* Time to refactor the code and reorganize, so that my code is not placed in the main PiratenKarpen file.
* Finished reorganizing and finished step 3

Jan 20 2023:
* Removed some magic numbers in the player class and playstyle class for clarity.
* Modified where I create the players to support encapsulation.
* Modified how to calculate score based on a map of each face to their occurance in the rolls.
* Optimized even further how the players reroll by using maps in preparation for step 4. Version step3.2.
* Completed F11 by implementing scoring 3 of a kind.
* Completed F12 by implementing all the combo bonuses except full chest.
* Completed F13 by implementing the full chest feature. 
* Changed MVPs to clarify my tasks.
* Allowed user input on how many random players they want and how many players using the combination strategy they want.
* Finished F10 by implementing an option to aim only for combos for players. I also made some variables global to the player class.
* Going to work on implementing some work of logic when choosing what to reroll instead of randomly doing it when playing for combos.

Jan 21 2023:
* Completed F14 by having the player roll based on a strategy. The player will choose a face that occurs the most amount of times, and reroll everything but that face until a face appears 3 or more times.
* Fixed some bugs and also updated the final results printing to include what strategy each player is using.

Jan 27 2023:
* Started Step 5 and updated the README with new MVP's.
* Created card class, cardpile class, cardfaces enum, carddrawer class, drawer abstract class, and source interface to draw from.
* Created methods so that when the card pile is created it is already shuffled.
* Created the draw method in the card drawer class
* Allowed players to draw cards and add the card score to their score that round if they did not get 3 skulls
* Completed condition that you must get a certain number of sabers in order to win the sea battle
* Completed strategy for player to use in case they are in a sea battle
* Found error when reroll dice (When I reroll a type of die, the result is added onto the other die and is counted as part of the original. Messes with how many die of that face I am supposed to reroll)