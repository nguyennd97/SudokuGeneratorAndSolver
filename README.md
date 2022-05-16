## Puzzle Form
Puzzle form is form of all boxes inside the puzzle. **The first box index is 0**.  

For example, the classic form of 9x9 sudoku is

``` json 
[
    [0, 0, 0, 1, 1, 1, 2, 2, 2],
    [0, 0, 0, 1, 1, 1, 2, 2, 2],
    [0, 0, 0, 1, 1, 1, 2, 2, 2],
    [3, 3, 3, 4, 4, 4, 5, 5, 5],
    [3, 3, 3, 4, 4, 4, 5, 5, 5],
    [3, 3, 3, 4, 4, 4, 5, 5, 5],
    [6, 6, 6, 7, 7, 7, 8, 8, 8],
    [6, 6, 6, 7, 7, 7, 8, 8, 8],
    [6, 6, 6, 7, 7, 7, 8, 8, 8]
]
```

| `0` | `0` | `0` | 1   | 1   | 1   | `2`  | `2` | `2 ` |
|-----|-----|-----|-----|-----|-----|------|-----|------|
| `0` | `0` | `0` | 1   | 1   | 1   | `2`  | `2` | `2 ` |
| `0` | `0` | `0` | 1   | 1   | 1   | `2`  | `2` | `2 ` |
| 3   | 3   | 3   | `4` | `4` | `4` | 5    | 5   | 5    |
| 3   | 3   | 3   | `4` | `4` | `4` | 5    | 5   | 5    |
| 3   | 3   | 3   | `4` | `4` | `4` | 5    | 5   | 5    |
| `6` | `6` | `6` | 7   | 7   | 7   | `8 ` | `8` | `8`  |
| `6` | `6` | `6` | 7   | 7   | 7   | `8 ` | `8` | `8`  |
| `6` | `6` | `6` | 7   | 7   | 7   | `8 ` | `8` | `8`  |

And one custom form is

``` json 
[
    [0, 0, 0, 0, 1, 1, 1, 2, 2],
    [0, 0, 0, 1, 1, 2, 2, 2, 2],
    [3, 0, 3, 1, 1, 1, 1, 2, 2],
    [3, 0, 3, 4, 4, 4, 5, 5, 2],
    [3, 3, 3, 4, 4, 4, 5, 5, 5],
    [6, 3, 3, 4, 4, 4, 5, 8, 5],
    [6, 6, 7, 7, 7, 7, 5, 8, 5],
    [6, 6, 6, 6, 7, 7, 8, 8, 8],
    [6, 6, 7, 7, 7, 8, 8, 8, 8]
]
```

| `0` | `0` | `0` | `0` | _1_ | _1_ | _1_  | `2` | `2 ` |
|-----|-----|-----|-----|-----|-----|------|-----|------|
| `0` | `0` | `0` | _1_ | _1_ | `2` | `2`  | `2` | `2 ` |
| 3   | `0` | 3   | _1_ | _1_ | _1_ | _1_  | `2` | `2 ` |
| 3   | `0` | 3   | `4` | `4` | `4` | 5    | 5   | `2`  |
| 3   | 3   | 3   | `4` | `4` | `4` | 5    | 5   | 5    |
| `6` | 3   | 3   | `4` | `4` | `4` | 5    | `8` | 5    |
| `6` | `6` | _7_ | _7_ | _7_ | _7_ | 5    | `8` | 5    |
| `6` | `6` | `6` | `6` | _7_ | _7_ | `8 ` | `8` | `8`  |
| `6` | `6` | _7_ | _7_ | _7_ | `8` | `8 ` | `8` | `8`  |

### Supported Form

#### 4x4
Support Form Register
- Classic

| 0   | 0   | 1   | 1   |
|-----|-----|-----|-----|
| 0   | 0   | 1   | 1   |
| 2   | 2   | 3   | 3   |
| 2   | 2   | 3   | 3   |

- Form 1

| 0   | 0   | 0   | 1   |
|-----|-----|-----|-----|
| 0   | 2   | 1   | 1   |
| 2   | 2   | 1   | 3   |
| 2   | 3   | 3   | 3   |

- Form 2

| 0   | 0   | 0   | 1   |
|-----|-----|-----|-----|
| 0   | 1   | 1   | 1   |
| 2   | 2   | 2   | 3   |
| 2   | 3   | 3   | 3   |

#### 5x5
Support Form Register
- Form 1 

| 0   | 0   | 0   | 1   | 1   |
|-----|-----|-----|-----|-----|
| 0   | 0   | 2   | 1   | 1   |
| 3   | 2   | 2   | 2   | 1   |
| 3   | 3   | 2   | 4   | 4   |
| 3   | 3   | 4   | 4   | 4   |

