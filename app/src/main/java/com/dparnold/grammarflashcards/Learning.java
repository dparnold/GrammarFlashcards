package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dparnold.grammarflashcards.Helper.TextFormatter;

import java.util.ArrayList;
import java.util.List;

public class Learning extends AppCompatActivity {

    private AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();
    private Flashcard currentCard;
    private int id = 0;

    private Button turnCardButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private TextView title;
    private TextView question;
    private LinearLayout buttonLayout;
    private LinearLayout.LayoutParams buttonParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

// Get Views
        title = findViewById(R.id.title);
        question = findViewById(R.id.question);
        buttonLayout = findViewById(R.id.buttonLayout);;
// Get database and read flashcards
        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
        flashcards = db.flashcardDAO().getAll();
        currentCard = flashcards.get(0);
        setTextViews(currentCard);
// Create Buttons
        buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        button1 = new Button(this);
        button2 = new Button(this);
        button3 = new Button(this);
        button4 = new Button(this);

        turnCardButton = new Button(this);
        turnCardButton.setLayoutParams(buttonParams);
        turnCardButton.setText("turn around");
        turnCardButton.setTextSize(20);
        turnCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnCard(currentCard);
            }
        });
        buttonLayout.addView(turnCardButton);

    }
    public void back (View view){
        this.finish();
    }

    private void setTextViews(Flashcard card){
        this.title.setText(card.getTitle());
        this.question.setText(Html.fromHtml(TextFormatter.highlight(this,card.getQuestion())));
    }
    private void turnCard(Flashcard card){
        this.question.setText(Html.fromHtml(TextFormatter.highlight(this,card.getAnswer())));
        this.title.setText("");
        buttonLayout.removeAllViews();

        button1 = new Button(this);
        button1.setLayoutParams(buttonParams);
        button1.setText("1");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard();
            }
        });

        button2 = new Button(this);
        button2.setLayoutParams(buttonParams);
        button2.setText("2");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard();
            }
        });

        button3 = new Button(this);
        button3.setLayoutParams(buttonParams);
        button3.setText("3");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard();
            }
        });

        button4 = new Button(this);
        button4.setLayoutParams(buttonParams);
        button4.setText("4");
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard();
            }
        });

        buttonLayout.addView(button1);
        buttonLayout.addView(button2);
        buttonLayout.addView(button3);
        buttonLayout.addView(button4);
    }
    private void newCard(){
        id+=1;
        currentCard=flashcards.get(id);
        this.title.setText(currentCard.getTitle());
        this.question.setText(currentCard.getQuestion());
        buttonLayout.removeAllViews();
        buttonLayout.addView(turnCardButton);
    }
}