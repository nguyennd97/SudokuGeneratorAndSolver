    
    Map map = Generator.generateRandomMap(size, time(ms), minScore, maxScore);
Example: Generate sudoku 9x9 in maximum 5 seconds and get score between 1 to 5000.

    Map map = Generator.generateRandomMap(9, 5000, 1, 5000);
    
###### Score of sudoku puzzle is defined:
+ `<` **100** : know the rule
+ `>` **100** and `<` **1000** : know some techniques
+  `>` **1000** : requires flat-out trial (_`score / 1000`_ is the number of tries)

To show the map as string, use `map.toString()`

To get score of a puzzle, use `map.difficultyScore(answer)` where **_answer_** is the solved puzzle, and **_map_** is the puzzle.

To solve a puzzle use:

    int size = <your type of sudoku puzzle>;
    int[][] puzzle = <your puzzle, minus numbers in all your puzzle's squares 1, for example: empty square 0 becomes -1, 1 becomes 0>;
    // init puzzle
    Map map = MapFactory.get(size);
    map.setMap(puzzle);
    // create a solver
    PseudoMap pseudo = new PseudoMap(map);
    pseudo.solve();
    // print solved puzzle
    System.out.println(pseudo.getResult());
    

Supported:
- ~~3x3~~
- 4x4
- 5x5
- 6x6
- 7x7
- 8x8
- 9x9
- 12x12
- 16x16
- 25x25