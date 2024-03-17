package com.example.Puzzle.model;

public class Game {

    GameState gameState;
    Boolean playerWon;

    public Game() {
        gameState = new GameState();
        playerWon = false;
    }

    public int[][] getBoard() {
        return gameState.getBoard();
    }

    public Boolean getPlayerWon() {
        return playerWon;
    }

    public Boolean playerHasPreviouslyWon() {
        return playerWon;
    }

    // This is a fake implementation of the gameHasEnded method that checks if the board is in the winning state,
    // but in our case, the player wins after the first move because we are not implementing the tile logic.
    private Boolean gameHasEnded() {return true;}

    private Boolean moveIsValid(String move) {
        if (move.equals("up") || move.equals("down") || move.equals("left") || move.equals("right")) {
            return true;
        }
        return false;
    }
    public void applyMove(String move) {
        if (!moveIsValid(move)) {
            throw new InvalidMoveException(String.format("Invalid move: %s. You can only go up, down, left or right", move));
        }
        this.gameState.moveTile(move);
        if (gameHasEnded()) {
            playerWon = true;
        }
    }

    public void setPlayerWon(boolean b) {
        playerWon = b;
    }

    public Game copy() {
        Game game = new Game();
        game.gameState = this.gameState;
        game.playerWon = this.playerWon;
        return game;
    }

    public boolean equals(Game game) {
        return this.gameState.equals(game.gameState) && this.playerWon == game.playerWon;
    }
}