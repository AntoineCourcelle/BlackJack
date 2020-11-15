package com.example.blackjack.controller;

import com.example.blackjack.model.Card;

import java.util.LinkedList;
import java.util.List;

public class BlackJack {

    private Deck deck;
    private Hand playerHand;
    private Hand bankHand;

    public boolean gameFinished;

    //constructeur sans paramètre
    public BlackJack(){
        gameFinished = false;
        deck = new Deck();
        playerHand = new Hand();
        bankHand = new Hand();

        reset();

    }

    //constructeur avec le nombre de jeu dans le sabot et la mise en paramètre
    public BlackJack(int nbBox, int somme){
        gameFinished = false;
        deck = new Deck(nbBox);
        playerHand = new Hand();
        bankHand = new Hand();

        reset();

    }

    //nettoyage de la main de la banque et du joueur (n'arrête pas une partie en cours)
    public void reset(){
        playerHand.clear();
        bankHand.clear();
    }

    //lancement d'une partie
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

    //----------------------------------------------------------------------------------------------
    //getters
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
    //----------------------------------------------------------------------------------------------

    //retourne true si le joueur rassemble les conditions pour gagner sinon false
    public boolean isPlayerWinner(){
        if(isGameFinished()) {
            if (getPlayerBest() > getBankBest() && getPlayerBest() <= 21)
                return true;
            return getPlayerBest() <= 21 && getBankBest() > 21;
        }
        return false;
    }

    //retourne true si la banque rassemble les conditions pour gagner sinon false
    public boolean isBankWinner(){
        if(isGameFinished()) {
            if (getBankBest() > getPlayerBest() && getBankBest() <= 21)
                return true;
            return getBankBest() <= 21 && getPlayerBest() > 21;
        }
        return false;
    }

    //retourne true si la partie est terminée sinon false
    public boolean isGameFinished(){
        return gameFinished;
    }

    //le joueur pioche une carte si cela est possible
    public void playerDrawAnotherCard() throws EmptyDeckException{
        if(!isGameFinished()){
            playerHand.add(deck.draw());
            if(getPlayerBest() > 21)
                gameFinished = true;
        }
    }

    //la banque termine la manche en piochant tant que son score ne dépasse pas celui du joueur
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

    //retourne une copie de la main du joueur sous la forme d'une liste de 'Card'
    public List<Card> getPlayerCardList(){
        LinkedList<Card> originalList = (LinkedList<Card>) playerHand.getCardList();
        LinkedList<Card> copyList = new LinkedList<Card>(originalList);
        return copyList;
    }

    //retourne une copie de la main de la banque sous la forme d'une liste de 'Card'
    public List<Card> getBankCardList(){
        LinkedList<Card> originalList = (LinkedList<Card>) bankHand.getCardList();
        LinkedList<Card> copyList = new LinkedList<Card>(originalList);
        return copyList;
    }

}
