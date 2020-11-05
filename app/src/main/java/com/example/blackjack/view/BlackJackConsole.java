package com.example.blackjack.view;

import android.util.Log;

import com.example.blackjack.controller.Deck;
import com.example.blackjack.controller.EmptyDeckException;
import com.example.blackjack.controller.Hand;
import com.example.blackjack.model.Card;
import com.example.blackjack.model.Color;
import com.example.blackjack.model.Value;



public class BlackJackConsole {

    private static final String TAG = "MainAct";

    public BlackJackConsole() {
        Card[] tab = {new Card(Value.TWO, Color.HEART), new Card(Value.JACK, Color.SPADE)};
        for (Card c : tab) {
            Log.e(TAG, "This card is a " + c + " worth "+ c.getPoints() + " points.");
            Log.e(TAG, "It's a name "+ c.getColorName());
            switch (c.getColorSymbol()){
                case "\u2665": Log.e(TAG, "symbol : heart ");break;
                case "\u2660": Log.e(TAG, "symbol : spade ");break;
                case "\u2663": Log.e(TAG, "symbol : club ");break;
                case "\u2666": Log.e(TAG, "symbol : diamond ");break;
            }
            if(c.getValueSymbol().matches("[JQK]")){
                Log.e(TAG, "it's a face !");
            }
            else{
                Log.e(TAG, "it's not a face !");
            }
        }
        Deck deck = new Deck(2);
        Log.i(TAG, "Here is the deck "+ deck+"\n");
        for(int i = 0; i<3 ; i++){
            try{
                Card c = deck.draw();
                Log.i(TAG, "This card is a "+ c +" worth "+ c.getPoints()+" points");
            }catch (EmptyDeckException ex){
                System.err.println(ex.getMessage());
                System.exit(-1);
            }
        }

        Hand hand = new Hand();
        Log.i(TAG, "Your hand is currently : " + hand);
        for(int i=0; i < 3; i++){
            try{
                hand.add(deck.draw());
            }catch(EmptyDeckException ex){
                System.err.println(ex.getMessage());
                System.exit(-1);
            }
        }
        Log.i(TAG,"Your hand is currently : "+ hand);
        Log.i(TAG, "The best score is :" + hand.best());
        hand.clear();
        Log.i(TAG,"Your hand is currently : "+ hand);
    }
}
