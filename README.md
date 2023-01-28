# A1 - Piraten Karpen

  * Author: Ahren Chen
  * Email: chena125@mcmaster.ca

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in trace mode:
    * `mvn -q exec:java -Dexec.mainClass=PiratenKarpen -Dexec.args="trace [random/combo] [random/combo]"` (here, `-q` tells maven to be _quiet_)
  * To run the project normally:
    * `mvn -q exec:java -Dexec.mainClass=PiratenKarpen -Dexec.args="normal [random/combo] [random/combo]"`
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar` 

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Game Notes
* Each player draws a card and rolls dice based on their strategy.
* If 2 players score the same amount of final points, no one gets the win.
* If a player is in a sea battle, and they end up getting the required amount of sabers but still has 3 skulls: No points are deducted, but not points are earned either. The rulebook did not account for this specific situation, so I took my own liberty and decided this.
* The combo player will play be keeping all gold and diamond coins, and also try to hit 3 of a kind with the rest of the dice.

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * The feature works during the game with no errors during operation and the feature accounts for all abnormal and edge cases. The feature will be coded and commented in a way that is clear to others who read it as well.

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01 | Roll a die and display the result |  D | 01/01/23 | 01/10/23 |
| x   | F02 | Roll eight die and display results  |  D | 01/10/23 | 01/10/23 |
| x   | F03 | Set number of games as 42 roll until and print results |  D  | 01/10/23 | 01/17/23 |
| x   | F04 | end of turn with three or more skulls | D | 01/11/23 | 01/17/23 |
| x   | F05 | Player keeping random dice at their turn | D | 01/11/23 | 01/17/23 |
| x   | F06 | Score points: multiply number of gold coins & diamonds by 100 | D | 01/11/23 | 01/17/23 |
| x   | F07 | Add tracers instead of print statements | D | 01/17/23 | 01/17/23 |
| x   | F08 | Allow users to choose whether they want to see the project in trace mode or not based on input arguments | D | 01/17/23 | 01/17/23 |
| x   | F09 | When rerolling, players must reroll at least 2 die | D | 01/18/23 | 01/18/23 |
| x   | F10 | Allow users to choose what strategy they want each player to use. Player will randomly roll dice until they get 3 of a kind | D | 01/20/23 | 01/21/23 |
| x   | F11 | Implement point scoring for 3 of a kind | D | 01/20/23 | 01/20/23 |
| x   | F12 | Implement point scoring for the rest of the combos | D | 01/20/23 | 01/20/23 |
| x   | F13 | Implement full chest scoring combo | D | 01/20/23 | 01/20/23 |
| x   | F14 | Have the player roll dice until they hit at least 3 of a kind by rolling based on logic | D | 01/20/23 | 01/21/23 |
| x   | F15 | Give the correct point bonus to the 6 cards for sea battle | D | 01/27/23 | 01/27/23 |
| x   | F16 | Add in the condition that you must get a certain amount of swords to get points for sea battle | D | 01/27/23 | 01/27/23 |
| x   | F17 | Have the player play a different strategy specifically for sea battle card | D | 01/27/23 | 01/27/23 |
