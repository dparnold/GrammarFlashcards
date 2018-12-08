package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dparnold.grammarflashcards.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private com.dparnold.grammarflashcards.AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
        if(db.flashcardDAO().getAll().isEmpty()){
            Flashcard card1 = new Flashcard(1, "por or para?", "Por qué?", "porque!");
            Flashcard card2 = new Flashcard(2, "Presente o Subjuntivo", "Warum?", "darum!");
            Flashcard card3 = new Flashcard(3, "Masculino of Femenino", "hombre", "hembra!");
            flashcards.add(card1);
            flashcards.add(card2);
            flashcards.add(card3);
            db.flashcardDAO().insertAll(flashcards);
        }
    }
    public void learn (View view){
        startActivity(new Intent(MainActivity.this, Learning.class));
    }
}
