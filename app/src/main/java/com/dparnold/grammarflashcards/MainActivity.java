package com.dparnold.grammarflashcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dparnold.grammarflashcards.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private com.dparnold.grammarflashcards.AppDatabase db;
    private List<Flashcard> flashcards = new ArrayList<Flashcard>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = com.dparnold.grammarflashcards.AppDatabase.getAppDatabase(this);
        db.flashcardDAO().nukeTable();
        if(db.flashcardDAO().getAll().isEmpty()){

            flashcards.add(new Flashcard(1,
                    "Adjectives",
                    "Which adjective endings agree with both masculine and feminine and therfore only change for number?",
                    "Adjectives that end in *-e* or -*ista*\n\n " +
                            "Tengo un abuelo interesant*e*.\n" +
                            "Mi abuela es interessant*e*.\n" +
                            "Mi profesor/profesor#a# es muy ideal*ista*."));
            flashcards.add(new Flashcard(2,
                    "Adjectives",
                    "What happens to adjectives that end in -z in singular?",
                    "*-z* changes to #-c# in plural\n\n " +
                            "El gato es feli*z*.\n" +
                            "Los gatos son feli#c#*es*.\n"));
            flashcards.add(new Flashcard(3,
                    "Adjectives",
                    "Which adjectives end in consonants but still have feminine forms?\n" +
                            "What is an exception to that rule?",
                    "Adjectives that end in *-or*, *-ón* or *-ín*\n\n " +
                            "Mi herman*o* es trabajad*or*.\n" +
                            "Mi herman#a# es trabajad*or*#a#.\n" +
                            "Mis herman*os* son trabajador*es*\n" +
                            "Mis herman#as# son trabajador#as#\n\n" +
                            "**Exception**: Adjectives that end in *-erior*\n\n" +
                            "Está en #la parte# *posterior* del edificio."));
            flashcards.add(new Flashcard(4,
                    "Adjectives",
                    "General rule for adjective placement?",
                    "In general *adjectives* **follow** the #noun#.\n\n" +
                            "Me gustan las #flores# *rojas*."));
            flashcards.add(new Flashcard(5,
                    "Adjectives",
                    "When do you put adjectives in front of the noun (4 cases)?",
                    "**1. Descriptive adjectives that emphasize an essential quality of a noun**\n\n " +
                            "El *valiente* #león# protege su territorio.\n" +
                            "La *dulce* #miel# es deliciosa en pan tostado.\n\n" +
                            "**2. Limiting Adjectives**\n" +
                            "Hay *pocas* #naranjas# este verano.\n" +
                            "Tienes *suficiente* #tiempo#.\n\n" +
                            "**3. Meaning-change Adjectives**\n" +
                            "Pablo es mi *viejo* #amigo#.(long-time friend)\n" +
                            "Juanita es mi #amiga# *vieja*.(elderly friend)\n\n" +
                            "**4. Possessive Adjectives and Demonstrative Adjectives**\n" +
                            "*Mi* #hermana# es alta.\n" +
                            "*Este* #árbol# tiene muchas manzanas."));


            db.flashcardDAO().insertAll(flashcards);
        }
    }
    public void learn (View view){
        startActivity(new Intent(MainActivity.this, Learning.class));
    }
}
