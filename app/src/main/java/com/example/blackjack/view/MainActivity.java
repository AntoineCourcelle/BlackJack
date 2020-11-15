package com.example.blackjack.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blackjack.R;
import com.example.blackjack.controller.BlackJack;
import com.example.blackjack.controller.EmptyDeckException;
import com.example.blackjack.model.Card;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------
    //déclaration des variables
    AlertDialog.Builder diag;
    AlertDialog.Builder exceptionDiag;
    RadioGroup rg;
    RadioButton green;
    RadioButton black;
    EditText decks;
    EditText playerNameEdit;
    EditText poolSet;
    AlertDialog alertDialog;
    AlertDialog exceptionAlertDialog;
    LayoutInflater inflater;
    View dialogView;
    LinearLayout playerLayout;
    LinearLayout bankLayout;
    LinearLayout mainLayout;
    int state;
    int bet = 0;
    int pool = 0;
    boolean languageState;
    List<Card> playerCardList;
    List<Card> bankCardList;
    BlackJack blackJack;
    TextView playerScore;
    TextView bankScore;
    TextView playerNameLabel;
    TextView betLabel;
    TextView currency;
    SeekBar betSeekBar;
    Button reset;
    Button anotherCard;
    Button noMoreCard;
    Button betButton;
    ImageButton languageButton;
    String playerName;
    boolean isUS = true;