- Form 2

| 0   | 0   | 0   | 0   | 1   |
|-----|-----|-----|-----|-----|
| 3   | 0   | 2   | 1   | 1   |
| 3   | 2   | 2   | 2   | 1   |
| 3   | 3   | 2   | 4   | 1   |
| 3   | 4   | 4   | 4   | 4   |

- Form 3

| 0   | 0   | 0   | 0   | 1   |
|-----|-----|-----|-----|-----|
| 3   | 2   | 2   | 0   | 1   |
| 3   | 3   | 2   | 1   | 1   |
| 3   | 4   | 2   | 2   | 1   |
| 3   | 4   | 4   | 4   | 4   |


#### 6x6
Support Form Register

- Classic

| 0   | 0   | 0   | 1   | 1   | 1   |
|-----|-----|-----|-----|-----|-----|
| 0   | 0   | 0   | 1   | 1   | 1   |
| 2   | 2   | 2   | 3   | 3   | 3   |
| 2   | 2   | 2   | 3   | 3   | 3   |
| 4   | 4   | 4   | 5   | 5   | 5   |
| 4   | 4   | 4   | 5   | 5   | 5   |

- Form 1

| 0   | 0   | 0   | 0   | 1   | 1   |
|-----|-----|-----|-----|-----|-----|
| 0   | 0   | 2   | 1   | 1   | 1   |
| 2   | 2   | 2   | 1   | 3   | 3   |
| 2   | 2   | 4   | 3   | 3   | 3   |
| 4   | 4   | 4   | 3   | 5   | 5   |
| 4   | 4   | 5   | 5   | 5   | 5   |

- Form 2

| 0   | 0   | 0   | 0   | 0   | 1   |
|-----|-----|-----|-----|-----|-----|
| 2   | 0   | 2   | 1   | 1   | 1   |
| 2   | 2   | 2   | 1   | 1   | 3   |
| 2   | 4   | 4   | 3   | 3   | 3   |
| 4   | 4   | 4   | 3   | 5   | 3   |
| 4   | 5   | 5   | 5   | 5   | 5   |

#### 7x7
Support Form Register

- Form 1

| 0   | 0   | 0   | 1   | 1   | 1   | 1   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | 0   | 1   | 1   | 1   | 4   |
| 2   | 2   | 0   | 3   | 3   | 4   | 4   |
| 2   | 2   | 3   | 3   | 3   | 4   | 4   |
| 2   | 2   | 3   | 3   | 6   | 4   | 4   |
| 2   | 5   | 5   | 5   | 6   | 6   | 6   |
| 5   | 5   | 5   | 5   | 6   | 6   | 6   |

- Form 2

| 0   | 0   | 1   | 1   | 1   | 1   | 2   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | 0   | 1   | 1   | 1   | 2   |
| 3   | 0   | 0   | 4   | 4   | 2   | 2   |
| 3   | 3   | 4   | 4   | 4   | 2   | 2   |
| 3   | 3   | 4   | 4   | 6   | 6   | 2   |
| 3   | 5   | 5   | 5   | 6   | 6   | 6   |
| 3   | 5   | 5   | 5   | 5   | 6   | 6   |

- Form 3

| 0   | 0   | 0   | 1   | 2   | 2   | 2   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | 1   | 1   | 1   | 1   | 2   |
| 0   | 0   | 4   | 1   | 4   | 1   | 2   |
| 3   | 3   | 4   | 4   | 4   | 2   | 2   |
| 3   | 5   | 4   | 5   | 4   | 6   | 6   |
| 3   | 5   | 5   | 5   | 5   | 6   | 6   |
| 3   | 3   | 3   | 5   | 6   | 6   | 6   |

- Form 4

| 0   | 0   | 0   | 1   | 1   | 2   | 2   |
|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | 1   | 1   | 1   | 2   | 2   |
| 0   | 0   | 3   | 1   | 1   | 2   | 2   |
| 4   | 4   | 3   | 3   | 3   | 2   | 5   |
| 4   | 4   | 3   | 3   | 3   | 5   | 5   |
| 4   | 4   | 6   | 6   | 6   | 5   | 5   |
| 4   | 6   | 6   | 6   | 6   | 5   | 5   |

- Form 5
                  
| 0   | 0   | 0   | 0   | 0   | 0   | 1   |
|-----|-----|-----|-----|-----|-----|-----|
| 2   | 2   | 3   | 3   | 0   | 1   | 1   |
| 2   | 2   | 2   | 3   | 1   | 1   | 4   |
| 2   | 5   | 5   | 3   | 1   | 1   | 4   |
| 2   | 5   | 5   | 3   | 4   | 4   | 4   |
| 5   | 5   | 6   | 3   | 3   | 4   | 4   |
| 5   | 6   | 6   | 6   | 6   | 6   | 6   |

