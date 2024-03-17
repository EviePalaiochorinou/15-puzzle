package com.example.Puzzle.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void playerHasPreviouslyWonFalseAfterNewGame() {
        assertFalse(game.playerHasPreviouslyWon());
    }

    @Test
    void applyMoveThrowsInvalidMoveException() {
        assertThrows(InvalidMoveException.class, () -> game.applyMove("invalid"));
    }

    @Test
    void applyMoveModifiesBoard() {
        int[][] initialBoard = game.getBoard();
        game.applyMove("up");
        assertNotEquals(initialBoard, game.getBoard());
    }
}