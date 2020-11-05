package com.example.blackjack.model;

import com.example.blackjack.model.Color;
import com.example.blackjack.model.Value;

public class Card {

    private Color color;
    private Value value;

    public Card(Value value, Color color){
        this.color = color;
        this.value = value;
    }

    public String toString() {
        return getValueSymbol() + this.color.getSymbol();
    }

    public String getColorSymbol(){
        return this.color.getSymbol();
    }

    public String getColorName(){
        return this.color.name();
    }

    public String getValueSymbol(){
        return this.value.getSymbol();
    }

    public int getPoints(){
        return this.value.getPoints();
    }
}
