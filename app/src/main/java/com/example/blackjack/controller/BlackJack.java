package com.example.blackjack.controller;

import com.example.blackjack.model.Card;

import java.util.LinkedList;
import java.util.List;

public class BlackJack {

    private Deck deck;
    private Hand playerHand;
    private Hand bankHand;

    public boolean gameFinished;

    public BlackJack(){
        gameFinished = false;
        deck = new Deck();
        playerHand = new Hand();
        bankHand = new Hand();

        reset();

    }

    public BlackJack(int nbBox, int somme){
        gameFinished = false;
        deck = new Deck(nbBox);
        playerHand = new Hand();
        bankHand = new Hand();

        reset();

    }

    public void reset(){
        playerHand.clear();
        bankHand.clear();
    }

    public void play() throws EmptyDeckException{
        gameFinished = false;
        for(int i=0; i < 2; i++){
            try {
                playerHand.add(deck.draw());
            }catch(EmptyDeckException ex){
                System.err.println(ex.getMessage());
                throw ex;
            }
        }
        try{
            bankHand.add(deck.draw());
        }catch(EmptyDeckException ex){
            System.err.println(ex.getMessage());
        }
    }

    public String getPlayerHandString(){
        return playerHand.toString();
    }

    public String getBankHandString(){
        return bankHand.toString();
    }

    public int getPlayerBest(){
        return playerHand.best();
    }

    public int getBankBest(){
        return bankHand.best();
    }

    public boolean isPlayerWinner(){
        if(isGameFinished()) {
            if (getPlayerBest() > getBankBest() && getPlayerBest() <= 21)
                return true;
            return getPlayerBest() <= 21 && getBankBest() > 21;
        }
        return false;
    }

    public boolean isBankWinner(){
        if(isGameFinished()) {
            if (getBankBest() > getPlayerBest() && getBankBest() <= 21)
                return true;
            return getBankBest() <= 21 && getPlayerBest() > 21;
        }
        return false;
    }

    public boolean isGameFinished(){
        return gameFinished;
    }

    public void playerDrawAnotherCard() throws EmptyDeckException{
        if(!isGameFinished()){
            playerHand.add(deck.draw());
            if(getPlayerBest() > 21)
                gameFinished = true;
        }
    }

    public void bankLastTurn() throws EmptyDeckException{
        if(!isGameFinished() && bankHand.best() < 21 && playerHand.best() < 21 && playerHand.best() > 17){
            while(getBankBest() < getPlayerBest()){
                bankHand.add(deck.draw());
            }
            gameFinished = true;
        }
        if(!isGameFinished() && bankHand.best() < 21 && playerHand.best() < 21 && playerHand.best() <= 17){
            while(getBankBest() <= 17){
                bankHand.add(deck.draw());
            }
            gameFinished = true;
        }
    }

    public List<Card> getPlayerCardList(){
        LinkedList<Card> originalList = (LinkedList<Card>) playerHand.getCardList();
        LinkedList<Card> copyList = new LinkedList<Card>(originalList);
        return copyList;
    }

    public List<Card> getBankCardList(){
        LinkedList<Card> originalList = (LinkedList<Card>) bankHand.getCardList();
        LinkedList<Card> copyList = new LinkedList<Card>(originalList);
        return copyList;
    }

}
