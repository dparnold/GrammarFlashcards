package com.dparnold.grammarflashcards.CustomViews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.graphics.PorterDuff;
import android.widget.TextView;

import com.dparnold.grammarflashcards.R;

public class PackageView extends RelativeLayout {
// Views
    private ProgressBar progressBar;
    private TextView titleTextView;
// Attributes
    private Context context;
    private String title;
    private int progress;
// Constructors
    public PackageView(Context context,String title,int progress) {
        super(context);
        this.context=context;
        this.title=title;
        this.progress=progress;
        initiate();

    }
// Methods
    private void initiate(){

        inflate(getContext(), R.layout.package_view, this);
        progressBar=findViewById(R.id.progress);
        progressBar.getProgressDrawable().setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null), PorterDuff.Mode.MULTIPLY);
        progressBar.setProgress(this.progress);

        titleTextView=findViewById(R.id.title);
        titleTextView.setText(this.title);

    }

}
