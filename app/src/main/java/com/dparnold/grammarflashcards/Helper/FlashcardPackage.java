package com.dparnold.grammarflashcards.Helper;

import android.content.Context;

import com.dparnold.grammarflashcards.CustomViews.PackageView;
import com.dparnold.grammarflashcards.Flashcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlashcardPackage {
// Attributes
    private List<Flashcard> flashcards;
    private String name;
    private int numberOfCards;
    private PackageView packageView;
    private Context context;
    public FlashcardPackage(Context context,List<Flashcard> flashcards, String name){
        this.flashcards=flashcards;
        this.name=name;
        this.context=context;
        initialize();
    }
    public FlashcardPackage(Context context, String name){
        this.name=name;
        this.context=context;
        this.flashcards=FlashcardPackage.readPackage(context,name);
        initialize();
    }
// Methods

    private void initialize(){
        this.numberOfCards=flashcards.size();

    }

    public static ArrayList<Flashcard> readPackage(Context context, String name){
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(name)));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            br.close();
        }
        catch (IOException e) {
            // error handling
        }
        ArrayList<Flashcard> cardPackage =new ArrayList<>();
        ArrayList<String> splitted = new ArrayList<>(Arrays.asList(text.toString().split("<card>"))); // Important to create a new Arraylist
        splitted.remove(""); // Arrays.asList(text.toString().split("<card>")) the array is still referenced and remove causes an error


        for(int i=0;i<splitted.size();i++){
            Flashcard newCard = new Flashcard();
            String[] splitted2 =splitted.get(i).split("\n    <.*>|\n\t<.*>|\n<.*>|<.*>"); //Split by the tags either with newline+ spaces or newline + tab or only tag
            newCard.setTitle(splitted2[1]);
            newCard.setQuestion(splitted2[2]);
            newCard.setAnswer(splitted2[3]);
            newCard.setPackageName(name);
            cardPackage.add(newCard);
        }
        return cardPackage;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
