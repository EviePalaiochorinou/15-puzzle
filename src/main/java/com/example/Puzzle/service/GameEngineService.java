package com.example.Puzzle.service;

import com.example.Puzzle.dao.GameEngineStore;
import com.example.Puzzle.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GameEngineService {

    private final GameEngineStore gameEngineStore;

    public GameEngineService(@Autowired GameEngineStore gameEngineStore) {
        this.gameEngineStore = gameEngineStore;
    }

    public Game startNewGame(String playerId) {
        Game game = new Game();
        gameEngineStore.add(playerId, game);
        return game;
    }

    public Game playNextMove(String playerId, String move) throws Exception {
        Game game;
        try {
            game = gameEngineStore.getGame(playerId);
        }
        catch (RuntimeException e) {
            throw new GameNotFoundException("Game not found");
        }
        if (game.playerHasPreviouslyWon()) {
            throw new Exception("Player has already won. Start a new game");
        }
        game.applyMove(move);
        gameEngineStore.add(playerId, game);
        return game;
    }

    public Game getPlayerGame(String playerId) throws GameNotFoundException {
        try {
            return gameEngineStore.getGame(playerId);
        }
        catch (RuntimeException e) {
            throw new GameNotFoundException("Game not found");
        }
    }
}
