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