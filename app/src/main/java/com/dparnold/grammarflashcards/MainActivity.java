package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dparnold.grammarflashcards.CustomViews.PackageView;
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
// Variables
    List<String> packagesNames = Arrays.asList("Beginner", "Intermediate", "Advanced");
    ArrayList<FlashcardPackage> packages=new ArrayList<>();
// Views
    private TextView textOutput;
    private LinearLayout mainLinearLayout;
    private com.dparnold.grammarflashcards.AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Get views
        mainLinearLayout=findViewById(R.id.packageLinearLayout);
// Get flashcards
        for(int i=0;i<packagesNames.size();i++){
            mainLinearLayout.addView(new PackageView(this,packagesNames.get(i),33));
            packages.add(new FlashcardPackage(this,packagesNames.get(i)));
        }

        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
        db.flashcardDAO().nukeTable();
        if(db.flashcardDAO().getAll().isEmpty()){
            db.flashcardDAO().insertAll(FlashcardPackage.readPackage(this,"Beginner")); // read new
        }


    }
    public void learn (View view){startActivity(new Intent(MainActivity.this, Learning.class));
    }
    public void showall(View view){startActivity(new Intent(MainActivity.this, AllFlashcards.class));}
}
