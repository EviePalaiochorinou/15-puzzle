package com.example.Puzzle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void playerHasPreviouslyWonFalseWhenNoMove() {
        Game game = new Game();
        assertFalse(game.playerHasPreviouslyWon());
    }

    @Test
    void applyMoveThrowsInvalidMoveException() {
        Game game = new Game();
        assertThrows(InvalidMoveException.class, () -> game.applyMove("invalid"));
    }

    @Test
    void applyMoveModifiesBoard() {
        Game game = new Game();
        int[][] initialBoard = game.getBoard();
        game.applyMove("up");
        assertNotEquals(initialBoard, game.getBoard());
    }
}