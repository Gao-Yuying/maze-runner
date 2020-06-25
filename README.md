# maze-runner
A program that generates mazes and looks for a way out.

## Available options
### Generate a new maze with size of *n*
```
=== Menu ===
1. Generate a new maze
2. Load a maze
0. Exit
>1
Enter the size of a new maze
>15
██████████████████████████████
██              ██  ██      ██
██████  ██████████  ██  ██████
██                  ██      ██
██████  ██  ██████████  ██████
██      ██                  ██
██████  ██████  ██  ██  ██████
██  ██      ██  ██  ██      ██
██  ██████  ██████████  ██    
██  ██              ██  ██  ██
██  ██  ██  ██████████████  ██
██      ██          ██  ██  ██
    ██████  ██████████  ██  ██
██  ██                      ██
██████████████████████████████

```
### Load a maze
```
=== Menu ===
1. Generate a new maze
2. Load a maze
0. Exit
>2
Enter file name(.txt):
>maze.txt
Success!
```
### Save the maze (when a maze exists)
```
=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
5. Find the escape
0. Exit
>3
Enter file name(.txt):
>another_maze.txt
Success!
```
### Display the maze (when a maze exists)
```
=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
5. Find the escape
0. Exit
>4
████████████████████████████████████████
██                      ██  ██  ██  ████
██████████  ██████████████  ██  ██  ████
██              ██      ██      ██    ██
██████  ██  ██████  ██████  ██████      
██      ██              ██          ████
██████████████████████  ██  ██████  ████
        ██          ██                ██
██  ██  ██████████  ██  ██████████  ████
██  ██          ██  ██  ██          ████
██████████  ██  ██  ██  ████████████████
██      ██  ██              ██      ████
██  ██  ██████████  ██  ██████  ██  ████
██  ██  ██  ██      ██  ██      ██    ██
██  ██  ██  ██  ██  ██████████  ████████
██  ██          ██  ██      ██        ██
██████████████  ██  ██████  ██████  ████
██                                    ██
██  ██  ██████  ██████████  ██  ██  ████
████████████████████████████████████████
```
### Find the escape (when a maze exists)
```
=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
5. Find the escape
0. Exit
>5
████████████████████████████████████████
██                      ██  ██  ██  ████
██████████  ██████████████  ██  ██  ████
██              ██      ██      ██    ██
██████  ██  ██████  ██████  ██████//////
██      ██              ██        //████
██████████████████████  ██  ██████//████
////////██          ██//////////////  ██
██  ██//██████████  ██//██████████  ████
██  ██//////////██  ██//██          ████
██████████  ██//██  ██//████████████████
██      ██  ██//////////    ██      ████
██  ██  ██████████  ██  ██████  ██  ████
██  ██  ██  ██      ██  ██      ██    ██
██  ██  ██  ██  ██  ██████████  ████████
██  ██          ██  ██      ██        ██
██████████████  ██  ██████  ██████  ████
██                                    ██
██  ██  ██████  ██████████  ██  ██  ████
████████████████████████████████████████
```
### Exit
```
=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
5. Find the escape
0. Exit
>0
Bye!

Process finished with exit code 0
```
## Demo
![demo.gif](https://github.com/Gao-Yuying/maze-runner/blob/master/Maze%20Runner/task/src/maze/res/demo.gif "demo")
