package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dparnold.grammarflashcards.Helper.FlashcardPackage;
import com.dparnold.grammarflashcards.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
// Views
    private TextView textOutput;
    private com.dparnold.grammarflashcards.AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Get views
        textOutput=findViewById(R.id.textOutput);
        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
        db.flashcardDAO().nukeTable();
        if(db.flashcardDAO().getAll().isEmpty()){
            db.flashcardDAO().insertAll(FlashcardPackage.readPackage(this)); // read new
        }


        textOutput.setText("well done!");
    }
    public void learn (View view){startActivity(new Intent(MainActivity.this, Learning.class));
    }
    public void showall(View view){startActivity(new Intent(MainActivity.this, AllFlashcards.class));}
}
