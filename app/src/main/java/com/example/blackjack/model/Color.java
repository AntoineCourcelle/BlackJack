package com.example.blackjack.model;

public enum Color {
    HEART("\u2665"),
    SPADE("\u2660"),
    CLUB("\u2663"),
    DIAMOND("\u2666");

    private String symbol;

    private Color(String color){
        this.symbol = color;
    }

    public String getSymbol() {
        return this.symbol;
    }

}
