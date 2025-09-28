package org.example;

import org.example.board.SudokuBoard;
import org.example.board.Solver;
import org.example.board.SudokuBoardParser;
import org.example.common.Result;

public class Main {
   void main() {
       final String input = """
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
         """;

       System.out.println("Original:");
       System.out.println(input);

       final SudokuBoard original = SudokuBoardParser.tryParse(input).orElseThrow();
       final Result<SudokuBoard> result = new Solver(original).solve();

       System.out.println();
       switch (result) {
           case Result.Success (SudokuBoard solved) -> System.out.println("Solved:\n" + SudokuBoardParser.toString(solved));
           case Result.Error (String message) -> System.out.println(message);
       }
   }
}
