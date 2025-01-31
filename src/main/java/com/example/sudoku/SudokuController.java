package com.example.sudoku;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*") // Allows frontend to call backend
public class SudokuController {
    
    @PostMapping("/solve")
    public Map<String, Object> solve(@RequestBody Map<String, int[][]> payload) {
        int[][] board = payload.get("board");
        boolean solved = solveSudoku(board);
        
        Map<String, Object> response = new HashMap<>();
        response.put("solved", solved);
        response.put("board", board);
        return response;
    }

    private static final int SIZE = 9;

    private static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) { 
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        int boxRowStart = row - row % 3, boxColStart = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRowStart + i][boxColStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
