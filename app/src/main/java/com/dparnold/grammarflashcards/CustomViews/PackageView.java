package com.dparnold.grammarflashcards.CustomViews;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.graphics.PorterDuff;
import android.widget.TextView;

import com.dparnold.grammarflashcards.Learning;
import com.dparnold.grammarflashcards.MainActivity;
import com.dparnold.grammarflashcards.R;

public class PackageView extends RelativeLayout {
// Views
    private ProgressBar progressBar;
    private TextView titleTextView;
    private TextView cardsStudiedTextView;
    private Button reviewButton;
// Attributes
    private Context context;
    private String title;
    private int progress =33;
    private int numberOfCards=0;
    private int cardsStudied=3;
// Constructors
    public PackageView(Context context,String title) {
        super(context);
        this.context=context;
        this.title=title;
        initiate();

    }
// Methods
    private void initiate(){

        inflate(getContext(), R.layout.package_view, this);
        progressBar=findViewById(R.id.progress);
        progressBar.getProgressDrawable().setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null), PorterDuff.Mode.MULTIPLY);
        titleTextView=findViewById(R.id.title);
        cardsStudiedTextView=findViewById(R.id.progressText);
        reviewButton=findViewById(R.id.reviewButton);
        reviewButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            // Handing over the package name when the button is pressed
                Intent learningIntent = new Intent(context, Learning.class);
                learningIntent.putExtra("packageName",getTitle());
                context.startActivity(learningIntent);
            }
        });
        update();

    }
    private void update(){
        if(numberOfCards==0){
            progress=0;
        }
        else{
            progress=(int)(100*(((double)cardsStudied/numberOfCards)));
        }
        cardsStudiedTextView.setText(Integer.toString(cardsStudied)+ " of "+Integer.toString(numberOfCards)+" flashcards studied");
        progressBar.setProgress(this.progress);
        titleTextView.setText(this.title);
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
        update();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
