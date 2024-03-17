package com.example.Puzzle.controller.model;

import com.example.Puzzle.model.Game;

public class GameResponse {
    private int[][] board;
    private boolean playerWon;

    public GameResponse(Game game) {
        this.board = game.getBoard();
        this.playerWon = game.getPlayerWon();
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean getPlayerWon() {
        return playerWon;
    }
}
