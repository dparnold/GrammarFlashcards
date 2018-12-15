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

    public static List<Flashcard> readPackage(Context context){
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("package1")));
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
        List<Flashcard> cardPackage =new ArrayList<>();
        List<String> splitted = new ArrayList<>(Arrays.asList(text.toString().split("<card>"))); // Important to create a new Arraylist
        splitted.remove(""); // Arrays.asList(text.toString().split("<card>")) the array is still referenced and remove causes an error


        for(int i=0;i<splitted.size();i++){
            Flashcard newCard = new Flashcard();
            String[] splitted2 =splitted.get(i).split("\n    <.*>|\n\t<.*>|<.*>"); //Split by the tags either with newline+ spaces or newline + tab or only tag
            newCard.setID(Integer.parseInt(splitted2[1]));
            newCard.setTitle(splitted2[2]);
            newCard.setLevel(Integer.parseInt(splitted2[3]));
            newCard.setQuestion(splitted2[4]);
            newCard.setAnswer(splitted2[5]);
            cardPackage.add(newCard);
        }
        return cardPackage;
    }




    public static List<Flashcard> get2Package(){
        List<Flashcard> flashcardPackage=new ArrayList<Flashcard>();
        flashcardPackage.add(new Flashcard(1,
                "Adjectives",
                "Which adjective endings agree with both masculine and feminine and therfore only change for number?",
                "Adjectives that end in *-e* or -*ista*\n\n " +
                        "Tengo un abuelo interesant*e*.\n" +
                        "Mi abuela es interessant*e*.\n" +
                        "Mi profesor/profesor#a# es muy ideal*ista*."));
        flashcardPackage.add(new Flashcard(2,
                "Adjectives",
                "What happens to adjectives that end in -z in singular?",
                "*-z* changes to #-c# in plural\n\n " +
                        "El gato es feli*z*.\n" +
                        "Los gatos son feli#c#*es*.\n"));
        flashcardPackage.add(new Flashcard(3,
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
        flashcardPackage.add(new Flashcard(4,
                "Adjectives",
                "General rule for adjective placement?",
                "In general *adjectives* **follow** the #noun#.\n\n" +
                        "Me gustan las #flores# *rojas*."));
        flashcardPackage.add(new Flashcard(5,
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
        return flashcardPackage;
    }
    
}
