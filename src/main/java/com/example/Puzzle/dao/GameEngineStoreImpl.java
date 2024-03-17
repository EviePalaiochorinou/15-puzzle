package com.example.Puzzle.dao;

import com.example.Puzzle.model.Game;

import java.util.HashMap;

public class GameEngineStoreImpl implements GameEngineStore {

    HashMap<String, Game> store = new HashMap<>();

    @Override
    public Game getGame(String playerId) {
        Game game = store.get(playerId);
        if (game == null) {
            throw new RuntimeException("Game not found");
        }
        return game.copy();
    }

    @Override
    public void add(String playerId, Game game) {
        store.put(playerId, game);
    }
}