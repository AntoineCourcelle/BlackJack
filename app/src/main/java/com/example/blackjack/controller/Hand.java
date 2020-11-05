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

    public List<Integer> count(){
        List<Integer> list = new LinkedList<>();
        list.add(0);
        boolean isAs = false;
        for (Card card : cardList) {
            // Si on a 2 scores on incremente le second
            if (isAs) {
                list.set(1, list.get(1) + card.getPoints());
            }
            // Si on tombe sur un as pour la premiere fois (2 as = 22 donc on prend pas en compte)
            if (!isAs && card.getValueSymbol().equals("A")) {
                isAs = true;
                list.add(list.get(0) + 11);
            }
            list.set(0, list.get(0) + card.getPoints());
        }
        return list;
    }

        public int best(){
            List<Integer> list = this.count();
            int choice = list.get(0);
            for(int i=0; i < list.size(); i++){
                if(list.get(i) > choice && list.get(i) <= 21 )
                    choice = list.get(i);
            }
            return choice;
        }

        public List<Card> getCardList(){
            return cardList;
        }
}
