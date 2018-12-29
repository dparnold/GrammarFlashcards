package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dparnold.grammarflashcards.Helper.FlashcardPackage;
import com.dparnold.grammarflashcards.Helper.TextFormatter;

import java.util.List;

public class AllFlashcards extends AppCompatActivity {
    private com.dparnold.grammarflashcards.AppDatabase db;
    LinearLayout linearLayout;
    String flashcardText="";
    String packageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_flashcards);
// get views
        linearLayout = findViewById(R.id.linearLayout);
        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
//        db.flashcardDAO().nukeTable();
//        if(db.flashcardDAO().getAll().isEmpty()){
//            db.flashcardDAO().insertAll(FlashcardPackage.readPackage(this,"Beginner")); // read new
//        }
        Intent myIntent = getIntent(); // gets the previously created intent
        packageName = myIntent.getStringExtra("packageName");
        List<Flashcard> allFlashcards= db.flashcardDAO().getAllFromPackage(packageName);


        for(int i=0;i<allFlashcards.size();i++){
// create Views to add
            TextView newText = new TextView(this);
            View line = new View(this);
            line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5));
            line.setBackgroundColor(getResources().getColor(R.color.colorAccent));
// getting the data for the Views
            flashcardText = "\n"+ allFlashcards.get(i).getTitle()+"\n\n"+allFlashcards.get(i).getQuestion()+"\n\n"+ allFlashcards.get(i).getAnswer();
            newText.setText(Html.fromHtml(TextFormatter.highlight(this,flashcardText)));
// adding the Views to the linearLayout
            linearLayout.addView(newText);
            linearLayout.addView(line);
        }

    }
}
