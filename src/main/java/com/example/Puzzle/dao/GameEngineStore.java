package com.example.Puzzle.dao;

import com.example.Puzzle.model.Game;

public interface GameEngineStore {

    Game getGame(String playerId);

    void add(String playerId, Game game);
}
