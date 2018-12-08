package com.dparnold.grammarflashcards;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Flashcard {
    @PrimaryKey
    private int ID;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answer")
    private String answer;

    @ColumnInfo(name = "timesstudied")
    private int timesStudied;

    @ColumnInfo(name = "tostudy")
    private boolean tostudy = true;

    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "learnNextTime")
    private long learnNextTime;


    public Flashcard(int ID, String title, String question, String answer) {
        this.ID = ID;
        this.title=title;
        this.question=question;
        this.answer = answer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTimesStudied() {
        return timesStudied;
    }

    public void setTimesStudied(int timesStudied) {
        this.timesStudied = timesStudied;
    }

    public boolean isTostudy() {
        return tostudy;
    }

    public void setTostudy(boolean tostudy) {
        this.tostudy = tostudy;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getLearnNextTime() {
        return learnNextTime;
    }

    public void setLearnNextTime(long learnNextTime) {
        this.learnNextTime = learnNextTime;
    }
}