//--------------------------------------------------------------------------------------------------

    //actions effectuées au lancement de l'application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiation d'une BlackJackConsole qui sert à tester le programme
        BlackJackConsole console = new BlackJackConsole();

        //instantiation du jeu BlackJack
        blackJack = new BlackJack();

        //recupération des mains pour la partie lancée
        playerCardList = blackJack.getPlayerCardList();
        bankCardList = blackJack.getBankCardList();

        //------------------------------------------------------------------------------------------
        //liaison des éléments de MainActivity.java et des éléments dans les layouts
        playerScore = findViewById(R.id.playerBestLabel);
        bankScore = findViewById(R.id.bankBestLabel);

        playerLayout = findViewById(R.id.playerHandLayout);
        bankLayout = findViewById(R.id.bankHandLayout);

        playerNameLabel = findViewById(R.id.playerTextView);
        playerName = getString(R.string.player_labelUS);
        betLabel = findViewById(R.id.betLabel);

        betSeekBar = findViewById(R.id.betSeekBar);
        betButton = findViewById(R.id.betButton);

        mainLayout = findViewById(R.id.mainLayout);
        //------------------------------------------------------------------------------------------

        //mise initiale
        betLabel.setText(getString(R.string.yourBetUS) + "0" + getString(R.string.dollars));

        //initialisation le l'affichage
        updateBankPanel();
        updatePlayerPanel();
        updateScores();
        languageState = isUS;

        //constructeur d'un AlertDialog s'affichant quand le deck est vide
        exceptionDiag = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Error")
                .setMessage("The deck is empty")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"Ok", Toast.LENGTH_SHORT).show();
                    }
                });

        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.diag_config, null);

        //constructeur d'un AlertDialog accueillant les paramètres de configuration du jeu
        diag = new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.configTitleUS))
                .setIcon(R.drawable.settings)
                .setPositiveButton(getString(R.string.acceptUS), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this,"Valider", Toast.LENGTH_SHORT).show();
                        if(rg.getCheckedRadioButtonId() == R.id.greenRadioButton){
                            mainLayout.setBackgroundColor(getResources().getColor(R.color.colorBody));
                        }
                        if(rg.getCheckedRadioButtonId() == R.id.blackRadioButton){
                            mainLayout.setBackgroundColor(getResources().getColor(R.color.colorBody2));
                        }
                        if(decks.getText().toString().length() > 0){
                            playerLayout.removeAllViewsInLayout();
                            bankLayout.removeAllViewsInLayout();
                            blackJack = new BlackJack(Integer.parseInt(decks.getText().toString()),0);
                            updateHands();
                            updateBankPanel();
                            updatePlayerPanel();
                            updateScores();
                            checkGameState();
                        }
                        if(playerNameEdit.getText().length() > 0){
                            playerName = playerNameEdit.getText().toString();
                            playerNameLabel.setText(playerName);
                            updateScores();
                        }
                        pool = Integer.parseInt(poolSet.getText().toString());
                        decks.setText("");
                        updateLanguage();
                    }
                })
                .setNegativeButton(getString(R.string.cancelUS), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(state != rg.getCheckedRadioButtonId() && rg.getCheckedRadioButtonId() == R.id.greenRadioButton){
                            rg.check(R.id.blackRadioButton);
                        }
                        if(state != rg.getCheckedRadioButtonId() && rg.getCheckedRadioButtonId() == R.id.blackRadioButton){
                            rg.check(R.id.greenRadioButton);
                        }
                        if(languageState != isUS){
                            if(!isUS) {
                                languageButton.setImageDrawable(getDrawable(R.drawable.us));
                            }
                            if(isUS) {
                                languageButton.setImageDrawable(getDrawable(R.drawable.fr));
                            }
                            isUS = (!isUS);
                        }
                        Toast.makeText(MainActivity.this,"Annuler", Toast.LENGTH_SHORT).show();
                        decks.setText("");
                        poolSet.setText(Integer.toString(pool));
                    }
                })
                .setView(dialogView);

        //liaison des éléments de MainActivity.java et des éléments du layout diag_config.xml
        rg = dialogView.findViewById(R.id.themeRadioGroup);
        green = dialogView.findViewById(R.id.greenRadioButton);
        black = dialogView.findViewById(R.id.blackRadioButton);
        decks = dialogView.findViewById(R.id.deckNumberEdit);
        playerNameEdit = dialogView.findViewById(R.id.playerNameEdit);
        languageButton = dialogView.findViewById(R.id.languageButton);
        poolSet = dialogView.findViewById(R.id.poolEditText);
        currency = dialogView.findViewById(R.id.currencyLabel);

        //le fond de base est vert
        green.setChecked(true);

        //cagnotte initiale
        poolSet.setText(Integer.toString(pool));

        //création de l'AlertDialog a partir de diag
        alertDialog = diag.create();

        //------------------------------------------------------------------------------------------
        //gestion du bouton RESET
        reset = findViewById(R.id.resetButton);
        reset.setEnabled(false);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blackJack.reset();
                Toast.makeText(MainActivity.this, "Reset", Toast.LENGTH_SHORT).show();
                betButton.setEnabled(true);
                reset.setEnabled(false);
                playerLayout.removeAllViewsInLayout();
                bankLayout.removeAllViewsInLayout();
            }
        });

        //------------------------------------------------------------------------------------------
        //gestion du bouton ANOTHER CARD
        anotherCard = findViewById(R.id.anotherCardButton);
        anotherCard.setEnabled(false);

        anotherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    blackJack.playerDrawAnotherCard();
                    playerLayout.removeAllViewsInLayout();
                    bankLayout.removeAllViewsInLayout();
                    updateHands();
                    updateScores();
                    updatePlayerPanel();
                    updateBankPanel();
                    checkGameState();
                    if(blackJack.isGameFinished()){
                        anotherCard.setEnabled(false);
                        noMoreCard.setEnabled(false);
                    }
                    Toast.makeText(MainActivity.this,"Another Card", Toast.LENGTH_SHORT).show();
                }catch (EmptyDeckException ex){
                    System.err.println(ex.getMessage());
                    exceptionAlertDialog.show();
                }
            }
        });

        //------------------------------------------------------------------------------------------
        //gestion du bouton NO MORE CARD
        noMoreCard = findViewById(R.id.noMoreCardButton);
        noMoreCard.setEnabled(false);

        noMoreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    blackJack.bankLastTurn();
                    playerLayout.removeAllViewsInLayout();
                    bankLayout.removeAllViewsInLayout();
                    updateHands();
                    updateScores();
                    updatePlayerPanel();
                    updateBankPanel();
                    checkGameState();
                    if(blackJack.isGameFinished()){
                        anotherCard.setEnabled(false);
                        noMoreCard.setEnabled(false);
                    }
                    Toast.makeText(MainActivity.this,"No More Card", Toast.LENGTH_SHORT).show();
                }catch (EmptyDeckException ex){
                    System.err.println(ex.getMessage());
                    exceptionAlertDialog.show();
                }
            }
        });

        //------------------------------------------------------------------------------------------
        //gestion du bouton de séléction de la langue
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isUS) {
                    isUS = true;
                    languageButton.setImageDrawable(getDrawable(R.drawable.us));
                }
                else if(isUS) {
                    isUS = false;
                    languageButton.setImageDrawable(getDrawable(R.drawable.fr));
                }
            }
        });

        //------------------------------------------------------------------------------------------
        //gestion du curseur indiquant la mise
        betSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bet = i;
                if(isUS){
                    betLabel.setText(getString(R.string.yourBetUS) + Integer.toString(bet) + getString(R.string.dollars));
                }
                if(!isUS){
                    betLabel.setText(getString(R.string.yourBetFR) + Integer.toString(bet) + getString(R.string.euros));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //non géré
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //non géré
            }
        });

        //------------------------------------------------------------------------------------------
        //gestion du bouton BET
        betButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((pool-bet) >= 0) {
                    pool -= bet;
                    poolSet.setText(Integer.toString(pool));
                    try {
                        blackJack.play();
                        updateHands();
                        updateScores();
                        updatePlayerPanel();
                        updateBankPanel();
                        checkGameState();
                        anotherCard.setEnabled(true);
                        noMoreCard.setEnabled(true);
                        reset.setEnabled(false);
                        betButton.setEnabled(false);
                    } catch (EmptyDeckException ex) {
                        System.err.println(ex.getMessage());
                        exceptionDiag.show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "No money left", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //changement de la langue (manuel)
    private  void updateLanguage(){
        languageState = isUS;
        if(isUS){
            reset.setText(getString(R.string.resetUS));
            anotherCard.setText(getString(R.string.another_cardUS));
            noMoreCard.setText(getString(R.string.no_more_cardUS));
            green.setText(getString(R.string.greenBgUS));
            black.setText(getString(R.string.blackBgUS));
            alertDialog.setTitle(getString(R.string.configTitleUS));
            playerNameEdit.setHint(getString(R.string.nameHintUS));
            decks.setHint(getString(R.string.configHintUS));
            alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setText(getString(R.string.cancelUS));
            alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setText(getString(R.string.acceptUS));
            betLabel.setText(getString(R.string.yourBetUS) + Integer.toString(bet) + getString(R.string.dollars));
        }
        if(!isUS){
            reset.setText(getString(R.string.resetFR));
            anotherCard.setText(getString(R.string.another_cardFR));
            noMoreCard.setText(getString(R.string.no_more_cardFR));
            green.setText(getString(R.string.greenBgFR));
            black.setText(getString(R.string.blackBgFR));
            alertDialog.setTitle(getString(R.string.configTitleFR));
            playerNameEdit.setHint(getString(R.string.nameHintFR));
            decks.setHint(getString(R.string.configHintFR));
            alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setText(getString(R.string.cancelFR));
            alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setText(getString(R.string.acceptFR));
            betLabel.setText(getString(R.string.yourBetFR) + Integer.toString(bet) + getString(R.string.euros));
        }
    }

    //vérification de l'état du jeu (terminé/ joueur vainqueur / banque vainqueur)
    //pour afficher les cartes correspondantes à l'écran
    private void checkGameState() {
        if(blackJack.getPlayerBest() == 21){
            addToPanel(playerLayout, "blackjack");
        }
        if(blackJack.getBankBest() == 21){
            addToPanel(bankLayout, "blackjack");
        }
        if(blackJack.isPlayerWinner() && !blackJack.isBankWinner()){
            addToPanel(playerLayout, "winner");
            addToPanel(bankLayout, "looser");
        }
        if(blackJack.isBankWinner() && !blackJack.isPlayerWinner()){
            addToPanel(bankLayout, "winner");
            addToPanel(playerLayout, "looser");
        }
        if(blackJack.isGameFinished() && !blackJack.isBankWinner() && !blackJack.isPlayerWinner()){
            addToPanel(playerLayout, "draw");
            addToPanel(bankLayout, "draw");
        }
        if(blackJack.isGameFinished()){
            reset.setEnabled(true);
        }
    }

    //mise a jour des mains
    private void updateHands(){
        playerCardList = blackJack.getPlayerCardList();
        bankCardList = blackJack.getBankCardList();
    }

    //mise à jour des scores affichés
    private void updateScores(){
        if(isUS) {
            playerScore.setText(playerName + " " + getString(R.string.bestUS) + Integer.toString(blackJack.getPlayerBest()));
            bankScore.setText(getString(R.string.bankBestUS) + " " + Integer.toString(blackJack.getBankBest()));
        }
        if(!isUS){
            playerScore.setText(playerName + " " + getString(R.string.bestFR) + Integer.toString(blackJack.getPlayerBest()));
            bankScore.setText(getString(R.string.bankBestFR) + " " + Integer.toString(blackJack.getBankBest()));
        }
    }

    //----------------------------------------------------------------------------------------------
    //mise à jour des mains affichés

    //joueur
    private void updatePlayerPanel() {
        for (int i = 0; i < playerCardList.size(); i++) {
            displayPlayerCard(playerCardList.get(i));
        }
    }

    //banque
    private void updateBankPanel(){
        for(int i=0; i < bankCardList.size(); i++){
            displayBankCard(bankCardList.get(i));
        }
    }

    //----------------------------------------------------------------------------------------------
    //affichage d'une carte dans un panel donné

    //joueur
    private void displayPlayerCard(Card card){
        String token = card.getColorName() + "_" + card.getValueSymbol();
        addToPanel(playerLayout, token);
    }

    //banque
    private void displayBankCard(Card card){
        String token = card.getColorName() + "_" + card.getValueSymbol();
        addToPanel(bankLayout, token);
    }
    //----------------------------------------------------------------------------------------------

    //affichage d'une image donnée dans un panel donné
    private void addToPanel(LinearLayout lay, String token){
        ImageView imv = new ImageView(this);
        int resID = getResources().getIdentifier("card_" + token.toLowerCase(), "drawable", getPackageName());
        Bitmap tempBMP = BitmapFactory.decodeResource(getResources(), resID);
        imv.setPadding(10,10,10,10);
        imv.setImageBitmap(tempBMP);
        lay.addView(imv);
    }

    //----------------------------------------------------------------------------------------------
    //gestion du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.configButton:
                state = rg.getCheckedRadioButtonId();
                alertDialog.show();
                return true;
        }
        return true;
    }
}