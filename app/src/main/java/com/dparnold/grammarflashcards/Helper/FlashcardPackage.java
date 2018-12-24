package com.dparnold.grammarflashcards.Helper;

import android.content.Context;

import com.dparnold.grammarflashcards.Flashcard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlashcardPackage {
    private ArrayList<Flashcard> flashcards;
    private String name;

    public FlashcardPackage(ArrayList<Flashcard> flashcards, String name){
        this.flashcards=flashcards;
        this.name=name;
    }
    public FlashcardPackage(Context context, String name){
        this.name=name;
        this.flashcards=FlashcardPackage.readPackage(context,name);
    }
// Methods
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
            newCard.setLevel(Integer.parseInt(splitted2[2]));
            newCard.setQuestion(splitted2[3]);
            newCard.setAnswer(splitted2[4]);
            newCard.setPackageName(name);
            cardPackage.add(newCard);
        }
        return cardPackage;
    }


    
}
