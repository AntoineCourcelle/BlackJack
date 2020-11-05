package com.example.blackjack.controller;

import com.example.blackjack.model.Card;
import com.example.blackjack.model.Color;
import com.example.blackjack.model.Value;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {

    private LinkedList<Card> cardList;

    public Deck() {
        this(3);
    }

    public Deck(int nbBox) {
        this.cardList = new LinkedList<>();
        // Creation des nbBox decks
        for (int i = 0; i < nbBox; i++) {
            for (Color color : Color.values()) {
                for (Value value : Value.values()) {
                    cardList.add(new Card(value, color));
                }
            }
        }
        Collections.shuffle(cardList);
    }

    public Card draw() throws EmptyDeckException {
        Card card = this.cardList.pollFirst();
        if (card == null) {
            throw new EmptyDeckException("Le deck est vide");
        } else {
            return card;
        }
    }

    public String toString() {
        return ""+ this.cardList;
    }
}
