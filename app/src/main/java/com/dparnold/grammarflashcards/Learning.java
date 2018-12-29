package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
// ImageButtons
    private Button turnCardButton;
    private ImageButton ImageButton1;
    private ImageButton ImageButton2;
    private ImageButton ImageButton3;
    private ImageButton ImageButton4;
// Views
    private TextView title;
    private TextView question;
    private LinearLayout ImageButtonLayout;
    private LinearLayout.LayoutParams imageButtonParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
// Getting a timestamp for the current session
        timestamp = new Timestamp(System.currentTimeMillis());
// Get views
        title = findViewById(R.id.title);
        question = findViewById(R.id.question);
        ImageButtonLayout = findViewById(R.id.ImageButtonLayout);

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
// Create ImageButtons
        imageButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        imageButtonParams.weight=1;
        
        ImageButton1 = new ImageButton(this);
        ImageButton2 = new ImageButton(this);
        ImageButton3 = new ImageButton(this);
        ImageButton4 = new ImageButton(this);

        LinearLayout.LayoutParams turnCardButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //imageButtonParams.weight=1;
        turnCardButton = new Button(this);
        turnCardButton.setLayoutParams(turnCardButtonParams);
        turnCardButton.setText("Turn Around");
        turnCardButton.setTextSize(20);
        turnCardButton.setBackgroundColor(getResources().getColor(R.color.colorBar));
        turnCardButton.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_turn, 0, 0, 0);
        turnCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnCard(flashcards.get(idCurrentCard));
            }
        });

        ImageButtonLayout.addView(turnCardButton);

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
        ImageButtonLayout.removeAllViews();

        ImageButton1 = new ImageButton(this);
        ImageButton1.setLayoutParams(imageButtonParams);
        ImageButton1.setImageResource(R.drawable.ic_1);
        ImageButton1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton1.setBackgroundColor(getResources().getColor(R.color.colorBar));
        ImageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(-1);
            }
        });


        ImageButton2 = new ImageButton(this);
        ImageButton2.setLayoutParams(imageButtonParams);
        ImageButton2.setImageResource(R.drawable.ic_2);
        ImageButton2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton2.setBackgroundColor(getResources().getColor(R.color.colorBar));
        ImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(0);
            }
        });

        ImageButton3 = new ImageButton(this);
        ImageButton3.setLayoutParams(imageButtonParams);
        ImageButton3.setImageResource(R.drawable.ic_3);
        ImageButton3.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton3.setBackgroundColor(getResources().getColor(R.color.colorBar));
        ImageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(1);
            }
        });

        ImageButton4 = new ImageButton(this);
        ImageButton4.setLayoutParams(imageButtonParams);
        ImageButton4.setImageResource(R.drawable.ic_4);
        ImageButton4.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageButton4.setBackgroundColor(getResources().getColor(R.color.colorBar));
        ImageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCard(2);
            }
        });

        View line = new View(this);
        line.setLayoutParams(new LinearLayout.LayoutParams(1,LinearLayout.LayoutParams.MATCH_PARENT));
        line.setBackgroundColor(getResources().getColor(R.color.colorTextWeak));

        View line2 = new View(this);
        line2.setLayoutParams(new LinearLayout.LayoutParams(1,LinearLayout.LayoutParams.MATCH_PARENT));
        line2.setBackgroundColor(getResources().getColor(R.color.colorTextWeak));

        View line3 = new View(this);
        line3.setLayoutParams(new LinearLayout.LayoutParams(1,LinearLayout.LayoutParams.MATCH_PARENT));
        line3.setBackgroundColor(getResources().getColor(R.color.colorTextWeak));


        ImageButtonLayout.addView(ImageButton1);
        ImageButtonLayout.addView(line);
        ImageButtonLayout.addView(ImageButton2);
        ImageButtonLayout.addView(line2);
        ImageButtonLayout.addView(ImageButton3);
        ImageButtonLayout.addView(line3);
        ImageButtonLayout.addView(ImageButton4);
    }
    private void newCard(int result){
// Set the score and the next study time
        int newScore =flashcards.get(idCurrentCard).getScore()+result;
        if(newScore<0){
            newScore=0;
        }
        flashcards.get(idCurrentCard).setScore(newScore);
        flashcards.get(idCurrentCard).setLearnNextTime(timestamp.getTime()+MILLISDAY*((result+1)*(flashcards.get(idCurrentCard).getScore()+1))/2);
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
        ImageButtonLayout.removeAllViews();
        ImageButtonLayout.addView(turnCardButton);
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