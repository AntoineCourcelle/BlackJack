package com.example.blackjack.model;

//la classe Color sybolise la couleur de la carte (et son symbole associé)
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
