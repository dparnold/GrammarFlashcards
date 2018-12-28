package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dparnold.grammarflashcards.CustomViews.PackageView;
import com.dparnold.grammarflashcards.Helper.FlashcardPackage;
import com.dparnold.grammarflashcards.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
// Variables
    List<String> packagesNames = Arrays.asList("Beginner", "Intermediate", "Advanced", "New Flashcards");
    List<PackageView> packageViews = new ArrayList<>();
    ArrayList<FlashcardPackage> packages=new ArrayList<>();
// Views
    private TextView textOutput;
    private LinearLayout mainLinearLayout;
// Others
    private com.dparnold.grammarflashcards.AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();
    private Timestamp timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Get views
        mainLinearLayout=findViewById(R.id.packageLinearLayout);
// Getting a timestamp for the current session
        timestamp = new Timestamp(System.currentTimeMillis());
// Get flashcards
        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
        //db.flashcardDAO().nukeTable();
        db.flashcardDAO().removePackage("New Flashcards");
        for(int i=0;i<packagesNames.size();i++){
        //
            packageViews.add(new PackageView(this,packagesNames.get(i)));
            mainLinearLayout.addView(packageViews.get(i));


            if(db.flashcardDAO().getNumberOfCards(packagesNames.get(i))==0){
                packages.add(new FlashcardPackage(this,packagesNames.get(i)));
                db.flashcardDAO().insertAll(FlashcardPackage.readPackage(this,packagesNames.get(i))); // read the missing package
            }
            else{
                packages.add(new FlashcardPackage(this,db.flashcardDAO().getAllFromPackage(packagesNames.get(i)),packagesNames.get(i)));
            }

            updatePackage(packagesNames.get(i));
        // Setting the numbers of cards /cards studied
            packageViews.get(i).setNumberOfCards(db.flashcardDAO().getNumberOfCards(packagesNames.get(i)));
            packageViews.get(i).setCardsStudied((db.flashcardDAO().getNumberOfCardsStudied(packagesNames.get(i))));
            packageViews.get(i).setCardsToReview(db.flashcardDAO().getNumberOfCardsToReview(packagesNames.get(i),timestamp.getTime()));
            packageViews.get(i).setCardsIgnored((db.flashcardDAO().getNumberOfCardsIgnored(packagesNames.get(i))));

            packageViews.get(i).update();
        }


        if(db.flashcardDAO().getAll().isEmpty()){

        }
    }
    @Override
    public void onResume(){
        super.onResume();
        updatePackageViews();

    }

    public void showall(View view){startActivity(new Intent(MainActivity.this, AllFlashcards.class));}

    public void updateButtonPressed(View view){
        Intent intent = getIntent();
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Screen updated!", Toast.LENGTH_SHORT);
        toast1.show();
        finish();
        startActivity(intent);

    }
    private void updatePackageViews(){
        for(int i=0;i<packagesNames.size();i++){
            packageViews.get(i).setNumberOfCards(db.flashcardDAO().getNumberOfCards(packagesNames.get(i)));
            packageViews.get(i).setCardsStudied((db.flashcardDAO().getNumberOfCardsStudied(packagesNames.get(i))));
            packageViews.get(i).setCardsToReview(db.flashcardDAO().getNumberOfCardsToReview(packagesNames.get(i),timestamp.getTime()));
            packageViews.get(i).setCardsIgnored((db.flashcardDAO().getNumberOfCardsIgnored(packagesNames.get(i))));
            packageViews.get(i).update();
        }
    }

    private void updatePackage (String packageName){
        List<Flashcard> cardsFromPackage = FlashcardPackage.readPackage(this,packageName);
        List<Flashcard> cardsFromDatabase = this.db.flashcardDAO().getAllFromPackage(packageName);


        if(cardsFromPackage.size()> cardsFromDatabase.size()){
            for(int i=0;i<cardsFromDatabase.size();i++){
                int j = 0;
                while(j<cardsFromPackage.size()){
                    // Very important == tests for reference equality .equals() tests for the values
                    if(cardsFromDatabase.get(i).getQuestion().equals(cardsFromPackage.get(j).getQuestion())
                            ||cardsFromDatabase.get(i).getAnswer().equals(cardsFromPackage.get(j).getAnswer())){
                        cardsFromPackage.remove(j);
                        break;
                    }
                    j=j+1;
                }
            }
            this.db.flashcardDAO().insertAll(cardsFromPackage);
        }
    }
}
