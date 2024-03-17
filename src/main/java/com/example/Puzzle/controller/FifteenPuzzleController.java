package com.example.Puzzle.controller;

import com.example.Puzzle.controller.model.GameResponse;
import com.example.Puzzle.controller.model.PlayerMoveRequest;
import com.example.Puzzle.service.GameEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/player")
public class FifteenPuzzleController {

    private static GameEngineService gameEngineService;

    public FifteenPuzzleController(@Autowired GameEngineService gameEngineService) {
        this.gameEngineService = gameEngineService;
    }

    @PutMapping("/{playerId}/play")
    public GameResponse playNextMove(@PathVariable String playerId, @RequestBody PlayerMoveRequest playerMoveRequest) throws Exception {
        return new GameResponse(gameEngineService.playNextMove(playerId, playerMoveRequest.move));
    }

    @PostMapping("/{playerId}/game")
    public ResponseEntity<GameResponse> startNewGame(@PathVariable String playerId) {
        return new ResponseEntity(new GameResponse(gameEngineService.startNewGame(playerId)), HttpStatus.CREATED);
    }

    @GetMapping("/{playerId}/game")
    public GameResponse refreshPage(@PathVariable String playerId) {
        return new GameResponse(gameEngineService.getPlayerGame(playerId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseStatusException handleException(Exception e) {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
}
