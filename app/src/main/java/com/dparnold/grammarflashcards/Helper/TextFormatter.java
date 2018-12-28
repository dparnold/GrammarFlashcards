package com.dparnold.grammarflashcards.Helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.dparnold.grammarflashcards.R;

public class TextFormatter {
        public static String highlight(Context context, String inputString){ //context is needed to access the color
            String colorAccent = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorTextAccent) & 0x00ffffff);
            String colorAccent2 = "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorTextAccent2) & 0x00ffffff);
            String colorTextWeak ="#" + Integer.toHexString(ContextCompat.getColor(context, R.color.colorTextWeak) & 0x00ffffff);
            String formattedString="";
            // Order of replacing is important because of the # in the colors
            formattedString= TextFormatter.replaceByTag(inputString,"#","<font color=\""+colorAccent2+"\">","</font>");
            formattedString = TextFormatter.replaceByTag(formattedString,"\\*\\*","<b>","</b>");
            formattedString = TextFormatter.replaceByTag(formattedString,"\\*","<font color=\""+colorAccent+"\">","</font>");
            formattedString = formattedString.replace("(","<font color=\""+colorTextWeak+"\">- ");
            formattedString = formattedString.replace(")","</font>");
            formattedString = formattedString.replace("[","(");
            formattedString = formattedString.replace("]",")");
            formattedString = formattedString.replace("\n","<br>");
            return formattedString;
        }
        private static String replaceByTag (String inputString, String markup, String firstTag, String secondTag){
            boolean first = true;
            String outputString="";
            String[] splitted = inputString.split(markup); // need to escape meta character in regex
            for(int i=0; i< splitted.length; i++){
                if (i == splitted.length-1){
                    outputString=outputString+splitted[i];
                    return outputString;
                }
                if(first){
                    outputString = outputString+splitted[i]+firstTag;
                    first=!first;
                }
                else{
                    outputString=outputString+splitted[i]+secondTag;
                    first=!first;
                }
            }
            return outputString;
        }
}
