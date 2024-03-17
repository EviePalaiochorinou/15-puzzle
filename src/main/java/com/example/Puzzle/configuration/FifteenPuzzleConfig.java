package com.example.Puzzle.configuration;

import com.example.Puzzle.dao.GameEngineStore;
import com.example.Puzzle.dao.GameEngineStoreImpl;
import com.example.Puzzle.service.GameEngineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FifteenPuzzleConfig {

    @Bean
    public GameEngineService gameEngine() { return new GameEngineService(gameEngineStore()); }

    @Bean
    public GameEngineStore gameEngineStore() { return new GameEngineStoreImpl(); }
}
