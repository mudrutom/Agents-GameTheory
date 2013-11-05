# Agents-GameTheory

Solver for finding the Nash equilibrium of a simple multi-agent game.

It's the second assignment for the course Multiagent Systems (AE4M36MAS) at FEE CTU.

## The game description

An agent is placed on an island with a map and its task is to reach the destination square on the island while collecting as many gold
treasures as possible and stepping at each square at most once. Besides the agent, there are bandits on the island, that intend to ambush
the agent and steal the collected gold. The bandits appear on marked places and the agent only knows the overall number of the bandits,
however, does not know the exact places where they are waiting. The bandits know the utility value of your agent and their goal is exactly
opposite, however, they do not know the position of your agent, neither can they move. The agent can react on information gained during
the course of the game – i.e. if the agent crosses a dangerous place with a bandit, but the attack of the bandit was not successful,
the agent can exploit this knowledge as it knows, that there is less bandits in the remaining places, etc. As soon as the agent reaches
the destination square, the game ends.

The agent receives 10 points for reaching the destination, he receives 1 point for each collected gold treasure. If a bandit successfully
attacks the agent, the agent receives 0 points. If the agent follows a path that does not end in the destination square (e.g. if it
leads to a dead/blind end), the agent receives 0 points.

## Goals

* formalizing this problem as a two-player zero-sum extensive-form game
 * define information-sets and actions for players
 * define utility values for leafs in the game tree
* either:
 * (A) transform the game to the normal form (i.e. create a game matrix)
 * (B) formalize the game using the sequence form
* formalize the computation of Nash equilibrium as a linear program (LP) and solve the LP using CPLEX

## Input

The program takes one argument (the input file) with the following format:
* number: number of rows of the maze (M)
* number: number of columns of the maze (N)
* M rows, N symbols representing squares of the maze
* number: overall number of the bandits
* float number: probability of a successful attack (i.e., the probability that a bandit successfully attacks the agent if the agent crosses the square with a bandit)

Squares of the maze are defined as follows:
* ```#``` - obstacle
* ```-``` - empty square
* ```S``` - start of the agent
* ```D``` - destination of the agent
* ```G``` - gold treasure
* ```E``` - dangerous place

### Example
```
7
9
#########
#G-----E#
#-#####-#
#S--E--D#
#-#####-#
#G-----E#
#########
2
0.5
```

## Output

Output of the program will have several parts.
* a list of pure strategies or sequences for each of the players
* calculated utility function for all strategies or sequences
* calculated mixed strategies or realization plans in Nash equilibrium
* calculated value of the game

It will also export the linear program into a file.
