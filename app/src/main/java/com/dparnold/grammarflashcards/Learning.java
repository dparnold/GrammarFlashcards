package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dparnold.grammarflashcards.Helper.TextFormatter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Learning extends AppCompatActivity {
// User set values
    private int cardsToReview = 5;
    private int newCards = 3;
// Other variables
    private int idCurrentCard;
    private int result;
    private String toastFinishText;
    private final long MILLISDAY = 24*60*60*1000;
    //private final long MILLISDAY = 60*1000;
    String packageName;
// Objects
    private AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();
    private Random random = new Random();
    private Timestamp timestamp;
    private Toast toast;
// Buttons
    private Button turnCardButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
// Views
    private TextView title;
    private TextView question;
    private LinearLayout buttonLayout;
    private LinearLayout.LayoutParams buttonParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
// Getting a timestamp for the current session
        timestamp = new Timestamp(System.currentTimeMillis());
// Get views
        title = findViewById(R.id.title);
        question = findViewById(R.id.question);
        buttonLayout = findViewById(R.id.buttonLayout);

// Get database and read flashcards
    // Get name of package
        Intent myIntent = getIntent(); // gets the previously created intent
        packageName = myIntent.getStringExtra("packageName");
        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);

        flashcards = db.flashcardDAO().getCardsToReview(packageName, timestamp.getTime(), cardsToReview);
        toastFinishText = "Great! You have reviewed another "+ Integer.toString(flashcards.size())+" cards.";
        if (flashcards.isEmpty()){
            flashcards = db.flashcardDAO().getNewFlashcardsOfPackage(newCards, packageName);
            toastFinishText = "Great! You have learned "+ Integer.toString(flashcards.size())+" new cards.";
            for(int i=0;i<flashcards.size();i++){
                flashcards.get(i).setLearning(true);
                db.flashcardDAO().updateFlashcard(flashcards.get(i));
            }
            if(flashcards.isEmpty()){
                flashcards=db.flashcardDAO().getSomeCards(cardsToReview,packageName);
                toastFinishText = "Great! You have reviewed another "+ Integer.toString(flashcards.size())+" cards.";
                if(flashcards.isEmpty()){
                    toast =Toast.makeText(getApplicationContext(),
                            "You have ignored all cards.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                    return;
                }
            }
        }
// Pick a random flashcard
        idCurrentCard=random.nextInt(flashcards.size());
        setTextViews(flashcards.get(idCurrentCard));
// Create Buttons
        buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        button1 = new Button(this);
        button2 = new Button(this);
        button3 = new Button(this);
        button4 = new Button(this);

        turnCardButton = new Button(this);
        turnCardButton.setLayoutParams(buttonParams);
        turnCardButton.setText("Turn Around");
        turnCardButton.setTextSize(20);
        turnCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnCard(flashcards.get(idCurrentCard));
            }
        });
        buttonLayout.addView(turnCardButton);

    }
    public void back (View view){
        this.finish();
        return;
    }

    private void setTextViews(Flashcard card){
        this.title.setText(card.getTitle());
        this.question.setText(Html.fromHtml(TextFormatter.highlight(this,card.getQuestion())));
    }
    private void turnCard(Flashcard card){
        this.question.setText(Html.fromHtml(TextFormatter.highlight(this,card.getAnswer())));
        //this.title.setText("");
        buttonLayout.removeAllViews();

        button1 = new Button(this);
        button1.setLayoutParams(buttonParams);
        button1.setText("1");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(-1);
            }
        });

        button2 = new Button(this);
        button2.setLayoutParams(buttonParams);
        button2.setText("2");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(0);
            }
        });

        button3 = new Button(this);
        button3.setLayoutParams(buttonParams);
        button3.setText("3");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(1);
            }
        });

        button4 = new Button(this);
        button4.setLayoutParams(buttonParams);
        button4.setText("4");
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(2);
            }
        });

        buttonLayout.addView(button1);
        buttonLayout.addView(button2);
        buttonLayout.addView(button3);
        buttonLayout.addView(button4);
    }
    private void newCard(int result){
// Set the score and the next study time
        int newScore =flashcards.get(idCurrentCard).getScore()+result;
        if(newScore<0){
            newScore=0;
        }
        flashcards.get(idCurrentCard).setScore(newScore);
        flashcards.get(idCurrentCard).setLearnNextTime(timestamp.getTime()+MILLISDAY*((result+1)*(flashcards.get(idCurrentCard).getScore()+1)));
        db.flashcardDAO().updateFlashcard(flashcards.get(idCurrentCard));
        if(result>=0){
            flashcards.remove(idCurrentCard);
            if(flashcards.isEmpty()){
                toast =Toast.makeText(getApplicationContext(), toastFinishText, Toast.LENGTH_LONG);
                toast.show();
                finish();
                return; // is necessary to stop that method
            }
        }
// go to the next card and shuffle
        goToNextCard();
    }
    private void goToNextCard(){
    // go to the next card and shuffle
        int idOldCard = idCurrentCard;
        while(idOldCard==idCurrentCard && flashcards.size()!=1){
            idCurrentCard=random.nextInt(flashcards.size());
        }
        if(flashcards.size()==1){
            idCurrentCard=0;
        }
        this.title.setText(flashcards.get(idCurrentCard).getTitle());
        this.question.setText(Html.fromHtml(TextFormatter.highlight(this,flashcards.get(idCurrentCard).getQuestion())));
        buttonLayout.removeAllViews();
        buttonLayout.addView(turnCardButton);
    }

    public void ignoreCard(View view){
        flashcards.get(idCurrentCard).setIgnored(true);
        db.flashcardDAO().updateFlashcard(flashcards.get(idCurrentCard));
        flashcards.remove(idCurrentCard);
        if(flashcards.isEmpty()){
            toast.cancel();
            toast =Toast.makeText(getApplicationContext(), toastFinishText, Toast.LENGTH_LONG);
            toast.show();
            this.finish();
            return; // is necessary to stop that method
        }
        else{
            toast =Toast.makeText(getApplicationContext(),"Flashcard ignored", Toast.LENGTH_SHORT);
            toast.show();
        }
        goToNextCard();
    }

}