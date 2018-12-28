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

import com.dparnold.grammarflashcards.AllFlashcards;
import com.dparnold.grammarflashcards.Learning;
import com.dparnold.grammarflashcards.MainActivity;
import com.dparnold.grammarflashcards.R;

public class PackageView extends RelativeLayout {
// Views
    private ProgressBar progressBar;
    private TextView titleTextView;
    private TextView cardsStudiedTextView;
    private TextView toReview;
    private Button reviewButton;
// Attributes
    private Context context;
    private String title;
    private int progress =33;
    private int numberOfCards=33;
    private int cardsStudied=33;
    private int cardsToReview=33;
    private int cardsIgnored=33;
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
        toReview=findViewById(R.id.toReview);
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
        titleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handing over the package name when the textView is pressed
                Intent viewPackageIntent = new Intent(context, AllFlashcards.class);
                viewPackageIntent.putExtra("packageName",getTitle());
                context.startActivity(viewPackageIntent);
            }
        });
        update();

    }
    public void update(){
        if(numberOfCards==0){
            progress=0;
        }
        else{
            progress=(int)(100*(((double)cardsStudied/numberOfCards)));
        }
        if(cardsToReview==0 && numberOfCards!=cardsStudied){
            reviewButton.setText("Learn new cards");
        }
        else{
            reviewButton.setText("Review cards");
        }
        cardsStudiedTextView.setText(Integer.toString(cardsStudied)+ " of "+Integer.toString(numberOfCards)+" cards studied");
        toReview.setText(Integer.toString(cardsIgnored)+" cards ignored\n"+Integer.toString(cardsToReview)+" cards to review");
        progressBar.setProgress(this.progress);
        titleTextView.setText(this.title);
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCardsStudied() {
        return cardsStudied;
    }

    public void setCardsStudied(int cardsStudied) {
        this.cardsStudied = cardsStudied;
    }

    public int getCardsToReview() {
        return cardsToReview;
    }

    public void setCardsToReview(int cardsToReview) {
        this.cardsToReview = cardsToReview;
    }

    public int getCardsIgnored() {
        return cardsIgnored;
    }

    public void setCardsIgnored(int cardsIgnored) {
        this.cardsIgnored = cardsIgnored;
    }
}
