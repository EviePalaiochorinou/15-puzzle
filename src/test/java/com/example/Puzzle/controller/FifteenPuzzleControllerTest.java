package com.example.Puzzle.controller;

import com.example.Puzzle.controller.model.GameResponse;
import com.example.Puzzle.controller.model.PlayerMoveRequest;
import com.example.Puzzle.dao.GameEngineStore;
import com.example.Puzzle.dao.GameEngineStoreImpl;
import com.example.Puzzle.model.Game;
import com.example.Puzzle.service.GameEngineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class FifteenPuzzleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private GameEngineService gameEngineService;
    private FifteenPuzzleController fifteenPuzzleController;
    private GameEngineStore gameEngineStore;

    @BeforeEach
    void setUp() {
        gameEngineStore = new GameEngineStoreImpl();
        gameEngineService = new GameEngineService(gameEngineStore); // Initialize a Game for the player
        fifteenPuzzleController = new FifteenPuzzleController(gameEngineService);
    }

    @Test
    void playNextMoveReturnsGameResponse() throws Exception {
        String playerId = "1";
        PlayerMoveRequest playerMoveRequest = new PlayerMoveRequest("up");
        gameEngineService.startNewGame(playerId);

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.put("/player/" + playerId + "/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(playerMoveRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        assertNotNull(responseBody);
    }

    @Test
    void playNextMoveThrowsException() throws Exception {
        String playerId = "1";
        PlayerMoveRequest playerMoveRequest = new PlayerMoveRequest("up");
//      Necessary hacky steps to cause the exception
        Game game = new Game();
        game.setPlayerWon(true);
        gameEngineStore.add(playerId, game);

        mockMvc.perform(MockMvcRequestBuilders.put("/player/" + playerId + "/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(playerMoveRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void startNewGameReturnsGameResponse() throws Exception {
        String playerId = "1";

        mockMvc.perform(MockMvcRequestBuilders.post("/player/" + playerId + "/game"))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    void refreshPageReturnsSameBoardForPlayer() throws Exception {
        String playerId = "player1";

        String responseStringInitial = mockMvc.perform(MockMvcRequestBuilders.post("/player/" + playerId + "/game"))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn().getResponse().getContentAsString();
        String responseStringRefresh = mockMvc.perform(MockMvcRequestBuilders.get("/player/" + playerId + "/game"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

        responseStringInitial.equals(responseStringRefresh);
    }

    @Test
    void refreshPageThrowsExceptionIfPlayerDoesNotExist() throws Exception {
        String playerId = "player1";
        mockMvc.perform(MockMvcRequestBuilders.get("/player/" + playerId + "/game"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void playNextMoveThrowsExceptionIfInvalidMove() throws Exception {
        String playerId = "1";
        PlayerMoveRequest playerMoveRequest = new PlayerMoveRequest("invalid");
        gameEngineService.startNewGame(playerId);

        mockMvc.perform(MockMvcRequestBuilders.put("/player/" + playerId + "/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(playerMoveRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}