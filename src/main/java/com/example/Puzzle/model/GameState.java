package com.example.Puzzle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {

    private int[][] board;

    public GameState() {
        this.board = generateBoard();
    }

    private int[][] generateBoard() {
        int[][] newBoard = new int[4][4];
        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            numbers.add(i);
        }
        numbers.add(0); // the last cell is the empty space

        Collections.shuffle(numbers); // shuffle the numbers

        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newBoard[i][j] = numbers.get(count++);
            }
        }

        return newBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    public void moveTile(String move) {
//      We have to fake the moveTile method to have a meaningful test
        board = generateBoard();
    }
}


