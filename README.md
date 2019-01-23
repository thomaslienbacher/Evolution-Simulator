# Evolution Simulator

A hobby project about evolution.

## Description
This simulation is about how much food a robot can collect in a given world.
At the beginning of the simulation robots are generated with random genes,
then all robots are simulated, this is called a cycle.

### World
The world is made up of a grid like structure. A cell may contain food or not.
If the robot goes out of the world bounds it wraps around on the other side.

### Robot
The robot moves around the world in a pattern determined by its genes.
While moving around it scannes for food. Moving around costs energy gathering food
gives energy. Once the energy drops below 0 the robot dies.
Fitness is a measurement on how much food was collected by the robot.

### Genes
An array of bytes which determine the direction of the next move,
eg. `genes = [2, 1, 4, 5, 3, 7, 4, 0]`. The direction is a number between 0 and 7.

### Evolution
After each cycle the top 50% are allowed to reproduce.
The bottom 50% will be removed. 
Reproduction is done by copying the genes of a robot and changing them in length and/or value.
