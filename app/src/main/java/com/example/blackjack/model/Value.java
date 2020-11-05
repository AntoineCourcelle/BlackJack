package com.example.blackjack.model;

public enum Value {
    AS("A",1),
    TWO("2",2),
    THREE("3",3),
    FOUR("4",4),
    FIVE("5",5),
    SIX("6",6),
    SEVEN("7",7),
    EIGHT("8",8),
    NINE("9",9),
    TEN("10",10),
    JACK("J",10),
    QUEEN("Q",10),
    KING("K",10);

    private String symbol;
    private int points;

    private Value(String symbol,int points){
        this.symbol = symbol;
        this.points = points;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public int getPoints(){
        return this.points;
    }

}
