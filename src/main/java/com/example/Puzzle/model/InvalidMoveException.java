package com.example.Puzzle.model;

public class InvalidMoveException extends RuntimeException{

    public InvalidMoveException(String message) {
        super(message);
    }
}
