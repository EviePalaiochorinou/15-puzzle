package com.example.Puzzle.service;
import com.example.Puzzle.controller.model.GameResponse;
import com.example.Puzzle.dao.GameEngineStore;
import com.example.Puzzle.dao.GameEngineStoreImpl;
import com.example.Puzzle.model.Game;
import com.example.Puzzle.service.GameEngineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameEngineServiceTest {

    private GameEngineService gameEngineService;
    private GameEngineStore gameEngineStore;

    @BeforeEach
    void setUp() {
        gameEngineStore = new GameEngineStoreImpl();
        gameEngineService = new GameEngineService(gameEngineStore);
    }

    @Test
    void startNewGameCreatesNewGame() {
        String playerId = "player1";
        Game game = gameEngineService.startNewGame(playerId);

        assertNotNull(game);
        assertTrue(game.equals(gameEngineStore.getGame(playerId)));
        assertFalse(game.playerHasPreviouslyWon());
    }

    @Test
    void playNextMoveUpdatesGame() throws Exception {
        String playerId = "player1";
        Game initialGame = gameEngineService.startNewGame(playerId);
        Game updatedGame = gameEngineService.playNextMove(playerId, "up");

        assertNotEquals(initialGame, updatedGame);
    }

    @Test
    void playNextMoveThrowsExceptionIfPlayerHasWon() throws Exception {
        String playerId = "player1";
        Game game = gameEngineService.startNewGame(playerId);
        game.setPlayerWon(true);
        gameEngineStore.add(playerId, game);

        assertThrows(Exception.class, () -> gameEngineService.playNextMove(playerId, "up"));
    }

    @Test
    void getPlayerGameReturnsCorrectGame() throws GameNotFoundException {
        String playerId = "player1";
        Game game = gameEngineService.startNewGame(playerId);
        Game retrievedGame = gameEngineService.getPlayerGame(playerId);

        assertTrue(retrievedGame.equals(game));
    }

    @Test
    void getPlayerGameThrowsExceptionIfGameNotFound() {
        String playerId = "player1";
        assertThrows(GameNotFoundException.class, () -> gameEngineService.getPlayerGame(playerId));
    }

    @Test
    void playNextMoveThrowsExceptionIfGameNotFound() {
        String playerId = "player1";
        assertThrows(GameNotFoundException.class, () -> gameEngineService.playNextMove(playerId, "up"));
    }
}