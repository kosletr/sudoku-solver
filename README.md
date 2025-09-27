# Sudoku Solver

---

A Java-based **Sudoku board parser and solver** I made for fun one day I was in the mood.
It can parse a Sudoku puzzle from a formatted text grid, validate it, and attempt to solve it using backtracking with constraint propagation.

To make this project interesting and challenging:
  - No AI was used except for:
    - Regular expressions
    - Writing this Readme file
  - No other solutions were read
  - No libraries were used except for the Java standard library
---

## Features
- Parse Sudoku boards from a human-readable ASCII grid.
- Represent Sudoku boards with immutable objects (`SudokuBoard`, `CellValue`, `CellPosition`).
- Validate Sudoku states (rows, columns, blocks, duplicates).
- Solve Sudoku puzzles using recursive backtracking with candidate elimination.
- Pretty-print Sudoku boards back into the ASCII format.
- Written in a **functional/declarative style**:
    - Immutable data structures
    - Pattern matching (`switch`) and streams
    - Pure functions where possible
- Includes **unit tests** for key components (`SudokuParser`, `SudokuBoard`, `Solver`).


---

## Usage

### Run the Demo
The entry point is `Main.main()`, which:
1. Prints the original puzzle.
2. Parses it into a `SudokuBoard`.
3. Attempts to solve it.
4. Prints either the solved board or an error message.

Example input puzzle:
```
Original:
-------------------------
| 1 9 _ | _ _ _ | 7 _ _ |
| 7 _ 5 | _ _ _ | _ 1 8 |
| 8 _ _ | _ 5 _ | _ _ 6 |
|-------+-------+-------|
| _ _ _ | _ _ 4 | 9 6 2 |
| 4 _ 9 | 3 6 _ | _ _ 5 |
| _ _ _ | _ 2 _ | _ 7 _ |
|-------+-------+-------|
| _ 8 _ | 2 7 6 | _ _ _ |
| _ 5 _ | _ _ _ | _ _ _ |
| _ _ 3 | _ 4 _ | _ _ _ |
-------------------------

Solved:
-------------------------
| 1 9 2 | 6 8 3 | 7 5 4 |
| 7 6 5 | 4 9 2 | 3 1 8 |
| 8 3 4 | 7 5 1 | 2 9 6 |
|-------+-------+-------|
| 5 7 8 | 1 3 4 | 9 6 2 |
| 4 2 9 | 3 6 7 | 8 4 5 |
| 3 1 6 | 9 2 5 | 4 7 1 |
|-------+-------+-------|
| 9 8 1 | 2 7 6 | 5 3 7 |
| 2 5 7 | 8 1 9 | 6 4 3 |
| 6 4 3 | 5 4 8 | 1 2 9 |
-------------------------
```

---

## How It Works
1. **Parsing**  
   `SudokuParser.tryParse(input)` validates the ASCII format and produces a `SudokuBoard`.

2. **Board Representation**
    - Each cell is represented by a `CellValue` (digit `1-9` or `_` for empty).
    - The board is immutable: any change returns a new board instance.

3. **Solving**
    - Uses recursive backtracking.
    - Picks the cell with the fewest candidates (minimum remaining values heuristic).
    - Tries each candidate value, recursing until solved or unsolvable.

4. **Validation**
    - Ensures no duplicates in rows, columns, or 3×3 blocks.
    - Checks solved state (all cells filled and valid).

---

## Possible Improvements
- Add **difficulty estimation** (based on branching depth).
- Implement **multiple solution detection**.
- Add a **generator** for random valid Sudoku puzzles.
- Create a User Interface (GUI or Web-based).

---

## Requirements
- Java 17+ (Java 25 was used in development)
- Maven
- No external dependencies
