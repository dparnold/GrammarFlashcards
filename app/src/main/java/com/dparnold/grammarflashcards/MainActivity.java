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
    List<PackageView> packageViews = new ArrayList<>();
    ArrayList<FlashcardPackage> packages=new ArrayList<>();
// Views
    private TextView textOutput;
    private LinearLayout mainLinearLayout;
// Others
    private com.dparnold.grammarflashcards.AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Get views
        mainLinearLayout=findViewById(R.id.packageLinearLayout);
// Get flashcards
        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
        db.flashcardDAO().nukeTable();
        int numberOfCardsInPackage;
        for(int i=0;i<packagesNames.size();i++){
        //
            packageViews.add(new PackageView(this,packagesNames.get(i)));
            mainLinearLayout.addView(packageViews.get(i));
            numberOfCardsInPackage=db.flashcardDAO().getNumberOfCards(packagesNames.get(i));

            if(numberOfCardsInPackage==0){
                packages.add(new FlashcardPackage(this,packagesNames.get(i)));
                db.flashcardDAO().insertAll(FlashcardPackage.readPackage(this,packagesNames.get(i))); // read the missing package
            }
            else{
                packages.add(new FlashcardPackage(this,db.flashcardDAO().getAllFromPackage(packagesNames.get(i)),packagesNames.get(i)));
            }
            numberOfCardsInPackage=db.flashcardDAO().getNumberOfCards(packagesNames.get(i));
            packageViews.get(i).setNumberOfCards(numberOfCardsInPackage);
        }


        if(db.flashcardDAO().getAll().isEmpty()){

        }


    }

    public void showall(View view){startActivity(new Intent(MainActivity.this, AllFlashcards.class));}
}