#### 8x8
Support Form Register 
- Classic 
    
| 0   | 0   | 0   | 0   | 1   | 1   | 1   | 1   |
|-----|-----|-----|-----|-----|-----|-----|-----|
| 0   | 0   | 0   | 0   | 1   | 1   | 1   | 1   |
| 2   | 2   | 2   | 2   | 3   | 3   | 3   | 3   |
| 2   | 2   | 2   | 2   | 3   | 3   | 3   | 3   |
| 4   | 4   | 4   | 4   | 5   | 5   | 5   | 5   |
| 4   | 4   | 4   | 4   | 5   | 5   | 5   | 5   |
| 6   | 6   | 6   | 6   | 7   | 7   | 7   | 7   |
| 6   | 6   | 6   | 6   | 7   | 7   | 7   | 7   |

#### 9x9
Support Form Register
- Classic 

| `0` | `0` | `0` | 1   | 1   | 1   | `2`  | `2` | `2 ` |
|-----|-----|-----|-----|-----|-----|------|-----|------|
| `0` | `0` | `0` | 1   | 1   | 1   | `2`  | `2` | `2 ` |
| `0` | `0` | `0` | 1   | 1   | 1   | `2`  | `2` | `2 ` |
| 3   | 3   | 3   | `4` | `4` | `4` | 5    | 5   | 5    |
| 3   | 3   | 3   | `4` | `4` | `4` | 5    | 5   | 5    |
| 3   | 3   | 3   | `4` | `4` | `4` | 5    | 5   | 5    |
| `6` | `6` | `6` | 7   | 7   | 7   | `8 ` | `8` | `8`  |
| `6` | `6` | `6` | 7   | 7   | 7   | `8 ` | `8` | `8`  |
| `6` | `6` | `6` | 7   | 7   | 7   | `8 ` | `8` | `8`  |

#### 12x12
Support Classic Form Only 

- Classic 

#### 16x16
Support Classic Form Only

#### 25x25
Support Classic Form Only

## Generator

To generate new puzzle use `Generator.generate(size of puzzle, maximum time, minimum score, maximum score)`
In which `size of puzzle` is length of puzzle edge. Example 9x9 is 9, 25x25 is 25, ..etc.

Example generate 8x8 puzzle within 5 seconds with minimum score of 10 and maximum score of 1.000.000:
```java
Game game = Generator.generate(8,  5 * 1000, 10, 1000000);
// score of generated puzzle
System.out.println("Score: " + game.getScore());
// puzzle
System.out.println("Puzzle");
System.out.println(game.getQuestion());
// solved puzzle
System.out.println("Solved");
System.out.println(game.getAnswer());
// Form of puzzle (return null if classic form)
// use game.getQuestion().isClassicForm() to check classic form.
game.getQuestion().getForm();
```
    
###### Score of sudoku puzzle is defined:
+ `<` **100** : know the rule
+ `>` **100** and `<` **1000** : know some techniques
+ `>` **1000** : requires flat-out trial (_`score / 1000`_ is the number of tries)

The Puzzle with higher score is harder to solve.

To show the puzzle as string, use `puzzle.toString()`

To get score of a puzzle, use `puzzle.difficultyScore(answer)` where **_answer_** is the solved puzzle.

To solve a puzzle (example _6x6_)
- create new empty puzzle by `Puzzle6 puzzle = new Puzzle6()` with classic form or `Puzzle6 puzzle = new Puzzle6(Puzzle6.FORM1)`
if use custom form 1 of Puzzle 6. And 
- set board for puzzle by `puzzle.setBoard`, if square is empty, set it 0. 
- use `PuzzleSolver.solve(puzzle, time)` to solve puzzle and return the solved puzzle.

Example
```java
Puzzle7 puzzle = new Puzzle7(Puzzle7.FORM2);
puzzle.setBoard(new int[][]{
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 5},
        {0, 0, 0, 0, 0, 7, 0},
        {0, 2, 7, 0, 0, 4, 0},
        {6, 0, 0, 5, 0, 0, 0},
        {0, 0, 2, 3, 0, 0, 0},
});

Puzzle solved = PuzzleSolver.solve(puzzle, 5 * 1000);
System.out.println(solved);
```

    

Supported:
- 4x4
- 5x5
- 6x6
- 7x7
- 8x8
- 9x9
- 12x12
- 16x16
- 25x25
