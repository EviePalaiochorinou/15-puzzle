package com.example.Puzzle.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerMoveRequest {
    public String move;

    @JsonCreator
    public PlayerMoveRequest(@JsonProperty("move") String move) {
        this.move = move;
    }
}
