package com.example.blackjack.controller;

public class EmptyDeckException extends Exception{

    //exception lancée lorsque l'on pioche dans un deck vide
    public EmptyDeckException(String message){
        super(message);
    }
}
