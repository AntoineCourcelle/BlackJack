package com.example.blackjack.controller;

import com.example.blackjack.model.Card;
import com.example.blackjack.model.Value;

import java.util.LinkedList;
import java.util.List;

public class Hand {

    private LinkedList<Card> cardList;

    public Hand(){
        this.cardList = new LinkedList<>();
    }

    public String toString(){
        return  this.cardList.toString() + " : " + this.count().toString();
    }

    public void add(Card card){
        cardList.add(card);
    }

    public void clear(){
        cardList.clear();
    }

    //retourne une liste d'entier contenant les scores possibles en cours du joueur
    public List<Integer> count(){
        List<Integer> list = new LinkedList<>();
        list.add(0);
        boolean flagAs = false;
        for (Card card : cardList) {
            //Le joueur a un As dans la main
            if (flagAs) {
                list.set(1, list.get(1) + card.getPoints());
            }
            //Le joueur vient de piocher un As
            if (!flagAs && card.getValueSymbol().equals("A")) {
                flagAs = true;
                list.add(list.get(0) + 11);
            }
            list.set(0, list.get(0) + card.getPoints());
        }
        return list;
    }

        //retourne le score le plus favorable au joueur
        public int best(){
            List<Integer> list = this.count();
            int choice = list.get(0);
            for(int i=0; i < list.size(); i++){
                if(list.get(i) > choice && list.get(i) <= 21 )
                    choice = list.get(i);
            }
            return choice;
        }

        //retourne la main du joueur sous la forme d'une liste de 'Card'
        public List<Card> getCardList(){
            return cardList;
        }
}
