package com.tictactoe.model;

public class Player {

    private final String symbol;
    private final String name;

    public Player(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
